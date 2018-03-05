package com.example.tijoj.bakingapp.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.tijoj.bakingapp.data.Recipes;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tijoj on 3/1/2018.
 */

public class NetworkHelper {

    public Context context;
    public final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public NetworkHelper(Context context) {
        this.context = context;
    }

    //CHECKING IF INTERNET CONNECTION IS THERE
    public Boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
