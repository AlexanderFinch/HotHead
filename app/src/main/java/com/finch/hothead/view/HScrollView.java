package com.finch.hothead.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by finchrat on 7/18/2016.
 */
public class HScrollView extends HorizontalScrollView {
    public interface UpdateCallback {
        void update();
    }

    private Runnable scrollerTask; // holds the task to run
    private int initialPosition; // sets the initial position before swipe
    private int delayTime = 100; // time in ms to delay
    public static final String TAG = "HScrollView"; // Tag for this object
    private LinearLayout linearLayout; // the one (and only) child layout
    private UpdateCallback updateCallback;
    private int MAX_SCROLL_SPEED = 1800;
    private View currentChildView;

    public View getCurrentChildView() {
        return currentChildView;
    }

//    @Override
//    public void fling(int velocityY) {
//        int topVelocityY = (int) ((Math.min(Math.abs(velocityY), MAX_SCROLL_SPEED)) * Math.signum(velocityY));
//        super.fling(topVelocityY);
//    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        // Grab the last child placed in the ScrollView, we need it to determine the left position.
        View view = getChildAt(getChildCount() - 1);

        // if diff is zero, then the end has been reached
        if ((view.getRight() + 10 < getWidth() + getScrollX())) {
//            MainActivity.setCurrentPage(1);
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }

    public int getChildViewPosition() {
        int ret = 0;
        int childCount = linearLayout.getChildCount(); // how many items there are
        for (int i = 0; i < childCount; i++) {
            if (currentChildView == linearLayout.getChildAt(i)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public void setUpdateCallback(UpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }

    public interface OnScrollStoppedListener {
        void onScrollStopped();
    }

    // holds the listener
    private OnScrollStoppedListener onScrollStoppedListener;

    public HScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        // don't want the padding to cover our items
        setClipToPadding(false);

        // create the listener to handle centering of items
        setOnScrollStoppedListener(new HScrollView.OnScrollStoppedListener() {
            @Override
            public void onScrollStopped() {
                //get the center
                int center = getScrollX() + getWidth() / 2; // get the center of the view
                int childCount = linearLayout.getChildCount(); // how many items there are
                for (int i = 0; i < childCount; i++) {
                    View child = linearLayout.getChildAt(i);

//                    if(scrollToChild != 0 && scrollToChild + i > 0 && scrollToChild + i < childCount
//                    child == currentChildView && )

                    int viewLeft = child.getLeft() + getPaddingLeft();
                    int viewWidth = child.getWidth();
                    if (center >= viewLeft && center <= viewLeft + viewWidth) { // if we are within the child...
                        int childCenter = (viewLeft + viewWidth / 2); // get the center of the child
                        smoothScrollBy(childCenter - center, 0); // scroll to center
                        currentChildView = child;
                        break;
                    }
                }
                if (updateCallback != null) {
                    updateCallback.update();
                }
            }
        });

        // create listener to mark when we start scrolling
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startScrollerTask();
                }
                return false;
            }
        });

        // create runnable to call our listener when we stop scrolling
        scrollerTask = new Runnable() {
            @Override
            public void run() {
                int newPosition = getScrollX();
                if (initialPosition - newPosition == 0) { // scrolling has stopped
                    if (onScrollStoppedListener != null) {
                        onScrollStoppedListener.onScrollStopped();
                    }
                } else {
                    initialPosition = getScrollX();
                    HScrollView.this.postDelayed(scrollerTask, delayTime);
                }
            }
        };
    }

    public void scrollToEnd() {
        // run this delayed on create to center over the last item.
        postDelayed(new Runnable() {
            @Override
            public void run() {
                fullScroll(FOCUS_RIGHT);
                scrollTo(getScrollX() + getPaddingRight(), 0); // scroll to the right
                startScrollerTask(); // recenter in case we are not quite there

            }
        }, delayTime);
    }

    public void scrollToBeginning() {
        // run this delayed on create to center over the first item.
        postDelayed(new Runnable() {
            @Override
            public void run() {
                fullScroll(FOCUS_LEFT);
                scrollTo(getScrollX() + getPaddingLeft(), 0); // scroll to the left
                startScrollerTask(); // recenter in case we are not quite there

            }
        }, delayTime);
    }

    public void setOnScrollStoppedListener(HScrollView.OnScrollStoppedListener listener) {
        onScrollStoppedListener = listener;
    }

    /**
     * task that runs the scroller task
     */
    public void startScrollerTask() {
        initialPosition = getScrollX();
        HScrollView.this.postDelayed(scrollerTask, delayTime);
    }

    public void scrollTo(int position) {
        View view = linearLayout.getChildAt(position);
        int left = view.getLeft() + getPaddingLeft();
        int right = view.getRight() + getPaddingRight();
        smoothScrollTo((left + right - getWidth()) / 2, 0);
        currentChildView = view;
        updateCallback.update();
    }
}
