package com.armhansa.app.cutepid.fragment_authen;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.armhansa.app.cutepid.LoginActivity;
import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.model.User;
import com.armhansa.app.cutepid.tool.GetPostImage;
import com.armhansa.app.cutepid.validation.NameValidation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileDescriptor;
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

            progressDialog.show();

            StorageReference ref = storageRef.child("profileImages/"
                    + User.getOwnerAccount().getId());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();

                    User.getOwnerAccount().setProfile(taskSnapshot.getDownloadUrl().toString());

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLoginFragment, new SetBirthDayFragment())
                            .addToBackStack(null)
                            .commit();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Failed "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double process = (100.0 * taskSnapshot.getBytesTransferred()
                            /taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded"+ (int) process+"%");
                }
            });
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
