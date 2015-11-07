package webarch.aaruush15.ui_fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import webarch.aaruush15.R;


public class DomainsAdapter extends BaseAdapter
{
    String[] domain_names;
    Context context;
    int[] domain_images;
    private static LayoutInflater inflater = null;

    public DomainsAdapter(Context context, String[] prgmNameList, int[] prgmImages)
    {
        domain_names = prgmNameList;
        this.context = context;
        domain_images = prgmImages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return domain_names.length;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.domains_grid_item, null);

        holder.tv = (TextView) rowView.findViewById(R.id.domain_name);
        holder.img= (ImageView) rowView.findViewById(R.id.domain_image);

        holder.tv.setText(domain_names[position]);
        holder.img.setImageResource(domain_images[position]);
        return rowView;
    }
}

