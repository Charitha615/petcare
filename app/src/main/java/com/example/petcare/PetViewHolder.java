package com.example.petcare;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetViewHolder extends RecyclerView.ViewHolder {

    TextView petName, category;
    Button btnDelete;

    public PetViewHolder(@NonNull View itemView) {
        super(itemView);

        petName = itemView.findViewById(R.id.txtname);
        category = itemView.findViewById(R.id.txtcatogray);
        btnDelete = itemView.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the delete logic here
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Call the onDeleteClickListener method implemented in the Activity/Fragment
                    onDeleteClickListener.onDeleteClick(position);
                }
            }
        });
    }

    // Interface to handle delete button click events
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }
}

