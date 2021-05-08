package com.example.office_task_mangement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextView reg;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressDialog login_dialog= new ProgressDialog(this);
         reg = findViewById(R.id.register);
         mauth=FirebaseAuth.getInstance();
        final EditText login_email=findViewById(R.id.email);
        final EditText login_pass=findViewById(R.id.password);
        Button login_button =findViewById(R.id.button_Submit);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),registration.class));
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = login_email.getText().toString().trim();
                String mpass = login_pass.getText().toString().trim();
                if(TextUtils.isEmpty(memail))
                {
                    login_email.setError("Required Email");
                    return;
                }
                if(TextUtils.isEmpty(mpass))
                {
                    login_pass.setError("Required password");
                    return;
                }
                login_dialog.setMessage("Processing....");
                login_dialog.show();
             mauth.signInWithEmailAndPassword(memail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task)
                 {
                 if(task.isSuccessful())
                 {
                     Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                     login_dialog.dismiss();
                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(),"Problem",Toast.LENGTH_SHORT).show();
                     login_dialog.dismiss();
                 }
                 }
             });
            }

        });
    }
}