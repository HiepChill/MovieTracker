package com.hyep.movietracker.screens;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.hyep.movietracker.R;

public class ForgotPasswordScreen extends AppCompatActivity {

    private EditText edtEmail;

    private Button btnSendCode;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtEmail);
        btnSendCode = findViewById(R.id.btnSendCode);

        mAuth = FirebaseAuth.getInstance();

        btnSendCode.setOnClickListener(view -> {
            String emailAddress = edtEmail.getText().toString().trim();

            if (TextUtils.isEmpty(emailAddress)){
                Toast.makeText(ForgotPasswordScreen.this, "Please fill email!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordScreen.this, "Email sent", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Log.d("Loi", String.valueOf(task.getException()));
                        }
                    });
        });

    }
}