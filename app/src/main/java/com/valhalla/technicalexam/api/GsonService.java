package com.valhalla.technicalexam.api;

import com.google.gson.Gson;
import com.valhalla.technicalexam.ui.post.Post;

public class GsonService {
    private static Gson mGson;

    public static Gson getInstance() {
        if (mGson != null) {
            return mGson;
        } else {
            return new Gson();
        }
    }

    public static Post getPostObject(String jsonPost){
        return getInstance().fromJson(jsonPost, Post.class);
    }
}
