package com.hackerton.tellmehow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hackerton.tellmehow.APIResponses.PicturedComponentResponse;
import com.hackerton.tellmehow.APIResponses.RecycleInfoMinorComponentsResponse;
import com.hackerton.tellmehow.APIResponses.RecycleInfoResponse;
import com.hackerton.tellmehow.adapter.CategoryIconManager;
import com.hackerton.tellmehow.adapter.MinorComponentsRecycleInfoExpandableListAdapter;
import com.hackerton.tellmehow.databinding.ActivityGeneralRecycleInfoBinding;
import com.hackerton.tellmehow.databinding.ActivityPicturedInfoBinding;
import com.hackerton.tellmehow.view.AddComponentDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hackerton.tellmehow.MainCategoryActivity.ProductCodeKey;

public class ProductRecycleInfoActivity extends Activity {
    private String materialName = "material";
    private String categoryName = "category";

    ActivityPicturedInfoBinding binding;

    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pictured_info);

        expandableListTitle = new ArrayList<String>();
        expandableListAdapter = new MinorComponentsRecycleInfoExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        binding.expandableListView.setAdapter(expandableListAdapter);

        Intent myIntent = getIntent();
        String productCode = myIntent.getStringExtra(ProductCodeKey);

        int categoryId = myIntent.getIntExtra(MainCategoryActivity.CategoryIdKey, -1);

        new Requester(productCode).execute();

        binding.componentButton.setOnClickListener((v) -> {
            AddComponentDialog dialog = new AddComponentDialog(ProductRecycleInfoActivity.this);
            dialog.setDoneListener(() -> {
                Toast.makeText(getApplicationContext(), "Saved new materials", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            });
            dialog.show();
        });

        binding.moreTrashButton.setOnClickListener((v) -> {
            Intent intent = new Intent(ProductRecycleInfoActivity.this, MainCategoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    class Requester extends AsyncTask<Void, Void, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private String code;
        private String url;

        public Requester(String code){
            this.code = code;
            url = "http://128.199.69.81:8000/api/v1/product/code/" + code;
        }

        private ProgressDialog pDialog;

        private static final String TAG_SUCCESS = "status";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ProductRecycleInfoActivity.this);
            pDialog.setMessage("Catching data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... args) {

            try {
                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(url, "GET", null);

                if (json != null) {
                    Log.d("JSON result", json.toString());

                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
                Gson gson = new Gson();
                PicturedComponentResponse recycleInfo = gson.fromJson(json.toString(), PicturedComponentResponse.class);
                PopulateView(recycleInfo);
            }
        }

        private void PopulateView(PicturedComponentResponse recycleInfo) {
            // Main component details
//            binding.mainComponentName.setText(recycleInfo.name);
//            binding.mainComponentMaterial.setText(recycleInfo.material);
            binding.recycleInfo.setText(recycleInfo.mainComponent.recycleInformation);
            binding.mainComponentName.setText(recycleInfo.name);
            binding.mainComponentMaterial.setText(recycleInfo.mainComponent.material);

            Glide.with(ProductRecycleInfoActivity.this).load("http://128.199.69.81:8000" + recycleInfo.photo).into(binding.photo);

            //Other components details
            expandableListTitle = new ArrayList<String>();
            expandableListDetail = new HashMap<String, List<String>>();

            for (RecycleInfoMinorComponentsResponse response : recycleInfo.mainComponent.recycleInfoMinorComponentsResponses) {
                // name, recycle_info, material
                String headerName = response.name + "-" + response.material;
                expandableListTitle.add(headerName);
                List<String> newComponentDetails = new ArrayList<String>();
                newComponentDetails.add(response.recycleInformation);
                expandableListDetail.put(headerName, newComponentDetails);

                Log.d("Minor Component", headerName + " " + response.recycleInformation);
            }

            ((MinorComponentsRecycleInfoExpandableListAdapter)expandableListAdapter).updateAdapter(expandableListTitle, expandableListDetail);
        }
    }
}