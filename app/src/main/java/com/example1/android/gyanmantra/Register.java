package com.example1.android.gyanmantra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText username, email, password;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dialog = new ProgressDialog(Register.this);

        getSupportActionBar().setTitle("Register");
      //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        username = (EditText) findViewById(R.id.user);
        email = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.pass);
        //
        auth = FirebaseAuth.getInstance();
    }


    public void registering(final String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       dialog.dismiss();
                                       Toast.makeText(Register.this, "Please verify the Email", Toast.LENGTH_SHORT).show();

                                   }
                                   else{
                                       dialog.dismiss();
                                       Toast.makeText(Register.this, ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });


                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(Register.this, "you can not register with this email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register(View view) {
        dialog.setMessage("Wait......");
        dialog.show();
        String text_username = username.getText().toString();
        String text_email = email.getText().toString();
        String text_password = password.getText().toString();
        if(TextUtils.isEmpty(text_username) || TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)){
            Toast.makeText(this, "Fill Details", Toast.LENGTH_SHORT).show();
        }
        else if(text_password.length()<3){
            Toast.makeText(this, "Password is very short", Toast.LENGTH_SHORT).show();
        }
        else{
            registering(text_username,text_email,text_password);
        }
    }


    public void login(View view) {
        Intent i = new Intent(Register.this,LogIn.class);

        startActivity(i);
     /*   final FirebaseUser firebaseUser = auth.getCurrentUser();
        if(auth.getCurrentUser().isEmailVerified()){
            //assert firebaseUser != null;
            String userId = firebaseUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference("user").child(userId);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", userId);
            hashMap.put("username", String.valueOf(username));
            hashMap.put("ImageUrl", "default");
            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(Register.this, LogIn.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        }
        else{
            Toast.makeText(Register.this, "Verify Email", Toast.LENGTH_SHORT).show();
        }
    */
    }
}

