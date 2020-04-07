package com.example.githubsearch.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.githubsearch.R;
import com.example.githubsearch.adapter.ReposListAdapter;
import com.example.githubsearch.databinding.ActivityUserProfileBinding;
import com.example.githubsearch.model.Repository;
import com.example.githubsearch.model.UserProfile;
import com.example.githubsearch.network.ApiService;
import com.example.githubsearch.network.RetrofitAPIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserProfileActivity extends AppCompatActivity {

    private ActivityUserProfileBinding binding;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        setUp();
    }

    /**
     * set the basic details
     */
    private void setUp() {
        if (null != getIntent() && null != getIntent().getExtras()) {
            userProfile = getIntent().getExtras().getParcelable("userProfile");
        }
        if (!TextUtils.isEmpty(userProfile.getAvatarUrl())) {
            Picasso.with(this).load(userProfile.getAvatarUrl()).into(binding.imageProfile);
        }
        if (!TextUtils.isEmpty(userProfile.getName())) {
            binding.userName.setText(userProfile.getName());
        }
        if (!TextUtils.isEmpty(userProfile.getLocation())) {
            binding.location.setText(userProfile.getLocation());
        }
        binding.numberRepository.setText(String.valueOf(userProfile.getPublicRepos()));
        binding.textFollowers.setText(String.valueOf(userProfile.getFollowers()));

        if (!TextUtils.isEmpty(userProfile.getLogin())) {
            getRepositoryList(userProfile.getLogin());
        }

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * API call for user public repository
     * @param username string
     */
    private void getRepositoryList(String username) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = RetrofitAPIClient.getApiClient().create(ApiService.class);
        Observable<List<Repository>> observable = apiService.getUserReposList(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<List<Repository>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Repository> repositories) {
                progressDialog.dismiss();
                ReposListAdapter adapter = new ReposListAdapter(repositories);
                binding.rvList.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
