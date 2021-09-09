package com.example.miskaaproject.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "country_table")
public class CountryModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "capital")
    private String capital;

    @ColumnInfo(name = "region")
    private String region;

    @ColumnInfo(name = "subregion")
    private String subregion;

    @ColumnInfo(name = "flag")
    private String flag;

    @ColumnInfo(name = "population")
    private int population;

    @ColumnInfo(name = "borders")
    private String borders;

    @ColumnInfo(name = "languages")
    private String languages;

    public CountryModel(String name, String capital, String region, String subregion, String flag, int population, String borders, String languages) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.flag = flag;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getBorders() {
        return borders;
    }

    public void setBorders(String borders) {
        this.borders = borders;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
