package pl.pisquared.movieabout;

import android.graphics.Bitmap;

public class BitmapWrapper
{
    private Bitmap image;
    private int imageResId;

    public BitmapWrapper(Bitmap image, int imageResId)
    {
        this.image = image;
        this.imageResId = imageResId;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public int getImageResId()
    {
        return imageResId;
    }
}
