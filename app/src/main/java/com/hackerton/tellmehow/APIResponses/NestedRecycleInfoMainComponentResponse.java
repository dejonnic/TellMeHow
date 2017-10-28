package com.hackerton.tellmehow.APIResponses;

import com.google.gson.annotations.SerializedName;
import com.hackerton.tellmehow.APIResponses.RecycleInfoMinorComponentsResponse;

import java.util.List;

public class NestedRecycleInfoMainComponentResponse {
    public String text;

    public long id;

    @SerializedName("minor_components")
    public List<RecycleInfoMinorComponentsResponse> recycleInfoMinorComponentsResponses;

    @SerializedName("recyclinginfomation")
    public String recycleInformation;

    @SerializedName("material")
    public String material;

    @SerializedName("category")
    public String category;

    @SerializedName("name")
    public String name;

    @Override
    public String toString() {
        return "NestedRecycleInfoMainComponentResponse{" +
                "text='" + text + '\'' +
                ", id=" + id +
                ", recycleInfoMinorComponentsResponses=" + recycleInfoMinorComponentsResponses +
                ", recycleInformation='" + recycleInformation + '\'' +
                ", material='" + material + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}