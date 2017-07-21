package com.android.developer.e_visa.network;

/**
 * Created by HP on 17-06-2017.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * this class is used to get the static retrofit instance
 */
public class ApiClient {

    public static final String BASE_URL = "http://webcreationsx.com/evisa-apis/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
