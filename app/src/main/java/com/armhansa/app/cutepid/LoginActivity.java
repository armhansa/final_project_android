package com.armhansa.app.cutepid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.armhansa.app.cutepid.fragment_authen.MenuLoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainLoginFragment, new MenuLoginFragment())
                .commit();
    }
}
