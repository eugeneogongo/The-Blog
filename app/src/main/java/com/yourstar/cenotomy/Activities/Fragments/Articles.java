package com.yourstar.cenotomy.Activities.Fragments;

import android.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.prof.rssparser.Article;
import com.yourstar.cenotomy.Activities.Adapters.ArticleAdapter;
import com.youstar.bloggerssport.R;

import java.util.List;

public class Articles extends Fragment {

    private ArticlesViewModel mViewModel;
    private View view;
    private ArticleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    public static Articles newInstance() {
        return new Articles();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.articles_fragment, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ArticleAdapter(R.layout.template_post, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        progressBar = view.findViewById(R.id.progressBar);
        if (!isNetworkAvailable()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.alert_message)
                    .setTitle(R.string.alert_title)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_positive,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    getActivity().finish();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
            }

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ArticlesViewModel.class);
       mViewModel.getArticles().observe(this, new Observer<List<Article>>() {
           @Override
           public void onChanged(@Nullable List<Article> art) {
               Log.e("Size is ", String.valueOf(art.size()));
              mAdapter.setArticles(art);
              mAdapter.notifyDataSetChanged();
              progressBar.setVisibility(View.GONE);
           }
       });

    }

}
