package com.daniel.mamafood.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;
import com.squareup.picasso.Picasso;

public class MealViewHolder extends RecyclerView.ViewHolder {
    public MealsAdapter.OnItemClickListener listener;

    TextView mealName;
    TextView mealPrice;
    ImageView mealImage;

    int position;

    public MealViewHolder(@NonNull View itemView) {
        super(itemView);
        mealName = itemView.findViewById(R.id.meal_row_name);
        mealPrice = itemView.findViewById(R.id.meal_row_price);
        mealImage = itemView.findViewById(R.id.meal_row_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    public void bindData(Meal meal, int position) {
        mealName.setText(meal.getName());
        mealPrice.setText(meal.getPrice().toString() + " $");

        // Get image from Firestore using Picasso
        mealImage.setImageResource(R.drawable.meal_placeholder); // So that we'll first show the empty avatar as the load takes time
        if (meal.getImageUrl() != null) {
            Picasso.get().load(meal.getImageUrl()).placeholder(R.drawable.meal_placeholder).into(mealImage);
        }

        this.position = position;
    }
}
