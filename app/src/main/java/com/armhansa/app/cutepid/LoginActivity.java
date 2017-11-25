package com.armhansa.app.cutepid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.armhansa.app.cutepid.fragment_authen.MenuLoginFragment;
import com.armhansa.app.cutepid.model.User;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class LoginActivity extends AppCompatActivity
        implements CommonFirebase.FirebaseGetSingleValueListener {

    private CommonSharePreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        preference = new CommonSharePreference(this);

        String userId = (String) preference.read("UserID", String.class);
        if (userId != null) {
            CommonFirebase firebase = new CommonFirebase("users");
            firebase.setFirebaseGetSingleValueListener(this);
            firebase.getAccount(userId);
        } else {
            goToLoginMenu();
        }
    }


    @Override
    public void doOnSingleDataChange(DataSnapshot dataSnapshot) {
        User tmp_user = dataSnapshot.getValue(User.class);
        if (tmp_user != null) {
            User.setOwnAccount(tmp_user);
            goToHome();

        } else {
            goToLoginMenu();

        }
    }

    @Override
    public void doOnSingleCancelled(DatabaseError databaseError) {
        goToLoginMenu();

    }

    private void goToHome() {
        Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(goToHome);
        finish();

    }

    private void goToLoginMenu() {
        preference.clear();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.mainLoginFragment, new MenuLoginFragment())
                .commit();
    }

}
