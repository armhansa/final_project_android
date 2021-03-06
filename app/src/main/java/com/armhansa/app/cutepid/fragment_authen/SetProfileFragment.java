package com.armhansa.app.cutepid.fragment_authen;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.CommonFirebaseStorage;
import com.armhansa.app.cutepid.validation.NameValidation;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class SetProfileFragment extends Fragment
        implements View.OnClickListener{

    View rootView;
    private static int RESULT_LOAD_IMAGE = 1;

    ProgressDialog progressDialog;

    ImageView addImageBtn;
    EditText firstName;

    Button nextBtn;

    Uri filePath;

    FirebaseStorage storage;
    StorageReference storageRef;

    public SetProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_set_profile, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");

        addImageBtn = rootView.findViewById(R.id.addImageBtn);
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        firstName = rootView.findViewById(R.id.firstName);
        nextBtn = rootView.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        // Firebase Init
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        return rootView;
    }

    @Override
    public void onClick(View view) {
        // Easy Validation

        NameValidation validation = new NameValidation();
        validation.setName(firstName.getText().toString());

        if(validation.invalid()) {
            Toast.makeText(getContext(), validation.alert(), Toast.LENGTH_LONG).show();

        } else {
            User.getOwnerAccount().setFirstName(firstName.getText().toString());

            uploadImage();

        }

    }

    private void uploadImage() {

//        if(filePath == null) {
//            Uri tmp = Uri.parse("https://firebasestorage.googleapis.com/v0/b/cutepid-7bc10." +
//                    "appspot.com/o/profileImages%2F0.png?alt=media&token=c03b6ae1-2b1b-442c" +
//                    "-9001-740b2cae3d09");
//            filePath = tmp;
//        }


        if(filePath != null) {

            CommonFirebaseStorage storage = new CommonFirebaseStorage();
            storage.uploadProfile(progressDialog, filePath, getActivity());

        } else {
            User.getOwnerAccount().setProfile(
                    "https://firebasestorage.googleapis.com/" +
                            "v0/b/cutepid-7bc10.appspot.com/o/" +
                            "profileImages%2F0.png?" +
                            "alt=media&token=c03b6ae1-2b1b-442c-9001-740b2cae3d09");

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainLoginFragment, new SetBirthDayFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media
                        .getBitmap(getActivity().getContentResolver(), filePath);
                addImageBtn.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
