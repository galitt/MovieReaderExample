package com.galit.moviereaderexample.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by galit on 01/05/2018.
 */

public class NetworkUtils {

    private static ConnectivityManager connectivityManager;

    private NetworkUtils() {
        // This utility class is not publicly instantiable
    }

    public static void init(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isNetworkConnected() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
