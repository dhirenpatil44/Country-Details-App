package com.example.miskaaproject.Database;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import com.example.miskaaproject.Dao.CountryDao;
import com.example.miskaaproject.Model.CountryModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CountryDatabaseHandler {
    private static CountryDatabase countryDatabase;
    private static CountryDao countryDao;

    public static void getInstance(Context context) {
        countryDatabase = Room.databaseBuilder(context, CountryDatabase.class, "country_data")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        countryDao = countryDatabase.countryDao();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CompletableFuture<List<CountryModel>> completableFuture() {
        return CompletableFuture.supplyAsync(() -> countryDao.getAllCountries());
    }

    public static String db() {
        if (countryDao.getAllCountries().isEmpty()) {
            return null;
        } else {
            return "Data is available";
        }
    }

    public static void insertData(List<CountryModel> countryModels) {
        countryDao.insert(countryModels);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void deleteData() {
        CompletableFuture.runAsync(() -> countryDatabase.clearAllTables());
    }
}
