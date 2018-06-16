package com.example.android.moviesfirststage.utilities;

import android.net.Uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class NetworkUtils {

    private final static String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie";
    private final static String API = "api_key";

    public static URL buildUrl(String apiKeyValue, String orderValue) {
        Uri buildUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(orderValue)
                .appendQueryParameter(API, apiKeyValue)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
