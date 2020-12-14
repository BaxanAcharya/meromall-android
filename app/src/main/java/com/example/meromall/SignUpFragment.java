package com.example.meromall;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private ImageButton close;
    private ProgressBar progressBar;
    private EditText email, fullname, password, cpassword;
    private Button signUp;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAnAccount = view.findViewById(R.id.sign_up_sign_in);
        parentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);
        close = view.findViewById(R.id.sign_up_close_button);
        progressBar = view.findViewById(R.id.sign_up_progress);
        email = view.findViewById(R.id.sign_up_email);
        fullname = view.findViewById(R.id.sign_up_fullname);
        password = view.findViewById(R.id.sign_up_password);
        cpassword = view.findViewById(R.id.sign_up_cpassword);
        signUp = view.findViewById(R.id.sign_up_button);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.GONE);
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
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });

    }

    private void checkEmailAndPassword() {
        Drawable customerErrorIcon = getResources().getDrawable(R.drawable.ic_error);
        customerErrorIcon.setBounds(0, 0, customerErrorIcon.getIntrinsicWidth(), customerErrorIcon.getIntrinsicHeight());
        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(cpassword.getText().toString())) {
                progressBar.setVisibility(View.VISIBLE);
                signUp.setEnabled(false);
                //signUp.setTextColor(Color.argb(50f, 255, 255, 255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Map<Object, String> userdata = new HashMap<>();
                                    userdata.put("fullname", fullname.getText().toString());
                                    firebaseFirestore.collection("users")
                                            .add(userdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                openHome();
                                            } else {
                                                progressBar.setVisibility(View.GONE);
                                                signUp.setEnabled(false);
                                                //signUp.setTextColor(Color.argb(50f, 255, 255, 255));
                                                String error = task.getException().getMessage();
                                                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    signUp.setEnabled(false);
                                   // signUp.setTextColor(Color.argb(50f, 255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                cpassword.setError("Password doesnot match", customerErrorIcon);
            }
        } else {
            email.setError("Email is not valid !!", customerErrorIcon);
        }
    }

    private void openHome() {
        Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8) {
                if (!TextUtils.isEmpty(fullname.getText())) {
                    if (!TextUtils.isEmpty(cpassword.getText())) {
                        signUp.setEnabled(true);
                       // signUp.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        signUp.setEnabled(false);
                        //signUp.setTextColor(Color.argb(50f, 255, 255, 255));
                    }
                } else {
                    signUp.setEnabled(false);
                    //signUp.setTextColor(Color.argb(50f, 255, 255, 255));
                }
            } else {
                signUp.setEnabled(false);
                //signUp.setTextColor(Color.argb(50f, 255, 255, 255));
            }
        } else {
            signUp.setEnabled(false);
            //signUp.setTextColor(Color.argb(50f, 255, 255, 255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.silde_from_left, R.anim.silde_out_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}

