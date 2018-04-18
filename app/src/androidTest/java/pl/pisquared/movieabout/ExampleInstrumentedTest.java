package pl.pisquared.movieabout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

import pl.pisquared.movieabout.data.SampleMoviesDataProvider;
import pl.pisquared.movieabout.utils.BitmapUtils;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest
{
    @Test
    public void useAppContext() throws Exception
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("pl.pisquared.movieabout", appContext.getPackageName());
    }

    @Test
    public void movie_get_by_its_id_returns_correct_movie_reference()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        SampleMoviesDataProvider dataProvider = SampleMoviesDataProvider.getInstance(appContext);
        Movie byId = dataProvider.getMovieById(3);
        List<Movie> moviesList = dataProvider.getSampleMovieData();
        Movie movie = moviesList.get(2);
        assertEquals(movie, byId);
    }

    @Test
    public void sampled_bitmap_dimensions_are_as_expected()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Resources res = appContext.getResources();
        int resId = res.getIdentifier("prestige_1", "drawable", appContext.getPackageName());
        Bitmap resultBitmap = BitmapUtils.decodeSampledBitmapFromResource(appContext.getResources(), resId, 451, 300);
        int outWidth = resultBitmap.getWidth();
        int outHeight = resultBitmap.getHeight();
        assertEquals(752, outWidth);
        assertEquals(500, outHeight);
    }
}
