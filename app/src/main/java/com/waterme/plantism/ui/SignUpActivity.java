package com.waterme.plantism.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.waterme.plantism.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Sign Up Activity";
    private Button mSignUpButton, mBackButton;
    private EditText mEmailField, mPasswordField;
    private ProgressBar mLoadingIndicator;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUpButton = (Button) findViewById(R.id.btn_sign_up);
        mBackButton = (Button) findViewById(R.id.btn_back);
        mEmailField = (EditText) findViewById(R.id.et_signUp_email);
        mPasswordField = (EditText) findViewById(R.id.et_signUp_password);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_sign_up);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mSignUpButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "create account with: " + email);
        if (!validateForm()) {
            Log.d(TAG, "form is wrong");
            return;
        }

        showLoadingIndicator();

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "create email account success");
                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            // start new intent


                        } else {
                            Log.w(TAG, "create email account fails: ", task.getException());
                            Toast.makeText(SignUpActivity.this, "Sign Up Fails",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideLoadingIndicator();
                    }
                });
    }

    private void backToSignIn() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_sign_up) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (id == R.id.btn_back) {
            backToSignIn();
        }
    }

    private void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }
    private void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }
    public boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else if (password.length() < 8) {
            mPasswordField.setError("Minimum Length is 8");
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }
}
