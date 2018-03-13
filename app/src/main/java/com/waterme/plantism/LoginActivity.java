package com.waterme.plantism;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignInActivity";

    private SignInButton mGoogleSignInButton;
    private Button mSignInButton;
    private EditText inputEmail, inputPassword;
    private ProgressBar mLoadingIndicator;

    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mGoogleSignInButton = (SignInButton) findViewById(R.id.btn_google_login);
        mSignInButton = (Button) findViewById(R.id.btn_login);
        inputEmail = (EditText) findViewById(R.id.et_input_email);
        inputPassword = (EditText) findViewById(R.id.et_input_password);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mSignInButton.setOnClickListener(this);
        mGoogleSignInButton.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void signInWithEmail(String email, String password) {
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
                            
                        }

                    }
                })
    }
    private void signInWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        showLoadingIndicator();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign in success, start activity
                            Log.d(TAG, "login with google successful")
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                        } else {
                            Log.w(TAG, "signInWithGoogle fail:", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failure.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideLoadingIndicator();

                    }
                });
    }

    private void showLoadingIndicator() {

    }

    private void hideLoadingIndicator() {

    }
    @Override
    public void onClick(View view) {

    }

    public boolean validateForm() {
        return true;
    }
}
