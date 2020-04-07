package com.example.githubsearch;

import android.text.TextUtils;

import com.example.githubsearch.model.Repository;
import com.example.githubsearch.model.UserProfile;
import com.example.githubsearch.network.ApiService;
import com.example.githubsearch.network.RetrofitAPIClient;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.functions.Predicate;

public class APIAvailabilityTest {
    private ApiService apiService;
    private String userName = "ankitsaini12";

    @Before
    public void init() {
        apiService = RetrofitAPIClient.getApiClient().create(ApiService.class);
    }

    /**
     * userinfo Api test
     */
    @Test
    public void userInfoApiTestAvailability() {
        apiService.getUserInfo(userName).test().assertNoErrors().assertValue(new Predicate<UserProfile>() {
            @Override
            public boolean test(UserProfile userProfile) throws Exception {
                return null != userProfile;
            }
        });
    }

    /**
     * user public repository test
     */
    @Test
    public void userReposApiTestAvailability() {
        apiService.getUserReposList(userName).test().assertNoErrors().assertValue(new Predicate<List<Repository>>() {
            @Override
            public boolean test(List<Repository> repositories) throws Exception {
                return null != repositories;
            }
        });
    }
}
