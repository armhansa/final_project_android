package com.armhansa.app.cutepid.fragment_authen;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.armhansa.app.cutepid.HomeActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.model.UserChatter;
import com.armhansa.app.cutepid.model.UserFilter;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.model.UserFelt;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetGenderFragment extends Fragment
        implements View.OnClickListener, CommonFirebase.FirebaseSetValueListener {

    View rootView;

    int color[] = {Color.rgb(144, 144,255)
            , Color.rgb(255, 255, 255)};

    boolean isMen = false;
    Button menBtn;
    Button womenBtn;

    Button nextBtn;

    ProgressDialog progressDialog;

    public SetGenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_set_gender, container, false);

        progressDialog = new ProgressDialog(getContext());

        menBtn = rootView.findViewById(R.id.men);
        menBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchType();
            }
        });
        womenBtn = rootView.findViewById(R.id.women);
        womenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchType();
            }
        });
        switchType();

        nextBtn = rootView.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        if(!User.getOwnerAccount().isFacebookUser()) {
            nextBtn.setText("Next");
            nextBtn.setTextColor(Color.rgb(0, 0, 0));
            nextBtn.setBackgroundColor(Color.rgb(255, 255, 255));
        }


        return rootView;
    }

    public void switchType() {
        isMen = !isMen;

        menBtn.setBackgroundColor(color[isMen ? 0: 1]);
        womenBtn.setBackgroundColor(color[isMen ? 1: 0]);
        menBtn.setEnabled(!isMen);
        womenBtn.setEnabled(isMen);

    }

    @Override
    public void onClick(View view) {

        User.getOwnerAccount().setGender(isMen ? "Men" : "Women");

        if(User.getOwnerAccount().isFacebookUser()) {
            progressDialog.setMessage("Login...");
            progressDialog.show();

            String userId = User.getOwnerAccount().getId();

            CommonSharePreference preference = new CommonSharePreference(getActivity());
            preference.save("UserID", User.getOwnerAccount().getId());

            UserFilter myUserFilter = new UserFilter(
                    isMen ? "Women" : "Men", 18
                    , User.getOwnerAccount().getAge()+12);

            UserFelt myFelt = new UserFelt();

            UserChatter myUserChatter = new UserChatter();

            User.getOwnerAccount().setMyUserFilter(myUserFilter);
            User.getOwnerAccount().setMyUserFelt(myFelt);
            User.getOwnerAccount().setMyUserChatter(myUserChatter);

            CommonFirebase firebase = new CommonFirebase("users");
            firebase.setFirebaseSetValueListener(this);
            firebase.setWithListener(userId, User.getOwnerAccount());

        } else {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.mainLoginFragment, SetPasswordFragment.newInstance(false))
                    .addToBackStack(null)
                    .commit();
        }



    }

    @Override
    public void doOnComplete(Task<Void> task) {
        Intent goToHome = new Intent(getActivity(), HomeActivity.class);
        startActivity(goToHome);
        getActivity().finish();

        progressDialog.dismiss();


    }
}
