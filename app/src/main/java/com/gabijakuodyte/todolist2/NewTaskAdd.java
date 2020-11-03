package com.gabijakuodyte.todolist2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskAdd extends AppCompatActivity {

    TextView topSection, addtitle, adddesc, adddate, addcat;
    EditText titleToDo, descToDo, dateToDo, catToDo;
    Button btnSaveTask, btnCancel;
    DatabaseReference reference;
    Integer toDoID = new Random().nextInt();
    String keyToDo = Integer.toString(toDoID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_add);

        topSection = findViewById(R.id.topSection);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        addcat = findViewById(R.id.addcat);
        adddate = findViewById(R.id.adddate);

        titleToDo = findViewById(R.id.titleToDo);
        descToDo = findViewById(R.id.descToDo);
        catToDo = findViewById(R.id.catToDo);
        dateToDo = findViewById(R.id.dateToDo);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("ToDoList2").
                        child("ToDo" + toDoID);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("titleToDo").setValue(titleToDo.getText().toString());
                        dataSnapshot.getRef().child("descToDo").setValue(descToDo.getText().toString());
                        dataSnapshot.getRef().child("dateToDo").setValue(dateToDo.getText().toString());
                        dataSnapshot.getRef().child("catToDo").setValue(catToDo.getText().toString());
                        dataSnapshot.getRef().child("keyToDo").setValue(keyToDo);

                        Intent redirectToActivity = new Intent(NewTaskAdd.this,MainActivity.class);
                        startActivity(redirectToActivity);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Failed to create task", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToActivity = new Intent(NewTaskAdd.this,MainActivity.class);
                startActivity(redirectToActivity);

            }
        });
    }
}
