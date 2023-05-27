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

public class infoSnack extends AppCompatActivity {

    private DBHandler dbHandler;

    private SQLiteDatabase db;
    private ImageView snackInfoPicture;
    private TextView snackInfoName;
    private TextView snackinfoDescription;
    private TextView snackInfoPrice;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_snack);

        snackInfoPicture = findViewById(R.id.iv_snackInfoPicture);
        snackInfoName = findViewById(R.id.tv_snackInfoName);
        snackinfoDescription = findViewById(R.id.tv_snackinfoDescription);
        snackInfoPrice = findViewById(R.id.tv_snackInfoPrice);

        dbHandler = new DBHandler(this);
        dbHandler.openSnack();

        Bundle bundle = getIntent().getExtras();

        long foodId = bundle.getLong("snackId");

        Cursor cursor = dbHandler.getSnackInfo(foodId);
        if (cursor.moveToFirst()) {
            System.out.println(cursor.getString(1));
            snackInfoName.setText(cursor.getString(1));
            snackinfoDescription.setText(cursor.getString(cursor.getColumnIndex("description")));
            snackInfoPrice.setText(cursor.getString(cursor.getColumnIndex("price")));
            String pictureName = cursor.getString(cursor.getColumnIndex("pictureName"));
            try {
                FileInputStream fis = openFileInput(pictureName + ".jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                fis.close();
                snackInfoPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }    } else {
        }

    }
}