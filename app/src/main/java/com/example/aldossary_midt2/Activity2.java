package com.example.aldossary_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    EditText id, name, surname, nationalID;
    Button firstActive, thirdActive, insertBttn;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        db = new DatabaseHelper(this);

        id = (EditText) findViewById(R.id.idEdit);
        name = (EditText) findViewById(R.id.firstNameEdit);
        surname = (EditText) findViewById(R.id.surnameEdit);
        nationalID = (EditText) findViewById(R.id.nationalIDEdit);

        firstActive = (Button) findViewById(R.id.firstActive);
        thirdActive = (Button) findViewById(R.id.thirdActive);
        insertBttn = (Button) findViewById(R.id.insertBttn);

        Toast toast = new Toast(Activity2.this);
        toast.setDuration(Toast.LENGTH_SHORT);

        firstActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity2.this, MainActivity.class));
            }
        });

        thirdActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity2.this, Activity3.class));
            }
        });

        insertBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id2 = id.getText().toString();
                String name2 = name.getText().toString();
                String surname2 = surname.getText().toString();
                String nationalID2 = nationalID.getText().toString();

                if(id2.isEmpty() || name2.isEmpty() || surname2.isEmpty() || nationalID2.isEmpty()) {
                    Log.d("Dalal-Insert", "Empty");
                    toast.setText("TRY AGAIN, EMPTY FIELDS");
                    toast.show();
                } else {
                    Log.d("Dalal-Insert", "Inserted");
                    toast.setText("ADDED SUCCESSFULLY");
                    toast.show();
                    db.addData(id2, name2, surname2, nationalID2);
                }
            }
        });
    }
}