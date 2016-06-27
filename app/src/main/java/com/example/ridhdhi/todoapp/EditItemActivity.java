package com.example.ridhdhi.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    EditText etItemToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemToEdit = getIntent().getStringExtra("itemName");
        etItemToEdit = (EditText) findViewById(R.id.etItemToEdit);
        etItemToEdit.setText(itemToEdit);
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }

    public void onSaveItem(View v) {
        Intent data = new Intent();
        data.putExtra("newItemName", etItemToEdit.getText().toString());
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish();
    }

}