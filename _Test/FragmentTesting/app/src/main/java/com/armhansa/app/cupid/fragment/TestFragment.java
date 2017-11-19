package com.armhansa.app.cupid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.armhansa.app.cupid.R;

public class TestFragment extends Fragment implements View.OnClickListener {

    View rootView;

    TextView pageABtn;
    TextView pageBBtn;
    TextView pageCBtn;

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_test, container, false);

        pageABtn = rootView.findViewById(R.id.pageABtn);
        pageABtn.setOnClickListener(this);
        pageBBtn = rootView.findViewById(R.id.pageBBtn);
        pageBBtn.setOnClickListener(this);
        pageCBtn = rootView.findViewById(R.id.pageCBtn);
        pageCBtn.setOnClickListener(this);

        return rootView;
    }

    public void goToA() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new PageAFragment())
                .commit();
    }

    public void goToB() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new PageBFragment())
                .commit();
    }

    public void goToC() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new PageCFragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pageABtn: goToA(); break;
            case R.id.pageBBtn: goToB(); break;
            case R.id.pageCBtn: goToC(); break;
        }

    }
}
