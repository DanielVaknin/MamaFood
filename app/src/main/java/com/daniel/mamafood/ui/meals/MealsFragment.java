package com.daniel.mamafood.ui.meals;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.daniel.mamafood.R;
import com.daniel.mamafood.adapters.MealsAdapter;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MealsFragment extends Fragment {
    MealsViewModel viewModel;

    RecyclerView mealList;
    ProgressBar pb;
    FloatingActionButton fab;
    MealsAdapter adapter;
    SwipeRefreshLayout sref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        viewModel = new ViewModelProvider(this).get(MealsViewModel.class);

        // Floating action Button
        fab = view.findViewById(R.id.appbarmain_add_meal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_meals_to_mealAdd);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        // Swipe to refresh
        sref = view.findViewById(R.id.meal_list_swipe);
        sref.setOnRefreshListener(() -> {
            sref.setRefreshing(true);
            reloadData();
        });

        // Progress bar
        pb = view.findViewById(R.id.meal_list_pb);
        pb.setVisibility(View.INVISIBLE);

        // Meal list
        mealList = view.findViewById(R.id.meal_list_rv);
        mealList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mealList.setLayoutManager(layoutManager);

//        Model.instance.getAllMeals(result -> viewModel.setMealList(result));

        adapter = new MealsAdapter(getLayoutInflater());
//        adapter.data = viewModel.getMealList();
        mealList.setAdapter(adapter);

        adapter.setOnClickListener(new MealsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG", "row was clicked " + position);
            }
        });

        viewModel.getMealList().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> students) {
                adapter.notifyDataSetChanged();
            }
        });
        reloadData();
        return view;
    }

    void reloadData() {
        pb.setVisibility(View.VISIBLE);
        fab.setEnabled(false);
        Model.instance.refreshAllMeals(new Model.GetAllMealsListener() {
            @Override
            public void onComplete(List<Meal> result) {
                pb.setVisibility(View.INVISIBLE);
                fab.setEnabled(true);
                sref.setRefreshing(false);
//                adapter.data = result;
                adapter.notifyDataSetChanged();
            }
        });
    }
}