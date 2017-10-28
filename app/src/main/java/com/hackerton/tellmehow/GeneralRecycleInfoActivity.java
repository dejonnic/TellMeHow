package com.hackerton.tellmehow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.hackerton.tellmehow.APIResponses.NestedRecycleInfoMainComponentResponse;
import com.hackerton.tellmehow.APIResponses.RecycleInfoMinorComponentsResponse;
import com.hackerton.tellmehow.APIResponses.RecycleInfoResponse;
import com.hackerton.tellmehow.adapter.CategoryIconManager;
import com.hackerton.tellmehow.adapter.MinorComponentsRecycleInfoExpandableListAdapter;
import com.hackerton.tellmehow.databinding.ActivityGeneralRecycleInfoBinding;
import com.hackerton.tellmehow.view.AddComponentDialog;


public class GeneralRecycleInfoActivity extends Activity {
    private String materialName = "material";
    private String categoryName = "category";

    ActivityGeneralRecycleInfoBinding binding;

    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_general_recycle_info);

        expandableListTitle = new ArrayList<String>();
        expandableListAdapter = new MinorComponentsRecycleInfoExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        binding.expandableListView.setAdapter(expandableListAdapter);

        Intent myIntent = getIntent();
        String firstKeyName = myIntent.getStringExtra(MainCategoryActivity.CategoryNameKey);
        String secondKeyName = myIntent.getStringExtra(SubCategoryActivity.SubCategoryNameKey);

        int categoryId = myIntent.getIntExtra(MainCategoryActivity.CategoryIdKey, -1);
        int materialId = myIntent.getIntExtra(SubCategoryActivity.SubCategoryIdKey, -1);

        new Requester(categoryId, materialId).execute(materialName, firstKeyName, categoryName, secondKeyName);

        Drawable materialIcon = ContextCompat.getDrawable(this.getApplicationContext(), CategoryIconManager.getIcon(materialId, categoryId));
        binding.icon.setImageDrawable(materialIcon);

        binding.mainComponentName.setText(firstKeyName);
        binding.mainComponentMaterial.setText(secondKeyName);

        binding.componentButton.setOnClickListener((v) -> {
            AddComponentDialog dialog = new AddComponentDialog(GeneralRecycleInfoActivity.this);
            dialog.setDoneListener(() -> {
                Toast.makeText(getApplicationContext(), "Saved new materials", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            });
            dialog.show();
        });

        binding.moreTrashButton.setOnClickListener((v) -> {
            Intent intent = new Intent(GeneralRecycleInfoActivity.this, MainCategoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    class Requester extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private int categoryId;
        private int materialId;
        private String url;

        public Requester(int categoryId, int materialId){
            this.categoryId = categoryId;
            this.materialId = materialId;
            url = "http://128.199.69.81:8000/api/v1/category/component/" + materialId + "/?category=" + categoryId;
        }

        private ProgressDialog pDialog;

        private static final String TAG_SUCCESS = "status";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(GeneralRecycleInfoActivity.this);
            pDialog.setMessage("Catching data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

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
                RecycleInfoResponse recycleInfo = gson.fromJson(json.toString(), RecycleInfoResponse.class);
                PopulateView(recycleInfo);
            }
        }

        private void PopulateView(RecycleInfoResponse recycleInfo) {
            // Main component details
//            binding.mainComponentName.setText(recycleInfo.name);
//            binding.mainComponentMaterial.setText(recycleInfo.material);
            binding.recycleInfo.setText(recycleInfo.component.recycleInformation);

            //Other components details
            expandableListTitle = new ArrayList<String>();
            expandableListDetail = new HashMap<String, List<String>>();

            for (RecycleInfoMinorComponentsResponse response : recycleInfo.component.recycleInfoMinorComponentsResponses) {
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



