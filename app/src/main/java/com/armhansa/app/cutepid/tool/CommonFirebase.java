package com.armhansa.app.cutepid.tool;

import android.support.annotation.NonNull;
import android.util.Log;

import com.armhansa.app.cutepid.model.UserChatter;
import com.armhansa.app.cutepid.model.UserFelt;
import com.armhansa.app.cutepid.model.UserFilter;
import com.armhansa.app.cutepid.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        void doOnMultiDataChange(List<String> dataUser);
        void doOnMultiCancelled(DatabaseError databaseError);
    }

    public interface FirebaseGetChatterListener {
        void doOnChatterDataChange(List<User> dataUser);
        void doOnChatterCancelled(DatabaseError databaseError);
    }

    private FirebaseSetValueListener firebaseSetValueListener;
    private FirebaseGetSingleValueListener firebaseGetSingleValueListener;
    private FirebaseGetMultiValueListener firebaseGetMultiValueListener;
    private FirebaseGetChatterListener firebaseGetChatterListener;

    public void setFirebaseSetValueListener(FirebaseSetValueListener listener) {
        this.firebaseSetValueListener = listener;
    }

    public void setFirebaseGetSingleValueListener(FirebaseGetSingleValueListener listener) {
        this.firebaseGetSingleValueListener = listener;
    }

    public void setFirebaseGetMultiValueListener(FirebaseGetMultiValueListener listener) {
        this.firebaseGetMultiValueListener = listener;
    }

    public void setFirebaseGetChatterListener(FirebaseGetChatterListener listener) {
        this.firebaseGetChatterListener = listener;
    }

    public CommonFirebase(String path) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child(path);
    }

    public void setWithOutListener(String key, Object value) {
        mDatabase.child(key).setValue(value);
    }

    public void setWithListener(String key, Object value) {
        mDatabase.child(key).setValue(value).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                firebaseSetValueListener.doOnComplete(task);
            }
        });
    }

    public ValueEventListener getAccount(String userId, boolean forSingleEvent) {
        if(forSingleEvent) {
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
            return null;
        } else {
            return mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
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



    }


    public void getUsersPassFilter() {

        final UserFilter filter = User.getOwnerAccount().getMyUserFilter();

        Query filtered = mDatabase.orderByChild("age")
                .startAt(filter.getMin_age())
                .endAt(filter.getMax_age());
        filtered.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> list = dataSnapshot.getChildren();

                UserFelt myUserFelt = User.getOwnerAccount().getMyUserFelt();

                // Filter User
                List<String> userList = new ArrayList<>();

                for (DataSnapshot i : list) {
                    User myUser = i.getValue(User.class);
                    if (myUser != null && !myUser.getId().equals(User.getOwnerAccount().getId())
                            && !myUserFelt.hasFelt(myUser.getId())
                            && myUser.getGender().equals(filter.getGender())) {
                        userList.add(myUser.getId());
                    }
                }

                // Setting data
                firebaseGetMultiValueListener.doOnMultiDataChange(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                firebaseGetMultiValueListener.doOnMultiCancelled(databaseError);
            }
        });

    }

    public void getChatterUser() {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> list = dataSnapshot.getChildren();

                UserChatter chatter = User.getOwnerAccount().getMyUserChatter();

                // Chatter User
                List<User> userList = new ArrayList<>();

                for (DataSnapshot i : list) {
                    User myUser = i.getValue(User.class);
                    if (myUser != null && chatter.hasChatter(myUser.getId())) {
                        userList.add(myUser);
                    }
                }

                // Setting data
                firebaseGetChatterListener.doOnChatterDataChange(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                firebaseGetChatterListener.doOnChatterCancelled(databaseError);
            }
        });

    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }
}
