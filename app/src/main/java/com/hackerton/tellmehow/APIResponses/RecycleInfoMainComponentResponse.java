package com.hackerton.tellmehow.APIResponses;

import com.google.gson.annotations.SerializedName;
import com.hackerton.tellmehow.APIResponses.RecycleInfoMinorComponentsResponse;

import java.util.List;

public class RecycleInfoMainComponentResponse {
    public String text;

    public long id;

    @SerializedName("related_components")
    public List<RecycleInfoMinorComponentsResponse> recycleInfoMinorComponentsResponses;

    @SerializedName("recycle_info")
    public String recycleInformation;

    @SerializedName("material")
    public String material;

    @SerializedName("category")
    public String category;

    @SerializedName("name")
    public String name;

    @SerializedName("status")
    public int success;

}