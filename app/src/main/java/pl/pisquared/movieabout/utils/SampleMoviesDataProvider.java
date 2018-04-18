package pl.pisquared.movieabout.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import pl.pisquared.movieabout.Actor;
import pl.pisquared.movieabout.BitmapWrapper;
import pl.pisquared.movieabout.Movie;
import pl.pisquared.movieabout.R;

/**
 * Created by Kretek on 02/04/2018.
 */

public class SampleMoviesDataProvider
{
    // private static final String TAG = SampleMoviesDataProvider.class.getSimpleName();  // for debug logs
    private static final int POSTER_DP_WIDTH = 70;
    private static final int POSTER_DP_HEIGHT = 100;
    private static final int GALLERY_IMAGE_DP_WIDTH = 110;
    private static final int GALLERY_IMAGE_DP_HEIGHT = 110;
    private static final int ACTOR_PHOTO_WIDTH = 80;
    private static final int ACTOR_PHOTO_HEIGHT = 110;
    private final List<Movie> moviesList;
    private final SparseArray<Movie> idMovieDict;
    private final int SAMPLE_DATA_SIZE = 10;
    private final int MOVIE_IMAGES_NUMBER = 6;

    private static SampleMoviesDataProvider ourInstance = null;

    public static SampleMoviesDataProvider getInstance(Context context)
    {
        if(ourInstance == null)
            ourInstance = new SampleMoviesDataProvider(context);
        return ourInstance;
    }

    public List<Movie> getSampleMovieData()
    {
        return moviesList;
    }

    public SparseArray<Movie> getMovieIdDict()
    {
        return idMovieDict;
    }

    public Movie getMovieById(int movieId)
    {
        return idMovieDict.get(movieId);
    }

    public BitmapWrapper getMovieMainImage(Context context, int movieId, int reqWidth, int reqHeight)
    {
        String imageFileNamePattern = getMovieImageFileNamePattern(movieId);

        Random generator = new Random();
        int imageNumber = generator.nextInt(6) + 1;
        String imageFileName = imageFileNamePattern+String.valueOf(imageNumber);

        int resourceId = context.getResources().getIdentifier(imageFileName, "drawable", context.getPackageName());

        Bitmap image = BitmapUtils.decodeSampledBitmapFromResource(context.getResources(), resourceId, reqWidth, reqHeight);
        return new BitmapWrapper(image, resourceId);
    }

    public List<BitmapWrapper> getMovieImages(Context context, int movieId)
    {
        List<BitmapWrapper> images = new ArrayList<BitmapWrapper>(MOVIE_IMAGES_NUMBER);

        String imageNamePattern = getMovieImageFileNamePattern(movieId);
        String imageFileName = null;
        int resourceId = 0;
        Bitmap image = null;
        int imageWidth = convertDpToPixels(context, GALLERY_IMAGE_DP_WIDTH);
        int imageHeight = convertDpToPixels(context, GALLERY_IMAGE_DP_HEIGHT);
        Resources res = context.getResources();
        for(int i=0; i<MOVIE_IMAGES_NUMBER; i++)
        {
            imageFileName = imageNamePattern + String.valueOf(i+1);
            resourceId = res.getIdentifier(imageFileName, "drawable", context.getPackageName());
            image = BitmapUtils.decodeSampledBitmapFromResource(res, resourceId, imageWidth, imageHeight);
            images.add(new BitmapWrapper(image, resourceId));
        }

        return images;
    }

    public List<Actor> getMovieCast(Context context, int  movieId)
    {

        List<Actor> cast = null;

        switch(movieId)
        {
            case 1:
                cast = getScarfaceCast(context);
                break;
            case 2:
                cast = getGodfatherCast(context);
                break;
            case 3:
                cast = getInceptionCast(context);
                break;
            case 4:
                cast = getPrestigeCast(context);
                break;
            case 5:
                cast = getShutterIslandCast(context);
                break;
            case 6:
                cast = getWolfWallStreetCast(context);
                break;
            case 7:
                cast = getFightClubCast(context);
                break;
            case 8:
                cast = getSe7evCast(context);
                break;
            case 9:
                cast = getDepartedCast(context);
                break;
            case 10:
                cast = getCasinoRoyaleCast(context);
                break;
        }

        return cast;
    }

    private SampleMoviesDataProvider(Context context)
    {
        moviesList = new ArrayList<Movie>(SAMPLE_DATA_SIZE);
        idMovieDict = new SparseArray<Movie>();
        Movie movie = null;
        String [] moviesTitles = context.getResources().getStringArray(R.array.movies_titles);
        String [] moviesCategories = context.getResources().getStringArray(R.array.movies_categories);
        Bitmap[] moviesPosters = getMoviesPosters(context);
        Movie.Category category = null;
        for(int i=0; i<SAMPLE_DATA_SIZE; i++)
        {
            category = Movie.Category.valueOf(moviesCategories[i]);
            movie = new Movie(i + 1, moviesTitles[i], category, moviesPosters[i]);
            moviesList.add(movie);
            idMovieDict.append(i+1, movie);
        }
    }

