package com.example1.android.gyanmantra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    EditText email, password;
    FirebaseAuth auth;
    Button loginB;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //initialise Progress Dialog
        dialog = new ProgressDialog(LogIn.this);

        getSupportActionBar().setTitle("LogIn");
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        email = (EditText) findViewById(R.id.login_mail);
        password = (EditText) findViewById(R.id.login_pass);
        loginB = (Button)findViewById(R.id.login_button);
        auth = FirebaseAuth.getInstance();
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                if(TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)){
                    Toast.makeText(LogIn.this, "Fill All the details", Toast.LENGTH_SHORT).show();

                }
                else{
                    dialog.setMessage("Wait......");
                    dialog.show();
                    auth.signInWithEmailAndPassword(text_email,text_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        if(auth.getCurrentUser().isEmailVerified()){
                                            String user_email = auth.getCurrentUser().getEmail().toString();
                                         //   Toast.makeText(LogIn.this, ""+auth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                                            if (user_email.equals("akshaykathura@gmail.com")) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(LogIn.this, ShowDepartment.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else {
                                                dialog.dismiss();
                                                Intent intent = new Intent(LogIn.this, ShowDepartment_User.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                        else{
                                            dialog.dismiss();
                                            Toast.makeText(LogIn.this, "Please Verify Email", Toast.LENGTH_SHORT).show();
                                        }
                                      /*  Toast.makeText(LogIn.this, "Logged in", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LogIn.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                       */
                                    }
                                    else {
                                        dialog.dismiss();
                                        Toast.makeText(LogIn.this, "Auhtentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

}
