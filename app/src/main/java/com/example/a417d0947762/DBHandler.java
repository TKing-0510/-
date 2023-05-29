package com.example.a417d0947762;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a417d0947762.ui.home.HomeFragment;

public class DBHandler {

    private AppCompatActivity activity;
    public String mealPictureName;
    private static final String FOOD_NAME = "foods.db";
    private static final String DRINK_NAME = "drinks.db";
    private static final String SNACK_NAME = "snacks.db";
    private static final String SHOPPING_CAR_NAME = "shoppingcar.db";

    private static final String CREATE_FOOD_TABLE = "CREATE TABLE IF NOT EXISTS Foods(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "description TEXT, " +
            "price INTEGER NOT NULL, " +
            "pictureName TEXT NOT NULL);";

    private static final String CREATE_SNACK_TABLE = "CREATE TABLE IF NOT EXISTS Snacks(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "description TEXT, " +
            "price INTEGER NOT NULL, " +
            "pictureName TEXT NOT NULL);";

    private static final String CREATE_DRINK_TABLE = "CREATE TABLE IF NOT EXISTS Drinks(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "description TEXT, " +
            "price INTEGER NOT NULL, " +
            "pictureName TEXT NOT NULL);";

    private static final String CREATE_SHOPPING_CAR_TABLE = "CREATE TABLE IF NOT EXISTS ShoppingCar(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "description TEXT, " +
            "price INTEGER NOT NULL, " +
            "pictureName TEXT NOT NULL, " +
            "number INTEGER NOT NULL);";

    private SQLiteDatabase db;

    public DBHandler(
            AppCompatActivity activity) {
        this.activity = activity;
    }

    public void openFood() {
        db = activity.openOrCreateDatabase(FOOD_NAME, 0, null);
        db.execSQL(CREATE_FOOD_TABLE);
        /*ContentValues values = new ContentValues();
        values.put("name", "漢堡");
        values.put("description", "漢堡漢堡");
        values.put("price", 100);
        values.put("pictureName", "hamburger");
        long result = db.insertOrThrow("Foods", null, values);
        values = new ContentValues();
        values.put("name", "薯條");
        values.put("description", "薯條薯條");
        values.put("price", 200);
        values.put("pictureName", "frice");
        result = db.insertOrThrow("Foods", null, values);
        values = new ContentValues();
        values.put("name", "雞塊");
        values.put("description", "雞塊雞塊");
        values.put("price", 300);
        values.put("pictureName", "chicken");
        result = db.insertOrThrow("Foods", null, values);*/
    }

    public void openSnack() {
        db = activity.openOrCreateDatabase(SNACK_NAME, 0, null);
        db.execSQL(CREATE_SNACK_TABLE);
        /*ContentValues values = new ContentValues();
        values.put("name", "鷄塊");
        values.put("description", "鷄塊鷄塊");
        values.put("price", 30);
        values.put("pictureName", "chickennuggets");
        long result = db.insertOrThrow("Snacks", null, values);
        values = new ContentValues();
        values.put("name", "香腸");
        values.put("description", "香腸香腸");
        values.put("price", 20);
        values.put("pictureName", "sausage");
        result = db.insertOrThrow("Snacks", null, values);
        values = new ContentValues();
        values.put("name", "肉包");
        values.put("description", "肉包肉包");
        values.put("price", 20);
        values.put("pictureName", "meatbun");
        result = db.insertOrThrow("Snacks", null, values);*/
    }

    public void openDrink() {
        db = activity.openOrCreateDatabase(DRINK_NAME, 0, null);
        db.execSQL(CREATE_DRINK_TABLE);
        /*ContentValues values = new ContentValues();
        values.put("name", "紅茶");
        values.put("description", "紅茶紅茶");
        values.put("price", 10);
        values.put("pictureName", "blacktea");
        long result = db.insertOrThrow("Drinks", null, values);
        values = new ContentValues();
        values.put("name", "綠茶");
        values.put("description", "綠茶綠茶");
        values.put("price", 20);
        values.put("pictureName", "greentea");
        result = db.insertOrThrow("Drinks", null, values);
        values = new ContentValues();
        values.put("name", "烏龍茶");
        values.put("description", "烏龍茶烏龍茶");
        values.put("price", 30);
        values.put("pictureName", "oolong");
        result = db.insertOrThrow("Drinks", null, values);*/
    }

    public void openShoppingCar() {
        db = activity.openOrCreateDatabase(SHOPPING_CAR_NAME, 0, null);
        db.execSQL(CREATE_SHOPPING_CAR_TABLE);
    }

    public void addShoppingCar(String name, int price, String pictureName, int number) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        //values.put("description", description);
        values.put("price", price);
        values.put("pictureName", pictureName);
        values.put("number", number);
        long result = db.insertOrThrow("ShoppingCar", null, values);
    }

    public void deleteShoppingCar(long id) {
        db.execSQL("DELETE FROM ShoppingCar WHERE _id = " + id);
    }

    public Cursor getAllShoppingCar() {
        Cursor cursor = db.rawQuery("SELECT * FROM ShoppingCar", null);
        return cursor;
    }

    public Cursor getAllFoods() {
        Cursor cursor = db.rawQuery("SELECT * FROM Foods", null);
        return cursor;
    }

    public Cursor getAllSnacks() {
        Cursor cursor = db.rawQuery("SELECT * FROM Snacks", null);
        return cursor;
    }

    public Cursor getAllDrinks() {
        Cursor cursor = db.rawQuery("SELECT * FROM Drinks", null);
        return cursor;
    }

    public Cursor getFoodInfo(long id) {
        Cursor cursor = db.rawQuery("SELECT * FROM Foods WHERE _id = " + id, null);
        return cursor;
    }

    public Cursor getSnackInfo(long id) {
        Cursor cursor = db.rawQuery("SELECT * FROM Snacks WHERE _id = " + id, null);
        return cursor;
    }

    public Cursor getDrinkInfo(long id) {
        Cursor cursor = db.rawQuery("SELECT * FROM Drinks WHERE _id = " + id, null);
        return cursor;
    }

}