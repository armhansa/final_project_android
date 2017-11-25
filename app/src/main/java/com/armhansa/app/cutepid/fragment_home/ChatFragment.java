package com.armhansa.app.cutepid.fragment_home;


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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment
implements ChatAdapter.ChatListener{

    private View rootView;

    private List<User> chatter;

    private TextView noChatter;
    private RecyclerView listChat;

    private ChatAdapter chatAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        noChatter = rootView.findViewById(R.id.noChater);
        listChat = rootView.findViewById(R.id.listChat);
        listChat.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatAdapter = new ChatAdapter(getActivity());
        chatAdapter.setListener(this);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();

                for (DataSnapshot i :
                        dataSnapshot.getChildren()) {
                    users.add(i.getValue(User.class));
                }

                if(users.size() != 0) {
                    chatter = users;
                    noChatter.setVisibility(View.GONE);
                    listChat.setVisibility(View.VISIBLE);
                    chatAdapter.setUsers(users);
                    listChat.setAdapter(chatAdapter);

                } else {
                    noChatter.setVisibility(View.VISIBLE);
                    listChat.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Chatter is Cancelled", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }

    @Override
    public void onClickInItem(int position) {

    }
}
