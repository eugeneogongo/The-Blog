package com.yourstar.cenotomy.Activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.yourstar.cenotomy.Share;
import com.youstar.bloggerssport.R;

public class ViewPost extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;

    private View mFab;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


                Intent i = new Intent(getApplicationContext(), Share.class).putExtra("Link",getIntent().getStringExtra("link"));

                PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewPost.this, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                        .setExitAnimations(ViewPost.this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .setActionButton(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_share), "Share", pendingIntent)
                        .setToolbarColor(getResources().getColor(R.color.colorPrimary))
                        .setCloseButtonIcon(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.back)).enableUrlBarHiding().setShowTitle(true);

                CustomTabsIntent customTabsIntent = builder.build();



        customTabsIntent.launchUrl(ViewPost.this, Uri.parse(getIntent().getStringExtra("link")));
                }

    @Override
    protected void onResume() {
        super.onResume();

        finish();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(i)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;

                ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
            }
        }
    }

}