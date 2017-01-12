package com.chuckree.chucktheshow.interfaces;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Srichakradhar on 13-01-2017.
 */

public class ApiClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=a0d9f39596f4944adb268f1743681253";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
