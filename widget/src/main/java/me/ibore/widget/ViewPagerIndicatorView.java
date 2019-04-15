package me.ibore.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.View;

public class ViewPagerIndicatorView extends View {

    private float mPositionOffset = 0;
    private int mPosition;

    private int mItemCount;
    private int mItemWidth;
    private int mItemHeight;
    private int mItemGap;

    private Drawable mItemDrawable;
    private Drawable mItemDrawableSelected;


    public ViewPagerIndicatorView(Context context) {
        super(context);
        init(context, null);
    }

    public ViewPagerIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ViewPagerIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    }

    int mWidth = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mItemCount > 1) {
            mWidth = (mItemWidth + mItemGap) * mItemCount - mItemGap;
        } else if(mItemCount == 1) {
            mWidth = mItemWidth;
        } else {
            mWidth = 0;
        }
        setMeasuredDimension(mWidth, mItemHeight);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("WrongConstant")
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        int wg = mItemWidth + mItemGap;
        int x = (getWidth() - mWidth) / 2;
        int y = (getHeight() - mItemHeight) / 2;

        mItemDrawable.setBounds(0, 0, mItemWidth, mItemHeight);
        mItemDrawableSelected.setBounds(0, 0, mItemWidth, mItemHeight);

        for (int i = 0; i < mItemCount; i++) {
            canvas.save();
            canvas.translate(x + i * wg, y);
            mItemDrawable.draw(canvas);
            canvas.restore();
        }

        canvas.save();
        canvas.translate(x + (mPosition + mPositionOffset) * wg, y);
        mItemDrawableSelected.draw(canvas);
        canvas.restore();

        canvas.restoreToCount(sc);
    }

    public void setupWithViewPager(ViewPager pager) {
        mItemCount = pager.getAdapter().getCount();
        pager.removeOnPageChangeListener(onPageChangeListener);
        pager.addOnPageChangeListener(onPageChangeListener);
        requestLayout();
    }

    public void setPosition(int position) {
        mPosition = position;
        invalidate();
    }


    public ViewPagerIndicatorView setItemSize(int width, int height) {
        this.mItemWidth = width;
        this.mItemHeight = height;
        return this;
    }

    public ViewPagerIndicatorView setItemGap(int gap) {
        this.mItemGap = gap;
        return this;
    }

    public ViewPagerIndicatorView setItemColor(@ColorInt int color, @ColorInt int selected) {
        this.mItemDrawable = genDrawable(color);
        this.mItemDrawableSelected = genDrawable(selected);
        return this;
    }

    public ViewPagerIndicatorView setItemDrawable(@NonNull Drawable normal, @NonNull Drawable selected) {
        this.mItemDrawable = normal;
        this.mItemDrawableSelected = selected;
        return this;
    }

    Drawable genDrawable(int color) {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(color);
        drawable.getPaint().setAntiAlias(true);
        return drawable;
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mPosition = position;
            mPositionOffset = positionOffset;
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            mPosition = position;
            mPositionOffset = 0;
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}