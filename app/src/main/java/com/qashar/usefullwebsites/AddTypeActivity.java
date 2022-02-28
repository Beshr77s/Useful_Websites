package com.qashar.usefullwebsites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.qashar.usefullwebsites.Model.Type;

public class AddTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
    }

    public void onAdd(View view) {
        EditText text = findViewById(R.id.etName);
        if (text.getText().toString().isEmpty()){
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("Typs");
            reference.push().setValue(text.getText().toString());
            Snackbar.make(view,"تم الاضافة بنجاح",Snackbar.LENGTH_SHORT).show();
            text.setText("");
        }else {
            Snackbar.make(view,"هنالك حقل فارغ !",Snackbar.LENGTH_SHORT).show();
        }
    }
}