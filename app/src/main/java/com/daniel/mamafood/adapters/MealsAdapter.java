package com.daniel.mamafood.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealViewHolder> {
    public List<Meal> data;
    LayoutInflater inflater;

    public MealsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.meal_list_row, null);
        MealViewHolder holder = new MealViewHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = data.get(position);
        holder.bindData(meal, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
