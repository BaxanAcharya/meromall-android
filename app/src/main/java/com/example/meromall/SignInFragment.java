package com.example.meromall;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.meromall.RegisterActivity.onResetPasswordFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private ImageButton close;
    private ProgressBar progressBar;
    private EditText email, password;
    private Button signIn;
    private TextView forgotPassword;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.sign_in_sign_up);
        parentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);
        close = view.findViewById(R.id.sign_in_close_button);
        progressBar = view.findViewById(R.id.sign_in_progress);
        progressBar.setVisibility(View.GONE);
        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        forgotPassword = view.findViewById(R.id.sign_in_forgot_password);
        signIn = view.findViewById(R.id.sign_in_button);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkInputs();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkInputs();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
    }

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches(emailPattern)) {
            if (password.length() >= 8) {
                progressBar.setVisibility(View.VISIBLE);
                signIn.setEnabled(false);
                //  signIn.setTextColor(Color.argb(50f, 255, 255, 255));
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    openHome();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    signIn.setEnabled(true);

//                                    signIn.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(getActivity(), "Password Too Short !!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Email is not valid !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openHome() {
        Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(password.getText())) {
                signIn.setEnabled(true);
                //  signIn.setTextColor(Color.rgb(255, 255, 255));
            } else {
                signIn.setEnabled(false);
                // signIn.setTextColor(Color.argb(50, 255, 255, 255));
            }

        } else {
            signIn.setEnabled(false);
            // signIn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
