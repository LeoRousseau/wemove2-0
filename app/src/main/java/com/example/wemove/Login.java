package com.example.wemove;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmailText;
    private EditText mPasswordText;

    private Button mLoginBtn;
    private Button mNewBtn;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mEmailText = findViewById(R.id.emailField);
        mPasswordText = findViewById(R.id.passwordField);
        mLoginBtn = findViewById(R.id.loginBtn);
        mNewBtn = findViewById(R.id.newBtn);
        progressBar = findViewById(R.id.progressbar);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(Login.this,Home.class));
                }
            }
        };

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void signIn() {
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if(email.isEmpty()) {
            mEmailText.setError("L'email est requis");
            mEmailText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("Entrer une adresse valide svp");
            mEmailText.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            mPasswordText.setError("Le mot de passe est requis");
            mPasswordText.requestFocus();
            return;
        }
        if(password.length() < 6) {
            mPasswordText.setError("Mot de passe trop court");
            mPasswordText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "Un ou des champs sont vides", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Un problème est survenu", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void newUser() {
        startActivity(new Intent(Login.this,NewUser.class));
    }
}
