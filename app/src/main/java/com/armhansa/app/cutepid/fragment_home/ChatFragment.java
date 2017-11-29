package com.armhansa.app.cutepid.fragment_home;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.adapter.ChatAdapter;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.model.UserList;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment
        implements ChatAdapter.ChatListener, CommonFirebase.FirebaseGetChatterListener {

    private View rootView;


    private TextView noChatter;
    private RecyclerView listChat;

    private ChatAdapter chatAdapter;

    private UserList chatterList;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        chatterList = new UserList();

        noChatter = rootView.findViewById(R.id.noChater);
        listChat = rootView.findViewById(R.id.listChat);
        listChat.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatAdapter = new ChatAdapter(getActivity());
        chatAdapter.setListener(this);

        CommonFirebase firebase = new CommonFirebase("users");
        firebase.setFirebaseGetChatterListener(this);
        firebase.getChatterUser();


        return rootView;
    }

    @Override
    public void onClickInItem(int position) {

    }

    @Override
    public void doOnChatterDataChange(List<User> dataUser) {
        chatterList.setUsers(dataUser);

        if(chatterList.getUsers().size() != 0) {
            noChatter.setVisibility(View.GONE);
            listChat.setVisibility(View.VISIBLE);
            chatAdapter.setUsers(chatterList.getUsers());
            listChat.setAdapter(chatAdapter);

        } else {
            noChatter.setVisibility(View.VISIBLE);
            listChat.setVisibility(View.GONE);

        }
    }

}
