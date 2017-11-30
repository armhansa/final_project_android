package com.armhansa.app.cutepid.tool;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.fragment_authen.SetBirthDayFragment;
import com.armhansa.app.cutepid.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CommonFirebaseStorage {

    FirebaseStorage storage;
    StorageReference storageRef;

    public CommonFirebaseStorage() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    public void uploadProfile(final ProgressDialog progressDialog, Uri filePath, final FragmentActivity activity) {
        progressDialog.show();

        StorageReference ref = storageRef.child("profileImages/"
                + User.getOwnerAccount().getId());
        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(activity, "Uploaded", Toast.LENGTH_SHORT).show();

                User.getOwnerAccount().setProfile(taskSnapshot.getDownloadUrl().toString());

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainLoginFragment, new SetBirthDayFragment())
                        .addToBackStack(null)
                        .commit();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(activity, "Failed "+e.getMessage()
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
    }
}
