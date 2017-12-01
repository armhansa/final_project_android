package com.armhansa.app.cutepid.fragment_home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.armhansa.app.cutepid.controller.EditInfoActivity;
import com.armhansa.app.cutepid.controller.LoginActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.controller.SetFilterActivity;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.text.MessageFormat;

public class ProfileFragment extends Fragment {

    View rootView;

    ImageView profileImage;
    TextView firstName;

    LinearLayout settingBtn, editInfoBtn;

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

        firstName = rootView.findViewById(R.id.firstName);
        setValue();

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
                if(User.getOwnerAccount().isFacebookUser())
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

    private void setValue() {
        firstName.setText(MessageFormat.format("   {0}, {1}"
                , User.getOwnerAccount().getFirstName(), User.getOwnerAccount().getAge()));
        Glide.with(this)
                .load(User.getOwnerAccount().getProfile())
//                .apply(myOption)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(100)))
                .into(profileImage);
    }

    private void settingFilter() {
        Intent goToSetFilterPage = new Intent(getActivity(), SetFilterActivity.class);
        startActivity(goToSetFilterPage);
    }

    private void editInfo() {
        Intent goToEditInfoPage = new Intent(getActivity(), EditInfoActivity.class);
        startActivityForResult(goToEditInfoPage, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setValue();
    }
}
