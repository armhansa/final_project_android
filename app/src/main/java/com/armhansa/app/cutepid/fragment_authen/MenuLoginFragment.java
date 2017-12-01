package com.armhansa.app.cutepid.fragment_authen;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.armhansa.app.cutepid.controller.HomeActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.armhansa.app.cutepid.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;

import java.util.Arrays;

public class MenuLoginFragment extends Fragment
        implements CommonFirebase.FirebaseGetSingleValueListener {

    private View rootView;

    private CallbackManager callbackManager;
    private Profile profile;

    private ProgressDialog progressDialog;
    private int count = 2;

    private final String ERROR_MESSAGE = "Something wrong. Please try again.";

    public MenuLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_menu_login, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Login...");

        Button phoneLoginBtn = rootView.findViewById(R.id.loginBtn);
        phoneLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPageLogin();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginButton facebookLoginBtn = rootView.findViewById(R.id.facebookBtn);
        facebookLoginBtn.setFragment(this);
        facebookLoginBtn.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_friends"));
        facebookLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(loginResult != null) {
                    try {
                        profile = Profile.getCurrentProfile();

                        progressDialog.show();

                        CommonFirebase firebase = new CommonFirebase("users");
                        firebase.setFirebaseGetSingleValueListener(MenuLoginFragment.this);
                        firebase.getAccount(profile.getId(), true);


                    } catch (Exception ex) {
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getActivity(), ERROR_MESSAGE, Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                    }

                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void doOnSingleDataChange(DataSnapshot dataSnapshot) {
        User user_tmp = dataSnapshot.getValue(User.class);

        if(user_tmp != null) {
            User.setOwnAccount(user_tmp);

            CommonSharePreference preference = new CommonSharePreference(getActivity());
            preference.save("UserID", user_tmp.getId());

            Intent goToHome = new Intent(getActivity(), HomeActivity.class);
            startActivity(goToHome);
            getActivity().finish();

            progressDialog.dismiss();

        } else {
            user_tmp = new User();
            user_tmp.setFacebookUser(true);
            user_tmp.setId(profile.getId());
            user_tmp.setFirstName(profile.getFirstName());
            String photoUri = profile.getProfilePictureUri(720, 720).toString();
            user_tmp.setProfile(photoUri);

            User.setOwnAccount(user_tmp);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.mainLoginFragment, new SetBirthDayFragment())
                    .addToBackStack(null)
                    .commit();

            progressDialog.dismiss();
        }
    }

    private void nextPageLogin() {
        User user_tmp = new User();
        user_tmp.setFacebookUser(false);
        User.setOwnAccount(user_tmp);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.mainLoginFragment, new PhoneNumberLoginFragment())
                .addToBackStack(null)
                .commit();
    }
}
