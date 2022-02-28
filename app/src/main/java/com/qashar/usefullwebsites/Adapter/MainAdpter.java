package com.qashar.usefullwebsites.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.qashar.usefullwebsites.Model.Model;
import com.qashar.usefullwebsites.Model.Type;
import com.qashar.usefullwebsites.R;

import java.util.ArrayList;

public class MainAdpter extends RecyclerView.Adapter<MainAdpter.MyViewHolder> {
    final Context context;
    final ArrayList<Model> models;
    boolean isUp = false;



    public MainAdpter(Context context, final ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.Title.setText(models.get(position).getTitle());
        holder.Details.setText(models.get(position).getDetails());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setAction(models.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUp){
                    isUp = false;
                    holder.Details.setVisibility(View.GONE);
                    holder.button.setVisibility(View.GONE);
//                    holder.imageView.setImageResource(R.drawable.ic_down);
                }else {
                    holder.Details.setVisibility(View.VISIBLE);
                    holder.button.setVisibility(View.VISIBLE);
                    isUp = true;
//                    holder.imageView.setImageResource(R.drawable.ic_up);
                }
            }

        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(models.get(position).getUrl()));
                try {
                    context.startActivity(intent);
                }catch (Exception e){

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Title, Details;
        private Button button;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.txtTitle);
            button = itemView.findViewById(R.id.btnGo);
            imageView = itemView.findViewById(R.id.imageView);
            Details = itemView.findViewById(R.id.txtDetails);

        }


    }
}