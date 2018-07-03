package com.example.anuprathore.texteditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NewActivity extends AppCompatActivity {

    Database db;
    TextView cancel;
    TextView save;
    EditText data;
    EditText title;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        db= new Database(this);
        cancel= findViewById(R.id.delete);
        save= findViewById(R.id.save);
        data= findViewById(R.id.data);
        title= findViewById(R.id.title);
        intent=new Intent(this,MainActivity.class);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String heading = title.getText().toString();
                String note = data.getText().toString();
                long res;
                if (!note.isEmpty()) {
                    res = db.insertdata(heading, note);
                    Toast.makeText(NewActivity.this, "Note Saved.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    if (res == -1) {
                        Toast.makeText(NewActivity.this, "Cannot Save.Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewActivity.this, "Oops you forgot to write something!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }
}