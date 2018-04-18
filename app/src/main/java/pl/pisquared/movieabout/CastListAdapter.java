package pl.pisquared.movieabout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class CastListAdapter extends ArrayAdapter<Actor>
{
    private final int rowLayoutResourceId;

    public CastListAdapter(@NonNull Context context, int resource, @NonNull List<Actor> objects)
    {
        super(context, resource, objects);
        rowLayoutResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder holder = null;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(rowLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.ivPhoto = convertView.findViewById(R.id.iv_person_photo);
            holder.tvNameSurname = convertView.findViewById(R.id.tv_name_surname);
            holder.tvAge = convertView.findViewById(R.id.tv_display_age);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Actor actor = getItem(position);
        if(actor!=null)
        {
            holder.ivPhoto.setImageBitmap(actor.getPhoto());
            holder.tvNameSurname.setText(String.format(Locale.US, "%s %s", actor.getName(), actor.getSurname()));
            holder.tvAge.setText(String.valueOf(actor.getAge()));
        }
        return convertView;
    }

    private static class ViewHolder
    {
        private ImageView ivPhoto;
        private TextView tvNameSurname;
        private TextView tvAge;
    }
}
