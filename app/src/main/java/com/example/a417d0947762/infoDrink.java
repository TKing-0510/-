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

public class infoDrink extends AppCompatActivity {

    private DBHandler dbHandler;

    private SQLiteDatabase db;
    private ImageView drinkInfoPicture;
    private TextView drinkInfoName;
    private TextView drinkinfoDescription;
    private TextView drinkInfoPrice;
    private Button btnadd;
    private EditText drinknumber;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_drink);

        drinkInfoPicture = findViewById(R.id.iv_drinkInfoPicture);
        drinkInfoName = findViewById(R.id.tv_drinkInfoName);
        drinkinfoDescription = findViewById(R.id.tv_drinkinfoDescription);
        drinkInfoPrice = findViewById(R.id.tv_drinkInfoPrice);
        drinknumber = findViewById(R.id.et_snacknumber);
        btnadd = findViewById(R.id.btn_add);


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

        View.OnClickListener listener = view ->{
            dbHandler.openShoppingCar();
            int number = Integer.parseInt(String.valueOf(drinknumber.getText()));
            dbHandler.addShoppingCar(cursor.getString(1), Integer.parseInt(cursor.getString(cursor.getColumnIndex("price"))), cursor.getString(cursor.getColumnIndex("pictureName")), number);
            Toast.makeText(infoDrink.this,"成功加入購物車",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(infoDrink.this, activity_showlist.class);
            startActivity(intent);

        };
        btnadd.setOnClickListener(listener);

    }

}