package com.daniel.mamafood.ui.meals;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class MealUpdateFragment extends MealAddFragment {

    Meal currentMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        String mealId = MealUpdateFragmentArgs.fromBundle(getArguments()).getMealId();
        Log.d("TAG", "Found meal with id: " + mealId);

        // Display details of current meal
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

                currentMeal = meal;
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMeal(v, currentMeal);
            }
        });

        return view;
    }

    private void updateMeal(View view, Meal currentMeal) {
        if (nameEditText.getText().length() == 0 || descEditText.getText().length() == 0 || priceEditText.getText().length() == 0) {
            Snackbar.make(view, "You must provide a value for each of the fields", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Log.d("TAG", "One of more of the meal fields is empty.");
        }
        else {
            currentMeal.setName(nameEditText.getText().toString());
            currentMeal.setDescription(descEditText.getText().toString());
            currentMeal.setPrice(Double.parseDouble(priceEditText.getText().toString()));
            currentMeal.setAddress(addressEditText.getText().toString());

            // Set image for the meal and add the meal
            BitmapDrawable drawable = (BitmapDrawable) avatarImageView.getDrawable();
            Model.instance.uploadImage(drawable.getBitmap(), currentMeal.getId(), new Model.UploadImageListener() {
                @Override
                public void onComplete(String url) {
                    if (url == null) {
                        displayFailedError();
                    }
                    else {
                        currentMeal.setImageUrl(url);
                        pb.setVisibility(View.VISIBLE);
                        Model.instance.updateMeal(currentMeal, new Model.UpdateMealListener() {
                            @Override
                            public void onComplete() {
                                pb.setVisibility(View.INVISIBLE);
                                Navigation.findNavController(saveBtn).popBackStack();
                            }
                        });
                    }
                }
            });
        }
    }
}