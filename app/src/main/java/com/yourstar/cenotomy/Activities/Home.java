package com.yourstar.cenotomy.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yourstar.cenotomy.Activities.Adapters.DrawerAdapter;
import com.yourstar.cenotomy.Activities.Adapters.DrawerItem;
import com.yourstar.cenotomy.Activities.Adapters.SimpleItem;
import com.yourstar.cenotomy.Activities.Adapters.SpaceItem;
import com.yourstar.cenotomy.Activities.Fragments.Articles;
import com.yourstar.cenotomy.Activities.Fragments.NoInternet;
import com.yourstar.cenotomy.Activities.Fragments.SelectedBlog;
import com.yourstar.cenotomy.Constants;
import com.yourstar.cenotomy.Share;
import com.yourstar.cenotomy.utils.Netinfo;
import com.youstar.bloggerssport.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{

    private int kaluhi=0;
    private int bikozulu = 1;
    private int centonomy = 2;
    private  int sunwords = 3;
    private int All = 4;

    private TextView txttoolbar;
    private Toolbar toolbar;
    private SlidingRootNav slidingRootNav;
    private String[] screenTitles;
    private Drawable screenIcons;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private boolean doubleBackToExitPressedOnce=false;
    boolean isaddshowin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(!Netinfo.isOnline(this)){
            showFragment(NoInternet.newInstance());
        }else{
            showFragment(Articles.newInstance());
        }
        PrepareAds();
        initView(savedInstanceState);
        MobileAds.initialize(this, "ca-app-pub-1444752230904711~2162382300");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                PrepareAds();
                }
        }, 45000);
    }
    void PrepareAds(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1444752230904711/6513525000");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void initView(Bundle savedInstanceState) {
        txttoolbar = findViewById(R.id.txttoolbar);
        txttoolbar.setText("The Blog");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
screenIcons = ContextCompat.getDrawable(this,R.drawable.iop);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.leftside)
                .inject();

        screenTitles = loadScreenTitles();
        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(kaluhi).setChecked(true),
                createItemFor(bikozulu),
                createItemFor(centonomy),
                createItemFor(sunwords),

                new SpaceItem(48),
                createItemFor(All)));
        adapter.setListener(this);
        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(All);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menushare:
                Intent i = new Intent(getApplicationContext(), Share.class);
                i.putExtra("Link","https://play.google.com/store/apps/details?id=com.yourstar.cenotomy");
                sendBroadcast(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int position) {
        androidx.fragment.app.Fragment blog;
switch (position){

    case 0:
        blog = SelectedBlog.newInstance(Constants.KALUHI);
        txttoolbar.setText("Kaluhi's Kitchen");
        break;
    case 1:
        blog =  SelectedBlog.newInstance(Constants.BIKOZULU);
        txttoolbar.setText("Bikozulu");
        break;
    case 2:
        blog = SelectedBlog.newInstance(Constants.Centonomy);
        txttoolbar.setText("Centonomy");
        break;
    case 3 :
        blog = SelectedBlog.newInstance(Constants.SUNNYWORDS);
        txttoolbar.setText("Sunwords");
        break;
        default:
            txttoolbar.setText("The Blog");
            showFragment(Articles.newInstance());
            slidingRootNav.closeMenu();
            return;
}
        if (!isaddshowin) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                if (mInterstitialAd.isLoaded()) {
                    isaddshowin = true;
                }

            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            PrepareAds();
        }

        showFragment(blog);
        slidingRootNav.closeMenu();

    }

    private void showFragment(androidx.fragment.app.Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().addToBackStack("fragment")
                .replace(R.id.frag_pager, fragment).commit();
    }
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons, screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent ))
                .withSelectedTextTint(Color.parseColor("#20B2AA"));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.blognames);
    }
    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        showFragment(Articles.newInstance());

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(String event) {
       if(event== Constants.NotLoaded) {
           showFragment(NoInternet.newInstance());
       }else if(event==Constants.NetworkOn){
           showFragment(Articles.newInstance());
       }

    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}


