package webarch.aaruush15.ui_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import webarch.aaruush15.FlexibleSpaceWithImageListViewActivity;
import webarch.aaruush15.R;

/**
 * Created by Chinmay on 07-Jul-15.
 */
public class FragmentDomains extends Fragment {


    GridView gv;

    public static String[] DomainNames = {"MAGEFFICIE", "VIMANAZ", "MACHINATION", "ELECTRIZITE", "YUDDHAME", "FUNDAZ", "ARCHITECTURE", "ONLINE", "DIGITAL DESIGN", "PRAESENTATIO", "KONSTRUKTION", "BLUEBOOK", "X-ZONE", "ROBOGYAN"};
    public static int[] DomainImages = {R.drawable.bg_magefficie, R.drawable.bg_vimanaz, R.drawable.bg_machinations, R.drawable.bg_electrizite, R.drawable.bg_yuddhame, R.drawable.bg_fundaz, R.drawable.bg_architecture, R.drawable.bg_online, R.drawable.bg_digital_design, R.drawable.bg_praesentatio, R.drawable.bg_konstruktion, R.drawable.bg_bluebook, R.drawable.bg_xzone, R.drawable.bg_robogyan};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootview=inflater.inflate(R.layout.fragment_domains,container,false);
        setHasOptionsMenu(true);
        gv = (GridView) rootview.findViewById(R.id.gridView1);
        gv.setAdapter(new DomainsAdapter(getActivity(), DomainNames, DomainImages));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), FlexibleSpaceWithImageListViewActivity.class);
                intent.putExtra("Domain",DomainNames[i]);
                //Toast.makeText(getActivity(),"Clicked on "+ DomainNames[i],Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        return rootview;
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}