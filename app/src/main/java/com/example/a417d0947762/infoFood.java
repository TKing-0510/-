package com.example.a417d0947762;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import java.io.FileInputStream;
import java.io.IOException;

public class infoFood extends AppCompatActivity {

  private DBHandler dbHandler;
  private SQLiteDatabase db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);

    dbHandler = new DBHandler(this);

    Bundle bundle = getIntent().getExtras();

    long foodId = bundle.getLong("mealId");

    Cursor cursor = dbHandler.getFoodInfo(foodId);

    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_info, cursor, new String[]{"name", "description", "price", "pictureName"}, new int[]{R.id.tv_foodInfoName, R.id.tv_foodinfoDescription, R.id.tv_foodInfoPrice, R.id.iv_foodInfoPicture}, 0);

    adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
      public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view.getId() == R.id.iv_foodInfoPicture) {
          String pictureName = cursor.getString(columnIndex);
          try {
            FileInputStream fis = openFileInput(pictureName + ".jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            ImageView imageView = (ImageView) view;
            imageView.setImageBitmap(bitmap);
          } catch (IOException e) {
            e.printStackTrace();
          }
          return true;
        } else {
          return false;
        }
      }
    });
    infoFood.setAdapter(adapter);
  }
}