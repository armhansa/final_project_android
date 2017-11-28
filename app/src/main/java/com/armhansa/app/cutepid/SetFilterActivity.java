package com.armhansa.app.cutepid;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.model.UserFilter;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.google.android.gms.tasks.Task;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

public class SetFilterActivity extends AppCompatActivity implements View.OnClickListener, CommonFirebase.FirebaseSetValueListener {

    RangeSeekBar<Integer> seekBar;
    private int nowMinSeek, nowMaxSeek;

    Button menBtn, womenBtn, setBtn;
    boolean isMen;

    ProgressDialog progressDialog;

    int color[] = {Color.rgb(144, 144,255)
            , Color.rgb(255, 255, 255)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_filter);

        menBtn = findViewById(R.id.men);
        menBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchType();
            }
        });
        womenBtn = findViewById(R.id.women);
        womenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchType();
            }
        });
        setBtn = findViewById(R.id.set);
        setBtn.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

        seekBar = findViewById(R.id.rangeSeekBar);
        seekBar.setRangeValues(18, 60);

        setValue();

        seekBar.setOnRangeSeekBarChangeListener(
                new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(
                    RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekBar
                nowMinSeek = minValue;
                nowMaxSeek = maxValue;
            }
        });

// !Get noticed while dragging
        seekBar.setNotifyWhileDragging(false);


    }

    private void setValue() {
        UserFilter userFilter_tmp = User.getOwnerAccount().getMyUserFilter();

        nowMinSeek = userFilter_tmp.getMin_age();
        nowMaxSeek = userFilter_tmp.getMax_age();

        seekBar.setSelectedMinValue(nowMinSeek);
        seekBar.setSelectedMaxValue(nowMaxSeek);

        isMen = !"Men".equals(userFilter_tmp.getGender());

        switchType();
    }

    private void switchType() {
        isMen = !isMen;

        menBtn.setBackgroundColor(color[isMen ? 0: 1]);
        womenBtn.setBackgroundColor(color[isMen ? 1: 0]);
        menBtn.setEnabled(!isMen);
        womenBtn.setEnabled(isMen);

    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage("Editing");
        progressDialog.show();

        UserFilter userFilter_tmp = User.getOwnerAccount().getMyUserFilter();

        userFilter_tmp.setGender(isMen ? "Men" : "Women");
        userFilter_tmp.setMin_age(nowMinSeek);
        userFilter_tmp.setMax_age(nowMaxSeek);

        User.getOwnerAccount().setMyUserFilter(userFilter_tmp);

        CommonFirebase firebase = new CommonFirebase("users");
        firebase.setFirebaseSetValueListener(this);
        firebase.setWithListener(User.getOwnerAccount().getId(), User.getOwnerAccount());
    }

    @Override
    public void doOnComplete(Task<Void> task) {
        finish();

        progressDialog.dismiss();
    }
}
