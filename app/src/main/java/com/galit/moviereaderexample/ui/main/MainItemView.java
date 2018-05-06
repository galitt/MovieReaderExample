package com.galit.moviereaderexample.ui.main;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import com.galit.moviereaderexample.R;
import com.galit.moviereaderexample.ui.components.imageSwitcher.MultipleSourceImageSwitcher;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by galit on 01/05/2018.
 */

@NonReusable
@Layout(R.layout.view_main_item)
public class MainItemView {

    @View(R.id.mainItemTitle)
    public TextView mTitleTextView;

    @View(R.id.mainEmptyItemTextView)
    public TextView mMainEmptyItemTextView;



    @View(R.id.mainItemImage)
    public MultipleSourceImageSwitcher mContentImageSwitcher;

    private String mTitle;
    private List<String> mImages = new ArrayList<>();

    public MainItemView(String title, List<String> images) {
        this.mTitle = title;
        setImages(images);
    }

    public void setImages(List<String> images) {
        mImages.clear();
        if(images != null) {
            mImages.addAll(images);
        }

        if(mContentImageSwitcher != null){
            updateView();
        }
    }

    @Resolve
    public void onResolve() {

        mTitleTextView.setText(mTitle);
        updateView();
    }

    private void updateView(){
        if ( mImages.isEmpty() ){
            mContentImageSwitcher.setVisibility(android.view.View.GONE);
            mMainEmptyItemTextView.setVisibility(android.view.View.VISIBLE);

        }else{
            mContentImageSwitcher.setVisibility(android.view.View.VISIBLE);
            mMainEmptyItemTextView.setVisibility(android.view.View.GONE);
            mContentImageSwitcher.setImages(mImages);
        }
    }

}
