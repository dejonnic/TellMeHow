package com.hackerton.tellmehow.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.hackerton.tellmehow.APIResponses.SubCategoryRequestResponse;
import com.hackerton.tellmehow.JSONParser;

import org.json.JSONObject;

public class CategoryRequester extends AsyncTask<Void, Void, JSONObject> {
    private Context activity;
    private String url;
    private int categoryId;
    private OnCompleteListener onCompleteListener;

    JSONParser jsonParser = new JSONParser();

    private ProgressDialog pDialog;

    private static final String BASE_URL = "http://128.199.69.81:8000/api/v1/category/material/";
    private static final String TAG_SUCCESS = "status";
    private static final String TAG_MESSAGE = "message";

    public CategoryRequester(Context activity, int categoryId, OnCompleteListener onCompleteListener){
        this.activity = activity;
        this.categoryId = categoryId;
        this.url = BASE_URL + categoryId + "/";
        this.onCompleteListener = onCompleteListener;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Attempting login...");
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
            SubCategoryRequestResponse response = gson.fromJson(json.toString(), SubCategoryRequestResponse.class);
            onCompleteListener.onComplete(response);
        }
    }

    public interface OnCompleteListener {
        void onComplete(SubCategoryRequestResponse response);
    };
}
