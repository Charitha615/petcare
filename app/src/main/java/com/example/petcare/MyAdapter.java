package com.example.petcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<PetViewHolder> {

    Context context;
    List<Pet> items;

    public MyAdapter(Context context, List<Pet> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PetViewHolder(LayoutInflater.from(context).inflate(R.layout.main_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.petName.setText(items.get(position).getPetName());
        holder.category.setText(items.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
