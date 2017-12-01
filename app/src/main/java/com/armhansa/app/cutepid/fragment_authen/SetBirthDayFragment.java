package com.armhansa.app.cutepid.fragment_authen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.model.User;

import java.util.Calendar;

public class SetBirthDayFragment extends Fragment
        implements View.OnClickListener{

    View rootView;

    Button nextBtn;

    DatePicker datePicker;

    public SetBirthDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_set_birth_day, container, false);

        datePicker = rootView.findViewById(R.id.birthDay);

        nextBtn = rootView.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        User.getOwnerAccount().setBirthDay(calendar.getTime());
        if(User.getOwnerAccount().getAge() >= 18) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.mainLoginFragment, new SetGenderFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Min age is 18 years", Toast.LENGTH_LONG).show();

        }


    }
}
