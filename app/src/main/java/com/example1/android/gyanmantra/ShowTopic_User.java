package com.example1.android.gyanmantra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowTopic_User extends AppCompatActivity {
    public ListView mlist;
    DatabaseReference mref;
    ProgressDialog dialog;
    public  String department_name,subject_name,name,Link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_topic__user);


        dialog = new ProgressDialog(ShowTopic_User.this);

        getSupportActionBar().setTitle("Topic");
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        department_name = getIntent().getStringExtra("Department");
        subject_name = getIntent().getStringExtra("Subject");

        mlist = (ListView)findViewById(R.id.DepartmentList);
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ShowTopic_User.this,YoutubePlayer.class);
                String Subject_name_string =  mlist.getItemAtPosition(position).toString();
                i.putExtra("Subject",Subject_name_string);
                i.putExtra("Department",department_name);
                i.putExtra("YoutubeLink",Link);
                startActivity(i);
            }
        });


        final ArrayList<String> myList = new ArrayList<String>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShowTopic_User.this,android.R.layout.simple_list_item_1,myList);
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
                startActivity(new Intent(ShowTopic_User.this,MainActivity.class));
                finish();
                return true;
        }
        return false;
    }
}

