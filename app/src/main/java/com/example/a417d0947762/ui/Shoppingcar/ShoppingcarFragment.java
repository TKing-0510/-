package com.example.a417d0947762.ui.Shoppingcar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a417d0947762.DBHandler;
import com.example.a417d0947762.R;
import com.example.a417d0947762.databinding.FragmentShoppingcarBinding;
import com.example.a417d0947762.orderConfirmationactivity;

import java.io.FileInputStream;
import java.io.IOException;

public class ShoppingcarFragment extends Fragment {

  private FragmentShoppingcarBinding binding;
  private ListView lvshoppingcar;
  private DBHandler dbHandler;
  private long mealId = -1;
  private TextView tv_totalprice;



  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    ShoppingcarViewModel shoppingcarViewModel =
      new ViewModelProvider(this).get(ShoppingcarViewModel.class);


    binding = FragmentShoppingcarBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


    final TextView textView = binding.tvShoppingcar;
    shoppingcarViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    return root;
  }

  public void onViewCreated(View view, Bundle savedInstanceState) {
    lvshoppingcar = requireView().findViewById(R.id.lv_shoppingcar);
    super.onViewCreated(requireView(), savedInstanceState);

    dbHandler = new DBHandler((AppCompatActivity) this.getContext());
    dbHandler.openShoppingCar();

    Button btndelete = requireView().findViewById(R.id.btn_comfort);
    Button btncomfort = requireView().findViewById(R.id.btn_delete);
    //TextView totalprice = requireView().findViewById(R.id.tv_totalprice);


    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(view.getId() == R.id.btn_delete){
          dbHandler.deleteShoppingCar(mealId);
          showAllMeals();
        }
        else if(view.getId()==R.id.btn_comfort){

          Toast.makeText(getContext(),"成功下單，請稍作等待",Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(getActivity(), orderConfirmationactivity.class);
          startActivity(intent);
        }

      }
    };
    btncomfort.setOnClickListener(onClickListener);
    btndelete.setOnClickListener(onClickListener);
    //totalprice.setText("總金額 ： "+number);
    showAllMeals();

    AdapterView.OnItemClickListener listenerShoppingcar = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          mealId = id;
          Bundle bundle = new Bundle();
          bundle.putLong("mealId", mealId);
        }
      };
    lvshoppingcar.setOnItemClickListener(listenerShoppingcar);
  }

  private  void showAllMeals() {
    Cursor cursor = dbHandler.getAllShoppingCar();
    tv_totalprice = requireView().findViewById(R.id.tv_totalprice);
    final int[] totalprice = {0};
    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.requireActivity(), R.layout.item_list, cursor, new String[]{"name", "price", "pictureName","number"}, new int[]{R.id.tv_mainMealName, R.id.tv_mainMealPrice, R.id.iv_pictureName,R.id.tv_mainMealNumber}, 0);
    adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
      @SuppressLint("SetTextI18n")
      @Override
      public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view.getId() == R.id.iv_pictureName) {
          String pictureName = cursor.getString(columnIndex);
          int price = cursor.getInt(3);
          int number = cursor.getInt(5);
          totalprice[0] += price * number;
          try {
            FileInputStream fis = requireActivity().openFileInput(pictureName + ".jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            ImageView imageView = (ImageView) view;
            imageView.setImageBitmap(bitmap);

            tv_totalprice.setText("總金額 : "+ totalprice[0]);
          } catch (IOException e) {
            e.printStackTrace();
          }
          return true;
        } else {
          return false;
        }
      }
    });
    lvshoppingcar.setAdapter(adapter);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }


}