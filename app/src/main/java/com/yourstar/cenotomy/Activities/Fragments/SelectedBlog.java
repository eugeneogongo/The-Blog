package com.yourstar.cenotomy.Activities.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.yourstar.cenotomy.Activities.Adapters.ArticleAdapter;
import com.yourstar.cenotomy.Constants;
import com.youstar.bloggerssport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedBlog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedBlog extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "BlogName";


    // TODO: Rename and change types of parameters
    private String blogname;
    private ArticlesViewModel mViewModel;
    private View view;
    private ArticleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;


    public SelectedBlog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SelectedBlog.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectedBlog newInstance(String param1) {
        SelectedBlog fragment = new SelectedBlog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            blogname = getArguments().getString(ARG_PARAM1);
            Filter(blogname);
            }
    }

    private void Filter(final String blogname) {
        final List<Article> list = new ArrayList<>();
        mViewModel = ViewModelProviders.of(getActivity()).get(ArticlesViewModel.class);
        mViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> art) {
                Log.e("Size is ", String.valueOf(art.size()));
                for(Article article: art){
                    if(article.getLink().toLowerCase().contains(blogname.toLowerCase())){
                        list.add(article);
                    }
                }
                mAdapter.setArticles(list);
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        }

}
