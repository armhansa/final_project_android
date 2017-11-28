package com.armhansa.app.cutepid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.armhansa.app.cutepid.fragment_home.ChatFragment;
import com.armhansa.app.cutepid.fragment_home.HomeFragment;
import com.armhansa.app.cutepid.fragment_home.ProfileFragment;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class HomeActivity extends AppCompatActivity {

    private CommonSharePreference preference;
    private ProgressDialog progressDialog;

    CommonFirebase firebase;

    private String id_test[] = {"10210757356870183", "1549554765131256", "1609312035758079"
            , "1620676541322575", "1677606392278937", "1714682678544039", "1819681874730726"
            , "2031094127128098", "2173016396057118", "0909828682", "0914939888"};

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        preference = new CommonSharePreference(this);
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

                        @Override
                        public void doOnSingleCancelled(DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext()
                                    , "Error : "+databaseError.getMessage()
                                    , Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();
                        }
            });
            firebase.getAccount(userId, false);

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setCurrentItem(0);
            mViewPager.setCurrentItem(1);

            TabLayout tabLayout = findViewById(R.id.tabs);

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        } else {
            LoginManager.getInstance().logOut();
//
            CommonSharePreference preference = new CommonSharePreference(this);
            preference.clear();

            Intent logout = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(logout);
            finish();

            Toast.makeText(this, "Error userId is Null", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



//    @Override
//    public void doOnComplete(Task<Void> task) {
//        progressDialog.dismiss();
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
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
