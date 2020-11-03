package com.gabijakuodyte.todolist2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView topSection, bottomSection;
    Button btnAddNew;

    DatabaseReference reference;
    RecyclerView myToDos;
    ArrayList<MyToDo> list;
    ToDoAdapter toDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topSection = findViewById(R.id.topSection);
        bottomSection = findViewById(R.id.bottomSection);
        btnAddNew = findViewById(R.id.btnAddNew);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newTaskRedirect = new Intent(MainActivity.this,NewTaskAdd.class);
                startActivity(newTaskRedirect);
            }
        });


        myToDos = findViewById(R.id.myToDos);
        myToDos.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyToDo>();

        reference = FirebaseDatabase.getInstance().getReference().child("ToDoList2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyToDo p = dataSnapshot1.getValue(MyToDo.class);
                    list.add(p);
                }
                toDoAdapter = new ToDoAdapter(MainActivity.this, list);
                myToDos.setAdapter(toDoAdapter);
                toDoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

