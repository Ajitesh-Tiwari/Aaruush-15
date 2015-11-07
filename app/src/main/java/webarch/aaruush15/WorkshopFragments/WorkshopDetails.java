package webarch.aaruush15.WorkshopFragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import webarch.aaruush15.BackEnd.DOMParser;
import webarch.aaruush15.BackEnd.DatabaseHandler;
import webarch.aaruush15.R;

public class WorkshopDetails extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private Integer[] images={R.drawable.workshop_rc_cars_l,R.drawable.workshop_motor_bike_overhauling_l,R.drawable.workshop_application_of_gis_l,R.drawable.workshop_wireless_sensor_networks_l,R.drawable.workshop_hacktrack_l,R.drawable.workshop_trancebotics_l,R.drawable.workshop_accelerobotics_l,R.drawable.workshop_entreprenuer_and_business_plan_l};

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    DatabaseHandler dbHandler;
    private ImageView mImageView;
    Context context;
    //private View mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private TextView mTitleView;
    private View mFab;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;

    protected int getActionBarSize()
    {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshopdetails);
        final Bundle extras=getIntent().getExtras();

        //ImageView img=(ImageView)findViewById(R.id.image);
        TextView tvDesc=(TextView)findViewById(R.id.textViewDesc);
        TextView tvCost=(TextView)findViewById(R.id.textViewCost);
        TextView tvCrit=(TextView)findViewById(R.id.textViewCrit);
        TextView tvContact=(TextView)findViewById(R.id.textViewContact);

        DOMParser dom=new DOMParser(extras.getString("desc"));
        dom.ParseXML();

        tvDesc.setText(dom.getDesc());
        tvCost.setText(dom.getCost());
        tvCrit.setText(dom.getCrit());
        tvContact.setText(dom.getContact());
        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getActionBarSize();

        dbHandler=new DatabaseHandler(this);
        mImageView = (ImageView)findViewById(R.id.image);
        switch (extras.getInt("id"))
        {
            case 1:
                mImageView.setImageResource(images[0]);
                break;
            case 5:
                mImageView.setImageResource(images[1]);
                break;
            case 9:
                mImageView.setImageResource(images[2]);
                break;
            case 13:
                mImageView.setImageResource(images[3]);
                break;
            case 39:
                mImageView.setImageResource(images[4]);
                break;
            case 40:
                mImageView.setImageResource(images[5]);
                break;
            case 41:
                mImageView.setImageResource(images[6]);
                break;
            case 42:
                mImageView.setImageResource(images[7]);
                break;
        }
        mOverlayView = findViewById(R.id.overlay);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mTitleView = (TextView) findViewById(R.id.title);
        mTitleView.setText(extras.getString("name"));

        context=this;
        setTitle(null);
        mFab = findViewById(R.id.fab);
        Log.d("AARUUSH",""+extras.getInt("id"));
        if(dbHandler.isFavourite(extras.getInt("id"))==1) {
            mFab.setBackgroundResource(R.drawable.fav);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToFavourite(extras.getInt("id"), mFab);
                }
            });

        }
        else if(dbHandler.isFavourite(extras.getInt("id"))==0) {
            mFab.setBackgroundResource(R.drawable.nofav);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFromFavourite(extras.getInt("id"),mFab);
                }
            });
        }
        /*if(dbHandler.isFavourite(extras.getInt("id"))==1) {
            mFab.setBackgroundColor(getResources().getColor(R.color.Primary));
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WorkshopDetails.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
                    dbHandler.removeFavourite(extras.getInt("id"));
                    finish();
                    startActivity(getIntent());
                }
            });

        }
        else if(dbHandler.isFavourite(extras.getInt("id"))==0) {
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WorkshopDetails.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                    dbHandler.setFavourite(extras.getInt("id"));
                    finish();
                    startActivity(getIntent());
                }
            });
        }*/

        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);

        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, 1);
                //mFlexibleSpaceImageHeight - mActionBarSize
            }
        });
    }

    public void addToFavourite(final int id, final View mFab)
    {
        //mFab.setBackgroundColor(getResources().getColor(R.color.Accent));
        mFab.setBackgroundResource(R.drawable.nofav);
        //mFab.setActivated(false);
        Toast.makeText(WorkshopDetails.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
        dbHandler.removeFavourite(id);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFromFavourite(id, mFab);
            }
        });
    }
    public void removeFromFavourite(final int id,final View mFab)
    {
        //mFab.setBackgroundColor(getResources().getColor(R.color.Primary));
        mFab.setBackgroundResource(R.drawable.fav);
        //mFab.setActivated(true);
        Toast.makeText(WorkshopDetails.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
        dbHandler.setFavourite(id);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavourite(id, mFab);
            }
        });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);
        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);

        // Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
            lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
            lp.topMargin = (int) fabTranslationY;
            mFab.requestLayout();
        } else {
            ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
            ViewHelper.setTranslationY(mFab, fabTranslationY);
        }

        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }
}
