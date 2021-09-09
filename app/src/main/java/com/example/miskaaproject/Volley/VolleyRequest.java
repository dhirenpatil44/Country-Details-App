package com.example.miskaaproject.Volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequest {
    private RequestQueue requestQueue;
    private static VolleyRequest volleyRequest;

    private VolleyRequest(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleyRequest getVolleyRequest(Context context) {
        if (volleyRequest == null) {
            volleyRequest = new VolleyRequest(context);
        }
        return volleyRequest;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
