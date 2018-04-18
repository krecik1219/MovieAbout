package pl.pisquared.movieabout;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class GalleryAdapter extends BaseAdapter
{
    private List<BitmapWrapper> images;
    private Context context;

    public GalleryAdapter(Context context, List<BitmapWrapper> images)
    {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return images.size();
    }

    @Override
    public Object getItem(int position)
    {
        return images.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return ((BitmapWrapper)getItem(position)).getImageResId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
            holder = new ViewHolder();
            holder.ivImageHolder = convertView.findViewById(R.id.iv_gallery_image);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapWrapper image = (BitmapWrapper) getItem(position);
        holder.ivImageHolder.setImageBitmap(image.getImage());

        return convertView;
    }

    private static class ViewHolder
    {
        private ImageView ivImageHolder;
    }

}
