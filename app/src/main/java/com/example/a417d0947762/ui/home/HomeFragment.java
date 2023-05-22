package com.example.a417d0947762.ui.home;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a417d0947762.DBHandler;
import com.example.a417d0947762.databinding.FragmentHomeBinding;

import java.io.FileInputStream;
import java.io.IOException;

import com.example.a417d0947762.R;

public class HomeFragment extends Fragment {

    private ListView lvFoods;
    private DBHandler dbHandler;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lvFoods = requireView().findViewById(R.id.lv_food);
        super.onViewCreated(requireView(), savedInstanceState);

        dbHandler = new DBHandler((AppCompatActivity) this.getContext());
        dbHandler.openFood();
        showAllMeals();
    }

    private void showAllMeals() {
        Cursor cursor = dbHandler.getAllFoods();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.requireActivity(), R.layout.item_list, cursor, new String[]{"name", "price", "pictureName"}, new int[]{R.id.tv_mainMealName, R.id.tv_mainMealPrice, R.id.iv_pictureName}, 0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.iv_pictureName) {
                    String pictureName = cursor.getString(columnIndex);
                    try {
                        FileInputStream fis = requireActivity().openFileInput(pictureName + ".jpg");
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
        lvFoods.setAdapter(adapter);
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}