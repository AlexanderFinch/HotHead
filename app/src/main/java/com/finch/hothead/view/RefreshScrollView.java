package com.finch.hothead.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by finchrat on 4/3/2017.
 */
public class RefreshScrollView extends ScrollView {
    private Callback cb;
    private boolean isScrollChanged;

    public interface Callback {
        void update();
    }

    public RefreshScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN) {
            isScrollChanged = false;
        }
        if(ev.getAction() == MotionEvent.ACTION_UP) {
            if (!isScrollChanged && cb != null) {
                cb.update();
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        isScrollChanged = true;
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setUpdateCallback(RefreshScrollView.Callback cb){
        this.cb = cb;
    }
}
