package com.example.githubsearch.network;

import com.example.githubsearch.model.Repository;
import com.example.githubsearch.model.UserProfile;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("users/{username}")
    Observable<UserProfile> getUserInfo(@Path("username") String username);


    @GET("users/{username}/repos")
    Observable<List<Repository>> getUserReposList(@Path("username") String username);
}
