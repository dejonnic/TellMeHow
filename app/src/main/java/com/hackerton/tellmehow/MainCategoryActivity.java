package com.hackerton.tellmehow;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

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

            // lauch scan only if camera access permission is granted
            if (ContextCompat.checkSelfPermission(MainCategoryActivity.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                IntentIntegrator integrator = new IntentIntegrator(MainCategoryActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.setResultDisplayDuration(0);
                integrator.initiateScan();
            }
            else {
                // permission is not granted, functionnality is disabled
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.permission_needed)
                        .setMessage(R.string.camera_permission_rationale)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && scanResult.getContents() != null && !scanResult.getContents().isEmpty() ) {
            // handle scan result
            String re = scanResult.getContents();
            Log.d("Result", re);
            // Start Product Detail Activity
            Intent intentProduct = new Intent(MainCategoryActivity.this, ProductRecycleInfoActivity.class);
            intentProduct.putExtra(ProductCodeKey, re);
            startActivity(intentProduct);
        }
        else {
            // else continue with any other code you need in the method
            Toast.makeText(getApplicationContext(), R.string.no_scan_results, Toast.LENGTH_SHORT);
        }
    }
}