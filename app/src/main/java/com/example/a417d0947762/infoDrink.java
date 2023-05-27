package com.example.a417d0947762;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class infoDrink extends AppCompatActivity {

    private DBHandler dbHandler;

    private SQLiteDatabase db;
    private ImageView drinkInfoPicture;
    private TextView drinkInfoName;
    private TextView drinkinfoDescription;
    private TextView drinkInfoPrice;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_drink);

        drinkInfoPicture = findViewById(R.id.iv_drinkInfoPicture);
        drinkInfoName = findViewById(R.id.tv_drinkInfoName);
        drinkinfoDescription = findViewById(R.id.tv_drinkinfoDescription);
        drinkInfoPrice = findViewById(R.id.tv_drinkInfoPrice);

        dbHandler = new DBHandler(this);
        dbHandler.openDrink();

        Bundle bundle = getIntent().getExtras();

        long foodId = bundle.getLong("drinkId");

        Cursor cursor = dbHandler.getDrinkInfo(foodId);
        if (cursor.moveToFirst()) {
            System.out.println(cursor.getString(1));
            drinkInfoName.setText(cursor.getString(1));
            drinkinfoDescription.setText(cursor.getString(cursor.getColumnIndex("description")));
            drinkInfoPrice.setText(cursor.getString(cursor.getColumnIndex("price")));
            String pictureName = cursor.getString(cursor.getColumnIndex("pictureName"));
            try {
                FileInputStream fis = openFileInput(pictureName + ".jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                fis.close();
                drinkInfoPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }    } else {
        }

    }
}