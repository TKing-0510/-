package com.example.a417d0947762.ui.Shoppingcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.a417d0947762.databinding.FragmentShoppingcarBinding;

public class ShoppingcarFragment extends Fragment {

  private FragmentShoppingcarBinding binding;

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

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}