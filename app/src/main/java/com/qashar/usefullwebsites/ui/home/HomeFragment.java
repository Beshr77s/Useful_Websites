package com.qashar.usefullwebsites.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qashar.usefullwebsites.Adapter.MainAdpter;
import com.qashar.usefullwebsites.Adapter.TypeAdapter;
import com.qashar.usefullwebsites.AddActivity;
import com.qashar.usefullwebsites.InterFaces.MainData;
import com.qashar.usefullwebsites.Model.Model;
import com.qashar.usefullwebsites.Model.Type;
import com.qashar.usefullwebsites.R;
import com.qashar.usefullwebsites.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements MainData {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public TypeAdapter typeAdapter;
    private boolean isLOaded = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        loading();
        String android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        if (android_id.equals("2d1aa0123e7efeb4")|| android_id.equals("2d485a699118fde8")){
            binding.addNew.setVisibility(View.VISIBLE);
        }else {
            binding.addNew.setVisibility(View.GONE);
        }


        try {
            typs();
            setMainAdapterData("تعلم البرمجة");
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AddActivity.class));
            }
        });

        return root;
    }

    private void loading() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("value");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isLOaded = snapshot.getValue(Boolean.class);
                if (isLOaded){
                    binding.progressBar2.setVisibility(View.GONE);
                    Log.d("value","ok");
                    binding.typeRv.setVisibility(View.VISIBLE);

                }else {
                    startActivity(new Intent(getContext(), AddActivity.class));
                    Log.d("value","No");
                }
                Log.d("value",snapshot.getValue(Boolean.class)+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            typs();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void typs() throws Exception {
        ArrayList<Type> strings = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Typs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 :snapshot.getChildren()) {
                    strings.add(new Type(snapshot1.getValue(String.class)));
                    binding.progressBar2.setVisibility(View.GONE);
                    binding.typeRv.setVisibility(View.VISIBLE);
                    binding.mainRV.setVisibility(View.VISIBLE);
                }

                typeAdapter = new TypeAdapter(getContext(), strings, new MainData() {
                    @Override
                    public void setData(String ty) {
                        try {
                            setMainAdapterData(ty);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                binding.typeRv.setAdapter(typeAdapter);
                LinearLayoutManager llm = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                binding.typeRv.setLayoutManager(llm);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAGG",error.getMessage());
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void setMainAdapterData(String ty)throws Exception {
        ArrayList<Model> models = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("WebSites");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               models.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                    String[] result = dataSnapshot.getValue(String.class).split("#");

                    try {
                        String title = result[0];
                        String url = result[1];
                        String des = result[2];
                        String type = result[3];
                        if (type.equals(ty)){
                            models.add(new Model(title,des,url,type));
                        }
                    }catch (Exception e){

                    }

//                    if (type.equals(ty)){
//                            Log.d("KKK",title);
//                        }
//                    Log.d("KKK",title);

                }
                binding.mainRV.setAdapter(new MainAdpter(getContext(),models));
                binding.mainRV.setLayoutManager(new LinearLayoutManager(getContext()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
          }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void setData(String ty) {
        try {
            setMainAdapterData(ty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}