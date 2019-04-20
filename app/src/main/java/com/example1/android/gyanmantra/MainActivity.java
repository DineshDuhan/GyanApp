package com.example1.android.gyanmantra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
   FirebaseUser firebaseUser;
   private String admin_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Gyan Mantra");
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
       // getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        if(firebaseUser!=null) {
            admin_email = firebaseUser.getEmail().toString();
            if (admin_email.equals("akshaykathura@gmail.com")) {
                Intent intent = new Intent(MainActivity.this, ShowDepartment.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(MainActivity.this, ShowDepartment_User.class);
                startActivity(intent);
                finish();
            }

        }
    }

    public void LogIn(View view) {

        Intent intent = new Intent(MainActivity.this,LogIn.class);
        startActivity(intent);

    }

    public void Register(View view) {
        Intent intent = new Intent(MainActivity.this,Register.class);
        startActivity(intent);
    }
}
