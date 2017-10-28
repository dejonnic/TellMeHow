package com.hackerton.tellmehow;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.hackerton.tellmehow.databinding.ActivityGeneralRecycleInfoBinding;

public class ProductRecycleInfoActivity extends Activity {
    ActivityGeneralRecycleInfoBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_general_recycle_info);
        Intent myIntent = getIntent();


    }
}