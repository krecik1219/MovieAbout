package pl.pisquared.movieabout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import pl.pisquared.movieabout.utils.SampleMoviesDataProvider;

public class CastFragment extends Fragment
{
    private ListView lvCastList;
    private int movieId;
    private List<Actor> cast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        movieId = args.getInt(MainActivity.MOVIE_ID);
        SampleMoviesDataProvider dataProvider = SampleMoviesDataProvider.getInstance(getContext());
        cast = dataProvider.getMovieCast(getContext(), movieId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.casts_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        lvCastList = view.findViewById(R.id.lv_actors_list);

        CastListAdapter adapter = new CastListAdapter(getContext(), R.layout.cast_list_item, cast);
        lvCastList.setAdapter(adapter);

    }
}
