package com.daniel.mamafood.ui.meals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.model.Model;
import com.google.android.material.snackbar.Snackbar;

import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

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
                saveMeal(v);
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

    private void saveMeal(View view) {
        if (nameEditText.getText().equals("") || descEditText.getText().equals("") || priceEditText.getText().equals("")) {
            Snackbar.make(view, "You must provide a value for each of the fields", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Log.d("TAG", "One of more of the meal fields is empty.");
        }
        else {
            Meal meal = new Meal();
            meal.setId(UUID.randomUUID().toString());
            meal.setName(nameEditText.getText().toString());
            meal.setDescription(descEditText.getText().toString());
            meal.setPrice(Double.parseDouble(priceEditText.getText().toString()));

            // TODO: add seller to meal
            // meal.setSeller();

            BitmapDrawable drawable = (BitmapDrawable) avatarImageView.getDrawable();
            Model.instance.uploadImage(drawable.getBitmap(), meal.getId(), new Model.UploadImageListener() {
                @Override
                public void onComplete(String url) {
                    if (url == null) {
                        displayFailedError();
                    }
                    else {
                        meal.setImageUrl(url);
                        Model.instance.addMeal(meal, new Model.AddMealListener() {
                            @Override
                            public void onComplete() {
                                Navigation.findNavController(saveBtn).popBackStack();
                            }
                        });
                    }
                }
            });
        }
    }

    private void displayFailedError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Saving image failed, please try again later...");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void editImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        avatarImageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                avatarImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
}