package com.hackerton.tellmehow.APIResponses;

import com.google.gson.annotations.SerializedName;
import com.hackerton.tellmehow.model.Material;

import java.util.List;

/**
 * Created by idohyeon on 2017. 10. 28..
 */

public class SubCategoryRequestResponse {
    @SerializedName("materials")
    public List<Material> materials;
}
