package com.hackerton.tellmehow.APIResponses;

import com.google.gson.annotations.SerializedName;

public class RecycleInfoMinorComponentsResponse {
    @SerializedName("name")
    public String name;

    @SerializedName("recyclinginfomation")
    public String recycleInformation;

    @SerializedName("material")
    public String material;
}