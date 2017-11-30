package com.armhansa.app.cutepid.fragment_home;

import android.app.ProgressDialog;
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
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.model.UserChatter;
import com.armhansa.app.cutepid.model.UserFelt;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.tool.CommonNotification;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeFragment extends Fragment {

    View rootView;

    public List<String> allUserIdPassed;
    public User randomUser;

    TextView noUser;
    LinearLayout allContent;

    private ImageView profile;
    private TextView firstName, status;

    private CommonFirebase firebase;
    private ValueEventListener firebaseListener;

    private ProgressDialog progressDialog;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initialValue();
        setValue();

        return rootView;
    }

    public void initialValue() {
        firebase = new CommonFirebase("users");

        noUser = rootView.findViewById(R.id.noUser);
        noUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue();
            }
        });
        allContent = rootView.findViewById(R.id.allContent);

        profile = rootView.findViewById(R.id.profile);
        firstName = rootView.findViewById(R.id.firstName);
        status = rootView.findViewById(R.id.status);
        Button likeBtn = rootView.findViewById(R.id.like);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                like();
            }
        });
        Button disLikeBtn = rootView.findViewById(R.id.dislike);
        disLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disLike();
            }
        });

        progressDialog = new ProgressDialog(getContext());
    }

    public void setValue() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        if(firebaseListener != null) {
            firebase.getmDatabase().child(randomUser.getId()).removeEventListener(firebaseListener);

        }

        CommonFirebase firebaseMulti = new CommonFirebase("users");
        firebaseMulti.setFirebaseGetMultiValueListener(
                new CommonFirebase.FirebaseGetMultiValueListener() {
            @Override
            public void doOnMultiDataChange(List<String> dataUser) {
                if(dataUser != null && dataUser.size() > 0) {
                    allUserIdPassed = dataUser;

                    allContent.setVisibility(View.VISIBLE);
                    noUser.setVisibility(View.GONE);

                    Random random = new Random();

                    firebase.setFirebaseGetSingleValueListener(
                            new CommonFirebase.FirebaseGetSingleValueListener() {
                        @Override
                        public void doOnSingleDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue(User.class) != null) {
                                randomUser = dataSnapshot.getValue(User.class);

                                Glide.with(getContext())
                                        .load(randomUser.getProfile())
                                        .apply(new RequestOptions().transforms(
                                                new CenterCrop()
                                                , new RoundedCorners(150)))
                                        .into(profile);
                                firstName.setText(MessageFormat.format(
                                        "{0}, {1}", randomUser.getFirstName()
                                        , randomUser.getAge()));
                                status.setText(randomUser.getStatus());

                                progressDialog.dismiss();
                            }
                        }

                    });
                    firebaseListener = firebase.getAccount(
                            allUserIdPassed.get(random.nextInt(allUserIdPassed.size()))
                            , false);

                } else {
                    // Show text Not Have Someone
                    allContent.setVisibility(View.GONE);
                    noUser.setVisibility(View.VISIBLE);

                    progressDialog.dismiss();
                }
            }

        });
        firebaseMulti.getUsersPassFilter();

    }



    private void like() {
        progressDialog.show();

        UserFelt felt_tmp = User.getOwnerAccount().getMyUserFelt();
        felt_tmp.addLiked(randomUser.getId());
        User.getOwnerAccount().setMyUserFelt(felt_tmp);

        String user_tmp = User.getOwnerAccount().getId();

        CommonFirebase firebase = new CommonFirebase("users");

        if(randomUser.getMyUserFelt().hasLiked(user_tmp)) {
            // Notification
            CommonNotification notification = CommonNotification
                    .getInstance(getContext(), "You have New Match");
            notification.notify("You and "+ randomUser.getFirstName()+ " now Match. Let's see.");

            // Add Chat for User
            UserChatter myChatter_tmp = User.getOwnerAccount().getMyUserChatter();
            myChatter_tmp.addChatter(randomUser.getId());
            User.getOwnerAccount().setMyUserChatter(myChatter_tmp);

            // Add Chat for randomUser
            UserChatter chatter_tmp = randomUser.getMyUserChatter();
            chatter_tmp.addChatter(user_tmp);
            randomUser.setMyUserChatter(chatter_tmp);

            firebase.setWithOutListener(randomUser.getId(), randomUser);

        }

        firebase.setWithOutListener(User.getOwnerAccount().getId(), User.getOwnerAccount());

        setValue();

        progressDialog.dismiss();


    }

    private void disLike() {
        progressDialog.show();

        UserFelt felt_tmp = User.getOwnerAccount().getMyUserFelt();
        felt_tmp.addDisLiked(randomUser.getId());
        User.getOwnerAccount().setMyUserFelt(felt_tmp);

        CommonFirebase firebase = new CommonFirebase("users");

        firebase.setWithOutListener(User.getOwnerAccount().getId(), User.getOwnerAccount());

        setValue();

        progressDialog.dismiss();

    }

}
