package com.example.miskaaproject.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.miskaaproject.Dao.CountryDao;
import com.example.miskaaproject.Model.CountryModel;

@Database(entities = {CountryModel.class}, version = 1, exportSchema = false)
public abstract class CountryDatabase extends RoomDatabase {

    public abstract CountryDao countryDao();
}
