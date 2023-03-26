package com.example.gardneer;

import java.util.Comparator;

public class PlantInfoHomeClass {
    private int id;
    private String name;
    private int image;
    private String seed;
    private String weather_requirement;
    private String sprout_to_harvest;
    private String season;
    private String water;

    public PlantInfoHomeClass(int id, String name, int image, String seed, String weather_requirement, String sprout_to_harvest, String season, String water) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.seed = seed;
        this.weather_requirement = weather_requirement;
        this.sprout_to_harvest = sprout_to_harvest;
        this.season = season;
        this.water = water;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getWeather_requirement() {
        return weather_requirement;
    }

    public void setWeather_requirement(String weather_requirement) {
        this.weather_requirement = weather_requirement;
    }

    public String getSprout_to_harvest() {
        return sprout_to_harvest;
    }

    public void setSprout_to_harvest(String sprout_to_harvest) {
        this.sprout_to_harvest = sprout_to_harvest;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }
}
