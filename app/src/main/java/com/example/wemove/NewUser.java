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

public class NewUser extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mNameText;
    private EditText mPrenomText;
    private EditText mTagText;
    private EditText mDateText;
    private EditText mEmailText;
    private EditText mPasswordText;

    private Button mLoginBtn;
    private Button mSignupBtn;

    private WeMoveDB db;
    private String userID;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        db = new WeMoveDB();
        mAuth = FirebaseAuth.getInstance();

        mNameText = findViewById(R.id.nameText);
        mPrenomText = findViewById(R.id.prenomText);
        mTagText = findViewById(R.id.tagText);
        mDateText = findViewById(R.id.dateText);
        mEmailText = findViewById(R.id.emailText);
        mPasswordText = findViewById(R.id.passwordText);
        mSignupBtn = findViewById(R.id.signupBtn);
        mLoginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressbar);

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signUp() {
        final String name = mNameText.getText().toString();
        final String prenom = mPrenomText.getText().toString();
        final String tag = mTagText.getText().toString();
        final String date = mDateText.getText().toString();
        final String email = mEmailText.getText().toString();
        final String password = mPasswordText.getText().toString();

        if(name.isEmpty()) {
            mNameText.setError("Le nom est requis");
            mNameText.requestFocus();
            return;
        }if(prenom.isEmpty()) {
            mPrenomText.setError("Le prénom est requis");
            mPrenomText.requestFocus();
            return;
        }if(tag.isEmpty()) {
            mTagText.setError("Le tag est requis");
            mTagText.requestFocus();
            return;
        }if(date.isEmpty()) {
            mDateText.setError("La date de naissance est requis");
            mDateText.requestFocus();
            return;
        }if(email.isEmpty()) {
            mEmailText.setError("L'email est requis");
            mEmailText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("Entrer une adresse valide svp");
            mEmailText.requestFocus();
            return;
        }if(password.isEmpty()) {
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
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(prenom) || TextUtils.isEmpty(tag) || TextUtils.isEmpty(date)) {
            Toast.makeText(NewUser.this, "Un ou des champs sont vides", Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()) {
                        Log.d("SIGNIN", "onComplete: Signed_in success");
                        Toast.makeText(getBaseContext(),"Authentication successful",Toast.LENGTH_LONG).show();
                        FirebaseUser userAuth = mAuth.getCurrentUser();
                        userID = userAuth.getUid();
                        User user = new User(userID,tag,name,prenom,email,date);
                        db.addUser(user);
                        startActivity(new Intent(NewUser.this,Home.class));
                    } else {
                        Log.d("SIGNIN", "onComplete: Signed_in failed");
                        Toast.makeText(getBaseContext(),"Authentication failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void signIn() {
        startActivity(new Intent(NewUser.this, Login.class));
    }
}
