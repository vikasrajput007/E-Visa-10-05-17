package com.android.developer.e_visa.network;

/**
 * Created by HP on 17-06-2017.
 */

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * This class contains the rest api endpoints and the type of response it is expecting
 */
public interface ApiInterface {

//    indiaapi.php

    @FormUrlEncoded
    @POST("indiaapi.php")
    Call<ResponseBody> sendUserData(@FieldMap Map<String, String> map);
}
