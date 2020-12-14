package com.example.meromall;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {

    private EditText email;
    private Button forgot;
    private TextView back, emailSent;
    ImageView emailImage;
    ProgressBar progressBar;
    private FrameLayout parentFramelayout;
    private FirebaseAuth firebaseAuth;
    private ViewGroup linearLayout;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        parentFramelayout = getActivity().findViewById(R.id.register_frame_layout);
        email = view.findViewById(R.id.forgot_email);
        forgot = view.findViewById(R.id.btn_forgot);
        back = view.findViewById(R.id.forgot_back);
        emailSent = view.findViewById(R.id.forgot_email_sent);
        emailImage = view.findViewById(R.id.forgot_email_image);
        progressBar = view.findViewById(R.id.forgotProgress);
        linearLayout = view.findViewById(R.id.forgot_linear_layout);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        back.setOnClickListener(new View.OnClickListener() {
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
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(linearLayout);
                emailImage.setVisibility(View.GONE);
                //it makes the transition of elements inside the linear layout interesting(animation)
                TransitionManager.beginDelayedTransition(linearLayout);
                emailImage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                forgot.setEnabled(false);
                forgot.setTextColor(Color.argb(50, 255, 255, 255));
                firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,emailImage.getWidth()/2,emailImage.getHeight()/2);
                                    scaleAnimation.setDuration(100);
                                    scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);
                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            emailSent.setText("Recovery email sent successfully ! check your inbox");
                                            emailSent.setTextColor(getResources().getColor(R.color.successGreen));
                                            TransitionManager.beginDelayedTransition(linearLayout);
                                            emailSent.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                            emailImage.setImageResource(R.drawable.ic_email_green);
                                        }
                                    });
                                    emailImage.startAnimation(scaleAnimation);
                                } else {
                                    String error = task.getException().getMessage();
                                    forgot.setEnabled(true);
                                    forgot.setTextColor(Color.rgb(255, 255, 255));
                                    emailSent.setText(error);
                                    emailImage.setImageResource(R.drawable.ic_email_red);
                                    emailSent.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    TransitionManager.beginDelayedTransition(linearLayout);
                                    emailSent.setVisibility(View.VISIBLE);
                                }
                                progressBar.setVisibility(View.GONE);

                            }
                        });
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.silde_from_left, R.anim.silde_out_from_right);
        fragmentTransaction.replace(parentFramelayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (TextUtils.isEmpty(email.getText().toString())) {
            forgot.setEnabled(false);
            forgot.setTextColor(Color.argb(50, 255, 255, 255));
        } else {
            forgot.setEnabled(true);
            forgot.setTextColor(Color.rgb(255, 255, 255));
        }
    }
}
