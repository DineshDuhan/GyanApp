package com.example1.android.gyanmantra;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddTopic extends AppCompatActivity {
    Button showtopic;
    FirebaseAuth auth;
    DatabaseReference reference;
    private EditText addTopic,youtubelink,googleLink;
    public  String department_name,subject_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        getSupportActionBar().setTitle("Add Topic");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        department_name = getIntent().getStringExtra("Department");
        subject_name = getIntent().getStringExtra("Subject");

        addTopic = (EditText)findViewById(R.id.AddTopic);
        youtubelink = (EditText)findViewById(R.id.youtubeLink);
        googleLink = (EditText)findViewById(R.id.GoogleLink);
        showtopic = (Button)findViewById(R.id.showTopic);
        showtopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTopic.this, ShowTopics.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Department",department_name);
                intent.putExtra("Subject",subject_name);
                startActivity(intent);
                finish();
            }
        });


    }

    public void PostSubject(View view) {
        reference = FirebaseDatabase.getInstance().getReference("gyan").child(department_name).child("Subjects").child(subject_name).child("Topics").child(addTopic.getText().toString());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Topic", addTopic.getText().toString());
        hashMap.put("YoutubeLink",youtubelink.getText().toString());
        hashMap.put("GoogleLink",googleLink.getText().toString());
        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent = new Intent(AddTopic.this, ShowTopics.class);
                intent.putExtra("Department",department_name);
                intent.putExtra("Subject",subject_name);
                startActivity(intent);

            }
        });

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
                startActivity(new Intent(AddTopic.this,MainActivity.class));
                finish();
                return true;
        }
        return false;
    }

}
