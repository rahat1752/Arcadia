package com.example.arcadia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {



    Button bSignUp;
    FirebaseAuth mFirebaseAuth;
    EditText username,L_name,Age;
    EditText emailId,password;
    Button signupbtn,clicksignin_btn;

    private DatabaseReference mData;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailId=findViewById(R.id.user_mail_sign_up);
        username=findViewById(R.id.username_signup);
        L_name=findViewById(R.id.Lastname_signup);
        Age=findViewById(R.id.Age_signup);
        password=findViewById(R.id.user_pass_sign_up);
        bSignUp=findViewById(R.id.signup_button);
        clicksignin_btn=findViewById(R.id.click_sign_in_btn);

        mFirebaseAuth= FirebaseAuth.getInstance();
        mData= FirebaseDatabase.getInstance().getReference();

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                String name=username.getText().toString().trim();
                String Lname=L_name.getText().toString().trim();
                String SAge=Age.getText().toString().trim();
                if(name.isEmpty())
                {
                    username.setError("User Name is required");
                    username.requestFocus();
                }
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if(pass.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Fields are Empty!!!",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Users u=new Users(name,Lname,SAge);
                                String ID=mFirebaseAuth.getCurrentUser().getUid();
                                mData.child("Users").child(ID).setValue(u);
                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Error Occurred!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        clicksignin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}