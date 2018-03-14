package com.waterme.plantism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private SignInButton mGoogleSignInButton;
    private Button mSignInButton;
    private EditText mEmailField, mPasswordField;
    private ProgressBar mLoadingIndicator;
    private TextView mSignUpLink;

    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mGoogleSignInButton = (SignInButton) findViewById(R.id.btn_google_login);
        mSignInButton = (Button) findViewById(R.id.btn_login);
        mEmailField = (EditText) findViewById(R.id.et_input_email);
        mPasswordField = (EditText) findViewById(R.id.et_input_password);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_login);
        mSignUpLink = (TextView) findViewById(R.id.tv_link_signup);

        mSignInButton.setOnClickListener(this);
        mGoogleSignInButton.setOnClickListener(this);
        mSignUpLink.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }


    private void signInWithGoogle() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                authWithGoogle(account);
            } catch (Exception e) {
                Log.w(TAG, "google sign in fail", e);
                e.printStackTrace();
            }
        }
    }

    private void signInWithEmail(final String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showLoadingIndicator();
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "login with email successful");
                            //FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            //
                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();



                            editor.putString("userEmail", email);
                            editor.apply();
                            // start home intent;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Log.w(TAG, "sign in with email fail:", task.getException());
                            Toast.makeText(LoginActivity.this, "Sign In Failure.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideLoadingIndicator();

                    }
                });
    }

    private void authWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebase Auth WithGoogle: " + account.getId());
        showLoadingIndicator();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Sign in success, start activity
                            Log.d(TAG, "login with google successful");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                            // pass username to share preference data
                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userEmail", user.getEmail());
                            editor.apply();

                            Log.d(TAG, "email is " + user.getEmail());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Log.w(TAG, "sign In With Google fail:", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failure.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideLoadingIndicator();

                    }
                });
    }

    private void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void signUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_login) {
            signInWithEmail(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (id == R.id.btn_google_login) {
            signInWithGoogle();
        } else if (id == R.id.tv_link_signup) {
            signUp();
        }
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
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }
}
