package com.chuckree.chucktheshow.interfaces;

import com.chuckree.chucktheshow.models.MoviesResponse;
import com.chuckree.chucktheshow.models.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Srichakradhar on 13-01-2017.
 */

public interface ApiInterface {
    @GET("/movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{id}")
    Call<MovieResult> getMovieDetails(@Path("id") int id, @Query("api_key") String api_key);
}
