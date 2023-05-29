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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class infoSnack extends AppCompatActivity {

    private DBHandler dbHandler;

    private SQLiteDatabase db;
    private ImageView snackInfoPicture;
    private TextView snackInfoName;
    private TextView snackinfoDescription;
    private TextView snackInfoPrice;
    private EditText snacknumber;
    private Button btnadd;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_snack);

        snackInfoPicture = findViewById(R.id.iv_snackInfoPicture);
        snackInfoName = findViewById(R.id.tv_snackInfoName);
        snackinfoDescription = findViewById(R.id.tv_snackinfoDescription);
        snackInfoPrice = findViewById(R.id.tv_snackInfoPrice);
        snacknumber = findViewById(R.id.et_snacknumber);
        btnadd = findViewById(R.id.btn_add);


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

        View.OnClickListener listener = view ->{
            dbHandler.openShoppingCar();
            int number = Integer.parseInt(String.valueOf(snacknumber.getText()));
            dbHandler.addShoppingCar(cursor.getString(1), Integer.parseInt(cursor.getString(cursor.getColumnIndex("price"))), cursor.getString(cursor.getColumnIndex("pictureName")), number);
            Toast.makeText(infoSnack.this,"成功加入購物車",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(infoSnack.this, activity_showlist.class);
            startActivity(intent);

        };
        btnadd.setOnClickListener(listener);

    }
}