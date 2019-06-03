package me.ibore.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class XNestedScrollView extends NestedScrollView {

    private XOnScrollChangeListener mXOnScrollChangeListener;

    public XNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public XNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mXOnScrollChangeListener != null) {
            this.mXOnScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    public void setXOnScrollChangeListener(XOnScrollChangeListener xOnScrollChangeListener) {
        this.mXOnScrollChangeListener = xOnScrollChangeListener;
    }

    public interface XOnScrollChangeListener {
        void onScrollChange(XNestedScrollView view, int l, int t, int oldl, int oldt);
    }
}
