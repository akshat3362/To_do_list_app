package com.example.office_task_mangement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends Fragment {
    float v = 0;
    FirebaseAuth mauth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_page,null,false);
        TextView textView = view.findViewById(R.id.login_text);
        final EditText editText = view.findViewById(R.id.edit1);
        final EditText editText1 = view.findViewById(R.id.edit2);
        mauth = FirebaseAuth.getInstance();
        final ProgressDialog login_dialog = new ProgressDialog(getContext());
        Button button = view.findViewById(R.id.get);
        editText.setTranslationX(100);
        textView.setTranslationX(100);
        editText.setAlpha(v);
        textView.setAlpha(v);
        editText.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        textView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        editText1.setTranslationX(100);
        editText1.setAlpha(v);
        editText1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        button.setTranslationX(100);
        button.setAlpha(v);
        button.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        if (mauth.getCurrentUser()!=null){
            startActivity(new Intent(getContext(),HomeActivity.class));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String email = editText.getText().toString();
                String pass = editText1.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    editText.setError("Enter Email");
                    return;
                }
                 if (TextUtils.isEmpty(pass)) {
                    editText1.setError("Enter Password");
                    return;
                }
                login_dialog.setMessage("Processing....");
                login_dialog.show();
                    mauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), HomeActivity.class));
                                login_dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "  error", Toast.LENGTH_SHORT).show();
                                login_dialog.dismiss();
                            }
                        }
                    });
                }

        });

        return view;
    }
}
