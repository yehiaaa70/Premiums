package com.example.premiums.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;


import com.example.premiums.MainActivity;
import com.example.premiums.Models.AdminUsers_model;
import com.example.premiums.Models.Information_Users_Model;
import com.example.premiums.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class SignUp2Fragment extends Fragment {
    private Toast toast;

    private DatabaseReference datarff;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private StorageTask storageTask;
    private Uri resultUri;

    private CircularProgressButton circularProgressButton;
    private TextInputEditText name_ed, age_ed, address_ed, phone_ed;
    private FloatingActionButton floatingActionButton;
    private CircleImageView imageView;

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up2, container, false);

        circularProgressButton = view.findViewById(R.id.cirSubmit_btn);
        name_ed = view.findViewById(R.id.name_ed);
        age_ed = view.findViewById(R.id.age_ed);
        address_ed = view.findViewById(R.id.Address_ed);
        phone_ed = view.findViewById(R.id.phone_ed);
        floatingActionButton = view.findViewById(R.id.add_image_Fbtn);
        imageView = view.findViewById(R.id.choose_profile_iv);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AllClickListener();
    }


    //For Choose Image From Your Phone
    private void OpenFileChooser() {
        resultUri = null;
        CropImage.activity(resultUri).start(getContext(),this);
        imageView.setImageURI(resultUri);
    }

    //For Insert The Data In Realtime In Firebase
    private void insert_info(View view) {
        mAuth = FirebaseAuth.getInstance();
        datarff = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");

        if (TextUtils.isEmpty(name_ed.getText().toString().trim())) {
            name_ed.setError("Enter your Name");
        } else if (TextUtils.isEmpty(age_ed.getText().toString().trim())) {
            age_ed.setError("Enter your Age");
        } else if (TextUtils.isEmpty(address_ed.getText().toString().trim())) {
            address_ed.setError("Enter your Address");
        } else if (TextUtils.isEmpty(phone_ed.getText().toString().trim())) {
            phone_ed.setError("Enter your Phone");
        } else {
            circularProgressButton.startAnimation();

            StorageReference FileReference = storageReference.child(System.currentTimeMillis()+ "." + getFileExt(resultUri));

            storageTask = FileReference.putFile(resultUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            AdminUsers_model info;
                            info = new AdminUsers_model(name_ed.getText().toString()
                                    , phone_ed.getText().toString()
                                    , address_ed.getText().toString()
                                    , age_ed.getText().toString()
                                    , resultUri.toString());

                            datarff.child(mAuth.getCurrentUser().getUid()).setValue(info);
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        }
                    });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Navigation.findNavController(view).popBackStack(R.id.homePageFragment, false);
                    circularProgressButton.revertAnimation();

                    toast = Toast.makeText(requireContext(), "      Done      ", Toast.LENGTH_LONG);
                    View Toview = toast.getView();
                    Toview.setBackgroundResource(R.drawable.success_shape);
                    toast.show();
                }
            }, 3000);
        }
    }

    public String getFileExt(Uri uri) {
        ContentResolver c = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(c.getType(uri));
    }

    //All Click Listener In This Fragment
    private void AllClickListener() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileChooser();
            }
        });

        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert_info(view);
            }
        });
    }


}

