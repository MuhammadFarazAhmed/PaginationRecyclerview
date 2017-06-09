package com.example.farazahmed.paginationrecyclerview.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FarazAhmed on 4/21/2017.
 */

public class RetrofitClient {

    public  static final String BASE_URL = "http://api.themoviedb.org/3/";

    public static Retrofit retrofit = null;

    public static Retrofit getRetrofit()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
