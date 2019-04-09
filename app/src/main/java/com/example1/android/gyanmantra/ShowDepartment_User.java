package com.example1.android.gyanmantra;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowDepartment_User extends AppCompatActivity {
    public ListView mlist;
    DatabaseReference mref;
    ProgressDialog dialog;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_department__user);

        dialog = new ProgressDialog(ShowDepartment_User.this);

        getSupportActionBar().setTitle("Department");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
      getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mlist = (ListView)findViewById(R.id.DepartmentList);

        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ShowDepartment_User.this,ShowSubject_User.class);
                String department_name_string =  mlist.getItemAtPosition(position).toString();
                i.putExtra("Department",department_name_string);
                startActivity(i);
            }
        });

        final ArrayList<String> myList = new ArrayList<String>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShowDepartment_User.this,android.R.layout.simple_list_item_1,myList);
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
     /*   for (int i = 0; i < mlist.getChildCount(); i++) {
            ((TextView)mlist.getChildAt(i)).setTextColor(Color.WHITE);
        }
*/
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
                startActivity(new Intent(ShowDepartment_User.this,MainActivity.class));
                finish();
                return true;
        }
        return false;
    }

}
