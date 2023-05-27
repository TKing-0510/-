package com.example.a417d0947762.ui.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a417d0947762.DBHandler;
import com.example.a417d0947762.R;
import com.example.a417d0947762.databinding.FragmentDashboardBinding;
import com.example.a417d0947762.infoSnack;
import com.example.a417d0947762.ui.home.HomeFragment;

import java.io.FileInputStream;
import java.io.IOException;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private DBHandler dbHandler;

    private long snackId = -1;

    private ListView lvSnack;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lvSnack = requireView().findViewById(R.id.lv_snack);
        super.onViewCreated(requireView(),savedInstanceState);

        dbHandler = new DBHandler((AppCompatActivity) this.getContext());
        dbHandler.openSnack();
        showAllMeals();

    AdapterView.OnItemClickListener listenerSnack = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            snackId = id;
            Bundle bundle = new Bundle();
            bundle.putLong("snackId",snackId);
            Intent intent = new Intent(DashboardFragment.this.getContext(), infoSnack.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    lvSnack.setOnItemClickListener(listenerSnack);

    }

    private  void showAllMeals(){
        Cursor cursor = dbHandler.getAllSnacks();
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
        lvSnack.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}