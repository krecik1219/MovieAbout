package pl.pisquared.movieabout.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
    public static Date getDate(int year, int month, int day)
    {
        Calendar cal = Calendar.getInstance();
        month--;
        cal.set(year, month, day, 0, 0, 0);
        return cal.getTime();
    }

    public static int getDiffInYearsByToday(Date older)
    {
        Calendar calFirst = getCalendarWithDate(older);
        Calendar today  = Calendar.getInstance();
        return getDiffInYears(calFirst, today);
    }

    private static Calendar getCalendarWithDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private static int getDiffInYears(Calendar calFirst, Calendar calLater)
    {
        int yearsDiff = calLater.get(Calendar.YEAR) - calFirst.get(Calendar.YEAR);
        int firstMonth = calFirst.get(Calendar.MONTH);
        int laterMonth = calLater.get(Calendar.MONTH);
        if(firstMonth > laterMonth || firstMonth == laterMonth && calFirst.get(Calendar.DAY_OF_MONTH) > calLater.get(Calendar.DAY_OF_MONTH))
            yearsDiff--;
        return yearsDiff;
    }
}
