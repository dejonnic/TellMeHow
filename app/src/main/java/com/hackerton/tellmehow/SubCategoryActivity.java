package com.hackerton.tellmehow;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hackerton.tellmehow.adapter.ProductCategoryAdapter;
import com.hackerton.tellmehow.databinding.ActivitySubCategoryBinding;

public class SubCategoryActivity extends Activity {
    private ActivitySubCategoryBinding binding;
    public static String SubCategoryNameKey = "SubCategoryName";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        String firstKeyName = myIntent.getStringExtra(MainCategoryActivity.CategoryNameKey);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_category);
        binding.categories.setAdapter(new ProductCategoryAdapter(this));
        binding.categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = (TextView) view.getTag(R.id.text);
                Intent intent = new Intent(SubCategoryActivity.this, GeneralRecycleInfoActivity.class);
                intent.putExtra(MainCategoryActivity.CategoryNameKey,firstKeyName);
                intent.putExtra(SubCategoryNameKey,name.getText().toString());
                startActivity(intent);
            }
        });

        binding.labelArea.setOnClickListener((v) -> {
            finish();
        });

        binding.helpButton.setOnClickListener((v) -> {
            Intent intent = new Intent(SubCategoryActivity.this, HelpActivity.class);
            startActivity(intent);
        });
    }
}
