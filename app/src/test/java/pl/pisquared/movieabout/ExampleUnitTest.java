package pl.pisquared.movieabout;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import pl.pisquared.movieabout.utils.DateUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void years_diff_by_today_is_correct_for_valid_data()
    {
        Date date = DateUtils.getDate(2016, 5, 20);
        int diffYears = DateUtils.getDiffInYearsByToday(date);
        assertEquals(1, diffYears);
    }

    @Test
    public void years_diff_by_today_is_correct_for_invlaid_data()
    {
        Date date = DateUtils.getDate(2015, 5, 20);
        int diffYears = DateUtils.getDiffInYearsByToday(date);
        assertNotEquals(1, diffYears);
    }

    @Test
    public void years_diff_by_today_is_correct_for_data_matching_day_of_the_year()
    {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        cal.set(year - 2, month, day, 0 ,0, 0);
        int diffYears = DateUtils.getDiffInYearsByToday(cal.getTime());
        assertEquals(2, diffYears);
    }
}