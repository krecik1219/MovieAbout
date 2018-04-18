package pl.pisquared.movieabout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import pl.pisquared.movieabout.data.SampleMoviesDataProvider;
import pl.pisquared.movieabout.utils.BitmapUtils;

public class DetailsActivity extends AppCompatActivity implements DialogInterface.OnDismissListener, GalleryFragment.GalleryItemClickListener
{
    private static final String TAG = DetailsActivity.class.getSimpleName();  // for debug logs
    private static final int FRAGMENTS_COUNT = 2;
    private static final String GALLERY_PAGE_TITLE = "Gallery";
    private static final String CAST_PAGE_TITLE = "Cast";
    private static final double IMAGE_DOWNSIZE_FACTOR = 0.95;
    private ImageView ivMainImage;
    private TextView tvDetailMovieTitle;
    private TextView tvDetailCategory;
    private ViewPager vpFragmentHolder;
    private TabLayout tabs;
    private Movie currentMovie;
    private final ImageOnClickListener imageOnClickListener = new ImageOnClickListener();
    private Dialog imageDialog;
    private Bitmap popupImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Intent launchingIntent = getIntent();
        int movieId = launchingIntent.getIntExtra(MainActivity.MOVIE_ID, 0);
        currentMovie = SampleMoviesDataProvider.getInstance(this).getMovieById(movieId);

        initViews();
        initDetailPage();
    }

    public void initViews()
    {
        ivMainImage = findViewById(R.id.iv_main_image);
        tvDetailMovieTitle = findViewById(R.id.tv_detail_movie_title);
        tvDetailCategory = findViewById(R.id.tv_detail_category);
        tabs = findViewById(R.id.tabs_switch);
        vpFragmentHolder = findViewById(R.id.vp_fragment_holder);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        vpFragmentHolder.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(vpFragmentHolder);
        ivMainImage.post(new Runnable()
        {
            @Override
            public void run()
            {
                loadMainImage();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        dismissImageDialog();
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        if(popupImage!=null)
            popupImage.recycle();
    }

    @Override
    public void onItemClick(View view, int imageResId)
    {
        imageOnClickListener.onImageClick(imageResId);
    }

    private void initDetailPage()
    {
        tvDetailMovieTitle.setText(currentMovie.getTitle());
        tvDetailCategory.setText(currentMovie.getCategoryName());
        getWindow().getDecorView().getRootView().setBackgroundColor(currentMovie.getCategoryColor());
    }

    private void loadMainImage()
    {
        SampleMoviesDataProvider dataProvider = SampleMoviesDataProvider.getInstance(DetailsActivity.this);
        final BitmapWrapper imageWrapper = dataProvider.getMovieMainImage(this, currentMovie.getId(), ivMainImage.getWidth(), ivMainImage.getHeight());
        ivMainImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                imageOnClickListener.onImageClick(imageWrapper.getImageResId());
            }
        });
        ivMainImage.setImageBitmap(imageWrapper.getImage());
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        private ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = null;
            Bundle args = new Bundle();
            args.putInt(MainActivity.MOVIE_ID, currentMovie.getId());
            switch(position)
            {
                case 0:
                    fragment = new GalleryFragment();
                    break;
                case 1:
                    fragment = new CastFragment();
                    break;
                default:
                    fragment = new GalleryFragment();
                    break;
            }
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount()
        {
            return FRAGMENTS_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            String tabTitle = null;
            switch(position)
            {
                case 0:
                    tabTitle = GALLERY_PAGE_TITLE;
                    break;
                case 1:
                    tabTitle = CAST_PAGE_TITLE;
                    break;
                default:
                    tabTitle = GALLERY_PAGE_TITLE;
                    break;
            }
            return tabTitle;
        }
    }

    public class ImageOnClickListener
    {

        public void onImageClick(int imageResId)
        {
            View layout = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.image_display_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
            builder.setView(layout);
            imageDialog = builder.create();
            imageDialog.setCanceledOnTouchOutside(true);
            ImageView ivEnlargedImage = layout.findViewById(R.id.iv_enlarged_image);
            ivEnlargedImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dismissImageDialog();
                }
            });
            imageDialog.show();
            setImageInDialog(imageDialog, ivEnlargedImage, imageResId);
        }
    }

    private void dismissImageDialog()
    {
        if(imageDialog!=null && imageDialog.isShowing())
            imageDialog.dismiss();
    }

    private void setImageInDialog(Dialog imageDialog, ImageView imageInDialog, int imageResId)
    {
        if(popupImage != null)
            popupImage.recycle();
        int activityWidth = getUsableScreenWidth();
        int activityHeight = getUsableScreenHeight();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;  // prevent automatic density scaling, depends on drawable folder ldpi, mdpi etc. giving wrong sizes when set to true
        options.inJustDecodeBounds = true;  // only the dimensions are necessary, image is needless
        BitmapFactory.decodeResource(getResources(), imageResId, options);  // after that line the options object will be modified to fit real image dimensions
        int initialWidth = options.outWidth;  // read real image width and height
        int initialHeight = options.outHeight;
        double proportion = ((double) initialWidth)/initialHeight;
        int imageWidth = activityWidth;
        int imageHeight = activityHeight;
        if(activityHeight >= activityWidth)  // adjust right dimension to the other, depending on which is larger
        {
            imageHeight = (int) (imageWidth / proportion);
        }
        else
        {
            imageWidth = (int) (imageHeight * proportion);
        }
        popupImage = BitmapUtils.decodeSampledBitmapFromResource(getResources(), imageResId, imageWidth, imageHeight);
        imageInDialog.setImageBitmap(popupImage);  // cause imageView fitXY property image will be scaled up to fit the view if needed
        Log.d(TAG, "activity height: "+ activityHeight + " activity width: "+activityWidth);
        Log.d(TAG, "provided height (proportion adjusted): "+ imageHeight + " provided width (proportion adjusted): "+imageWidth);
        Log.d(TAG, "image not scaled height: "+ initialHeight + " image not scaled width: "+initialWidth);
        Log.d(TAG, "out height: "+ popupImage.getHeight() + " out width: "+popupImage.getWidth());

        // get nice imageDialog dimensions
        while(imageWidth >= activityWidth || imageHeight >= activityHeight)
        {
            imageWidth *= IMAGE_DOWNSIZE_FACTOR;
            imageHeight *= IMAGE_DOWNSIZE_FACTOR;
       }

        Window dialogWindow = imageDialog.getWindow();

        if(dialogWindow!=null)
            dialogWindow.setLayout(imageWidth, imageHeight);
    }

    private int getUsableScreenHeight()
    {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        Resources res = getResources();
        int currentOrientation = res.getConfiguration().orientation;
        if(currentOrientation == Configuration.ORIENTATION_PORTRAIT)
        {
            int resId = res.getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) {
                screenHeight -= getResources().getDimensionPixelSize(resId);
            }

            TypedValue typedValue = new TypedValue();
            if(getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)){
                screenHeight -= getResources().getDimensionPixelSize(typedValue.resourceId);
            }

            resId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resId > 0) {
                screenHeight -= res.getDimensionPixelSize(resId);
            }
        }

        return screenHeight;
    }

    private int getUsableScreenWidth()
    {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        Resources res = getResources();
        int currentOrientation = res.getConfiguration().orientation;
        if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                screenWidth -= res.getDimensionPixelSize(resourceId);
            }
        }
        return screenWidth;
    }

}
