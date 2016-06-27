package com.example.ridhdhi.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapater;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 20;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapater);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                todoItems.remove(position);
                aToDoAdapater.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                String selectedFromList =(lvItems.getItemAtPosition(position).toString());
                launchEditView(selectedFromList);
            }
        });
    }

    public void populateArrayItems() {
        readItems();
        aToDoAdapater = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, todoItems);
    }

    public void onAddItem(View view) {
        aToDoAdapater.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private void readItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir,"todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch (IOException e) {
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir,"todo.txt");
        try {
           FileUtils.writeLines(file,todoItems);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchEditView(String itemName) {
        // first parameter is the context, second is the class of the activity to launch
        Intent editItent = new Intent(this, EditItemActivity.class);
        // put "extras" into the bundle for access in the second activity
        editItent.putExtra("itemName",itemName);
        startActivityForResult(editItent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String name = data.getExtras().getString("newItemName");
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            todoItems.remove(mPosition);
            todoItems.add(mPosition, name);
            writeItems();
        }
        aToDoAdapater.notifyDataSetChanged();
    }
}
