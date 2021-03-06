package com.daniel.mamafood.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final private ListItemClickListener mOnClickListener;

    Context context;
    List<Meal> mealArrayList;

    public RecyclerViewAdapter(Context context, List<Meal> mealArrayList, ListItemClickListener onClickListener) {
        this.context = context;
        this.mealArrayList = mealArrayList;
        this.mOnClickListener = onClickListener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.meal_list_row, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Meal meal = mealArrayList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        viewHolder.txtView_name.setText(meal.getName());
        viewHolder.txtView_seller.setText(meal.getUserName());
        viewHolder.txtView_price.setText(meal.getPrice().toString() + " $");

        // Get image from Firestore using Picasso
        viewHolder.imgView_icon.setImageResource(R.drawable.meal_placeholder); // So that we'll first show the empty avatar as the load takes time
        if (meal.getImageUrl() != null) {
            Picasso.get().load(meal.getImageUrl()).placeholder(R.drawable.meal_placeholder).into(viewHolder.imgView_icon);
        }
    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgView_icon;
        TextView txtView_name;
        TextView txtView_price;
        TextView txtView_seller;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imgView_icon = itemView.findViewById(R.id.meal_row_image);
            txtView_name = itemView.findViewById(R.id.meal_row_name);
            txtView_price = itemView.findViewById(R.id.meal_row_price);
            txtView_seller = itemView.findViewById(R.id.meal_row_seller);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }
}