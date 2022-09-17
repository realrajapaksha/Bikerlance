package com.example.shehan.bikerlance;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.shehan.bikerlance.RegisterActivity.onResetPasswordFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText passwrod;

    private TextView forgotpassword;

    private ProgressBar progressBar;


    private Button signInBtn;

    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        dontHaveAnAccount = view.findViewById(R.id.tv_dont_have_an_account);

        forgotpassword = view.findViewById(R.id.sign_in_forgot_password);

        email = view.findViewById(R.id.sign_in_email);
        passwrod = view.findViewById(R.id.sign_in_password);

        progressBar = view.findViewById(R.id.sign_in_progressbar);

        signInBtn = view.findViewById(R.id.sign_in_btn);

        firebaseAuth = FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });



        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwrod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });

    }



    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(passwrod.getText())){
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255,255,255));
            }else{
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else{
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void checkEmailAndPassword(){
       if (email.getText().toString().matches(emailPattern)){
           if (passwrod.length() >= 8){

               progressBar.setVisibility(View.VISIBLE);
               signInBtn.setEnabled(false);
               signInBtn.setTextColor(Color.argb(50,255,255,255));

               firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),passwrod.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()){
                                   Intent mainIntent = new Intent(getActivity(),MainActivity.class);
                                   startActivity(mainIntent);
                                   getActivity().finish();
                               }else{
                                   progressBar.setVisibility(View.INVISIBLE);
                                   signInBtn.setEnabled(true);
                                   signInBtn.setTextColor(Color.rgb(255,255,255));
                                   String error = task.getException().getMessage();
                                   Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                               }
                           }
                       });

           }else{
               Toast.makeText(getActivity(), "Incorrect email or password!", Toast.LENGTH_SHORT).show();
           }
       }else{
           Toast.makeText(getActivity(), "Incorrect email or password!", Toast.LENGTH_SHORT).show();
       }

    }
    private void mainIntent(){
        if (disableCloseBtn){
            disableCloseBtn =false;

        }else {

            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
          getActivity().finish();

}}
