package com.example.anuprathore.texteditor;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {

    Database db;
    Intent intentEdit;
    Intent intentBack;
    TextView edit;
    String heading;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        db = new Database(this);

        edit = findViewById(R.id.edit);
        TextView back = findViewById(R.id.back);
        TextView header = findViewById(R.id.header);
        TextView note = findViewById(R.id.content);
        
        intentEdit = new Intent(this, EditActivity.class);
        intentBack = new Intent(this, MainActivity.class);
        
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        heading = bundle.getString("heading");
        id = bundle.getString("id");
        
        header.setText(heading);
        Cursor data = db.getData(id, heading);
        String value = null;
        if (data.getCount() == 0) {
            Toast.makeText(DisplayActivity.this, "No items found"+data.getColumnCount(), Toast.LENGTH_LONG).show();
        } else {
            data.moveToFirst();
            value = data.getString(2);
            note.setText(value);
        }

        intentEdit.putExtra("TITLE", heading);
        intentEdit.putExtra("NOTE", value);
        intentEdit.putExtra("ID", id);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup=new PopupMenu(DisplayActivity.this,edit);
                popup.getMenuInflater().inflate(R.menu.menu_main,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equals("Update")){
                            startActivity(intentEdit);
//                            Toast.makeText(DisplayActivity.this, "columns "+item.getTitle().toString(), Toast.LENGTH_LONG).show();
                        }
                        else if(item.getTitle().toString().equals("Delete")){
                            startActivity(intentBack);
                            long res = db.deletedata(id,heading);
                            if (res == -1) {
                                Toast.makeText(DisplayActivity.this, "Cannot Delete.Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DisplayActivity.this, "Note deleted!", Toast.LENGTH_LONG).show();
                            }
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentBack);
            }
        });

    }

}
