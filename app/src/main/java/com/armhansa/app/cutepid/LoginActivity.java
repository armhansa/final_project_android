package com.armhansa.app.cutepid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.armhansa.app.cutepid.controller.CommonSharePreference;
import com.armhansa.app.cutepid.fragment_authen.MenuLoginFragment;
import com.armhansa.app.cutepid.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    CommonSharePreference preference;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            Intent goToHome = new Intent(LoginActivity.this, MainActivity.class);
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
