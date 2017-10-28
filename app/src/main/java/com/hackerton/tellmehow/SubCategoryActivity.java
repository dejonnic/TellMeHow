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
import com.hackerton.tellmehow.api.CategoryRequester;
import com.hackerton.tellmehow.databinding.ActivitySubCategoryBinding;

public class SubCategoryActivity extends Activity {
    private ActivitySubCategoryBinding binding;
    public static String SubCategoryNameKey = "SubCategoryName";
    public static String SubCategoryIdKey = "SubCategoryId";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        String firstKeyName = myIntent.getStringExtra(MainCategoryActivity.CategoryNameKey);
        int categoryId = myIntent.getIntExtra(MainCategoryActivity.CategoryIdKey, -1);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_category);
        binding.label.setText(firstKeyName);

        CategoryRequester requester = new CategoryRequester(SubCategoryActivity.this, categoryId, (res) -> {
            ProductCategoryAdapter adapter = new ProductCategoryAdapter(this, res.materials, categoryId);
            binding.categories.setAdapter(adapter);
            binding.categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView name = (TextView) view.getTag(R.id.text);
                    ProductCategoryAdapter.Item item = adapter.getItem(position);
                    int subCategoryId = item.getId();

                    Intent intent = new Intent(SubCategoryActivity.this, GeneralRecycleInfoActivity.class);
                    intent.putExtra(MainCategoryActivity.CategoryNameKey, firstKeyName);
                    intent.putExtra(MainCategoryActivity.CategoryIdKey, categoryId);
                    intent.putExtra(SubCategoryNameKey, name.getText().toString());
                    intent.putExtra(SubCategoryIdKey, subCategoryId);
                    startActivity(intent);
                }
            });
        } );
        requester.execute();

        binding.labelArea.setOnClickListener((v) -> {
            finish();
        });

        binding.helpButton.setOnClickListener((v) -> {
            Intent intent = new Intent(SubCategoryActivity.this, HelpActivity.class);
            startActivity(intent);
        });
    }
}
