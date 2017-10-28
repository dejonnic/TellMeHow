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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hackerton.tellmehow.adapter.ProductCategoryAdapter;
import com.hackerton.tellmehow.databinding.ActivityMainCategoryBinding;

public class MainCategoryActivity extends Activity {
    public final static String CategoryNameKey = "CategoryName";
    public final static String CategoryIdKey = "CategoryId";
    public final static String ProductCodeKey = "ProductCode";
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

        binding.imageRecognitionButton.setOnClickListener((v) -> {
            IntentIntegrator integrator = new IntentIntegrator(MainCategoryActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            integrator.setPrompt("Scan a barcode");
            integrator.setResultDisplayDuration(0);
            integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
            integrator.initiateScan();
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            String re = scanResult.getContents();
            Log.d("Result is here!", re);
            // Start Product Detail Activity
            Intent intentProduct = new Intent(MainCategoryActivity.this, ProductRecycleInfoActivity.class);
            intentProduct.putExtra(ProductCodeKey, re);
            startActivity(intentProduct);
        }
        // else continue with any other code you need in the method
        /* TODO : no result message */
    }
}