    private List<Actor> getScarfaceCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Al", "Pacino", DateUtils.getDate(1940, 4, 25), getActorPhoto(context, "al_pacino")),
                        new Actor("Steven", "Bauer", DateUtils.getDate(1956, 12, 2), getActorPhoto(context, "steven_bauer")),
                        new Actor("Michelle", "Pfeiffer", DateUtils.getDate(1958, 4, 29), getActorPhoto(context, "michelle_pfeiffer")),
                        new Actor("Mary Elizabeth", "Mastrantonio", DateUtils.getDate(1958, 11, 17), getActorPhoto(context, "mary_elizabeth_mastrantonio")),
                        new Actor("Robert",  "Loggia", DateUtils.getDate(1930, 1, 3), getActorPhoto(context, "robert_loggia"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getGodfatherCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Marlon", "Brando", DateUtils.getDate(1924, 4, 3), getActorPhoto(context, "marlon_brando")),
                        new Actor("Al", "Pacino", DateUtils.getDate(1940, 4, 25), getActorPhoto(context, "al_pacino")),
                        new Actor("James", "Caan", DateUtils.getDate(1940, 3, 26), getActorPhoto(context, "james_caan")),
                        new Actor("Richard", "Castellano", DateUtils.getDate(1933, 9, 4), getActorPhoto(context, "richard_castellano")),
                        new Actor("Robert", "Duvall", DateUtils.getDate(1931, 1, 5), getActorPhoto(context, "robert_duvall"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getInceptionCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Leonardo",  "DiCaprio", DateUtils.getDate(1974, 11, 11), getActorPhoto(context, "leonardo_dicaprio")),
                        new Actor("Tom", "Hardy", DateUtils.getDate(1977, 9, 15), getActorPhoto(context, "tom_hardy")),
                        new Actor("Joseph", "Gordon-Levitt", DateUtils.getDate(1981, 2, 17), getActorPhoto(context, "joseph_gordon_levitt")),
                        new Actor("Ellen", "Page", DateUtils.getDate(1987, 2, 21), getActorPhoto(context, "ellen_page")),
                        new Actor("Ken", "Watanabe", DateUtils.getDate(1959, 10, 21), getActorPhoto(context, "ken_watanabe"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getPrestigeCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Hugh", "Jackman", DateUtils.getDate(1968, 10, 12), getActorPhoto(context, "hugh_jackman")),
                        new Actor("Christian", "Bale", DateUtils.getDate(1974, 1, 30), getActorPhoto(context, "christian_bale")),
                        new Actor("Michael", "Caine", DateUtils.getDate(1933, 3, 14), getActorPhoto(context, "michael_caine")),
                        new Actor("Piper", "Perabo", DateUtils.getDate(1976, 10, 31), getActorPhoto(context, "piper_perabo")),
                        new Actor("Rebecca", "Hall", DateUtils.getDate(1982, 5, 3), getActorPhoto(context, "rebecca_hall"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getShutterIslandCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Leonardo",  "DiCaprio", DateUtils.getDate(1974, 11, 11), getActorPhoto(context, "leonardo_dicaprio")),
                        new Actor("Mark", "Ruffalo", DateUtils.getDate(1967, 11, 22), getActorPhoto(context, "mark_ruffalo")),
                        new Actor("Ben", "Kingsley", DateUtils.getDate(1943, 12, 31), getActorPhoto(context, "ben_kingsley")),
                        new Actor("Max", "von Sydow", DateUtils.getDate(1929, 4, 10), getActorPhoto(context, "max_von_sydow")),
                        new Actor("Michelle", "Williams", DateUtils.getDate(1980, 9, 9), getActorPhoto(context, "michelle_williams"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getWolfWallStreetCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Leonardo",  "DiCaprio", DateUtils.getDate(1974, 11, 11), getActorPhoto(context, "leonardo_dicaprio")),
                        new Actor("Jonah", "Hill", DateUtils.getDate(1983, 12, 20), getActorPhoto(context, "jonah_hill")),
                        new Actor("Margot", "Robbie", DateUtils.getDate(1990, 7, 2), getActorPhoto(context, "margot_robbie")),
                        new Actor("Matthew", "McConaughey", DateUtils.getDate(1969, 11, 4), getActorPhoto(context, "matthew_mcconaughey")),
                        new Actor("Kyle", "Chandler", DateUtils.getDate(1965, 9, 17), getActorPhoto(context, "kyle_chandler"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getFightClubCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Edward", "Norton", DateUtils.getDate(1969, 8, 18), getActorPhoto(context, "edward_norton")),
                        new Actor("Brad", "Pitt", DateUtils.getDate(1963, 12, 18), getActorPhoto(context, "brad_pitt")),
                        new Actor("Helena", "Bonham Carter", DateUtils.getDate(1966, 5, 26), getActorPhoto(context, "helena_bonham_carter")),
                        new Actor("Meat", "Loaf", DateUtils.getDate(1947, 9, 27), getActorPhoto(context, "meat_loaf")),
                        new Actor("Zach", "Grenier", DateUtils.getDate(1954, 2, 12), getActorPhoto(context, "zach_grenier"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getSe7evCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Morgan", "Freeman", DateUtils.getDate(1937, 6, 1), getActorPhoto(context, "morgan_freeman")),
                        new Actor("Brad", "Pitt", DateUtils.getDate(1963, 12, 18), getActorPhoto(context, "brad_pitt")),
                        new Actor("Kevin",  "Spacey", DateUtils.getDate(1959, 7, 26), getActorPhoto(context, "kevin_spacey")),
                        new Actor("Andrew Kevin", "Walker", DateUtils.getDate(1964, 8, 14), getActorPhoto(context, "andrew_kevin_walker")),
                        new Actor("Daniel",  "Zacapa", DateUtils.getDate(1951, 6, 19), getActorPhoto(context, "daniel_zacapa"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getDepartedCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Leonardo",  "DiCaprio", DateUtils.getDate(1974, 11, 11), getActorPhoto(context, "leonardo_dicaprio")),
                        new Actor("Matt", "Damon", DateUtils.getDate(1970, 10, 8), getActorPhoto(context, "matt_damon")),
                        new Actor("Jack", "Nicholson", DateUtils.getDate(1937, 4, 22), getActorPhoto(context, "jack_nicholson")),
                        new Actor("Mark", "Wahlberg", DateUtils.getDate(1971, 6, 5), getActorPhoto(context, "mark_wahlberg")),
                        new Actor("Martin", "Sheen", DateUtils.getDate(1940, 8, 3), getActorPhoto(context, "martin_sheen"))
                };

        return Arrays.asList(actors);
    }

    private List<Actor> getCasinoRoyaleCast(Context context)
    {
        Actor[] actors =
                {
                        new Actor("Daniel", "Craig", DateUtils.getDate(1968, 3, 2), getActorPhoto(context, "daniel_craig")),
                        new Actor("Eva", "Green", DateUtils.getDate(1980, 7, 6), getActorPhoto(context, "eva_green")),
                        new Actor("Mads", "Mikkelsen", DateUtils.getDate(1965, 11, 22), getActorPhoto(context, "mads_mikkelsen")),
                        new Actor("Judi", "Dench", DateUtils.getDate(1934, 12, 9), getActorPhoto(context, "judi_dench")),
                        new Actor("Jeffrey",  "Wright", DateUtils.getDate(1965, 12, 7), getActorPhoto(context, "jeffrey_wright"))
                };

        return Arrays.asList(actors);
    }

    private Bitmap getActorPhoto(Context context, String fileName)
    {
        int imageWidth = convertDpToPixels(context, ACTOR_PHOTO_WIDTH);
        int imageHeight = convertDpToPixels(context, ACTOR_PHOTO_HEIGHT);
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(fileName, "drawable", context.getPackageName());
        return BitmapUtils.decodeSampledBitmapFromResource(res, resourceId, imageWidth, imageHeight);
    }

    private String getMovieImageFileNamePattern(int movieId)
    {
        String imageFileNamePattern = "";
        switch(movieId)
        {
            case 1:
                imageFileNamePattern = "scarface_";
                break;
            case 2:
                imageFileNamePattern = "godfather_";
                break;
            case 3:
                imageFileNamePattern = "inception_";
                break;
            case 4:
                imageFileNamePattern = "prestige_";
                break;
            case 5:
                imageFileNamePattern = "shutter_island_";
                break;
            case 6:
                imageFileNamePattern = "wolf_";
                break;
            case 7:
                imageFileNamePattern = "fight_club_";
                break;
            case 8:
                imageFileNamePattern = "se7en_";
                break;
            case 9:
                imageFileNamePattern = "departed_";
                break;
            case 10:
                imageFileNamePattern = "casino_";
                break;
        }

        return imageFileNamePattern;
    }

    private Bitmap[] getMoviesPosters(Context context)
    {
        int posterWidth = convertDpToPixels(context, POSTER_DP_WIDTH);
        int posterHeight = convertDpToPixels(context, POSTER_DP_HEIGHT);
        Resources res = context.getResources();
        Bitmap [] moviesPosters = {
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.scarface_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.godfather_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.inception_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.prestige_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.shutter_island_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.wolf_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.fight_club_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.se7en_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.departed_poster, posterWidth, posterHeight),
                BitmapUtils.decodeSampledBitmapFromResource(res, R.drawable.casino_poster, posterWidth, posterHeight)
        };

        return moviesPosters;
    }

    private int convertDpToPixels(Context context, int dp)
    {
        Resources res = context.getResources();
        float density = res.getDisplayMetrics().density;
        return (int) (dp * density);
    }
}
