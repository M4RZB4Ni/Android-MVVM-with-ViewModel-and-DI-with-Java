package com.marzbani.adjoetask.data.models;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Album {

    private int userId;
    private int id;
    private String title;

    // Constructors, getters, and setters

    public Album(int userId, int id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    // Getters and setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return "Album{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
    private static Album fromJson(JSONObject albumJson) throws JSONException {
        return new Album(
                albumJson.getInt("userId"),
                albumJson.getInt("id"),
                albumJson.getString("title")
        );
    }

    public static List<Album> fromJsonArray(String jsonArrayString) throws JSONException {
        List<Album> albums = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonArrayString);

        for (int i = 0; i < jsonArray.length(); i++) {
            albums.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return albums;
    }
}
