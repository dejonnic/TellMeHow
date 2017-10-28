package com.hackerton.tellmehow.APIResponses;

import com.google.gson.annotations.SerializedName;

public class RecycleInfoResponse {
    @SerializedName("component")
    public NestedRecycleInfoMainComponentResponse component;

    @Override
    public String toString() {
        return "RecycleInfoResponse{" +
                "component=" + component +
                '}';
    }
}
