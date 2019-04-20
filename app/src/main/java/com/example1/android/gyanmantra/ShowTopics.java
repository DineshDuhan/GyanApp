package com.example1.android.gyanmantra;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowTopics extends AppCompatActivity {
    public ListView mlist;
    DatabaseReference mref;
    public String Subject_name_string;
    public  String Link,name,googlelink;
    public  String department_name,subject_name;//,Subject_name_string;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_topics);

        dialog = new ProgressDialog(ShowTopics.this);

        getSupportActionBar().setTitle("Topic");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        department_name = getIntent().getStringExtra("Department");
        subject_name = getIntent().getStringExtra("Subject");

        mlist = (ListView)findViewById(R.id.DepartmentList);



        final ArrayList<String> myList = new ArrayList<String>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShowTopics.this,android.R.layout.simple_list_item_1,myList);
        mlist.setAdapter(arrayAdapter);


        dialog.setMessage("Wait......");
        dialog.show();
        // fetch all
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child("gyan").child(department_name).child("Subjects").child(subject_name).child("Topics");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.i("My msg",dataSnapshot.getKey());
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("My msg",ds.getKey());
                     name = ds.child("Topic").getValue(String.class);
                    Link = ds.child("YoutubeLink").getValue(String.class);
                    googlelink = ds.child("GoogleLink").getValue(String.class);
                  //  Toast.makeText(ShowTopics.this, ""+googlelink, Toast.LENGTH_SHORT).show();
                    Log.i("subject : ",""+name);
                    myList.add(name);
                    arrayAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subject_name_string =  mlist.getItemAtPosition(position).toString();
                AlertDialog.Builder myalert = new AlertDialog.Builder(ShowTopics.this);
                myalert.setTitle("Study Material");
                myalert.setMessage("What do you want to open?");
                myalert.setPositiveButton("Youtube", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ShowTopics.this,YoutubePlayer.class);

                        intent.putExtra("Subject",Subject_name_string);
                        intent.putExtra("Department",department_name);
                        intent.putExtra("YoutubeLink",Link);
                        intent.putExtra("GoogleLink",googlelink);
                        startActivity(intent);

                    }
                });
                myalert.setNegativeButton("Google", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(ShowTopics.this,GoogleStudy.class);

                        intent.putExtra("Subject",Subject_name_string);
                        intent.putExtra("Department",department_name);
                        intent.putExtra("YoutubeLink",Link);
                        intent.putExtra("GoogleLink",googlelink);
                        Toast.makeText(ShowTopics.this, ""+googlelink, Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }
                });
                myalert.show();


            }
        });

    }

    public void AddDepartment(View view) {

        Intent intent = new Intent(ShowTopics.this, AddTopic.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Subject",subject_name);
        intent.putExtra("Department",department_name);
        intent.putExtra("YoutubeLink",Link);
        intent.putExtra("GoogleLink",googlelink);
        startActivity(intent);
        finish();
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
                startActivity(new Intent(ShowTopics.this,MainActivity.class));

                finish();
                return true;
        }
        return false;
    }
}
