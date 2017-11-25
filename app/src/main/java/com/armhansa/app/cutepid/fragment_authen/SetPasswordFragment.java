package com.armhansa.app.cutepid.fragment_authen;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.armhansa.app.cutepid.HomeActivity;
import com.armhansa.app.cutepid.LoginActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.model.Feeling;
import com.armhansa.app.cutepid.model.Interest;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SetPasswordFragment extends Fragment
        implements View.OnClickListener, CommonFirebase.FirebaseSetValueListener{

    View rootView;

    TextView passwordTxt;

    EditText password;

    Button nextBtn;

    boolean isLogin;

    ProgressDialog progressDialog;
    private int count = 3;

    public SetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_set_password, container, false);

        progressDialog = new ProgressDialog(getContext());

        passwordTxt = rootView.findViewById(R.id.passwordTxt);
// Set Password Text Must Have New Argument

        password = rootView.findViewById(R.id.passwordInput);

        nextBtn = rootView.findViewById(R.id.phoneLoginBtn);
        nextBtn.setOnClickListener(this);

        isLogin = getArguments().getBoolean("IsLogin");

        return rootView;
    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage("Login...");
        progressDialog.show();

        if(isLogin) {
            if (User.getOwnerAccount().getPassword().equals(password.getText().toString())) {
                login();
                progressDialog.dismiss();

            } else {
                Toast.makeText(getActivity(), "Invalid Password, please try again.", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        } else {
//            Invalidation

            // Set Value
            User.getOwnerAccount().setPassword(password.getText().toString());

            User user_tmp = User.getOwnerAccount();

            CommonFirebase firebase = new CommonFirebase("users");
            firebase.setFirebaseSetValueListener(this);
            firebase.set(user_tmp.getId(), user_tmp);

            Interest myInterest = Interest.getInterest(
                    "Men".equals(user_tmp.getGender()) ? "Women" : "Men"
                    , 18, user_tmp.getAge()+12);

            CommonFirebase firebase1 = new CommonFirebase("interests");
            firebase1.setFirebaseSetValueListener(this);
            firebase1.set(user_tmp.getId(), myInterest);

            Feeling felt = Feeling.getMyFeeling();

            CommonFirebase firebase2 = new CommonFirebase("felt");
            firebase2.setFirebaseSetValueListener(this);
            firebase2.set(user_tmp.getId(), felt);



        }

    }

    public void login() {
        CommonSharePreference preference = new CommonSharePreference(getActivity());
        preference.save("UserID", User.getOwnerAccount().getId());

        Intent menuActivity = new Intent(getActivity(), HomeActivity.class);
        startActivity(menuActivity);
        getActivity().finish();

    }

    public static SetPasswordFragment newInstance(boolean isLogin) {

        Bundle args = new Bundle();

        SetPasswordFragment fragment = new SetPasswordFragment();
        args.putBoolean("IsLogin", isLogin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void doOnComplete(Task<Void> task) {
        if(--count <= 0) {
            login();
            progressDialog.dismiss();

        }
    }
}
