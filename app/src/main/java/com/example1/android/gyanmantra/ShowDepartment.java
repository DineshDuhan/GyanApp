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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.security.auth.Subject;

public class ShowDepartment extends AppCompatActivity {
    public ListView mlist;
    DatabaseReference mref;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_department);


        dialog = new ProgressDialog(ShowDepartment.this);

        getSupportActionBar().setTitle("Department");
      //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mlist = (ListView)findViewById(R.id.DepartmentList);

        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Intent i = new Intent(ShowDepartment.this,ShowSubject.class);
                 String department_name_string =  mlist.getItemAtPosition(position).toString();
                 i.putExtra("Department",department_name_string);
                 startActivity(i);
            }
        });

        final ArrayList<String> myList = new ArrayList<String>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShowDepartment.this,android.R.layout.simple_list_item_1,myList);
        mlist.setAdapter(arrayAdapter);


        dialog.setMessage("Wait......");
        dialog.show();
        // fetch all
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child("gyan");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.i("My msg",dataSnapshot.getKey());
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("My msg",ds.getKey());
                    String name = ds.child("DepartmentName").getValue(String.class);
                    Log.i("Department : ",""+name);
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

    public void AddDepartment(View view) {

        Intent intent = new Intent(ShowDepartment.this, PostDepartment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                startActivity(new Intent(ShowDepartment.this,MainActivity.class));
                finish();
                return true;
        }
        return false;
    }
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

}
