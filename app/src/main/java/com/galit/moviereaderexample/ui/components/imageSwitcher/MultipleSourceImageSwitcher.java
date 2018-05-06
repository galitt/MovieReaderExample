package com.galit.moviereaderexample.ui.components.imageSwitcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.galit.moviereaderexample.utils.NetworkUtils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by galit on 01/05/2018.
 */

public class MultipleSourceImageSwitcher extends ImageSwitcher implements Target {

    private List<String> mImages = new ArrayList<>();
    private int mCurrentImageIndex = 0;
    private List<Integer> failedImageIndexes = new ArrayList<>();


    private Handler mImageSwitcherHandler = new Handler(Looper.getMainLooper());
    private Runnable mImageSwitcherTask = new Runnable() {
        @Override
        public void run() {

            mCurrentImageIndex++;
            if(mCurrentImageIndex == mImages.size()){
                mCurrentImageIndex = 0;
            }

            loadImage(mImages.get(mCurrentImageIndex));
        }
    };

    private ViewSwitcher.ViewFactory mViewFactory = new ViewSwitcher.ViewFactory() {
        ImageView imageView;

        @Override
        public View makeView() {

            if(imageView != null) {
                imageView.setImageBitmap(null);
                imageView.destroyDrawingCache();
            }
            imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new
                    ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            return imageView;
        }
    };

    public MultipleSourceImageSwitcher(Context context) {
        super(context);
        init();
    }

    public MultipleSourceImageSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setFactory(mViewFactory);
        Animation in = AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_in);
        in.setDuration(5000);
        setInAnimation(in);
    }

    public void setImages(List<String> images) {

        stopImageSwitcherHandler();
        mImages.clear();
        mCurrentImageIndex = 0;
        if(images != null) {
            mImages.addAll(images);
        }

        //reload UI
        if(mImages.isEmpty()){
            setImageDrawable(null);

        }else{
            loadImage(mImages.get(mCurrentImageIndex));
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startImageSwitcherHandler();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopImageSwitcherHandler();
        super.onDetachedFromWindow();
    }

    private void startImageSwitcherHandler(){
        //has data to switch
        if( canStartImageSwitcherHandler()) {
            mImageSwitcherHandler.postDelayed(mImageSwitcherTask, 5000);
        }
    }

    private boolean canStartImageSwitcherHandler(){
        if( mImages.size() <= 1){
            return false;
        }

        if(mImages.size() - failedImageIndexes.size() <= 1){
            return false;
        }

        return true;
    }

    private void stopImageSwitcherHandler(){

        mImageSwitcherHandler.removeCallbacks(mImageSwitcherTask);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        if(bitmap != null) {
            setImageDrawable(new BitmapDrawable(getResources(), bitmap));
            startImageSwitcherHandler();

        }else{
            onBitmapFailed(null);
        }

    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        failedImageIndexes.add(mCurrentImageIndex);
        startImageSwitcherHandler();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    private void loadImage(final String imageUrl){
        Picasso.with(MultipleSourceImageSwitcher.this.getContext())
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        MultipleSourceImageSwitcher.this.onBitmapLoaded(bitmap, from);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                        if(NetworkUtils.isNetworkConnected()) {
                            //Try again online if cache failed
                            Picasso.with(MultipleSourceImageSwitcher.this.getContext())
                                    .load(imageUrl)
                                    .into(MultipleSourceImageSwitcher.this);

                        }else {
                            MultipleSourceImageSwitcher.this.onBitmapFailed(errorDrawable);
                        }
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}
