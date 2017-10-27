package com.hackerton.tellmehow;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.os.Bundle;


import com.hackerton.tellmehow.databinding.ActivityMainBinding;

public class MainActivity extends Activity {
    private ActivityMainBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.startButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, MainCategoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}



