package com.hackerton.tellmehow;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.hackerton.tellmehow.databinding.ActivityMainBinding;

public class MainActivity extends Activity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.startButton.setOnClickListener((v) -> {
            Toast.makeText(this.getApplicationContext(), "Hi", Toast.LENGTH_LONG).show();
        });
    }
}
