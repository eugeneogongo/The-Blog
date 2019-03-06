package com.yourstar.cenotomy.Activities;

import android.app.Application;
import android.content.res.Resources;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prof.rssparser.Article;
import com.youstar.bloggerssport.R;


public class Startup extends Application {
    protected static Startup instance;
    Article article;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        TypefaceProvider.registerDefaultIconSets();
        FirebaseMessaging.getInstance().subscribeToTopic("blogger");
        }
    public static Resources getResource() {
        return instance.getResources();
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
