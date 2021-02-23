package com.daniel.mamafood.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;

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
        mealPrice.setText(meal.getPrice().toString() + "$");
        this.position = position;
    }
}
