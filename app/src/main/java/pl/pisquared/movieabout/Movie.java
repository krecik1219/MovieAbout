package pl.pisquared.movieabout;

import android.graphics.Bitmap;

/**
 * Created by Kretek on 02/04/2018.
 */

public class Movie
{
    private static final int COLOR_RED = 0xffff0000;
    private static final int COLOR_YELLOW = 0xFFD9B611;
    private static final int COLOR_VIOLET = 0xff5B3256;
    private static final int COLOR_LAPIS_LAZULI = 0xff1F4788;

    private int id;
    private String title;
    private Movie.Category category;
    private Bitmap moviePoster;

    public Movie(int id, String title, Movie.Category category, Bitmap moviePoster)
    {
        this.title = title;
        this.category = category;
        this.moviePoster = moviePoster;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Movie.Category getCategory()
    {
        return category;
    }

    public void setCategory(Movie.Category category)
    {
        this.category = category;
    }

    public String getCategoryName()
    {
        return category.getCategoryName();
    }

    public Bitmap getMoviePoster()
    {
        return moviePoster;
    }

    public void setMoviePoster(Bitmap moveImage)
    {
        this.moviePoster = moveImage;
    }

    public int getCategoryColor()
    {
        return category.getColorHexCode();
    }

    public enum Category
    {
        CRIME(COLOR_RED),
        THRILLER(COLOR_VIOLET),
        COMEDY(COLOR_YELLOW),
        ACTION(COLOR_LAPIS_LAZULI);

        private String categoryName;
        private int colorHexCode;

        Category(int colorHexCode)
        {
            String name = this.name().toLowerCase();
            this.categoryName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            this.colorHexCode = colorHexCode;
        }

        public String getCategoryName()
        {
            return categoryName;
        }

        public int getColorHexCode()
        {
            return colorHexCode;
        }
    }
}
