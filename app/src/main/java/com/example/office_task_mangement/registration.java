package com.example.office_task_mangement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class registration extends AppCompatActivity {
    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mauth = FirebaseAuth.getInstance();
        final ProgressDialog reg_dialog= new ProgressDialog(this);
        final EditText email = findViewById(R.id.email_reg);
        final EditText pass = findViewById(R.id.password_reg);
        final Button reg = findViewById(R.id.button_register);
        final TextView login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString();
                String mPass = pass.getText().toString();
                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(mPass)) {
                    pass.setError("Required Field");
                    return;
                }


                reg_dialog.setMessage("Processing....");
                reg_dialog.show();
                mauth.createUserWithEmailAndPassword(mEmail, mPass).

                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                }
                                reg_dialog.dismiss();
                            }
                        });
            }
        });
    }
}