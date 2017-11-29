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
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.model.UserChatter;
import com.armhansa.app.cutepid.model.UserFelt;
import com.armhansa.app.cutepid.model.UserFilter;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.tool.CommonSharePreference;
import com.armhansa.app.cutepid.validation.PasswordValidation;
import com.google.android.gms.tasks.Task;

public class SetPasswordFragment extends Fragment
        implements View.OnClickListener, CommonFirebase.FirebaseSetValueListener{

    View rootView;

    TextView passwordTxt;

    EditText password;

    Button nextBtn;

    boolean isLogin;

    ProgressDialog progressDialog;

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

        if(isLogin) {
            progressDialog.show();

            if (User.getOwnerAccount()
                    .getPassword().equals(password.getText().toString())) {
                login();
                progressDialog.dismiss();

            } else {
                Toast.makeText(getActivity(), "Invalid Password, please try again.", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        } else {

//            Invalidation
            PasswordValidation invalidation = new PasswordValidation();
            invalidation.setPassword(password.getText().toString());
            if(invalidation.invalid()) {
                Toast.makeText(getContext(), invalidation.alert(), Toast.LENGTH_LONG).show();

            } else {
                progressDialog.show();

                // Set Value
                User.getOwnerAccount().setPassword(password.getText().toString());

                UserFilter myUserFilter = new UserFilter(
                        "Men".equals(User.getOwnerAccount().getGender()) ? "Women" : "Men"
                        , 18, User.getOwnerAccount().getAge()+12);

                UserFelt myFelt = new UserFelt();

                UserChatter myUserChatter = new UserChatter();

                User.getOwnerAccount().setMyUserFilter(myUserFilter);
                User.getOwnerAccount().setMyUserFelt(myFelt);
                User.getOwnerAccount().setMyUserChatter(myUserChatter);

                CommonFirebase firebase = new CommonFirebase("users");
                firebase.setFirebaseSetValueListener(this);
                firebase.setWithListener(User.getOwnerAccount().getId(), User.getOwnerAccount());

            }

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
        login();
        progressDialog.dismiss();

    }
}
