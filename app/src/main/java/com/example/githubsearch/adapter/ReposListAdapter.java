package com.example.githubsearch.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubsearch.R;
import com.example.githubsearch.databinding.AdapterReposListBinding;
import com.example.githubsearch.model.Repository;

import java.util.List;

public class ReposListAdapter extends RecyclerView.Adapter<ReposListAdapter.ViewHolder> {
    private List<Repository> repositoryList;

    public ReposListAdapter(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }

    @NonNull
    @Override
    public ReposListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterReposListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_repos_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposListAdapter.ViewHolder holder, int position) {
        holder.bind(repositoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return ((null != repositoryList && !repositoryList.isEmpty()) ? repositoryList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterReposListBinding listBinding;

        public ViewHolder(@NonNull AdapterReposListBinding binding) {
            super(binding.getRoot());
            listBinding = binding;
        }

        void bind(final Repository repository) {
            if (!TextUtils.isEmpty(repository.getName())) {
                listBinding.tvNameRepos.setText(repository.getName());
            }

            if (!TextUtils.isEmpty(repository.getHtmlUrl())) {
                listBinding.tvUrlRepos.setText(repository.getHtmlUrl());
            }
        }

    }
}
