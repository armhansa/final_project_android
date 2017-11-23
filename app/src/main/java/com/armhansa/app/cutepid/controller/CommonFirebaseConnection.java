package com.armhansa.app.cutepid.controller;

import com.armhansa.app.cutepid.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommonFirebaseConnection {

    private DatabaseReference mRootRef;

    private DataSnapshot object;

    public DatabaseReference getmRootRef() {
        return mRootRef;
    }

    public void setmRootRef(DatabaseReference mRootRef) {
        this.mRootRef = mRootRef;
    }

    public CommonFirebaseConnection(String path) {
        mRootRef = FirebaseDatabase.getInstance().getReference().child(path);
    }

    public void set(String key, Object value) {
        mRootRef.child(key).setValue(value);
    }

    public DataSnapshot getAt(String key) {
        object = null;

        mRootRef.equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                object = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return object;

    }

    public List<DataSnapshot> get(String key) {
        mRootRef.orderByChild("gender").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DataSnapshot> tmp = new ArrayList<>();
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    tmp.add(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }

}
