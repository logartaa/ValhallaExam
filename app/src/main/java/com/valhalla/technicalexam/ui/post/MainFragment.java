package com.valhalla.technicalexam.ui.post;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valhalla.technicalexam.R;
import com.valhalla.technicalexam.api.GsonService;
import com.valhalla.technicalexam.api.RetrofitService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements PostListener {

    private MainViewModel mViewModel;
    private CompositeDisposable mCompositeDisposables;
    private PostAdapter mAdapter;

    private NavController mNavController;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavController = Navigation.findNavController(view);
        mCompositeDisposables = new CompositeDisposable();
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mAdapter = new PostAdapter(new ArrayList<>(), this);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_post);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        Observable<List<Post>> postListObservable = requestPostList();
        DisposableObserver<List<Post>> postListObserver = loadPostResponseList();

        mCompositeDisposables.add(postListObservable.subscribeWith(postListObserver));
    }

    @NotNull
    private DisposableObserver<List<Post>> loadPostResponseList() {
        return new DisposableObserver<List<Post>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Post> posts) {
                mAdapter.updatePostList(posts);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @NotNull
    private Observable<List<Post>> requestPostList() {
        return RetrofitService.getInstance().getPost().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onDestroy() {
        mCompositeDisposables.dispose();
        super.onDestroy();
    }

    @Override
    public void checkPostDetails(Post post) {
        mNavController.enableOnBackPressed(true);
        String jsonPost = GsonService.getInstance().toJson(post);
        MainFragmentDirections.ViewPostDetails viewPostDetailsAction =
                MainFragmentDirections.viewPostDetails(jsonPost);
        mNavController.navigate(viewPostDetailsAction);

    }
}