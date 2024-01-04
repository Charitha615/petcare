package com.example.petcare;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetViewHolder extends RecyclerView.ViewHolder {

    TextView petName,category, petAge, ownerName, userId;
    Button btnDelete;
    public PetViewHolder(@NonNull View itemView) {
        super(itemView);

        petName = itemView.findViewById(R.id.txtname);
        category = itemView.findViewById(R.id.txtcatogray);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }

}
