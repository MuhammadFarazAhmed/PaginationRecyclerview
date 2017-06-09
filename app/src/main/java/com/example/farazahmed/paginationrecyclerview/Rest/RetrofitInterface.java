package com.example.farazahmed.paginationrecyclerview.Rest;

import com.example.farazahmed.paginationrecyclerview.Model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by FarazAhmed on 4/21/2017.
 */

public interface RetrofitInterface {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apikey , @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id , @Query("api_key") String apikey);

}
