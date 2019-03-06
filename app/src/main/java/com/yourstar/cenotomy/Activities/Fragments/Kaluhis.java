package com.yourstar.cenotomy.Activities.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;
import com.yourstar.cenotomy.Activities.Adapters.ArticleAdapter;
import com.youstar.bloggerssport.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Kaluhis extends Fragment {


    private View view;
    private ArticleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar progressBar;
    public Kaluhis() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kaluhis, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout = view.findViewById(R.id.container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.canChildScrollUp();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                mAdapter.clearData();
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        progressBar = view.findViewById(R.id.progressBar);
        }


    @Override
    public void onResume() {

        super.onResume();
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (mAdapter != null)
            mAdapter.clearData();
    }


    }
