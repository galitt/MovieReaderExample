package com.galit.moviereaderexample.ui.components.viewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by galit on 01/05/2018.
 */

public class DepthPageViewPager extends ViewPager {
    private Logger mLogger = LoggerFactory.getLogger(getClass().getSimpleName());

    public DepthPageViewPager(Context context) {
        super(context);
        postInitViewPager();
    }

    public DepthPageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }

    private ScrollerCustomDuration mScroller = null;

    /**
     * Override the Scroller instance to change the
     * duration
     */
    private void postInitViewPager() {

        Field scroller = null;
        try {
            scroller = ViewPager.class.getDeclaredField("mScroller");

            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerCustomDuration(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);

            setScrollDurationFactor(15);
            setPageTransformer(true, new DepthPageTransformer());

        } catch (NoSuchFieldException e) {
            mLogger.error("postInitViewPager failed", e);

        } catch (IllegalAccessException e) {
            mLogger.error("postInitViewPager failed", e);
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }
}
