package com.example.githubsearch.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.githubsearch.R;
import com.example.githubsearch.databinding.ActivitySearchBinding;
import com.example.githubsearch.model.UserProfile;
import com.example.githubsearch.network.ApiService;
import com.example.githubsearch.network.RetrofitAPIClient;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setSearchActivity(this);
    }

    /**
     * click on load button
     */
    public void onLoadClick() {
        String userName = binding.edtUserName.getText().toString();
        if (!TextUtils.isEmpty(userName)) {
            getUserInfo(userName);
        } else {
            Toast.makeText(this, "Please enter the username", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Api call for user information
     * @param username
     */
    private void getUserInfo(String username) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = RetrofitAPIClient.getApiClient().create(ApiService.class);
        Observable<UserProfile> observable = apiService.getUserInfo(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<UserProfile>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserProfile userProfile) {
                progressDialog.dismiss();
                if (null != userProfile) {
                    Intent intent = new Intent(SearchActivity.this, UserProfileActivity.class);
                    intent.putExtra("userProfile", userProfile);
                    startActivity(intent);
                }
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
