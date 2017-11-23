package com.armhansa.app.cutepid.fragment_authen;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.armhansa.app.cutepid.HomeActivity;
import com.armhansa.app.cutepid.LoginActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.controller.CommonSharePreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetGenderFragment extends Fragment implements View.OnClickListener {

    View rootView;

    int color[] = {Color.rgb(144, 144,255)
            , Color.rgb(255, 255, 255)};

    boolean isMen = false;
    Button menBtn;
    Button womenBtn;

    Button phoneLoginBtn;

    public SetGenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_set_gender, container, false);

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

        phoneLoginBtn = rootView.findViewById(R.id.phoneLoginBtn);
        phoneLoginBtn.setOnClickListener(this);





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

        LoginActivity.user.setGender(isMen ? "Men" : "Women");

        CommonSharePreference preference = new CommonSharePreference(getActivity());
        preference.save("User", LoginActivity.user);

        if(LoginActivity.user.isFacebookUser()) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(LoginActivity.user.getId()).setValue(LoginActivity.user);

            Intent goToHome = new Intent(getActivity(), HomeActivity.class);
            startActivity(goToHome);
            getActivity().finish();

        } else {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainLoginFragment, SetPasswordFragment.newInstance(false))
                    .addToBackStack(null)
                    .commit();
        }



    }
}