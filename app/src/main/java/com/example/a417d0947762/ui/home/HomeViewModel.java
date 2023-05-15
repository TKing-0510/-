package com.example.a417d0947762.ui.home;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.FileInputStream;
import java.io.IOException;

public class HomeViewModel extends ViewModel {


    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}