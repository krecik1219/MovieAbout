package pl.pisquared.movieabout;

import android.content.Intent;
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
    private RecyclerView rvMoviesList;
    private MovieListAdapter movieListAdapter;
    private List<Movie> moviesSampleData;
    public static final String MOVIE_ID = "movie_id";

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
