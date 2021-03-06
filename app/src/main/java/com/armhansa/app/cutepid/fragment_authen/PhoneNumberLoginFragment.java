package com.armhansa.app.cutepid.fragment_authen;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.validation.PhoneNumberValidation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class PhoneNumberLoginFragment extends Fragment
        implements View.OnClickListener, CommonFirebase.FirebaseGetSingleValueListener {

    private View rootView;

    private EditText phone;
    private Button nextBtn;

    private String phoneNumber;

    private ProgressDialog progressDialog;

    public PhoneNumberLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_phone_number_login
                , container, false);

        progressDialog = new ProgressDialog(getContext());

        phone = rootView.findViewById(R.id.phone);
        nextBtn = rootView.findViewById(R.id.phoneLogin);
        nextBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        phoneNumber = phone.getText().toString();

        PhoneNumberValidation validation = new PhoneNumberValidation();
        validation.setPhoneNumber(phoneNumber);
        if(validation.invalid()) {
            Toast.makeText(getContext(), validation.alert(), Toast.LENGTH_LONG).show();

        } else {
            progressDialog.setMessage("Waiting...");
            progressDialog.show();

            CommonFirebase firebase = new CommonFirebase("users");
            firebase.setFirebaseGetSingleValueListener(this);
            firebase.getAccount(phoneNumber, true);

        }

    }

    @Override
    public void doOnSingleDataChange(DataSnapshot dataSnapshot) {
        User user_tmp = dataSnapshot.getValue(User.class);
        if(user_tmp != null) {
            User.setOwnAccount(user_tmp);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.mainLoginFragment, SetPasswordFragment.newInstance(true))
                    .addToBackStack(null)
                    .commit();

        } else {
            User.getOwnerAccount().setId(phoneNumber);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.mainLoginFragment, new SetProfileFragment())
                    .addToBackStack(null)
                    .commit();
        }
        progressDialog.dismiss();
    }

}
