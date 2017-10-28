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
import com.hackerton.tellmehow.databinding.ActivityMainCategoryBinding;

public class MainCategoryActivity extends Activity {
    public static String CategoryNameKey = "CategoryName";
    public static String CategoryIdKey = "CategoryId";
    private ActivityMainCategoryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_category);

        ProductCategoryAdapter adapter = new ProductCategoryAdapter(this);
        binding.categories.setAdapter(adapter);
        binding.categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = (TextView) view.getTag(R.id.text);
                ProductCategoryAdapter.Item item = adapter.getItem(position);
                int categoryId = item.getId();

                Intent intent = new Intent(MainCategoryActivity.this, SubCategoryActivity.class);
                intent.putExtra(CategoryNameKey, name.getText().toString());
                intent.putExtra(CategoryIdKey, categoryId);
                startActivity(intent);
            }
        });

        binding.helpButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainCategoryActivity.this, HelpActivity.class);
            startActivity(intent);
        });
    }
}
