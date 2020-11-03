package com.gabijakuodyte.todolist2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTaskDesc extends AppCompatActivity {

    EditText titleToDo, descToDo, dateToDo, catToDo;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_desc);

        titleToDo = findViewById(R.id.titleToDo);
        descToDo = findViewById(R.id.descToDo);
        dateToDo = findViewById(R.id.dateToDo);
        catToDo = findViewById(R.id.catToDo);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        titleToDo.setText(getIntent().getStringExtra("titleToDo"));
        descToDo.setText(getIntent().getStringExtra("descToDo"));
        dateToDo.setText(getIntent().getStringExtra("dateToDo"));
        catToDo.setText(getIntent().getStringExtra("catToDo"));

        final String oldKeyToDo = getIntent().getStringExtra("keyToDo");
        reference = FirebaseDatabase.getInstance().getReference().child("ToDoList2").
                child("ToDo" + oldKeyToDo);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent redirectToMainActivity = new Intent(EditTaskDesc.this,MainActivity.class);
                            startActivity(redirectToMainActivity);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titleToDo").setValue(titleToDo.getText().toString());
                        dataSnapshot.getRef().child("descToDo").setValue(descToDo.getText().toString());
                        dataSnapshot.getRef().child("dateToDo").setValue(dateToDo.getText().toString());
                        dataSnapshot.getRef().child("catToDo").setValue(catToDo.getText().toString());
                        dataSnapshot.getRef().child("keyToDo").setValue(oldKeyToDo);

                        Intent redirectToMainActivity = new Intent(EditTaskDesc.this,MainActivity.class);
                        startActivity(redirectToMainActivity);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Failed to update task", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
