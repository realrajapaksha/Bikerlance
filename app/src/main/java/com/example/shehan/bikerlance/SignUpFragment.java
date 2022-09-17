package com.example.shehan.bikerlance;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SupportActivity;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shehan.bikerlance.R.id.sign_up_progressbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;

    private ImageButton closeBtn;
    private Button SignUpBtn;

    private ProgressBar progressBar;


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String emailPattern = "[a-zA-z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        alreadyHaveAnAccount = view.findViewById(R.id.tv_already_have_an_account);
        email = view.findViewById(R.id.sign_up_email);
        fullName = view.findViewById(R.id.sign_up_fullname);
        password = view.findViewById(R.id.sign_up_password);
        confirmPassword = view.findViewById(R.id.sign_up_confirm_password);

        closeBtn = view.findViewById(R.id.sign_up_close_btn);
        SignUpBtn = view.findViewById(R.id.sign_up_btn);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar = view.findViewById(R.id.sign_up_progressbar);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
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
        fullName.addTextChangedListener(new TextWatcher() {
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
        password.addTextChangedListener(new TextWatcher() {
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
        confirmPassword.addTextChangedListener(new TextWatcher() {
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


        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(fullName.getText())) {
                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmPassword.getText())) {
                        SignUpBtn.setEnabled(true);
                        SignUpBtn.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        SignUpBtn.setEnabled(false);
                        SignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    SignUpBtn.setEnabled(false);
                    SignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                SignUpBtn.setEnabled(false);
                SignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            SignUpBtn.setEnabled(false);
            SignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkEmailAndPassword() {

        Drawable customErorIcon = getResources().getDrawable(R.mipmap.custom_error_icon);
        customErorIcon.setBounds(0, 0, customErorIcon.getIntrinsicWidth(), customErorIcon.getIntrinsicHeight());


        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {

                progressBar.setVisibility(View.VISIBLE);
                SignUpBtn.setEnabled(false);
                SignUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Map<String,Object> userdata = new HashMap<>();
                                    userdata.put("fullname", fullName.getText().toString());
                                    userdata.put("newUser", "0");

                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                            .set(userdata)
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if (task.isSuccessful()) {

                                                     CollectionReference  userDataReference = firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

                                                      /// MAPS
                                                       Map<String,Object> wishlistMap = new HashMap<>();
                                                       wishlistMap.put("list_size", (long) 0);

                                                       Map<String,Object> ratingsMap = new HashMap<>();
                                                       ratingsMap.put("list_size", (long) 0);

                                                       Map<String,Object> cartMap = new HashMap<>();
                                                       cartMap.put("list_size", (long) 0);

                                                       Map<String,Object> MyaddressMap = new HashMap<>();
                                                       MyaddressMap.put("list_size", (long) 0);


                                                       ///MAPS


                                                       final List<String> documentNames = new ArrayList<>();
                                                       documentNames.add("MY_WISHLIST");
                                                       documentNames.add("MY_RATINGS");
                                                       documentNames.add("MY_CART");
                                                       documentNames.add("MY_ADDRESSES");

                                                       List< Map<String,Object>> documentFields = new ArrayList<>();
                                                       documentFields.add(wishlistMap);
                                                       documentFields.add(ratingsMap);
                                                       documentFields.add(cartMap);
                                                       documentFields.add(MyaddressMap);


                                                       for (int x = 0;x < documentNames.size();x++){

                                                           final int finalX = x;
                                                           userDataReference.document(documentNames.get(x))
                                                                   .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                   if (task.isSuccessful()){
                                                                       if (finalX == documentNames.size() -1) {
                                                                           mainIntent();
                                                                       }
                                                                   }else {
                                                                       progressBar.setVisibility(View.INVISIBLE);
                                                                       SignUpBtn.setEnabled(true);
                                                                       SignUpBtn.setTextColor(Color.rgb(255, 255, 255));
                                                                       String error = task.getException().getMessage();
                                                                       Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                                   }
                                                               }
                                                           });
                                                       }

                                                   } else {
                                                       String error = task.getException().getMessage();
                                                       Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                   }
                                               }
                                           });
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    SignUpBtn.setEnabled(true);
                                    SignUpBtn.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            } else {
                confirmPassword.setError("Password doesn't matched!", customErorIcon);
            }

        } else {
            email.setError("Invalid Email!", customErorIcon);
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

    }
}


