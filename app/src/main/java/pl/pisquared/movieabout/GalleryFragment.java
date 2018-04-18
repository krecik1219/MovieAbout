package pl.pisquared.movieabout;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import pl.pisquared.movieabout.utils.SampleMoviesDataProvider;

public class GalleryFragment extends Fragment
{
    private GridView gvGallery;
    private Movie currentMovie;
    private List<BitmapWrapper> moviesImages;
    private GalleryItemClickListener galleryItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        int movieId = args.getInt(MainActivity.MOVIE_ID);
        SampleMoviesDataProvider dataProvider = SampleMoviesDataProvider.getInstance(getContext());
        currentMovie = dataProvider.getMovieById(movieId);
        moviesImages = dataProvider.getMovieImages(getContext(), currentMovie.getId());
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            galleryItemClickListener = (GalleryItemClickListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException("Activity that uses GalleryFragment must implement GalleryFragment.GalleryItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.gallery_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        gvGallery = view.findViewById(R.id.gv_movie_gallery);
        GalleryAdapter galleryAdapter = new GalleryAdapter(getContext(), moviesImages);
        gvGallery.setAdapter(galleryAdapter);
        gvGallery.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                galleryItemClickListener.onItemClick(view, (int)id);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }

    public interface GalleryItemClickListener
    {
        void onItemClick(View view, int imageResId);
    }
}
