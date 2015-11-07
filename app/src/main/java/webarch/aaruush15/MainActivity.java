
package webarch.aaruush15;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import webarch.aaruush15.HomeFragments.FragmentHome;
import webarch.aaruush15.TeamFragments.FragmentPatrons;
import webarch.aaruush15.TeamFragments.FragmentTeam;
import webarch.aaruush15.WorkshopFragments.FragmentWorkshops;
import webarch.aaruush15.ui_fragments.FragmentAbout;
import webarch.aaruush15.ui_fragments.FragmentCredits;
import webarch.aaruush15.ui_fragments.FragmentDomains;
import webarch.aaruush15.ui_fragments.FragmentHighlights;
import webarch.aaruush15.ui_fragments.FragmentSponsors;

public class MainActivity extends MaterialNavigationDrawer
{


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void init(Bundle savedInstanceState)
    {
        View drawerHeaderView = LayoutInflater.from(this).inflate(R.layout.custom_drawer,null);
        setDrawerHeaderCustom(drawerHeaderView);
        this.addSection(newSectionWithRealColor("Home", R.drawable.drawer_about, new FragmentAbout()));
        this.addSection(newSectionWithRealColor("Domains", R.drawable.drawer_domains, new FragmentDomains()));
        this.addSection(newSectionWithRealColor("Workshops", R.drawable.drawer_workshops, new FragmentWorkshops()));
        this.addSection(newSectionWithRealColor("Favourites", R.drawable.drawer_fav, new FragmentHome()));
        this.addSection(newSectionWithRealColor("Highlights", R.drawable.drawer_highlights, new FragmentHighlights()));
        this.addSection(newSectionWithRealColor("Sponsors", R.drawable.drawer_sponsors, new FragmentSponsors()));
        this.addSection(newSectionWithRealColor("Patrons", R.drawable.drawer_patrons, new FragmentPatrons()));
        this.addSection(newSectionWithRealColor("Team", R.drawable.drawer_team, new FragmentTeam()));
        this.addBottomSection(newSectionWithRealColor("Credits", R.drawable.drawer_credits, new FragmentCredits()));

        this.allowArrowAnimation();
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to Exit...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}