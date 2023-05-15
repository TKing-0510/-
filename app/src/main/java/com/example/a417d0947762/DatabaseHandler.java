package com.example.a417d0947762;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatabaseHandler {

  private AppCompatActivity activity;

  private static final String DATABASE_NAME = "breakfast.db";

  private static final String CREATE_MEAL_TABLE = "Create table IF NOT EXISTS Meals("+"_id Integer Primary Key Autoincrement,"
    +"name Text not Null,"
    +"description Text,"
    +"price Integer);";

  private SQLiteDatabase db;
  public  DatabaseHandler(AppCompatActivity activity){
    this.activity = activity;

  }
  public void open(){
    db = activity.openOrCreateDatabase(DATABASE_NAME,0,null);
    db.execSQL(CREATE_MEAL_TABLE);
  }
  public void addMeal(String name,String description,int price){
    ContentValues values = new ContentValues();
    values.put("name",name);
    values.put("description",description);
    values.put("price",price);
    db.insert("Meals",null,values);
  }
  public Cursor getAllMeals(){
    Cursor cursor = db.rawQuery("SELECT * FROM Meals",null);
    while(cursor.moveToNext()){
      String mealName = cursor.getString(1);
      Toast.makeText(activity,"Add meal: "+ mealName,Toast.LENGTH_SHORT).show();
    }
    return cursor;
  }

  public void deletMeal(long id ){
    db.execSQL("DELETE FROM Meals WHERE _id = " + id);
  }



}
