package com.daniel.mamafood.ui.meals;

import android.net.Uri;
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
import android.widget.Toast;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MealDetailsFragment extends MealAddFragment {

    String mealId;
    String mealUserId;
    Meal mMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setHasOptionsMenu(true);

        editImage.setVisibility(View.INVISIBLE);
        nameEditText.setEnabled(false);
        nameEditText.setBackgroundResource(android.R.color.transparent);
        descEditText.setEnabled(false);
        descEditText.setBackgroundResource(android.R.color.transparent);
        priceEditText.setEnabled(false);
        priceEditText.setBackgroundResource(android.R.color.transparent);
        addressEditText.setEnabled(false);
        addressEditText.setBackgroundResource(android.R.color.transparent);
        saveBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setVisibility(View.INVISIBLE);
        buyBtn.setVisibility(View.VISIBLE);
        pb.setVisibility(View.INVISIBLE);

        mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        Log.d("TAG", "Found meal with id: " + mealId);

        Model.instance.getMeal(mealId, new Model.GetMealListener() {
            @Override
            public void onComplete(Meal meal) {
                mMeal = meal;

                nameEditText.setText(meal.getName());
                descEditText.setText(meal.getDescription());
                addressEditText.setText("Address: " + meal.getAddress());
                addressEditText.setTextSize(16);
                priceEditText.setText("Price ($): " + meal.getPrice().toString());

                if (meal.getImageUrl() != null){
                    Picasso.get().load(meal.getImageUrl()).placeholder(R.drawable.meal_placeholder).into(avatarImageView);
                }

                mealUserId = meal.getUserId(); // Get meal user ID for future use
                getActivity().invalidateOptionsMenu(); // now onCreateOptionsMenu(...) is called again

                buyBtn.setOnClickListener(v -> Snackbar.make(v, "Great! The chef " + meal.getUserName() + " will contact you shortly :)", Snackbar.LENGTH_SHORT).show());
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Display options menu only when logged in as the user of the meal
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getUid().equals(mealUserId)) {
            inflater.inflate(R.menu.main, menu);
        }
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
                pb.setVisibility(View.VISIBLE);
                Model.instance.deleteMeal(mMeal, new Model.DeleteMealListener() {
                    @Override
                    public void onComplete() {
                        pb.setVisibility(View.INVISIBLE);
                    }
                });
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
                return true;
        }

        return false;
    }
}