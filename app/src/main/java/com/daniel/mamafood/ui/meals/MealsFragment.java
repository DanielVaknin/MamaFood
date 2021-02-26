package com.daniel.mamafood.ui.meals;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.mamafood.R;
import com.daniel.mamafood.adapters.MealsAdapter;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;

import java.util.List;

public class MealsFragment extends Fragment {

    RecyclerView mealList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        mealList = view.findViewById(R.id.meal_list_rv);
        mealList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mealList.setLayoutManager(layoutManager);

        List<Meal> data = Model.instance.getAllMeals();

        MealsAdapter adapter = new MealsAdapter(getLayoutInflater());
        adapter.data = data;
        mealList.setAdapter(adapter);

        adapter.setOnClickListener(new MealsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG", "row was clicked " + position);
            }
        });

        return view;
    }
}