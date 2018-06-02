package com.apps.brz.cardstack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ophir on 5/4/2018.
 */

class ImageCard {

    public enum ImageState {
        DEFAULT, PENDING_DELETION, DELETED;
    }

    private String path;
    private ImageState state;

    public ImageCard(String path) {
        this.path = path;
        state = ImageState.DEFAULT;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageState getState() {
        return state;
    }

    public void setState(ImageState state) {
        this.state = state;
    }

    public String toJSON() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public static ImageCard fromJSON(String json) {
        Gson gson = new Gson();
        ImageCard image = gson.fromJson(json, ImageCard.class);
        return image;
    }

    public static String toJSONList(List<ImageCard> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    public static List<ImageCard> fromJSONList(String json) {
        Gson gson = new Gson();
        List<ImageCard> images = gson.fromJson(json, new TypeToken<List<ImageCard>>(){}.getType());
        return images;
    }


}
