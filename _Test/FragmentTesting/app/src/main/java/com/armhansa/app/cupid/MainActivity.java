package com.armhansa.app.cupid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.armhansa.app.cupid.fragment.PageAFragment;
import com.armhansa.app.cupid.fragment.PageBFragment;
import com.armhansa.app.cupid.fragment.PageCFragment;
import com.armhansa.app.cupid.fragment.TestFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragment, new TestFragment())
                .commit();

    }

}
