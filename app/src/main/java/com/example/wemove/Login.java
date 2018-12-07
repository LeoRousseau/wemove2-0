package com.example.wemove;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

import Utils.AccessData;

import Utils.AccessData;
import profile.ProfileActivity;


public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmailText;
    private EditText mPasswordText;
    public static Boolean isFirstTime = false;

    private Button mLoginBtn;
    private Button mNewBtn;

    private LoginButton loginButton;
    private CallbackManager callbackManager;


    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mAuth = FirebaseAuth.getInstance();
        mEmailText = findViewById(R.id.emailField);
        mPasswordText = findViewById(R.id.passwordField);
        mLoginBtn = findViewById(R.id.loginBtn);
        mNewBtn = findViewById(R.id.newBtn);
        progressBar = findViewById(R.id.progressbar);
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(Login.this,Home.class));
                    //AccessData.db.getPhoto();
                    isFirstTime = true;
                }
            }
        };

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFb();
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

    public void loginFb() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(),"Cancelled",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getBaseContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleFacebookToken(AccessToken accessToken) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                    Log.d("SIGNIN", "onComplete: Signed_in success");
                    Toast.makeText(getBaseContext(),"Authentication successful",Toast.LENGTH_LONG).show();
                    final FirebaseUser userAuth = mAuth.getCurrentUser();
                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mUserRef = mRootRef.child("Users");
                    mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(userAuth.getUid())) {
                                Log.d("login", "User already exists");
                                //startActivity(new Intent(Login.this,Home.class));
                                isFirstTime = true;
                            }
                            else {
                                Log.d("login", "User created");
                                String parts[] = userAuth.getDisplayName().split(" ");
                                User user = new User(userAuth.getUid(),parts[1],parts[0],userAuth.getEmail()); //Sans tag et faut le mettre ailleurs sinon prend pas en compte modif
                                AccessData.db.addUser(user);
                                isFirstTime = true;
                                Toast.makeText(getBaseContext(),"Compléter votre profil", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, ProfileActivity.class));

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("test", "onCancelled: ");
                        }
                    });

                }
                else {
                    Toast.makeText(getBaseContext(),"Un problème est survenu",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
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
                    isFirstTime = true;
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
