package com.qashar.usefullwebsites.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.qashar.usefullwebsites.InterFaces.MainData;
import com.qashar.usefullwebsites.Model.Type;
import com.qashar.usefullwebsites.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder> {
    final Context context;
    final ArrayList<Type> modelFeedArrayList;
    public boolean isSelected = false;
    private int checkedPos = 0;
    private MainData data;

    public TypeAdapter(Context context, ArrayList<Type> modelFeedArrayList) {
        this.context = context;
        this.modelFeedArrayList = modelFeedArrayList;
    }

    public TypeAdapter(Context context, final ArrayList<Type> modelFeedArrayList, MainData mainData) {
        this.context = context;
        this.data = mainData;
        this.modelFeedArrayList = modelFeedArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_typs, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.textView.setText(modelFeedArrayList.get(position).getName());
//        holder.bind(modelFeedArrayList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setData(modelFeedArrayList.get(position).getName());
                holder.itemView.setBackgroundResource(R.drawable.type_row);
                if (checkedPos!=position){
                    notifyItemChanged(checkedPos);
                    checkedPos=position;

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelFeedArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Chip chip;
        private int checkedPos = 0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ypeTXT);
            chip = itemView.findViewById(R.id.chip4);
        }


        public void bind(Type type) {
            if (checkedPos == -1){
            }else {
                if (checkedPos == getAdapterPosition()){
//                    itemView.setBackgroundColor(Color.GREEN);
                }else {
                }
            }
        }
    }

}