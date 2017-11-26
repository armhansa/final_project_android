package com.armhansa.app.cutepid;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonFirebase;
import com.armhansa.app.cutepid.validation.NameValidation;
import com.google.android.gms.tasks.Task;

public class EditInfoActivity extends AppCompatActivity
        implements View.OnClickListener, CommonFirebase.FirebaseSetValueListener {

    EditText firstName, status;
    Button menBtn, womenBtn, editBtn;
    boolean isMen;

    ProgressDialog progressDialog;

    int color[] = {Color.rgb(144, 144,255)
            , Color.rgb(255, 255, 255)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        firstName = findViewById(R.id.firstName);
        status = findViewById(R.id.status);
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
        editBtn = findViewById(R.id.edit);
        editBtn.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

        setValue();

    }

    public void setValue() {
        User user_tmp = User.getOwnerAccount();

        firstName.setText(user_tmp.getFirstName());
        status.setText(user_tmp.getStatus());
        isMen = !"Men".equals(user_tmp.getGender());

        switchType();

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


        NameValidation nameValidation = new NameValidation();
        nameValidation.setName(firstName.getText().toString());

        if(nameValidation.invalid()) {
            Toast.makeText(this, nameValidation.alert(), Toast.LENGTH_LONG).show();

        } else {
            progressDialog.setMessage("Editing");
            progressDialog.show();

            User.getOwnerAccount().setFirstName(firstName.getText().toString());
            User.getOwnerAccount().setGender(isMen ? "Men" : "Women");
            String statusTxt = status.getText().toString();
            if(!statusTxt.isEmpty())
                User.getOwnerAccount().setStatus(statusTxt);

            CommonFirebase firebase = new CommonFirebase("users");
            firebase.setFirebaseSetValueListener(this);
            firebase.set(User.getOwnerAccount().getId(), User.getOwnerAccount());

        }

    }

    @Override
    public void doOnComplete(Task<Void> task) {
        finish();

        progressDialog.dismiss();

    }
}
