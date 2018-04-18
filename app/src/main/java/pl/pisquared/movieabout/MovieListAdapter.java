package pl.pisquared.movieabout;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.pisquared.movieabout.data.SampleMoviesDataProvider;

/**
 * Created by Kretek on 02/04/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>
{
    private List<Movie> moviesList;
    private OnListItemClickListener onListItemClickListener;
    private SparseArray<Movie> idMovieDictHelper;

    public MovieListAdapter(List<Movie> moviesList, OnListItemClickListener onListItemClickListener)
    {
        super();
        this.moviesList = moviesList;
        this.onListItemClickListener = onListItemClickListener;
        idMovieDictHelper = SampleMoviesDataProvider.getInstance((Context)onListItemClickListener).getMovieIdDict();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Movie movie = moviesList.get(position);
        holder.ivMovieImage.setImageBitmap(movie.getMoviePoster());
        holder.tvMovieTitle.setText(movie.getTitle());
        holder.tvMovieCategory.setText(movie.getCategoryName());
        LayerDrawable layer = (LayerDrawable) holder.vRow.getBackground();
        GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.movie_item_background);
        shape.setColor(movie.getCategoryColor());
    }

    @Override
    public int getItemCount()
    {
        return moviesList.size();
    }

    public void removeItem(int position)
    {
        Movie movieBeingRemoved = getMovie(position);
        moviesList.remove(position);
        idMovieDictHelper.remove(movieBeingRemoved.getId());
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public Movie getMovie(int position)
    {
        return moviesList.get(position);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private View vRow;
        private ImageView ivMovieImage;
        private TextView tvMovieTitle;
        private TextView tvMovieCategory;

        private ViewHolder(View itemView)
        {
            super(itemView);
            vRow = itemView;
            ivMovieImage = itemView.findViewById(R.id.iv_movie_image);
            tvMovieTitle = itemView.findViewById(R.id.tv_movie_title);
            tvMovieCategory = itemView.findViewById(R.id.tv_movie_category);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            onListItemClickListener.onListItemClick(v, getAdapterPosition());
        }
    }

    public interface OnListItemClickListener
    {
        void onListItemClick(View view, int position);
    }
}
