package com.hyep.movietracker.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hyep.movietracker.R;

public class NewPasswordScreen extends AppCompatActivity {

    private EditText edtNewPassword, edtConfirmPassword;

    private Button btnConfirm;
    
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.screen_new_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnConfirm = findViewById(R.id.btnConfirm);
        
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String newPassword = edtNewPassword.getText().toString().trim();
                
                String confirmPassword = edtConfirmPassword.getText().toString().trim();
                
                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(NewPasswordScreen.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPassword.length()<8){
                    Toast.makeText(NewPasswordScreen.this, "Password must be less than 8 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPassword.equals(confirmPassword)){
                    Toast.makeText(NewPasswordScreen.this, "Confirm password is incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }


                assert user != null;
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(NewPasswordScreen.this, "Password was successfully changed", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(NewPasswordScreen.this, MainScreen.class));
                                    finish();
                                }else{
                                    Toast.makeText(NewPasswordScreen.this, "Fail: "+task.getException(), Toast.LENGTH_SHORT).show();
                                    Log.d("Loi", String.valueOf(task.getException()));
                                }
                            }
                        });
            }
        });
    }
}