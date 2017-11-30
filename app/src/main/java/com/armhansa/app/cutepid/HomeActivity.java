package com.armhansa.app.cutepid;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.armhansa.app.cutepid.fragment_home.ChatFragment;
import com.armhansa.app.cutepid.fragment_home.HomeFragment;
import com.armhansa.app.cutepid.fragment_home.ProfileFragment;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.google.firebase.database.DataSnapshot;

public class HomeActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    CommonFirebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        CommonSharePreference preference = new CommonSharePreference(this);
        String userId = (String) preference.read("UserID", String.class);

        if(userId != null) {
            firebase = new CommonFirebase("users");
            firebase.setFirebaseGetSingleValueListener(
                    new CommonFirebase.FirebaseGetSingleValueListener() {
                        @Override
                        public void doOnSingleDataChange(DataSnapshot dataSnapshot) {
                            User user_tmp = dataSnapshot.getValue(User.class);
                            if(user_tmp != null) {
                                User.setOwnAccount(user_tmp);
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(getApplicationContext()
                                        , "Error null", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }

                        }

            });
            firebase.getAccount(userId, false);

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            ViewPager mViewPager = findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

//            ItMakeTestFailed
//            mViewPager.setCurrentItem(1);

            TabLayout tabLayout = findViewById(R.id.tabs);

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new ProfileFragment();
                case 1:
                    return new HomeFragment();
                default:
                    return new ChatFragment();

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }

}
