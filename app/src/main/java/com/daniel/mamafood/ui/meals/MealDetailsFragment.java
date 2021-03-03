package com.daniel.mamafood.ui.meals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;
import com.squareup.picasso.Picasso;

public class MealDetailsFragment extends MealAddFragment {

    String mealId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setHasOptionsMenu(true);

        editImage.setVisibility(View.INVISIBLE);
        nameEditText.setEnabled(false);
        descEditText.setEnabled(false);
        priceEditText.setEnabled(false);
        addressEditText.setEnabled(false);
        saveBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.INVISIBLE);

        mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                MealDetailsFragmentDirections.ActionMealDetailsFragmentToMealUpdateFragment direction = MealDetailsFragmentDirections.actionMealDetailsFragmentToMealUpdateFragment(mealId);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(direction);
                return true;

            case R.id.action_delete:
                Log.d("TAG", "Clicked on delete");
                return true;
        }

        return false;
    }
}