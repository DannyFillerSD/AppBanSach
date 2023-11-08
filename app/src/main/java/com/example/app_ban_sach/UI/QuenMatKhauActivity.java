package com.example.app_ban_sach.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_sach.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMatKhauActivity extends AppCompatActivity {
    EditText edEmailQMK;
    TextView btnReset;
//    ProgressBar progressBar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);

        edEmailQMK = findViewById(R.id.edEmailQMK);
        btnReset = findViewById(R.id.btnReset);


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmailQMK.getText().toString().trim();
                ResetPassword(email);


            }
        });

    }

    private void ResetPassword(String resetEmail){
//        progressBar
        Toast.makeText(QuenMatKhauActivity.this, resetEmail, Toast.LENGTH_SHORT).show();
        mAuth.sendPasswordResetEmail(resetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(QuenMatKhauActivity.this, "Reset thành công", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(QuenMatKhauActivity.this,DangNhapActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}