package webarch.aaruush15.ui_fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONException;

import webarch.aaruush15.BackEnd.ConnectionManager;
import webarch.aaruush15.BackEnd.DatabaseHandler;
import webarch.aaruush15.R;

/**
 * Created by Chinmay on 07-Jul-15.
 */
public class FragmentAbout extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    ConnectionManager con;
    DatabaseHandler dbHandler;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        dbHandler=new DatabaseHandler(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_about,container,false);
        swipeRefreshLayout=(SwipeRefreshLayout)rootview.findViewById(R.id.swipe_refresh_layout);

        //SharedPreferences sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //String isFirstRun=sharedpreferences.getString("isFirstRun","true");
        con = new ConnectionManager(getActivity(), swipeRefreshLayout, null, "-1", null);

        Log.d("AARUUSH","OnCreateView");
        /*if(isFirstRun.equals("true")) {
            //con = new ConnectionManager(getActivity(), null, null, "-1", null);
            try {
                rnrStatic();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("isFirstRun", "false");
            editor.apply();
            editor.commit();
        }*/
        FloatingActionsMenu rightLabels = (FloatingActionsMenu) rootview.findViewById(R.id.right_labels);

        FloatingActionButton facebook = new FloatingActionButton(getActivity());
        facebook.setImageResource(R.drawable.actionfb);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getOpenFacebookIntent(getActivity()));
                //Toast.makeText(getActivity(), "Facebook", Toast.LENGTH_SHORT).show();
            }
        });
        facebook.setTitle("Facebook");
        rightLabels.addButton(facebook);

        FloatingActionButton youtube = new FloatingActionButton(getActivity());
        youtube.setImageResource(R.drawable.actionyoutube);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getOpenutubeIntent(getActivity()));
                //Toast.makeText(getActivity(), "Youtube", Toast.LENGTH_SHORT).show();
            }
        });
        youtube.setTitle("Youtube");
        rightLabels.addButton(youtube);

        FloatingActionButton twitter = new FloatingActionButton(getActivity());
        twitter.setImageResource(R.drawable.actiontwitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getOpentwitterIntent(getActivity()));
                //Toast.makeText(getActivity(),"Twitter",Toast.LENGTH_SHORT).show();
            }
        });
        twitter.setTitle("Twitter");
        rightLabels.addButton(twitter);

        TextView text=(TextView)rootview.findViewById(R.id.text);
        text.setMovementMethod(new ScrollingMovementMethod());

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRun();
            }

        });
        return rootview;
    }
    public static Intent getOpenFacebookIntent(Context context)
    {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/172731949541472"));
        } catch (Exception e)
        {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/aaruush.srm"));
        }
    }
    public static Intent getOpentwitterIntent(Context context)
    {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.twitter.android", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?user_id=1357708807"));
        } catch (Exception e)
        {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/Aaruush_Srmuniv"));
        }
    }
    public static Intent getOpenutubeIntent(Context context)
    {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.google.android.youtube", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/user/aaruush12"));
        } catch (Exception e)
        {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/user/aaruush12"));
        }
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