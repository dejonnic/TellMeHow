package com.hackerton.tellmehow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.hackerton.tellmehow.APIResponses.RecycleInfoMainComponentResponse;
import com.hackerton.tellmehow.APIResponses.RecycleInfoMinorComponentsResponse;
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

        new PostAsync().execute(materialName, firstKeyName, categoryName, secondKeyName);

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

    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://www.mocky.io/v2/59f3ac333200006f1ea62655";

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

                HashMap<String, String> params = new HashMap<>();
                int i = 0;
                for (i = 0; i < args.length; i++) {
                    params.put(args[i], args[++i]);
                }

                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

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

            int success = 0;
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
                success = json.optInt(TAG_SUCCESS);
                message = json.optString(TAG_MESSAGE);

                Gson gson = new Gson();

                if (success == 1) {
                    Log.d("Success!", message);
                    RecycleInfoMainComponentResponse recycleInfo = gson.fromJson(json.toString(), RecycleInfoMainComponentResponse.class);
                    Log.d("Response", recycleInfo.name + recycleInfo.category + recycleInfo.material + recycleInfo.recycleInformation);

                    for (RecycleInfoMinorComponentsResponse response : recycleInfo.recycleInfoMinorComponentsResponses) {
                        Log.d("Minor Component", response.name + response.material + response.recycleInformation);
                    }

                    PopulateView(recycleInfo);

                } else {
                    Log.d("Failure", message);
                }
            }
        }

        private void PopulateView(RecycleInfoMainComponentResponse recycleInfo) {
            // Main component details
            binding.mainComponentName.setText(recycleInfo.name);
            binding.mainComponentMaterial.setText(recycleInfo.material);
            binding.mainComponentCategory.setText(recycleInfo.category);

            //Other components details
            expandableListTitle = new ArrayList<String>();
            expandableListDetail = new HashMap<String, List<String>>();

            for (RecycleInfoMinorComponentsResponse response : recycleInfo.recycleInfoMinorComponentsResponses) {
                // name, recycle_info, material
                String headerName = response.name + "-" + response.material;
                expandableListTitle.add(headerName);
                List<String> newComponentDetails = new ArrayList<String>();
                newComponentDetails.add(recycleInfo.recycleInformation);

                expandableListDetail.put(headerName, newComponentDetails);

                Log.d("Minor Component", headerName + " " + response.recycleInformation);
            }

            ((MinorComponentsRecycleInfoExpandableListAdapter)expandableListAdapter).updateAdapter(expandableListTitle, expandableListDetail);
        }
    }
}



