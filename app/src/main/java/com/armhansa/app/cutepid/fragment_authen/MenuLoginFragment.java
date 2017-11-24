package com.armhansa.app.cutepid.fragment_authen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.armhansa.app.cutepid.HomeActivity;
import com.armhansa.app.cutepid.LoginActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.armhansa.app.cutepid.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MenuLoginFragment extends Fragment{

    View rootView;

    ImageView profileImage;

    CallbackManager callbackManager;

    Profile profile;
    User user;

    public MenuLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_menu_login, container, false);

        LoginActivity.user = new User();
        profileImage = rootView.findViewById(R.id.imageProfile);

        final Button loginBtn = rootView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPageLogin();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = rootView.findViewById(R.id.facebookBtn);
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(loginResult != null) {
                    try {
                        profile = Profile.getCurrentProfile();
                        String userId = profile.getId();

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("users").child(profile.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user_tmp = dataSnapshot.getValue(User.class);
                                LoginActivity.user.setFacebookUser(true);
                                if(user_tmp != null) {
                                    user = user_tmp;
                                    CommonSharePreference preference = new CommonSharePreference(getActivity());
                                    preference.save("User", LoginActivity.user);

                                    Intent goToHome = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(goToHome);
                                    getActivity().finish();

                                } else {
                                    LoginActivity.user.setId(profile.getId());
                                    String photoUri = profile.getProfilePictureUri(720, 720).toString();
                                    LoginActivity.user.setFirstName(profile.getFirstName());
                                    LoginActivity.user.setProfile(photoUri);

                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.mainLoginFragment, new SetBirthDayFragment())
                                            .addToBackStack(null)
                                            .commit();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });



                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), "Please Login with facebook", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(), "Facebook Login Error, please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void nextPageLogin() {
        LoginActivity.user.setFacebookUser(false);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainLoginFragment, new PhoneNumberLoginFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
