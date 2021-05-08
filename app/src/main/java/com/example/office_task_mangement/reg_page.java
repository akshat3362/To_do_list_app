package com.example.office_task_mangement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class reg_page extends Fragment
{
    private FirebaseAuth mauth;
    float v=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reg_page,container,false);
        final EditText editText2 = view.findViewById(R.id.reg1);
        mauth=FirebaseAuth.getInstance();
        final EditText editText3 = view.findViewById(R.id.reg2);
        Button button = view.findViewById(R.id.save);
        final ProgressDialog reg_dialog= new ProgressDialog(getContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = editText2.getText().toString();
                String mPass = editText3.getText().toString();
                if (TextUtils.isEmpty(mEmail)) {
                    editText2.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(mPass)) {
                    editText3.setError("Required Field");
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
                                    Toast.makeText(getContext(), "Successfull", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getContext(), HomeActivity.class));
                                } else {
                                    Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                }
                                reg_dialog.dismiss();
                            }
                        });
            }
        });
        return view;
    }
}