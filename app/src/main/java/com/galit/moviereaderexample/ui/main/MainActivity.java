package com.galit.moviereaderexample.ui.main;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.galit.moviereaderexample.R;
import com.galit.moviereaderexample.ui.base.BaseTitleActivity;
import com.galit.moviereaderexample.utils.EMovieGroupType;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.List;

/**
 * Created by galit on 01/05/2018.
 */

public class MainActivity extends BaseTitleActivity implements MainView {
    PlaceHolderView mPlaceHolderView;
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initViews();
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.start();
    }

    @Override
    protected void onDestroy() {
        mMainPresenter.stop();
        super.onDestroy();
    }

    private void initViews(){
        mPlaceHolderView = findViewById(R.id.mainScreenPlaceHolderView);
        mPlaceHolderView.getBuilder().setHasFixedSize(true).setItemViewCacheSize(EMovieGroupType.MOVIE_GROUP_COUNT)
                .setLayoutManager(new GridLayoutManager(this
                        ,getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4:1));
    }

    @Override
    public void setLoadingInProgress(boolean inProgress) {

    }

    @Override
    public void setNoData() {
        //todo
    }

    @Override
    public void addViewItem(String titleResId, List<String> images) {

        if(mPlaceHolderView != null){
            mPlaceHolderView.addView(new MainItemView(titleResId, images));
        }
    }

    @Override
    public void updateViewItem(int itemIndex, List<String> images) {
        if(mPlaceHolderView != null){
            ((MainItemView)mPlaceHolderView.getViewResolverAtPosition(itemIndex)).setImages(images);
            mPlaceHolderView.getAdapter().notifyItemChanged(itemIndex);
        }
    }

    @Override
    public void showError(int msg) {

        //todo handle error dialog
    }

    @Override
    public Context getContext() {
        return this;
    }

}
