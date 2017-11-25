package com.armhansa.app.cutepid.tool;

import android.support.annotation.NonNull;

import com.armhansa.app.cutepid.model.Interest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CommonFirebase {

    private DatabaseReference mDatabase;

    public interface FirebaseSetValueListener {
        void doOnComplete(Task<Void> task);
    }

    public interface FirebaseGetSingleValueListener {
        void doOnSingleDataChange(DataSnapshot dataSnapshot);
        void doOnSingleCancelled(DatabaseError databaseError);
    }

    public interface FirebaseGetMultiValueListener {
        void doOnMultiDataChange(DataSnapshot dataSnapshot);
        void doOnMultiCancelled(DatabaseError databaseError);
    }

    private FirebaseSetValueListener firebaseSetValueListener;
    private FirebaseGetSingleValueListener firebaseGetSingleValueListener;
    private FirebaseGetMultiValueListener firebaseGetMultiValueListener;

    public void setFirebaseSetValueListener(FirebaseSetValueListener listener) {
        this.firebaseSetValueListener = listener;
    }

    public void setFirebaseGetSingleValueListener(FirebaseGetSingleValueListener listener) {
        this.firebaseGetSingleValueListener = listener;
    }

    public void setFirebaseGetMultiValueListener(FirebaseGetMultiValueListener listener) {
        this.firebaseGetMultiValueListener = listener;
    )

    public CommonFirebase(String path) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(path);
    }

    public void set(String key, Object value) {
        mDatabase.child(key).setValue(value).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                firebaseSetValueListener.doOnComplete(task);
            }
        });
    }

    public void getAccount(String userId) {
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        firebaseGetSingleValueListener.doOnSingleDataChange(dataSnapshot);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        firebaseGetSingleValueListener.doOnSingleCancelled(databaseError);

                    }
                });

    }

    public void getUsersPassFilter(Interest filter) {
        Query filtered = mDatabase.orderByChild("Gender").equalTo(filter.getGender());
        filtered.orderByChild("Age").startAt(filter.getMin_age()).endAt(filter.getMax_age());
        filtered.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseGetMultiValueListener.doOnMultiDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                firebaseGetMultiValueListener.doOnMultiCancelled(databaseError);
            }
        });

    }


}
