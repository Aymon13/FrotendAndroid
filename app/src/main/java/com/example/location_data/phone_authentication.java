package com.example.location_data;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phone_authentication extends AppCompatActivity {


    EditText phonenumber,otprece;
    Button submit,sendtop;
    String verificationId;
    String phone,otp;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_authentication_layout);

        mAuth=FirebaseAuth.getInstance();
        phonenumber=findViewById(R.id.phonenumber);
        otprece=findViewById(R.id.otp);
        submit=findViewById(R.id.submit);
        sendtop=findViewById(R.id.sendotp);

        sendtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=phonenumber.getText().toString();

                if (phone.length()==10)
                {
                    sendOTP(phone);
                }
                else
                {
                    phonenumber.setError("Please enter a valid mobile number");
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(phone_authentication.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(phone_authentication.this, "Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(phone_authentication.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //loading.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(phone_authentication.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            //loading.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otprece.setText(code);
                verifyCode(code);

                //loading.setVisibility(View.GONE);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(phone_authentication.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    public void sendOTP(String num)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+num,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack);        // OnVerificationStateChangedCallbacks
    }



}