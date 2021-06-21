package com.example.premiums.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.premiums.Models.AdminUsers_model;
import com.example.premiums.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private TextInputEditText ProName_ed, ProAge_ed, PeoAddress_ed, ProPhone_ed;
    private TextInputLayout ProName_tf, ProAge_tf, PeoAddress_tf, ProPhone_tf;
    private FloatingActionButton floatingActionButton;
    private CircleImageView imageView;
    private TextView TopName, ProEmail, closeWin;
    private ProgressBar process;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private Uri uri;
    private StorageTask storageTask;

    private AdminUsers_model adminModel;
    private ArrayList<AdminUsers_model> list = new ArrayList<>();


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                imageView.setImageURI(uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ProName_ed = view.findViewById(R.id.GetName_ed);
        ProAge_ed = view.findViewById(R.id.GetAge_ed);
        PeoAddress_ed = view.findViewById(R.id.GetAddress_ed);
        ProPhone_ed = view.findViewById(R.id.GetPhone_ed);

        ProName_tf = view.findViewById(R.id.tf_GetName);
        ProAge_tf = view.findViewById(R.id.tf_GetAge);
        PeoAddress_tf = view.findViewById(R.id.tf_GetAddress);
        ProPhone_tf = view.findViewById(R.id.tf_GetPhone);

        floatingActionButton = view.findViewById(R.id.editFab);
        imageView = view.findViewById(R.id.user_profileImage);
        TopName = view.findViewById(R.id.user_profileName);
        ProEmail = view.findViewById(R.id.User_profileEmail_tv);
        closeWin = view.findViewById(R.id.x_closeWindow);
        process = view.findViewById(R.id.processBar_recycle);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AllClickListener();

        GetFirebaseData();
    }


    //For Choose Image From Your Phone
    private void OpenFileChooser() {
        CropImage.activity(uri).start(getContext(), this);
        imageView.setImageURI(uri);
    }

    private String getFileExt(Uri uri) {
        ContentResolver c = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(c.getType(uri));
    }


    //Get Data From Firebase
    private void GetFirebaseData() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                process.setVisibility(View.INVISIBLE);

                adminModel = dataSnapshot.getValue(AdminUsers_model.class);
                list.add(adminModel);

                ProName_ed.setText(adminModel.getName());
                TopName.setText(adminModel.getName());
                ProEmail.setText(mAuth.getCurrentUser().getEmail());
                ProAge_ed.setText(adminModel.getAge());
                PeoAddress_ed.setText(adminModel.getAddress());
                ProPhone_ed.setText(adminModel.getPhone());
                Glide.with(requireContext()).load(adminModel.getUrl()).apply(new RequestOptions().centerCrop()).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(requireContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                process.setVisibility(View.INVISIBLE);
            }
        });
    }

    /////////////////For Update//////////////
    private void insert_info() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");

        if (TextUtils.isEmpty(ProName_ed.getText().toString().trim())) {
            ProName_ed.setError("Enter your Name");
        } else if (TextUtils.isEmpty(ProAge_ed.getText().toString().trim())) {
            ProAge_ed.setError("Enter your Age");
        } else if (TextUtils.isEmpty(PeoAddress_ed.getText().toString().trim())) {
            PeoAddress_ed.setError("Enter your Address");
        } else if (TextUtils.isEmpty(ProPhone_ed.getText().toString().trim())) {
            ProPhone_ed.setError("Enter your Phone");
        } else {
            StorageReference FileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExt(uri));

            storageTask = FileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            AdminUsers_model info;
                            info = new AdminUsers_model(ProName_ed.getText().toString()
                                    , ProPhone_ed.getText().toString()
                                    , PeoAddress_ed.getText().toString()
                                    , ProAge_ed.getText().toString()
                                    , uri.toString());
                            databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(info);
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
                    ProName_tf.setEnabled(false);
                    PeoAddress_tf.setEnabled(false);
                    ProAge_tf.setEnabled(false);
                    ProPhone_tf.setEnabled(false);
                    floatingActionButton.setImageResource(R.drawable.ic_edit);
                    Toast toast = Toast.makeText(requireContext(), "      Done      ", Toast.LENGTH_LONG);
                    View Toview = toast.getView();
                    Toview.setBackgroundResource(R.drawable.success_shape);
                    toast.show();


                }
            }, 3000);
        }
    }

    //All Click Listener In This Fragment
    private void AllClickListener() {
        closeWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack(R.id.homePageFragment, false);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ProName_tf.isEnabled()) {
                    OpenFileChooser();
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ProName_tf.isEnabled()) {
                    insert_info();
                } else {
                    ProName_tf.setEnabled(true);
                    PeoAddress_tf.setEnabled(true);
                    ProAge_tf.setEnabled(true);
                    ProPhone_tf.setEnabled(true);
                    floatingActionButton.setImageResource(R.drawable.ic_check);
                }
            }
        });
    }
}