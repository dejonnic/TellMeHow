package com.hackerton.tellmehow.APIResponses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by idohyeon on 2017. 10. 29..
 */

public class PicturedComponentResponse {
    @SerializedName("name")
    public String name;

    @SerializedName("photo")
    public String photo;

    @SerializedName("code")
    public String code;

    @SerializedName("main_component")
    public NestedRecycleInfoMainComponentResponse mainComponent;
}
