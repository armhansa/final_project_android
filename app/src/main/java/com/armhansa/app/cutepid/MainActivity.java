package com.armhansa.app.cutepid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.armhansa.app.cutepid.controller.CommonSharePreference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void logout(View view) {
        CommonSharePreference preference = new CommonSharePreference(this);
        preference.clear();
        finish();
    }
}
