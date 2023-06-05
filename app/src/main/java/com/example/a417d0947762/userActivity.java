package com.example.a417d0947762;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;

  public class userActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private EditText etMealName;
    private EditText etMealDescription;
    private EditText etMealPrice;
    private EditText etmealPictureName;
    //private ListView lvMeal;
    private Button btnAddMeal;
    private Button btnDeleteMeal;
    private RadioGroup unit;
    private long mealId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_user);

      dbHandler = new DBHandler(this);
      dbHandler.openFood();
      dbHandler.openSnack();
      dbHandler.openDrink();

      etMealName = findViewById(R.id.et_mealName);
      etMealDescription = findViewById(R.id.et_mealDescription);
      etMealPrice = findViewById(R.id.et_mealPrice);
      etmealPictureName = findViewById(R.id.et_mealPictureName);
      btnAddMeal = findViewById(R.id.btn_addMeal);
      btnDeleteMeal = findViewById(R.id.btn_deleteMeal);
      unit = findViewById(R.id.radioGroup);

      //lvMeal = findViewById(R.id.lv_meals);

      View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (v.getId() == R.id.btn_addMeal) {
            String mealName = etMealName.getText().toString();
            String mealDescription = etMealDescription.getText().toString();
            int mealPrice = Integer.parseInt(etMealPrice.getText().toString());
            String pictureName = etmealPictureName.getText().toString();

            if (unit.getCheckedRadioButtonId() == R.id.radioButtonFood) {
              dbHandler.addFood(mealName, mealDescription, mealPrice, pictureName);
            }
            else if (unit.getCheckedRadioButtonId() == R.id.radioButtonSnack) {
              dbHandler.addSnack(mealName, mealDescription, mealPrice, pictureName);
            }
            else if (unit.getCheckedRadioButtonId() == R.id.radioButtonDrink) {
              dbHandler.addDrink(mealName, mealDescription, mealPrice, pictureName);
            }

            //showAllMeals();

            etMealName.setText("");
            etMealDescription.setText("");
            etMealPrice.setText("");
          } else if (v.getId() == R.id.btn_deleteMeal) {
            //dbHandler.deleteMeals(mealId);
            //Toast.makeText(MealManagementActivity.this, String.valueOf(mealId), Toast.LENGTH_SHORT).show();
            mealId = -1;
            //showAllMeals();
          }

        }
      };
      btnAddMeal.setOnClickListener(listener);
      btnDeleteMeal.setOnClickListener(listener);

      AdapterView.OnItemClickListener listener1 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          //Toast.makeText(MealManagementActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
          mealId = id;
        }
      };
      //lvMeal.setOnItemClickListener(listener1);
      //showAllMeals();
    }

    /*private void showAllMeals() {
      Cursor cursor = dbHandler.getAllMeals();
      SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[]{"name", "price"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
      lvMeal.setAdapter(adapter);
    }*/
  }