package com.daniel.mamafood.ui.meals;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniel.mamafood.R;
import com.daniel.mamafood.adapters.RecyclerViewAdapter;
import com.daniel.mamafood.model.Meal;

import java.util.List;
import java.util.stream.Collectors;

public class MyMealsFragment extends MealsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Override "add meal" button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_myMeals_to_mealAddFragment);
            }
        });

        // Meal list
        recyclerView = view.findViewById(R.id.meal_list_rv);
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MealsViewModel.class);
        viewModel.getMealLiveData().observe(getViewLifecycleOwner(), mealListUpdateObserver);

        return view;
    }

    Observer<List<Meal>> mealListUpdateObserver = new Observer<List<Meal>>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onChanged(List<Meal> mealArrayList) {
            String currentUserId = currentUser.getUid();
            List<Meal> userMealList = mealArrayList.stream().filter(meal -> meal.getUserId().equals(currentUserId)).collect(Collectors.toList());
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), userMealList, new RecyclerViewAdapter.ListItemClickListener() {
                @Override
                public void onListItemClick(int position) {
                    String mealId = userMealList.get(position).getId();
                    MyMealsFragmentDirections.ActionNavMyMealsToMealDetailsFragment direction = MyMealsFragmentDirections.actionNavMyMealsToMealDetailsFragment(mealId);
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(direction);
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };
}