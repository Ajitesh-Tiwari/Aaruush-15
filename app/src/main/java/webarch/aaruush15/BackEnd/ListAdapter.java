package webarch.aaruush15.BackEnd;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import webarch.aaruush15.R;

/**
 * Created by Chinmay on 26-Jul-15.
 */
public class ListAdapter extends ArrayAdapter<Data> {

    List<Data> list;
    Context context;
    public ListAdapter(Context context, int resource, List<Data> objects) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.card_view, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView desc = (TextView) rowView.findViewById(R.id.txtName);
        ImageView image= (ImageView) rowView.findViewById(R.id.picdesc);

        Data current=list.get(position);
        title.setText(current.getName());
        /*if(current.getType().equals("2"))
        {*/
            DOMParser dom=new DOMParser(current.getDesc());
            Log.d("AARUUSHTEST",current.getDesc());
            dom.ParseXML();
            if(dom.getDesc().length()>=100)
                desc.setText(dom.getDesc().substring(0,100)+".....");
            else
                desc.setText(dom.getDesc());
        /*}
        else {
            DOMParser dom=new DOMParser(current.getDesc());
            Log.d("AARUUSHTEST",current.getDesc());
            dom.ParseXML();
            if(dom.getDesc().length()>=100)
                desc.setText(dom.getDesc().substring(0,100)+".....");
            else
                desc.setText(dom.getDesc());
            String x=current.getDesc();
            x=x.replace("<Description><Desc>","");
            if (x.length() >= 100)
                desc.setText(x.substring(0, 100) + "...");
            else
                desc.setText(x);
        }*/
        int resID = context.getResources().getIdentifier(current.getImage() , "drawable", context.getPackageName());
        image.setImageResource(resID);


        return rowView;
    }
}
