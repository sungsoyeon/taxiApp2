package com.ossw.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginActivity extends AppCompatActivity {

    private EditText d_email, d_password;
    private Button d_login, d_register, back;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(DriverLoginActivity.this, DriverMenuActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        d_email = (EditText) findViewById(R.id.email);
        d_password = (EditText) findViewById(R.id.password);
        d_login = (Button) findViewById(R.id.login);
        d_register = (Button) findViewById(R.id.register);
        back = (Button) findViewById(R.id.back);


        //회원가입
        d_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLoginActivity.this, DriverRegisterActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });



        d_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = d_email.getText().toString();
                final String password = d_password.getText().toString();
                mAuth.signInWithEmailAndPassword(id, password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(DriverLoginActivity.this, "잘못된 이메일 및 패스워드입니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(DriverLoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DriverLoginActivity.this, DriverMenuActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLoginActivity.this, MainActivity.class);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(this, "자동 로그인 : " + user.getUid(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DriverLoginActivity.this, DriverMenuActivity.class);
            startActivity(intent);
            finish();
            return;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
