package com.hackerton.tellmehow.APIResponses;

import com.google.gson.annotations.SerializedName;
import com.hackerton.tellmehow.model.Material;

import java.util.List;

public class SubCategoryRequestResponse {
    @SerializedName("materials")
    public List<Material> materials;
}
