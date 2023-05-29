package com.example.a417d0947762;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class infoFood extends AppCompatActivity {

  private DBHandler dbHandler;

  private SQLiteDatabase db;
  private ImageView foodInfoPicture;
  private TextView foodInfoName;
  private TextView foodinfoDescription;
  private TextView foodInfoPrice;
  private Button btnadd;
  private EditText foodnumber;

  @SuppressLint("Range")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);

    foodInfoPicture = findViewById(R.id.iv_foodInfoPicture);
    foodInfoName = findViewById(R.id.tv_foodInfoName);
    foodinfoDescription = findViewById(R.id.tv_foodinfoDescription);
    foodInfoPrice = findViewById(R.id.tv_foodInfoPrice);
    btnadd = findViewById(R.id.btn_add);
    foodnumber = findViewById(R.id.et_snacknumber);

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
    View.OnClickListener listener = view ->{
      dbHandler.openShoppingCar();
      int number = Integer.parseInt(String.valueOf(foodnumber.getText()));
      dbHandler.addShoppingCar(cursor.getString(1), Integer.parseInt(cursor.getString(cursor.getColumnIndex("price"))), cursor.getString(cursor.getColumnIndex("pictureName")), number);
      Toast.makeText(infoFood.this,"成功加入購物車",Toast.LENGTH_SHORT).show();

      Intent intent = new Intent(infoFood.this, activity_showlist.class);
      startActivity(intent);


    };
    btnadd.setOnClickListener(listener);
  }
}