package com.example.miskaaproject.Utility;

import android.content.Context;
import android.widget.ImageView;

import com.example.miskaaproject.R;
import com.pixplicity.sharp.Sharp;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HTTPRequest {
    public static OkHttpClient okHttpClient;

    public static void fetchData(Context context, String url, final ImageView flagImgView) {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .build();
        }

        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                flagImgView.setImageResource(R.drawable.asia);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Sharp.loadInputStream(inputStream).into(flagImgView);
                inputStream.close();
            }
        });
    }

}
