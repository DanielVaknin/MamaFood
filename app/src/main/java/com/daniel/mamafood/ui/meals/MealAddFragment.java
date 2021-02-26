package com.daniel.mamafood.ui.meals;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.daniel.mamafood.R;

public class MealAddFragment extends Fragment {

    ImageView avatarImageView;
    ImageButton editImage;
    EditText nameEditText;
    EditText descEditText;
    EditText priceEditText;
    Button saveBtn;
    Button cancelBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_add, container, false);

        avatarImageView = view.findViewById(R.id.addmeal_avatar_imageview);
        editImage = view.findViewById(R.id.addmeal_edit_image_btn);
        nameEditText = view.findViewById(R.id.addmeal_name_edittext);
        descEditText = view.findViewById(R.id.addmeal_desc_edittext);
        priceEditText = view.findViewById(R.id.addmeal_price_edittext);
        saveBtn = view.findViewById(R.id.addmeal_save_btn);
        cancelBtn = view.findViewById(R.id.addmeal_cancel_btn);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editImage();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMeal();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        return view;
    }

    private void saveMeal() {
    }

    private void editImage() {
    }
}