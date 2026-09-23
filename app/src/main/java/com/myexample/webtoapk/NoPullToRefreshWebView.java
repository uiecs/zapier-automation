package com.myexample.webtoapk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

public class NoPullToRefreshWebView extends WebView {
    private float initialTouchY;

    public NoPullToRefreshWebView(Context context) {
        super(context);
        disableOverscroll();
    }

    public NoPullToRefreshWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        disableOverscroll();
    }

    public NoPullToRefreshWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        disableOverscroll();
    }

    private void disableOverscroll() {
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setNestedScrollingEnabled(false);
    }

    @Override
    protected boolean overScrollBy(
            int deltaX,
            int deltaY,
            int scrollX,
            int scrollY,
            int scrollRangeX,
            int scrollRangeY,
            int maxOverScrollX,
            int maxOverScrollY,
            boolean isTouchEvent) {
        return super.overScrollBy(
                deltaX,
                deltaY,
                scrollX,
                scrollY,
                scrollRangeX,
                scrollRangeY,
                0,
                0,
                isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                initialTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() <= 0 && event.getY() > initialTouchY) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
