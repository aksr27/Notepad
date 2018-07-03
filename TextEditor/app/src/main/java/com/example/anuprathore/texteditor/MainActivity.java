package com.example.anuprathore.texteditor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database db;
    ArrayList<String> id_list;
    ArrayList<String> t_list;
    ArrayList<String> title_list;
    ListView listView;
    TextView addNew;
    Intent intent;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        db=new Database(this);
        listView= findViewById(R.id.list);
        intent = new Intent(this, DisplayActivity.class);
        intent1 = new Intent(this, NewActivity.class);
        addNew = findViewById(R.id.add);
        title_list=new ArrayList<>();
        id_list=new ArrayList<>();
        t_list=new ArrayList<>();
        
        final Cursor data = db.getHeading();
            if (data.getCount() == 0) {
                Toast.makeText(MainActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                }
            else {
                while ((data.moveToNext())) {
                    title_list.add(data.getString(data.getColumnIndex("TITLE")));
                    id_list.add(data.getString(data.getColumnIndex("ID")));
                    t_list.add(data.getString(data.getColumnIndex("TIME")));
                }
                    ListAdapter listAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,title_list);
                    listView.setAdapter(listAdapter);
//                Toast.makeText(MainActivity.this, "timelist "+t_list.get(0),Toast.LENGTH_SHORT).show();
            }


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                startActivity(intent1);
            }
        });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("heading", listView.getItemAtPosition(position).toString());
                int x=(int)id;
//                Toast.makeText(MainActivity.this, "list "+String.valueOf(id_list.get(x)), Toast.LENGTH_SHORT).show();
                intent.putExtra("id",String.valueOf(id_list.get(x)));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
           Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            db.close();
            return true;
        }
        return false;
    }

}
