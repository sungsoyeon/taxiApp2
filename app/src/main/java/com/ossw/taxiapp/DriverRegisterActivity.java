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

public class DriverRegisterActivity extends AppCompatActivity {

    private EditText d_email, d_password;
    private Button d_register, d_cancel;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);


        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
        };

        d_email = (EditText) findViewById(R.id.email);
        d_password = (EditText) findViewById(R.id.password);
        d_register = (Button)findViewById(R.id.register);
        d_cancel = (Button)findViewById(R.id.cancel);

        //회원가입
        d_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // final String name = d_name.getText().toString();
                final String id = d_email.getText().toString();
                final String password = d_password.getText().toString();
                mAuth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(DriverRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(DriverRegisterActivity.this, "회원가입 오류", Toast.LENGTH_SHORT).show();
                        }else{
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
                            current_user_db.setValue(true);
                            Toast.makeText(DriverRegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Toast.makeText(DriverRegisterActivity.this, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DriverRegisterActivity.this, DriverMenuActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                });
            }
        });


        d_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverRegisterActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
