package com.android.developer.e_visa.retrofit;

import com.android.developer.e_visa.models.Languages;
import com.android.developer.e_visa.models.ListDetail;

import java.util.List;

import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by zx on 5/26/2017.
 */

public interface ApiInterface {


    @GET("languageapi.php")
     Callback<ListDetail> response(Callback<List<ListDetail>> callback);





}
