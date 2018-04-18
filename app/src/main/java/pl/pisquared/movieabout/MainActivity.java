package pl.pisquared.movieabout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

import pl.pisquared.movieabout.data.SampleMoviesDataProvider;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.OnListItemClickListener
{
    // private static final String TAG = MainActivity.class.getSimpleName();  // for debug logs
    public static final String MOVIE_ID = "movie_id";
    private static final String COLOR_DELETE_BACKGROUND = "#cc0000";
    private RecyclerView rvMoviesList;
    private MovieListAdapter movieListAdapter;
    private List<Movie> moviesSampleData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesSampleData = SampleMoviesDataProvider.getInstance(this).getSampleMovieData();

        rvMoviesList = findViewById(R.id.rv_movies_list);
        rvMoviesList.setLayoutManager(new LinearLayoutManager(this));
        movieListAdapter = new MovieListAdapter(moviesSampleData, this);
        rvMoviesList.setAdapter(movieListAdapter);

        initSwipeCallback();
    }

    private void initSwipeCallback()
    {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                movieListAdapter.removeItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
            {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                Bitmap icon = null;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    Paint p = new Paint();
                    p.setColor(Color.parseColor(COLOR_DELETE_BACKGROUND));
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                    RectF background = null;
                    RectF icon_dest = null;
                    if(dX > 0){
                        background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                    } else {
                        background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                    }
                    c.drawRect(background,p);
                    c.drawBitmap(icon,null,icon_dest,p);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallBack);
        itemTouchHelper.attachToRecyclerView(rvMoviesList);
    }

    @Override
    public void onListItemClick(View view, int position)
    {
        Intent launchDetails = new Intent(this, DetailsActivity.class);
        Movie movie = movieListAdapter.getMovie(position);
        launchDetails.putExtra(MOVIE_ID, movie.getId());
        startActivity(launchDetails);
    }
}
