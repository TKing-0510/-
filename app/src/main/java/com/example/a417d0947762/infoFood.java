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

public class infoFood extends AppCompatActivity {

  private DBHandler dbHandler;

  private SQLiteDatabase db;
  private ImageView foodInfoPicture;
  private TextView foodInfoName;
  private TextView foodinfoDescription;
  private TextView foodInfoPrice;

  @SuppressLint("Range")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);

    foodInfoPicture = findViewById(R.id.iv_foodInfoPicture);
    foodInfoName = findViewById(R.id.tv_foodInfoName);
    foodinfoDescription = findViewById(R.id.tv_foodinfoDescription);
    foodInfoPrice = findViewById(R.id.tv_foodInfoPrice);

    dbHandler = new DBHandler(this);
    dbHandler.openFood();

    Bundle bundle = getIntent().getExtras();

    long foodId = bundle.getLong("mealId");

    Cursor cursor = dbHandler.getFoodInfo(foodId);
    if (cursor.moveToFirst()) {
      System.out.println(cursor.getString(1));
      foodInfoName.setText(cursor.getString(1));
      foodinfoDescription.setText(cursor.getString(cursor.getColumnIndex("description")));
      foodInfoPrice.setText(cursor.getString(cursor.getColumnIndex("price")));
      String pictureName = cursor.getString(cursor.getColumnIndex("pictureName"));
      try {
        FileInputStream fis = openFileInput(pictureName + ".jpg");
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        fis.close();
        foodInfoPicture.setImageBitmap(bitmap);
      } catch (IOException e) {
        e.printStackTrace();
      }    } else {
    }

  }
}