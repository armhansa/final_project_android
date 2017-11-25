package com.armhansa.app.cutepid.fragment_home;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.adapter.ChatAdapter;
import com.armhansa.app.cutepid.firebase_cloud_message.MyFirebaseInstanceIDService;
import com.armhansa.app.cutepid.firebase_cloud_message.SharedPrefManager;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.model.UserList;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseError;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
        implements ChatAdapter.ChatListener
        , CommonFirebase.FirebaseGetMultiValueListener {

    View rootView;

    public UserList randomUserFiltered;
    public User randomUser;

    TextView noUser;
    LinearLayout allContent;

    private ImageView profile;
    private TextView firstName, status;
    private Button likeBtn, disLikeBtn;
//    private BroadcastReceiver broadcastReceiver;

    private ProgressDialog progressDialog;

//     tmp
//    private ChatAdapter chatAdapter;
//    public RecyclerView listChat;
//



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        randomUserFiltered = new UserList();

        noUser = rootView.findViewById(R.id.noUser);
        noUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        allContent = rootView.findViewById(R.id.allContent);

        profile = rootView.findViewById(R.id.profile);
        firstName = rootView.findViewById(R.id.firstName);
        status = rootView.findViewById(R.id.status);
        likeBtn = rootView.findViewById(R.id.like);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                like();
            }
        });
        disLikeBtn = rootView.findViewById(R.id.dislike);
        disLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disLike();
            }
        });


//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                firstName.setText(SharedPrefManager.getInstance(getContext()).getToken());
//            }
//        };
//
//        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));


//        if(SharedPrefManager.getInstance(getContext()).getToken() == null) {
//            Toast.makeText(getContext(), "Token is Null", Toast.LENGTH_LONG).show();
//        }
//        Test
//        listChat = rootView.findViewById(R.id.listChat);
//        listChat.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        chatAdapter = new ChatAdapter(getActivity());
//        chatAdapter.setListener(this);
//
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        progressDialog.show();

        CommonFirebase firebase = new CommonFirebase("users");
        firebase.setFirebaseGetMultiValueListener(this);
        firebase.setContext(getContext());
        firebase.getUsersPassFilter();


        return rootView;
    }

    private void refresh() {
        progressDialog.setMessage("Loading...");

        progressDialog.show();

        CommonFirebase firebase = new CommonFirebase("users");
        firebase.setFirebaseGetMultiValueListener(this);
        firebase.setContext(getContext());
        firebase.getUsersPassFilter();
    }


    @Override
    public void doOnMultiDataChange(List<User> dataUser) {
        if(dataUser != null && dataUser.size() > 0) {
            randomUserFiltered.setUsers(dataUser);

            allContent.setVisibility(View.VISIBLE);
            noUser.setVisibility(View.GONE);
            switchRandom();

//            chatAdapter.setUsers(dataUser);
//            listChat.setAdapter(chatAdapter);

            progressDialog.dismiss();
        } else {
            Toast.makeText(getContext(), "Error UserFelt and UserFilter?"
                    , Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void doOnMultiCancelled(DatabaseError databaseError) {
        Toast.makeText(getContext(), "Error UserFelt and UserFilter?", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    private void switchRandom() {
        if(randomUserFiltered.getUsers().size() > 0) {
            randomUser = randomUserFiltered.getRandomUser();

            Glide.with(getContext())
                    .load(randomUser.getProfile())
//                .apply(myOption)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(150)))
                    .into(profile);
            firstName.setText(randomUser.getFirstName());
            status.setText(randomUser.getStatus());
        } else {
            // Show text Not Have Someone
            allContent.setVisibility(View.GONE);
            noUser.setVisibility(View.VISIBLE);
        }

    }

    private void like() {

        switchRandom();

    }

    private void disLike() {

        switchRandom();

    }

    @Override
    public void onClickInItem(int position) {

    }
}
