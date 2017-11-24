package com.armhansa.app.cutepid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.armhansa.app.cutepid.fragment_authen.MenuLoginFragment;
import com.armhansa.app.cutepid.model.User;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class LoginActivity extends AppCompatActivity {

    CommonSharePreference preference;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        preference = new CommonSharePreference(this);
        user = (User) preference.read("User", User.class);
        if(user != null) {
//            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//            DatabaseReference mUsers = mRootRef.child("user").child("KzNg2LSbDQw1UmpueWZ");
//            mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    User tmp_user = dataSnapshot.getValue(User.class);
//                    if(tmp_user != null) {
//                        user = tmp_user;
            Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(goToHome);
            finish();
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Log.e("", databaseError.getMessage());
//                }
//            });
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainLoginFragment, new MenuLoginFragment())
                    .commit();
        }

    }
}
