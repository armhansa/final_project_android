package com.armhansa.app.cutepid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.armhansa.app.cutepid.controller.CommonSharePreference;
import com.armhansa.app.cutepid.fragment_home.ChatFragment;
import com.armhansa.app.cutepid.fragment_home.HomeFragment;
import com.armhansa.app.cutepid.fragment_home.ProfileFragment;
import com.armhansa.app.cutepid.model.User;

public class HomeActivity extends AppCompatActivity {

    private CommonSharePreference preference;
    public static User myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preference = new CommonSharePreference(this);
        myAccount = (User) preference.read("User", User.class);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainHomeFragment, new HomeFragment())
                .commit();

    }

    public void switchToProfile(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainHomeFragment, new ProfileFragment())
                .commit();
    }

    public void switchToHome(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainHomeFragment, new HomeFragment())
                .commit();
    }

    public void switchToChat(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainHomeFragment, new ChatFragment())
                .commit();
    }

}
