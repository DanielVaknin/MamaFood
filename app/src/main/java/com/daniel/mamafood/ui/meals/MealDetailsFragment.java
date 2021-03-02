package com.daniel.mamafood.ui.meals;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;
import com.squareup.picasso.Picasso;

public class MealDetailsFragment extends MealAddFragment {
    Meal meal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        editImage.setVisibility(View.INVISIBLE);
        nameEditText.setEnabled(false);
        descEditText.setEnabled(false);
        priceEditText.setEnabled(false);
        addressEditText.setEnabled(false);
        saveBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.INVISIBLE);

        final String mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        Log.d("TAG", "Found meal with id: " + mealId);

        Model.instance.getMeal(mealId, new Model.GetMealListener() {
            @Override
            public void onComplete(Meal meal) {
                nameEditText.setText(meal.getName());
                descEditText.setText(meal.getDescription());
                addressEditText.setText(meal.getAddress());
                priceEditText.setText(meal.getPrice().toString());

                if (meal.getImageUrl() != null){
                    Picasso.get().load(meal.getImageUrl()).placeholder(R.drawable.meal_placeholder).into(avatarImageView);
                }
            }
        });

        return view;
    }
}