package com.example.miskaaproject.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.miskaaproject.Model.CountryModel;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CountryModel> countryModels);

    @Query("SELECT * FROM country_table")
    List<CountryModel> getAllCountries();
}
