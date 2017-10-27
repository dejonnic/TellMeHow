package com.hackerton.tellmehow;

import com.google.gson.annotations.SerializedName;

public class RecycleInfoMinorComponentsResponse {

    @SerializedName("name")
    public String name;

    @SerializedName("recycle_info")
    public String recycleInformation;

    @SerializedName("material")
    public String material;
}