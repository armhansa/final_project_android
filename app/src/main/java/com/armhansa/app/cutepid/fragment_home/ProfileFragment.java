package com.armhansa.app.cutepid.fragment_home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.armhansa.app.cutepid.EditInfoActivity;
import com.armhansa.app.cutepid.LoginActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.SetFilterActivity;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

public class ProfileFragment extends Fragment {

    View rootView;

    ImageView profileImage;
    TextView firstName;

    ImageButton settingBtn, editInfoBtn;

    Button facebookLogoutBtn;

    CallbackManager callbackManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = rootView.findViewById(R.id.profileImage);
        Glide.with(this)
                .load(User.getOwnerAccount().getProfile())
//                .apply(myOption)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(100)))
                .into(profileImage);

        firstName = rootView.findViewById(R.id.firstName);
        firstName.setText(String.format("   %s, %d", User.getOwnerAccount().getFirstName(), User.getOwnerAccount().getAge()));

        settingBtn = rootView.findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingFilter();
            }
        });
        editInfoBtn = rootView.findViewById(R.id.editInfoBtn);
        editInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInfo();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        facebookLogoutBtn = rootView.findViewById(R.id.logoutBtn);
        facebookLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(Facebook User)
                LoginManager.getInstance().logOut();
//
                CommonSharePreference preference = new CommonSharePreference(getActivity());
                preference.clear();

                Intent logout = new Intent(getActivity(), LoginActivity.class);
                startActivity(logout);
                getActivity().finish();

            }
        });

        return rootView;
    }

    private void settingFilter() {
        Intent goToSetFilterPage = new Intent(getActivity(), SetFilterActivity.class);
        startActivity(goToSetFilterPage);
    }

    private void editInfo() {
        Intent goToEditInfoPage = new Intent(getActivity(), EditInfoActivity.class);
        startActivity(goToEditInfoPage);
    }
}
