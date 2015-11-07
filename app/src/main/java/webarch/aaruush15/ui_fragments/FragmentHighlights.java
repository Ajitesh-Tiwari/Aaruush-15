package webarch.aaruush15.ui_fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.json.JSONException;

import java.util.List;

import webarch.aaruush15.BackEnd.ConnectionManager;
import webarch.aaruush15.BackEnd.DOMParser;
import webarch.aaruush15.BackEnd.Data;
import webarch.aaruush15.BackEnd.DatabaseHandler;
import webarch.aaruush15.BackEnd.ListAdapter;
import webarch.aaruush15.R;

/**
 * Created by Chinmay on 07-Jul-15.
 */
public class FragmentHighlights extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    DatabaseHandler dbHandler;
    ListView list;
    SwipeRefreshLayout swipeRefreshLayout;
    ConnectionManager con;
    Context context;
    ListAdapter adapter;
    NiftyDialogBuilder dialogBuilder;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_highlights,container,false);
        list=(ListView)rootview.findViewById(R.id.listView);
        dbHandler=new DatabaseHandler(getActivity());

        swipeRefreshLayout=(SwipeRefreshLayout)rootview.findViewById(R.id.swipe_refresh_layout);
        con=new ConnectionManager(getActivity(),swipeRefreshLayout,list,"3",null);
        context=getActivity();
        dialogBuilder=NiftyDialogBuilder.getInstance(context);

        final List<Data> highlightList=dbHandler.getDatabyType("3");
        if(highlightList!=null) {
            adapter = new ListAdapter(getActivity(), -1, highlightList);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (highlightList.get(i).getName().equalsIgnoreCase("Pro Show")) {
                        Intent intent = new Intent(getActivity(), ProshowSlideShow.class);
                        //Bundle bundle = highlightList.get(i).getAsBundle();
                        //intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        DOMParser dom = new DOMParser(highlightList.get(i).getDesc());
                        dom.ParseXML();
                        dialogBuilder
                                .withTitle(highlightList.get(i).getName())
                                .withTitleColor("#FFFFFF")
                                .withDividerColor("#11000000")
                                .withMessage(dom.getDesc())
                                .withMessageColor("#FFFFFFFF")
                                .withDialogColor("#FFE74C3C")
                                .withEffect(Effectstype.Fall)
                                .withButton1Text("Close")
                                .isCancelableOnTouchOutside(true)
                                .setButton1Click(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialogBuilder.dismiss();
                                    }
                                })
                                .show();
                    }

                }
            });

            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    onRun();
                }
            });
        }
        return rootview;
    }
    public void onRefresh() {
        onRun();
    }
    public void onRun(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected)
        {
            try {
                con.getDatabaseUpdate(dbHandler.getVersion());

            } catch (JSONException e) {
                e.printStackTrace();
            }}
        else
        {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(context, "Please Connect To The Internet", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}