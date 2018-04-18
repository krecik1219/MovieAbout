package pl.pisquared.movieabout;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.Date;

import pl.pisquared.movieabout.utils.DateUtils;

public class Actor
{
    private String name;
    private String surname;
    private Date birthDate;
    private Bitmap photo;

    public Actor(String name, String surname, Date birthDate, Bitmap photo)
    {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.photo = photo;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public Bitmap getPhoto()
    {
        return photo;
    }

    public int getAge()
    {
        return DateUtils.getDiffInYearsByToday(birthDate);
    }
}
