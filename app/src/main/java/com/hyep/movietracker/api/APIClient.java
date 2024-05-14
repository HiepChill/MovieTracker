package com.hyep.movietracker.api;

import static com.hyep.movietracker.utils.Utils.API_KEY;
import static com.hyep.movietracker.utils.Utils.BASE_URL;

import com.hyep.movietracker.models.MovieResponse;
import com.hyep.movietracker.models.TVResponse;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {
    static APIInterface apiInterface;

    // Retrofit Instance
    public static APIInterface getApiInterface(){
        if (apiInterface == null){
            OkHttpClient.Builder client = new OkHttpClient.Builder();

            client.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key",API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }


    public interface APIInterface{
        @GET("movie/upcoming")
        Call<MovieResponse> getUpcomingMovies(@Query("api_key") String api_key);

        @GET("discover/movie")
        Call<MovieResponse> getDiscoverMovie(@Query("api_key") String api_key);

        @GET("discover/tv")
        Call<TVResponse> getDiscoverTV(@Query("api_key") String api_key,
                                       @Query("language") String language);
    }

}
