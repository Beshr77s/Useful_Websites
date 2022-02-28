package com.qashar.usefullwebsites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qashar.usefullwebsites.Model.Model;
import com.qashar.usefullwebsites.databinding.ActivityAddBinding;

import java.util.ArrayList;


public class AddActivity extends AppCompatActivity {
    private ActivityAddBinding activityAddBinding;
    private boolean isLOaded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddBinding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(activityAddBinding.getRoot());
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("value");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    isLOaded = snapshot.getValue(Boolean.class);
                  if (isLOaded){
                      Log.d("value","ok");
                      activityAddBinding.lr.setVisibility(View.VISIBLE);
                      activityAddBinding.progressBar.setVisibility(View.GONE);
                  }else {
                      Log.d("value","No");
                  }
                    Log.d("value",snapshot.getValue(Boolean.class)+"");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            typs();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("taggg", e.getMessage());
        }
//        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        Log.d("taggg", android_id);
    }

    private void typs()throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Typs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 :snapshot.getChildren()) {
                    strings.add(snapshot1.getValue(String.class));
                    Log.d("TAGG",snapshot1.getValue(String.class));
                }
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,strings);
                activityAddBinding.dropMenu.setAdapter(stringArrayAdapter);
                activityAddBinding.dropMenu.setThreshold(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAGG",error.getMessage());
                Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void addNewOne(View view) {
        String title = activityAddBinding.etTitle.getText().toString();
        String des =activityAddBinding.etDes.getText().toString();
        String url = activityAddBinding.etURL.getText().toString();
        String type = activityAddBinding.dropMenu.getText().toString();
        if (!title.isEmpty()||des.isEmpty()||url.isEmpty()||type.isEmpty()){
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("WebSites");
            reference.push().setValue(title+"#"+url+"#"+des+"#"+type);
            Snackbar.make(view,"تم الاضافة بنجاح !",Snackbar.LENGTH_SHORT).show();
            activityAddBinding.etURL.setText("");
            activityAddBinding.etDes.setText("");
            activityAddBinding.etTitle.setText("");
        }else {
            Snackbar.make(view,"هنالك حقل فارغ !",Snackbar.LENGTH_SHORT).show();
        }
    }

    public void addType(View view) {
        startActivity(new Intent(getApplicationContext(),AddTypeActivity.class));
    }
}