package com.galit.moviereaderexample.ui.main;
import android.content.Context;
import android.content.SharedPreferences;

import com.galit.moviereaderexample.data.DataRepository;
import com.galit.moviereaderexample.data.local.model.MovieGroup;
import com.galit.moviereaderexample.ui.base.BasePresenter;
import com.galit.moviereaderexample.utils.EMovieGroupType;
import com.galit.moviereaderexample.utils.ISharedPreferencesProvider;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by galit on 01/05/2018.
 */

public class MainPresenter extends BasePresenter<MainView> {

    /**
     * Map view item indexes by movie type
     */
    private HashMap<EMovieGroupType, Integer> mMovieGroups = new HashMap<>();

    public MainPresenter(MainView baseView) {
        super(baseView);
    }

    @Override
    protected void onStart() {
        //DataRepository.getInstance().updateData(this);
        initViewList();
        loadData();
    }

    @Override
    protected void onStop() {

    }

    private void initViewList(){

        if(getBaseView() != null){
            EMovieGroupType[] types = EMovieGroupType.values();

            for(int i = 0; i < types.length; i++){
                mMovieGroups.put(types[i], i);
                getBaseView().addViewItem(types[i].getTitle(getBaseView().getContext()), null);
            }
        }
    }

    private void loadData(){
        EMovieGroupType[] types = EMovieGroupType.values();

        for(EMovieGroupType movieGroupType : types){
            getMovieGroup(movieGroupType);
        }
    }

    private void getMovieGroup(final EMovieGroupType groupType){

        addDisposable(DataRepository.getInstance().getMovies(groupType)
                .filter(result -> result.isLoaded())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieGroup>() {
                    @Override
                    public void accept(MovieGroup movieGroup) {

                        if(movieGroup != null && movieGroup.isValid() && getBaseView() != null){

                            int viewItemIndex = mMovieGroups.get(groupType);
                            getBaseView().updateViewItem(viewItemIndex, movieGroup.getBestMoviesImages(5));
                        }
                    }

                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(Throwable throwable){

                        mLogger.error("ERROR: getMovieGroup  " + groupType.toString(), throwable);
                    }
                })

        );
    }
}
