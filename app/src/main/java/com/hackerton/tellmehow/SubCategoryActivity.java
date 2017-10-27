package com.hackerton.tellmehow;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.hackerton.tellmehow.adapter.ProductCategoryAdapter;
import com.hackerton.tellmehow.databinding.ActivitySubCategoryBinding;

public class SubCategoryActivity extends Activity {
    private ActivitySubCategoryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_category);
        binding.categories.setAdapter(new ProductCategoryAdapter(this));
        binding.categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        binding.helpButton.setOnClickListener((v) -> {
            Intent intent = new Intent(SubCategoryActivity.this, HelpActivity.class);
            startActivity(intent);
        });
    }
}
