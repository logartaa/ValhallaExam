package com.valhalla.technicalexam.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.valhalla.technicalexam.R;
import com.valhalla.technicalexam.api.GsonService;
import com.valhalla.technicalexam.api.RetrofitService;
import com.valhalla.technicalexam.ui.post.Post;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailFragment extends Fragment {

    private DetailViewModel mViewModel;
    private CompositeDisposable mCompositeDisposables;
    private CommentsAdapter mAdapter;
    private AppCompatActivity mAppCompatActivity;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCompositeDisposables = new CompositeDisposable();
        String jsonPost = DetailFragmentArgs.fromBundle(getArguments()).getPostData();
        Post post = GsonService.getPostObject(jsonPost);

        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        mAdapter = new CommentsAdapter(new ArrayList<>(), post);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_details);
        recyclerView.setAdapter(mAdapter);

        mAppCompatActivity = (AppCompatActivity) requireActivity();
        Objects.requireNonNull(mAppCompatActivity.getSupportActionBar()).setHomeButtonEnabled(true);
        Objects.requireNonNull(mAppCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        Observable<List<Comments>> observable = RetrofitService.getInstance()
                .getComments(post.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        DisposableObserver<List<Comments>> observer = new DisposableObserver<List<Comments>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Comments> comments) {
                mAdapter.updateCommentsList(comments);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mCompositeDisposables.add(observable.subscribeWith(observer));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Objects.requireNonNull(mAppCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            requireActivity().onBackPressed();
        }
        return true;
    }
}