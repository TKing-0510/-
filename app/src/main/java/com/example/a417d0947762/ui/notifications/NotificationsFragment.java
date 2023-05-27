package com.example.a417d0947762.ui.notifications;

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
import com.example.a417d0947762.databinding.FragmentNotificationsBinding;
import com.example.a417d0947762.infoDrink;

import java.io.FileInputStream;
import java.io.IOException;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private DBHandler dbHandler;

    private long drinkId = -1;

    private ListView lvDrink;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

    binding = FragmentNotificationsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        lvDrink = requireView().findViewById(R.id.lv_drink);
        super.onViewCreated(requireView(),savedInstanceState);

        dbHandler = new DBHandler((AppCompatActivity) this.getContext());
        dbHandler.openDrink();
        showAllMeals();

        AdapterView.OnItemClickListener listenerDrink = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drinkId = id;
                Bundle bundle = new Bundle();
                bundle.putLong ("drinkId",drinkId);
                Intent intent = new Intent(NotificationsFragment.this.getContext(), infoDrink.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        lvDrink.setOnItemClickListener(listenerDrink);

    }

    private void showAllMeals(){
        Cursor cursor = dbHandler.getAllDrinks();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.requireActivity(), R.layout.item_list, cursor, new String[]{"name", "price", "pictureName"}, new int[]{R.id.tv_mainMealName, R.id.tv_mainMealPrice, R.id.iv_pictureName}, 0);

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
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
        lvDrink.setAdapter(adapter);
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}