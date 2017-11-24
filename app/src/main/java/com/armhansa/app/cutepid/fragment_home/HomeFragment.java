package com.armhansa.app.cutepid.fragment_home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.tool.CommonFirebaseConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View rootView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        CommonFirebaseConnection firebase = new CommonFirebaseConnection("/users");

        return rootView;
    }

}
