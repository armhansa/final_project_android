package com.armhansa.app.cutepid.fragment_home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.armhansa.app.cutepid.LoginActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonNotification;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

public class ProfileFragment extends Fragment {

    View rootView;

    Button logoutBtn;

    CallbackManager callbackManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        CommonSharePreference preference = new CommonSharePreference(getContext());

        callbackManager = CallbackManager.Factory.create();
        logoutBtn = rootView.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();

                CommonSharePreference preference = new CommonSharePreference(getActivity());
                preference.clear();

                Intent logout = new Intent(getActivity(), LoginActivity.class);
                startActivity(logout);
                getActivity().finish();

            }
        });

        return rootView;
    }

}
