package com.hank.process.services;

import com.hank.models.InsultResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;

public class InsultGenerator {
    private static final String BASE_URL = "https://evilinsult.com/";

    public static InsultResponse buildInsultResponse() throws IOException {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient client = httpBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        InsultRest insultRest = retrofit.create(InsultRest.class);
        Response<InsultResponse> response = insultRest.getInsult("en", "json").execute();
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
        return response.body();
    }

    public interface InsultRest {
        @GET("generate_insult.php")
        Call<InsultResponse> getInsult(@Query("lang") String language, @Query("type") String type);
    }
}
