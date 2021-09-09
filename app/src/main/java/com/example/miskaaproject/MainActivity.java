package com.example.miskaaproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.miskaaproject.Adapter.CustomAdapter;
import com.example.miskaaproject.Database.CountryDatabase;
import com.example.miskaaproject.Database.CountryDatabaseHandler;
import com.example.miskaaproject.Model.CountryModel;
import com.example.miskaaproject.Volley.VolleyRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private List<CountryModel> countryModelList;

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CountryDatabaseHandler.getInstance(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        requestQueue = VolleyRequest.getVolleyRequest(MainActivity.this).getRequestQueue();
        countryModelList = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if (CountryDatabaseHandler.db() != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Delete all data?")
                            .setPositiveButton("Delete",((dialog, which) -> {
                                deleteAllData(MainActivity.this);
                            }))
                            .setNegativeButton("Cancel",null)
                            .setCancelable(true);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });

        if (internetConnected(MainActivity.this)){
            fetchAllCountriesData();

        }else if (CountryDatabaseHandler.db() != null){
            CountryDatabaseHandler.completableFuture().thenAccept(this::offlineData);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteAllData(Context context) {
        countryModelList.clear();

        CustomAdapter customAdapter = new CustomAdapter(context, countryModelList);
        CountryDatabaseHandler.deleteData();

        recyclerView.setAdapter(customAdapter);
    }

    private static boolean internetConnected(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void offlineData(List<CountryModel> modelList) {
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, modelList);
        recyclerView.setAdapter(customAdapter);
    }

    private void fetchAllCountriesData() {
        String URL = "https://restcountries.eu/rest/v2/region/asia";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        String capital = jsonObject.getString("capital");
                        String region = jsonObject.getString("region");
                        String subregion = jsonObject.getString("subregion");
                        String flag = jsonObject.getString("flag");

                        int population = jsonObject.getInt("population");

                        JSONArray borders = jsonObject.getJSONArray("borders");

                        String initialBorder = "";
                        if (borders.length() > 0) {
                            for (int j = 0; j < borders.length(); j++) {

                                String borderLen = borders.getString(j);

                                initialBorder += borderLen + ", ";
                            }
                            initialBorder = initialBorder.substring(0, initialBorder.length() - 1);
                        }

                        JSONArray language = jsonObject.getJSONArray("languages");
                        String initialLanguage = "";

                        if (language.length() > 0) {
                            for (int j = 0; j < language.length(); j++) {

                                JSONObject jsonObject1 = language.getJSONObject(j);
                                String languageLen = jsonObject1.getString("name");

                                initialLanguage += languageLen + ", ";
                            }

                            initialLanguage = initialLanguage.substring(0, initialLanguage.length() - 1);
                        }

                        CountryModel countryModel = new CountryModel(name, capital, region, subregion, flag, population, initialBorder, initialLanguage);
                        countryModelList.add(countryModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                CustomAdapter myAdapter = new CustomAdapter(MainActivity.this, countryModelList);
                recyclerView.setAdapter(myAdapter);

                if (CountryDatabaseHandler.db() == null) {
                    CountryDatabaseHandler.insertData(countryModelList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}