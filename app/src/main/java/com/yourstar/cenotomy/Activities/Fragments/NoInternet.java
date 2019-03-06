package com.yourstar.cenotomy.Activities.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youstar.bloggerssport.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoInternet extends Fragment {


    public NoInternet() {
        // Required empty public constructor
    }
 public static NoInternet newInstance() {

    Bundle args = new Bundle();

    NoInternet fragment = new NoInternet();
    fragment.setArguments(args);
    return fragment;
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_internet, container, false);
    }

}
