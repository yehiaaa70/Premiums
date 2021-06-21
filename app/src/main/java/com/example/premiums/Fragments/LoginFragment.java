package com.example.premiums.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premiums.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static androidx.navigation.Navigation.findNavController;

public class LoginFragment extends Fragment {

    private Toast toast;
    private View view;

    private CircularProgressButton progressButton;
    private TextView SignUpBtn, goToSignUp;

    private TextInputEditText email_Tied, password_Tied;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        progressButton = view.findViewById(R.id.cirLogin_btn);
        SignUpBtn = view.findViewById(R.id.sinUp_btn);
        goToSignUp = view.findViewById(R.id.GoSignUp);
        email_Tied = view.findViewById(R.id.ed_email_login);
        password_Tied = view.findViewById(R.id.ed_password_login);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AllClickListener();

    }


    //For Login In Firebase
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void SignInn() {
        auth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email_Tied.getText().toString())) {
            email_Tied.setError("Enter your Email");
        } else if (TextUtils.isEmpty(password_Tied.getText().toString())) {
            password_Tied.setError("Enter The Password");
        } else if (password_Tied.getText().toString().length() < 8) {
            password_Tied.setError("Enter at Least 8 Characters");
        } else {
            progressButton.startAnimation();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    auth.signInWithEmailAndPassword(email_Tied.getText().toString(), password_Tied.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                findNavController(view).navigate(R.id.action_loginFragment2_to_homePageFragment);
                                progressButton.revertAnimation();
                                toast = Toast.makeText(requireContext(), "  Successful LogIn  ", Toast.LENGTH_SHORT);
                                View view = toast.getView();
                                view.setBackgroundResource(R.drawable.success_shape);
                                toast.show();

                            } else {
                                progressButton.revertAnimation();
                                toast = Toast.makeText(requireContext(), "  There is a Problem  ", Toast.LENGTH_SHORT);
                                View view = toast.getView();
                                view.setBackgroundResource(R.drawable.error_shape);
                                toast.show();
                            }
                        }
                    });
                }

            }, 3000);

        }


    }

    //All Click Listener In This Fragment
    private void AllClickListener() {

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_loginFragment2_to_registerFragment);

            }
        });

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_loginFragment2_to_registerFragment);

            }
        });

        progressButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                SignInn();
            }
        });

    }

}