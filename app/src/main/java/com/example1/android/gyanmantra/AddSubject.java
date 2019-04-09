package com.example1.android.gyanmantra;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddSubject extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference reference;
   private EditText addSubject;
   public  String department_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        getSupportActionBar().setTitle("Add Subject");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addSubject = (EditText)findViewById(R.id.AddSubject);
         department_name = getIntent().getStringExtra("Department");

    }

    public void PostSubject(View view) {
        reference = FirebaseDatabase.getInstance().getReference("gyan").child(department_name).child("Subjects").child(addSubject.getText().toString());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("subject", addSubject.getText().toString());
        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent = new Intent(AddSubject.this, ShowSubject.class);
                intent.putExtra("Department",department_name);
                startActivity(intent);

            }
        });

    }

    public void showSubject(View view) {
        Intent intent = new Intent(AddSubject.this, ShowSubject.class);
        intent.putExtra("Department",department_name);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AddSubject.this,MainActivity.class));
                finish();
                return true;
        }
        return false;
    }
}
