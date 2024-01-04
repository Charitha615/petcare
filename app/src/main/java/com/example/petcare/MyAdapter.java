package com.example.petcare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        View view = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.petName.setText(items.get(position).getPetName());
        holder.category.setText(items.get(position).getCategory());
        holder.setOnDeleteClickListener(new PetViewHolder.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Implement the delete logic here
                deleteItem(position);
            }
        });
    }

    private void deleteItem(final int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets");

        // Get the unique key of the item to be deleted
        String itemKey = items.get(position).getItemKey(); // Add a method to your Pet class to retrieve the item key

        // Remove the item from Firebase
        databaseReference.child(itemKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // If the deletion from Firebase is successful, update the local list
                    items.remove(position);
                    // Notify the adapter that the data set has changed
                    notifyDataSetChanged();
                } else {
                    // Handle the error case, if needed
                    Log.e("MyAdapter", "Failed to delete item from Firebase");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}
