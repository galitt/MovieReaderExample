package com.galit.moviereaderexample.ui.main;

import com.galit.moviereaderexample.ui.base.BaseView;

import java.util.List;

/**
 * Created by galit on 01/05/2018.
 */

public interface MainView extends BaseView {

    void addViewItem(String titleResId, List<String> images);

    void updateViewItem(int itemIndex, List<String> images);
}
