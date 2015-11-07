package webarch.aaruush15.HomeFragments;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import webarch.aaruush15.BackEnd.ConnectionManager;
import webarch.aaruush15.BackEnd.Data;
import webarch.aaruush15.BackEnd.DatabaseHandler;
import webarch.aaruush15.BackEnd.ListAdapter;
import webarch.aaruush15.R;
import webarch.aaruush15.WorkshopFragments.WorkshopDetails;

/**
 * Created by Chinmay on 09-Jul-15.
 */
public class FragmentHomeHighlights extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    String type="3";
    ListView mainlist;
    List<Data> list;
    DatabaseHandler dbHandler;
    SwipeRefreshLayout swipeRefreshLayout;
    ConnectionManager con;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        dbHandler=new DatabaseHandler(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootview=inflater.inflate(R.layout.fragment_home_highlights, container, false);
        mainlist=(ListView)rootview.findViewById(R.id.listView);
        swipeRefreshLayout=(SwipeRefreshLayout)rootview.findViewById(R.id.swipe_refresh_layout);
        con=new ConnectionManager(getActivity(),swipeRefreshLayout,mainlist,null,type);
        list=dbHandler.getDatabyTypeFav(type);
        List<Data> EmptyList=new ArrayList<Data>();
        //EmptyList.add(new Data("Nothing To Display","","","","","","<Description><Desc>Please add Something...</Desc></Description>","empty"));
        EmptyList.add(new Data("Nothing To Display","","","","","","Please add Something...","empty"));
        if(list!=null)
            mainlist.setAdapter(new ListAdapter(context,-1,list));
        else
            mainlist.setAdapter(new ListAdapter(context, -1, EmptyList));
        mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView title = (TextView) view.findViewById(R.id.title);
                if (!title.getText().equals("Nothing To Display")){
                    Intent intent = new Intent(getActivity(), WorkshopDetails.class);
                    Bundle bundle = list.get(i).getAsBundle();
                    intent.putExtras(bundle);
                    startActivity(intent);}
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRun();
            }

        });
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
    }
}
