package com.example.a417d0947762.ui.Shoppingcar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShoppingcarViewModel extends ViewModel {
  private final MutableLiveData<String> mText;

  public ShoppingcarViewModel() {
    mText = new MutableLiveData<>();
    //mText.setValue("This is shoppingcar fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}