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
import com.daniel.mamafood.adapters.RecyclerViewAdapter;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MealsFragment extends Fragment {
    MealsViewModel viewModel;

    ProgressBar pb;
    FloatingActionButton fab;
    SwipeRefreshLayout sref;
    FirebaseAuth mAuth;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        // Check if user is logged-in
        // Will be replaced with the below IF condition when it will work - we will check if the user is already signed in - if so,
        // we will navigate to "add meal" page, else, we will navigate to login page
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            //return inflater.inflate(R.layout.fragment_login, container, false);
            Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_meals_to_loginFragment);
        }

        // Floating action Button
        fab = view.findViewById(R.id.appbarmain_add_meal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_meals_to_mealAddFragment);
            }
        });

        // Swipe to refresh
        sref = view.findViewById(R.id.meal_list_swipe);
        sref.setOnRefreshListener(() -> {
            sref.setRefreshing(true);
            pb.setVisibility(View.VISIBLE);
            fab.setEnabled(false);
            Model.instance.refreshAllMeals(new Model.GetAllMealsListener() {
                @Override
                public void onComplete(List<Meal> result) {
                    sref.setRefreshing(false);
                    pb.setVisibility(View.INVISIBLE);
                    fab.setEnabled(true);
                }
            });
        });

        // Progress bar
        pb = view.findViewById(R.id.meal_list_pb);
        pb.setVisibility(View.INVISIBLE);

        // Meal list
        recyclerView = view.findViewById(R.id.meal_list_rv);
        recyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MealsViewModel.class);
        viewModel.getMealLiveData().observe(getViewLifecycleOwner(), mealListUpdateObserver);

        return view;
    }

    Observer<List<Meal>> mealListUpdateObserver = new Observer<List<Meal>>() {
        @Override
        public void onChanged(List<Meal> mealArrayList) {
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), mealArrayList, new RecyclerViewAdapter.ListItemClickListener() {
                @Override
                public void onListItemClick(int position) {
                    Log.d("TAG", "Row was clicked: " + position);
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };
}