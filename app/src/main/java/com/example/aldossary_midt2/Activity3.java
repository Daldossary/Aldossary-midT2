package com.example.aldossary_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity3 extends AppCompatActivity {

    DatabaseHelper db;
    EditText mainInput;
    Button viewBttn, deleteBttn;
    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        mainInput = (EditText) findViewById(R.id.mainInputEdit);
        viewBttn = (Button) findViewById(R.id.viewBttn);
        deleteBttn = (Button) findViewById(R.id.deleteBttn);
        view = (TextView) findViewById(R.id.viewTable);
        Toast toast = new Toast(Activity3.this);
        toast.setDuration(Toast.LENGTH_SHORT);

        viewBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.getListContents();
                String records = "";
                int i = 0;
                while(c.moveToNext()) {
                    records+= c.getString(i);
                }
                view.setText(records);
            }
        });

        deleteBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mainInput.getText().toString();
                if(text.isEmpty()) {
                    toast.setText("TRY AGAIN, EMPTY FIELDS");
                    toast.show();
                    Log.d("Dalal-Delete", "Empty");
                } else {
                    //CHECK DATABASEHELPER
                    toast.setText("RECORD: " + text);
                    toast.show();
                    db.deleteEntry(text);
                    Log.d("Dalal-Delete", "Deleted");
                }
            }
        });

    }
}