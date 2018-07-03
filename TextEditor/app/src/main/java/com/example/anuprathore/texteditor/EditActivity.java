package com.example.anuprathore.texteditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    Database db;
    TextView save;
    EditText data;
    EditText title;
    Intent intent;
    String heading;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db= new Database(this);

        save = findViewById(R.id.save);
        data = findViewById(R.id.data);
        title = findViewById(R.id.title);
        intent = new Intent(this,MainActivity.class);

        Bundle bundleEdit=getIntent().getExtras();
        assert bundleEdit!=null;
        heading = bundleEdit.getString("TITLE");
        String note=bundleEdit.getString("NOTE");
        id = bundleEdit.getString("ID");
        title.setText(heading);
        data.setText(note);
        data.setSelection(data.getText().length());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long res = db.updatedata(id,heading,title.getText().toString(),data.getText().toString());
                if(res==-1){
                    Toast.makeText(EditActivity.this,"Data not updated!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditActivity.this,"Data is updated!",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }
}
