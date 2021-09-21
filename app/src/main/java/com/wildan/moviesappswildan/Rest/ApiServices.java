package com.wildan.moviesappswildan.Rest;

import com.wildan.moviesappswildan.Model.DetailMovie;
import com.wildan.moviesappswildan.Model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("movie/popular")
    Call<Response> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                       @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<DetailMovie> getMovieDetails(@Path("movie_id") String id, @Query("api_key") String apiKey);

}
