package com.valhalla.technicalexam.api;

import com.valhalla.technicalexam.details.Comments;
import com.valhalla.technicalexam.ui.post.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("posts")
    public Observable<List<Post>> getPost();

    @GET("posts/{postId}/comments")
    public Observable<List<Comments>> getComments(@Path("postId") Long postId);
}
