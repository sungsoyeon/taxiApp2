package com.ossw.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


public class CustomerMenuActivity extends AppCompatActivity {

    private Button call, together, paytable, mbox, c_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);

        call = (Button)findViewById(R.id.call);
        together = (Button)findViewById(R.id.together);
        c_logout = (Button) findViewById(R.id.back);


        //승객 로그아웃
        c_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CustomerMenuActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(CustomerMenuActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        });




        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenuActivity.this, CustomerMapActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
