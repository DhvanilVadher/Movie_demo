package com.example.inshortsmovigdemo.depedency;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "ViewModel";

    @Inject
    public AuthViewModel() {
        Log.d(TAG, "ViewModel working");
    }
}
//Dgger learmimg