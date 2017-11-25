package com.armhansa.app.cutepid.fragment_home;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.adapter.ChatAdapter;
import com.armhansa.app.cutepid.model.Feeling;
import com.armhansa.app.cutepid.model.Interest;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
        implements CommonFirebase.FirebaseGetSingleValueListener
        , CommonFirebase.FirebaseGetMultiValueListener
        , ChatAdapter.ChatListener{

    View rootView;

    private ProgressDialog progressDialog;
    private int count = 2;

    private ChatAdapter chatAdapter;
    public RecyclerView listChat;

    public List<User> randomOnFilter;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

//        Test
        listChat = rootView.findViewById(R.id.listChat);
        listChat.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatAdapter = new ChatAdapter(getActivity());
        chatAdapter.setListener(this);
//



        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        progressDialog.show();

        User user = User.getOwnerAccount();

        CommonFirebase firebase = new CommonFirebase("/interests");
        firebase.setFirebaseGetSingleValueListener(this);
        firebase.getAccount(user.getId());

        return rootView;
    }

    @Override
    public void doOnSingleDataChange(DataSnapshot dataSnapshot) {
        try {
            Interest.setInterest(dataSnapshot.getValue(Interest.class));

        } catch (Exception ex) {
            try {
                Feeling.setMyFeeling(dataSnapshot.getValue(Feeling.class));
                Feeling.getMyFeeling();
            } catch (Exception ex2) {
                count++;
                Toast.makeText(getContext(), "Error Feeling and Interest?"
                        , Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }
        if(--count <= 0) {
            CommonFirebase firebase = new CommonFirebase("users");
            firebase.setFirebaseGetMultiValueListener(this);
            firebase.getUsersPassFilter();

        }
    }

    @Override
    public void doOnSingleCancelled(DatabaseError databaseError) {
        Toast.makeText(getContext(), "Error Feeling and Interest?", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    @Override
    public void doOnMultiDataChange(List<User> dataUser) {
        if(dataUser != null && dataUser.size() > 0) {
            randomOnFilter = dataUser;

            chatAdapter = new ChatAdapter(getActivity());
            chatAdapter.setUsers(dataUser);
            chatAdapter.setListener(this);

            progressDialog.dismiss();
        } else {
            Toast.makeText(getContext(), "Error Feeling and Interest?"
                    , Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void doOnMultiCancelled(DatabaseError databaseError) {
        Toast.makeText(getContext(), "Error Feeling and Interest?", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    private void switchRandom() {

    }

    private void like() {

    }

    private void disLike() {

    }

    @Override
    public void onClickInItem(int position) {

    }
}
