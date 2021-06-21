package com.example.premiums.Fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.premiums.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterFragment extends Fragment {
    private Toast toast;

    private FirebaseAuth auth;

    private TextInputEditText email_signUP, password_signUP, conPassword_signUp;
    private CircularProgressButton signUp_cirBtn;
    private TextView goToLogin;

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

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        email_signUP = view.findViewById(R.id.ed_email_signUP);
        password_signUP = view.findViewById(R.id.ed_password_signUP);
        conPassword_signUp = view.findViewById(R.id.ed_Confpassword_signUP);
        signUp_cirBtn = view.findViewById(R.id.cirSignUp_btn);
        goToLogin = view.findViewById(R.id.Login_Btn);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AllClickListener();
    }


    //For Register In Firebase
    private void SignUp(View view) {
        auth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email_signUP.getText().toString().trim())) {
            email_signUP.setError("Enter your Email");
        } else if (TextUtils.isEmpty(password_signUP.getText().toString().trim())) {
            password_signUP.setError("Enter The Password");
        } else if (password_signUP.getText().toString().trim().length() < 8) {
            password_signUP.setError("Enter at Least 8 Characters");
        } else if (TextUtils.isEmpty(conPassword_signUp.getText().toString().trim())) {
            conPassword_signUp.setError("Enter The Config Password");
        } else if (conPassword_signUp.getText().toString().trim().length() < 8) {
            conPassword_signUp.setError("Enter at Least 8 Characters");
        } else if (!conPassword_signUp.getText().toString().equalsIgnoreCase(password_signUP.getText().toString())) {
            conPassword_signUp.setError("The Password Didn't Match");
        } else {
            signUp_cirBtn.startAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    auth.createUserWithEmailAndPassword(email_signUP.getText().toString(), password_signUP.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_signUp2Fragment);
                                signUp_cirBtn.revertAnimation();

                                toast = Toast.makeText(requireContext(), "  The Information Successful Register  ", Toast.LENGTH_LONG);
                                View view = toast.getView();
                                view.setBackgroundResource(R.drawable.success_shape);
                                toast.show();
                            } else {
                                signUp_cirBtn.revertAnimation();

                                toast = Toast.makeText(requireContext(), "  There is a Problem  ", Toast.LENGTH_LONG);
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
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack(R.id.loginFragment2, false);
            }
        });

        signUp_cirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(view);
            }
        });
    }

}