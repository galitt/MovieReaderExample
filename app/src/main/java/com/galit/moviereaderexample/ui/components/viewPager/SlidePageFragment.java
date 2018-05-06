package com.galit.moviereaderexample.ui.components.viewPager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by galit on 01/05/2018.
 */

public class SlidePageFragment extends Fragment {

    public SlidePageFragment() {
    }

    public static SlidePageFragment newInstance(String imagePath) {
        SlidePageFragment myFragment = new SlidePageFragment();

        Bundle args = new Bundle();
        args.getString("MemoriesSlidePageFragmentData", imagePath);
        myFragment.setArguments(args);

        return myFragment;
    }
}
