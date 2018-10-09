package com.example.quangtan.thuvien_tan_linh_minh;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String DATABASE_NAME = "library.db";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;
    Button btnTimKiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        database = Database.initDatabase(this, DATABASE_NAME);
//        Cursor cursor = database.rawQuery("SELECT * FROM book", null);
//        cursor.moveToFirst();
//        Toast.makeText(this, cursor.getString(1), Toast.LENGTH_SHORT).show();
        load();
        readData();
        timKiem();
    }

    private void load(){
        listView = (ListView) findViewById(R.id.listViewBook);
        list = new ArrayList<String>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    private void readData(){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM book",null);
        list.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String tenSach = cursor.getString(1);
            list.add(tenSach);
        }
        adapter.notifyDataSetChanged();
    }

    private void timKiem(){
        btnTimKiem = (Button) findViewById(R.id.btnTimKiem);
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtTimKiem = (EditText) findViewById(R.id.txtTimKiem);
                String query = "SELECT * FROM book WHERE tenSach LIKE '%" + txtTimKiem.getText() + "%'";
                database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                Cursor cursor = database.rawQuery(query,null);
                list.clear();
                for(int i = 0; i < cursor.getCount(); i++){
                    cursor.moveToPosition(i);
                    int id = cursor.getInt(0);
                    String tenSach = cursor.getString(1);
                    list.add(tenSach);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
