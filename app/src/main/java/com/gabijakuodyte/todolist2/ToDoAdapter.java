package com.gabijakuodyte.todolist2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder>{

    Context context;
    ArrayList<MyToDo> myToDos;
    DatabaseReference reference;

    public ToDoAdapter(Context c, ArrayList<MyToDo> p) {
        context = c;
        myToDos = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.titleToDo.setText(myToDos.get(i).getTitleToDo());
        myViewHolder.descToDo.setText(myToDos.get(i).getDescToDo());
        myViewHolder.dateToDo.setText(myToDos.get(i).getDateToDo());
        myViewHolder.catToDo.setText(myToDos.get(i).getCatToDo());

        final String getTitleToDo = myToDos.get(i).getTitleToDo();
        final String getCatToDo = myToDos.get(i).getCatToDo();
        final String getDescToDo = myToDos.get(i).getDescToDo();
        final String getDateToDo = myToDos.get(i).getDateToDo();
        final String getKeyToDo = myToDos.get(i).getKeyToDo();

        final String oldKeyToDo = getKeyToDo;
        reference = FirebaseDatabase.getInstance().getReference().child("ToDoList2").
                child("ToDo" + oldKeyToDo);

        myViewHolder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent redirectToMainActivity = new Intent(context,MainActivity.class);
                            context.startActivity(redirectToMainActivity);
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editActivityRedirect = new Intent(context,EditTaskDesc.class);
                editActivityRedirect.putExtra("titleToDo", getTitleToDo);
                editActivityRedirect.putExtra("descToDo", getDescToDo);
                editActivityRedirect.putExtra("dateToDo", getDateToDo);
                editActivityRedirect.putExtra("catToDo", getCatToDo);
                editActivityRedirect.putExtra("keyToDo", getKeyToDo);
                context.startActivity(editActivityRedirect);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myToDos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleToDo, descToDo, dateToDo, catToDo, keyToDo;
        Button btnDone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleToDo = (TextView) itemView.findViewById(R.id.titleToDo);
            descToDo = (TextView) itemView.findViewById(R.id.descToDo);
            dateToDo = (TextView) itemView.findViewById(R.id.dateToDo);
            catToDo = (TextView) itemView.findViewById(R.id.catToDo);
            btnDone = (Button) itemView.findViewById(R.id.btnDone);
        }
    }

}
