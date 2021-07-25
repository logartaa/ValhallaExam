package com.valhalla.technicalexam.details;

import com.google.gson.annotations.SerializedName;

public class Comments {
    @SerializedName("postId")
    Long postId;

    @SerializedName("id")
    Long id;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("body")
    String body;

    public Long getPostId() {
        return postId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
