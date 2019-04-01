package com.yourstar.cenotomy.Activities;

import android.app.Application;
import android.content.res.Resources;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prof.rssparser.Article;


public class Startup extends Application {
    protected static Startup instance;
    Article article;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        TypefaceProvider.registerDefaultIconSets();
        FirebaseApp.initializeApp(this);
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
