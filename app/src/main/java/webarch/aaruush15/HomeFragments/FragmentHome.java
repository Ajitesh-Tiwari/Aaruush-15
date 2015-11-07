package webarch.aaruush15.HomeFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import webarch.aaruush15.R;

/**
 * Created by Chinmay on 07-Jul-15.
 */
public class FragmentHome extends Fragment implements MaterialTabListener
{
    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootview=inflater.inflate(R.layout.tab_activity_text, container, false);
        setHasOptionsMenu(true);

        tabHost = (MaterialTabHost) rootview.findViewById(R.id.tabHost);
        tabHost.setPrimaryColor(this.getActivity().getResources().getColor(R.color.Primary));
        tabHost.setAccentColor(this.getActivity().getResources().getColor(R.color.PrimaryLight));
        pager = (ViewPager) rootview.findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        new SetAdapterTask().execute();
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                tabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < adapter.getCount(); i++)
        {
            tabHost.addTab(tabHost.newTab().setText(adapter.getPageTitle(i)).setTabListener(this));
        }
        return rootview;
    }

    @Override
    public void onTabSelected(MaterialTab tab)
    {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab)
    {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(MaterialTab tab)
    {
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter
    {
        public ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return new FragmentHomeEvents();
                case 1:
                    return new FragmentHomeWorkshops();
                /*case 2:
                    return new FragmentHomeHighlights();*/
            }
            return new FragmentHomeEvents();
        }

        @Override
        public int getCount()
        {
            //return 3;
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "Events";
                case 1:
                    return "Workshops";
                /*case 2:
                    return "Highlights";*/
            }
            return "Error";
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader)
        {
            // do nothing here! no call to super.restoreState(state, loader);
        }
    }

    private class SetAdapterTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(adapter != null) {
                pager.setAdapter(adapter);
            }
        }
    }

}