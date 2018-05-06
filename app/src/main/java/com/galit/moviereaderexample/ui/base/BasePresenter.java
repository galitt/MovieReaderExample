package com.galit.moviereaderexample.ui.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by galit on 01/05/2018.
 */

public abstract class BasePresenter <T extends BaseView> {
    protected Logger mLogger = LoggerFactory.getLogger(getClass().getSimpleName());

    private WeakReference<T> mBaseView;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(T baseView) {
        this.mBaseView = new WeakReference<T>(baseView);
    }

    protected abstract void onStart();
    protected abstract void onStop();

    public void start(){

        if(mCompositeDisposable == null || mCompositeDisposable.isDisposed()){
            mCompositeDisposable = new CompositeDisposable();
        }
        onStart();
    }

    public void stop(){
        onStop();

        mCompositeDisposable.dispose();
        mBaseView = null;
    }

    public T getBaseView() {
        return mBaseView == null ? null : mBaseView.get();
    }

    protected void addDisposable(Disposable disposable){

        if(disposable != null && !disposable.isDisposed()) {

            //presenter stopped or not started yet
            if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
                disposable.dispose();

            } else {
                mCompositeDisposable.add(disposable);
            }
        }
    }
}
