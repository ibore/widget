package me.ibore.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.ibore.widget.listener.OnItemClickListener;

import static me.ibore.widget.WheelView.Mode.MODE_2D;
import static me.ibore.widget.WheelView.Mode.MODE_3D;


public class WheelView extends View {

    private Camera mCamera;
    private Matrix mMatrix;

    private boolean mCyclic;
    private int mVisibleItems = 9;
    private int mLineSpace = 10;
    private int mTextSize = 20;
    private int mSelectedColor = Color.parseColor("#1db0b8");
    private int mUnselectedColor = Color.parseColor("#727272");
    private Drawable mDividerTop;
    private Drawable mDividerBottom;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        if (this.mode != mode) {
            this.mode = mode;
            invalidate();
        }
    }

    private int mode = MODE_2D;

    int centerX;
    int centerY;
    int upperLimit;
    int lowerLimit;
    int baseline;
    int itemWidth;
    int itemHeight;

    Paint mPaint;
    WheelScroller mScroller;

    @IntDef({MODE_2D, MODE_3D})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
        int MODE_2D = 0;
        int MODE_3D = 1;
    }

    final List<CharSequence> mEntries = new ArrayList<>();

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        boolean cyclic = a.getBoolean(R.styleable.WheelView_wvCyclic, false);
        CharSequence[] entries = a.getTextArray(R.styleable.WheelView_wvEntries);
        int visibleItems = a.getInt(R.styleable.WheelView_wvVisibleItems, mVisibleItems);
        int lineSpace = a.getDimensionPixelOffset(R.styleable.WheelView_wvLineSpace, mLineSpace);
        int textSize = a.getDimensionPixelSize(R.styleable.WheelView_wvTextSize, mTextSize);
        int selectedColor = a.getColor(R.styleable.WheelView_wvSelectedColor, mSelectedColor);
        int unselectedColor = a.getColor(R.styleable.WheelView_wvUnselectedColor, mUnselectedColor);
        mDividerTop = a.getDrawable(R.styleable.WheelView_wvDivider);
        mDividerBottom = a.getDrawable(R.styleable.WheelView_wvDivider);
        mode = a.getInt(R.styleable.WheelView_wvMode, MODE_2D);
        a.recycle();
        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mScroller = new WheelScroller(context, this);

        setCyclic(cyclic);
        setVisibleItems(visibleItems);
        setLineSpace(lineSpace);
        setTextSize(textSize);
        setSelectedColor(selectedColor);
        setUnselectedColor(unselectedColor);
        setEntries(entries);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY
                && heightSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
            mVisibleItems = getPrefVisibleItems();
        } else if (widthSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSpecSize, getPrefHeight());
        } else if (heightSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(getPrefWidth(), heightSpecSize);
            mVisibleItems = getPrefVisibleItems();
        } else {
            setMeasuredDimension(getPrefWidth(), getPrefHeight());
        }

        centerX = (getMeasuredWidth() + getPaddingLeft() - getPaddingRight()) / 2;
        centerY = (getMeasuredHeight() + getPaddingTop() - getPaddingBottom()) / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // 计算上方分割线的高度
        upperLimit = centerY - itemHeight / 2;
        // 计算下方分割线的高度
        lowerLimit = centerY + itemHeight / 2;

        if (mDividerTop != null) {
            int h = mDividerTop.getIntrinsicHeight();
            mDividerTop.setBounds(getPaddingLeft(), upperLimit,
                    getWidth() - getPaddingRight(), upperLimit + h);

        }
        if (mDividerBottom != null) {
            int h = mDividerBottom.getIntrinsicHeight();
            mDividerBottom.setBounds(getPaddingLeft(), lowerLimit - h,
                    getWidth() - getPaddingRight(), lowerLimit);
        }
    }

    /**
     * @return 控件的预算宽度
     */
    public int getPrefWidth() {
        int padding = getPaddingLeft() + getPaddingRight();
        int innerWidth = (int) (itemWidth + mTextSize * .5f);
        int prefWidth = innerWidth + padding;
        if (mode == MODE_3D) {
            int innerHeight = (int) (itemHeight * mVisibleItems * 2 / Math.PI);
            int towardRange = (int) (Math.sin(Math.PI / 48) * innerHeight);
            // 必须增加滚轮的内边距,否则当toward不为none时文字显示不全
            prefWidth += towardRange;
        }
        return prefWidth;
    }

    /**
     * @return 控件的预算高度
     */
    public int getPrefHeight() {
        if (mode == MODE_2D) {
            int padding = getPaddingTop() + getPaddingBottom();
            int innerHeight = itemHeight * mVisibleItems;
            return innerHeight + padding;
        } else {
            int padding = getPaddingTop() + getPaddingBottom();
            int innerHeight = (int) (itemHeight * mVisibleItems * 2 / Math.PI);
            return innerHeight + padding;
        }
    }

    /**
     * @return 可见项的预算数量
     */
    public int getPrefVisibleItems() {
        if (mode == MODE_2D) {
            int innerHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            return innerHeight / itemHeight;
        } else {
            int innerHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            return (int) (innerHeight * Math.PI / itemHeight / 2);
        }
    }

    void measureItemSize() {
        int width = 0;
        for (CharSequence cs : mEntries) {
            float w = Layout.getDesiredWidth(cs, (TextPaint) mPaint);
            width = Math.max(width, Math.round(w));
        }
        int height = Math.round(mPaint.getFontMetricsInt(null) + mLineSpace);
        itemWidth = width;
        if (itemHeight != height) {
            // 每一项的高度改变时，需要重新计算滚动偏移量；
//            mScroller.setCurrentIndex(mScroller.getCurrentIndex(), false);
            itemHeight = height;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int index = mScroller.getItemIndex();
        final int offset = mScroller.getItemOffset();
        final int hf = (mVisibleItems + 1) / 2;
        final int minIdx, maxIdx;
        if (offset < 0) {
            minIdx = index - hf - 1;
            maxIdx = index + hf;
        } else if (offset > 0) {
            minIdx = index - hf;
            maxIdx = index + hf + 1;
        } else {
            minIdx = index - hf;
            maxIdx = index + hf;
        }
        for (int i = minIdx; i < maxIdx; i++) {
            if (mode == MODE_2D) {
                draw2DItem(canvas, i, offset);
            } else {
                draw3DItem(canvas, i, offset);
            }
        }
        if (mDividerTop != null) {
            mDividerTop.draw(canvas);
        }
        if (mDividerBottom != null) {
            mDividerBottom.draw(canvas);
        }
    }

    protected void draw2DItem(Canvas canvas, int index, int offset) {
        CharSequence text = getCharSequence(index);
        if (text == null) return;

        // 和中间选项的距离
        final int range = (index - mScroller.getItemIndex()) * itemHeight - offset;

        int clipLeft = getPaddingLeft();
        int clipRight = getWidth() - getPaddingRight();
        int clipTop = getPaddingTop();
        int clipBottom = getHeight() - getPaddingBottom();

        // 绘制两条分界线之间的文字
        if (Math.abs(range) <= 0) {
            mPaint.setColor(mSelectedColor);
            canvas.save();
            canvas.clipRect(clipLeft, upperLimit, clipRight, lowerLimit);
            canvas.drawText(text, 0, text.length(), centerX, centerY + range - baseline, mPaint);
            canvas.restore();
        }
        // 绘制与下分界线相交的文字
        else if (range > 0 && range < itemHeight) {
            mPaint.setColor(mSelectedColor);
            canvas.save();
            canvas.clipRect(clipLeft, upperLimit, clipRight, lowerLimit);
            canvas.drawText(text, 0, text.length(), centerX, centerY + range - baseline, mPaint);
            canvas.restore();

            mPaint.setColor(mUnselectedColor);
            canvas.save();
            canvas.clipRect(clipLeft, lowerLimit, clipRight, clipBottom);
            canvas.drawText(text, 0, text.length(), centerX, centerY + range - baseline, mPaint);
            canvas.restore();
        }
        // 绘制与上分界线相交的文字
        else if (range < 0 && range > -itemHeight) {
            mPaint.setColor(mSelectedColor);
            canvas.save();
            canvas.clipRect(clipLeft, upperLimit, clipRight, lowerLimit);
            canvas.drawText(text, 0, text.length(), centerX, centerY + range - baseline, mPaint);
            canvas.restore();

            mPaint.setColor(mUnselectedColor);
            canvas.save();
            canvas.clipRect(clipLeft, clipTop, clipRight, upperLimit);
            canvas.drawText(text, 0, text.length(), centerX, centerY + range - baseline, mPaint);
            canvas.restore();
        } else {
            mPaint.setColor(mUnselectedColor);
            canvas.save();
            canvas.clipRect(clipLeft, clipTop, clipRight, clipBottom);
            canvas.drawText(text, 0, text.length(), centerX, centerY + range - baseline, mPaint);
            canvas.restore();
        }
    }

    protected void draw3DItem(Canvas canvas, int index, int offset) {
        CharSequence text = getCharSequence(index);
        if (text == null) return;
        // 滚轮的半径
        final int r = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        // 和中间选项的距离
        final int range = (index - mScroller.getItemIndex()) * itemHeight - offset;
        // 当滑动的角度和y轴垂直时（此时文字已经显示为一条线），不绘制文字
        if (Math.abs(range) > r * Math.PI / 2) return;

        final double angle = (double) range / r;
        // 绕x轴滚动的角度
        float rotate = (float) Math.toDegrees(-angle);
        // 滚动的距离映射到x轴的长度
//        float translateX = (float) (Math.cos(angle) * Math.sin(Math.PI / 36) * r * mToward);
        // 滚动的距离映射到y轴的长度
        float translateY = (float) (Math.sin(angle) * r);
        // 滚动的距离映射到z轴的长度
        float translateZ = (float) ((1 - Math.cos(angle)) * r);
        // 折射偏移量x
        float refractX = getTextSize() * .05f;
        // 透明度
        int alpha = (int) (Math.cos(angle) * 255);

        int clipLeft = getPaddingLeft();
        int clipRight = getWidth() - getPaddingRight();
        int clipTop = getPaddingTop();
        int clipBottom = getHeight() - getPaddingBottom();

        // 绘制两条分界线之间的文字
        if (Math.abs(range) <= 0) {
            mPaint.setColor(getSelectedColor());
            canvas.save();
            canvas.translate(refractX, 0);
            canvas.clipRect(clipLeft, upperLimit, clipRight, lowerLimit);
            draw3DText(canvas, text, 0, translateY, translateZ, rotate);
            canvas.restore();
        }
        // 绘制与下分界线相交的文字
        else if (range > 0 && range < itemHeight) {
            mPaint.setColor(getSelectedColor());
            canvas.save();
            canvas.translate(refractX, 0);
            canvas.clipRect(clipLeft, upperLimit, clipRight, lowerLimit);
            draw3DText(canvas, text, 0, translateY, translateZ, rotate);
            canvas.restore();

            mPaint.setColor(getUnselectedColor());
            mPaint.setAlpha(alpha);
            canvas.save();
            canvas.clipRect(clipLeft, lowerLimit, clipRight, clipBottom);
            draw3DText(canvas, text, 0, translateY, translateZ, rotate);
            canvas.restore();
        }
        // 绘制与上分界线相交的文字
        else if (range < 0 && range > -itemHeight) {
            mPaint.setColor(getSelectedColor());
            canvas.save();
            canvas.translate(refractX, 0);
            canvas.clipRect(clipLeft, upperLimit, clipRight, lowerLimit);
            draw3DText(canvas, text, 0, translateY, translateZ, rotate);
            canvas.restore();

            mPaint.setColor(getUnselectedColor());
            mPaint.setAlpha(alpha);
            canvas.save();
            canvas.clipRect(clipLeft, clipTop, clipRight, upperLimit);
            draw3DText(canvas, text, 0, translateY, translateZ, rotate);
            canvas.restore();
        } else {
            mPaint.setColor(getUnselectedColor());
            mPaint.setAlpha(alpha);
            canvas.save();
            canvas.clipRect(clipLeft, clipTop, clipRight, clipBottom);
            draw3DText(canvas, text, 0, translateY, translateZ, rotate);
            canvas.restore();
        }
    }

    private void draw3DText(Canvas canvas, CharSequence text, float translateX, float translateY, float translateZ, float rotateX) {
        if (null == mCamera) {
            mCamera = new Camera();
            mMatrix = new Matrix();
        }
        mCamera.save();
        mCamera.translate(translateX, 0, translateZ);
        mCamera.rotateX(rotateX);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        final float x = centerX;
        final float y = centerY + translateY;

        // 设置绕x轴旋转的中心点位置
        mMatrix.preTranslate(-x, -y);
        mMatrix.postTranslate(x, y);

        canvas.concat(mMatrix);
        canvas.drawText(text, 0, text.length(), x, y - baseline, mPaint);
    }


    CharSequence getCharSequence(int index) {
        int size = mEntries.size();
        if (size == 0) return null;
        CharSequence text = null;
        if (isCyclic()) {
            int i = index % size;
            if (i < 0) {
                i += size;
            }
            text = mEntries.get(i);
        } else {
            if (index >= 0 && index < size) {
                text = mEntries.get(index);
            }
        }
        return text;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScroller.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        mScroller.computeScroll();
    }

    public boolean isCyclic() {
        return mCyclic;
    }

    public void setCyclic(boolean cyclic) {
        mCyclic = cyclic;
        mScroller.reset();
        invalidate();
    }

    public int getVisibleItems() {
        return mVisibleItems;
    }

    public void setVisibleItems(int visibleItems) {
        mVisibleItems = Math.abs(visibleItems / 2 * 2 + 1); // 当传入的值为偶数时,换算成奇数;
        mScroller.reset();
        requestLayout();
        invalidate();
    }

    public int getLineSpace() {
        return mLineSpace;
    }

    public void setLineSpace(int lineSpace) {
        mLineSpace = lineSpace;
        mScroller.reset();
        measureItemSize();
        requestLayout();
        invalidate();
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
        mPaint.setTextSize(textSize);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        baseline = (int) ((fontMetrics.top + fontMetrics.bottom) / 2);
        mScroller.reset();
        measureItemSize();
        requestLayout();
        invalidate();
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
        invalidate();
    }

    public int getUnselectedColor() {
        return mUnselectedColor;
    }

    public void setUnselectedColor(int unselectedColor) {
        mUnselectedColor = unselectedColor;
        invalidate();
    }

    public int getItemSize() {
        return mEntries.size();
    }

    public CharSequence getItem(int index) {
        if (index < 0 && index >= mEntries.size())
            return null;

        return mEntries.get(index);
    }

    public CharSequence getCurrentItem() {
        return mEntries.get(getCurrentIndex());
    }

    public int getCurrentIndex() {
        return mScroller.getCurrentIndex();
    }

    public void setCurrentIndex(int index) {
        setCurrentIndex(index, false);
    }

    public void setCurrentIndex(int index, boolean animated) {
        mScroller.setCurrentIndex(index, animated);
    }

    public void setEntries(CharSequence... entries) {
        mEntries.clear();
        if (entries != null && entries.length > 0) {
            Collections.addAll(mEntries, entries);
        }
        mScroller.reset();
        measureItemSize();
        requestLayout();
        invalidate();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mScroller.onItemClickListener = onItemClickListener;
    }


    public class WheelScroller extends Scroller {
        private int mScrollOffset;
        private float lastTouchY;
        private boolean isScrolling;

        final WheelView mWheelView;
        private VelocityTracker mVelocityTracker;
        OnItemClickListener<WheelView, View> onItemClickListener;

        public static final int JUSTIFY_DURATION = 400;

        public WheelScroller(Context context, WheelView wheelView) {
            super(context);
            mWheelView = wheelView;
        }

        public void computeScroll() {
            if (isScrolling) {
                isScrolling = computeScrollOffset();
                doScroll(getCurrY() - mScrollOffset);
                if (isScrolling) {
                    mWheelView.postInvalidate();
                } else {
                    // 滚动结束后，重新调整位置
                    justify();
                }
            }
        }

        private int currentIndex;

        private void doScroll(int distance) {
            mScrollOffset += distance;
            if (!mWheelView.isCyclic()) {
                // 限制滚动边界
                final int maxOffset = (mWheelView.getItemSize() - 1) * mWheelView.itemHeight;
                if (mScrollOffset < 0) {
                    mScrollOffset = 0;
                } else if (mScrollOffset > maxOffset) {
                    mScrollOffset = maxOffset;
                }
            }
            notifyWheelChangedListener();
        }

        void notifyWheelChangedListener() {
            int oldValue = currentIndex;
            int newValue = getCurrentIndex();
            if (oldValue != newValue) {
                currentIndex = newValue;
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mWheelView, newValue, null);
                }
            }
        }

        public int getCurrentIndex() {
            final int itemHeight = mWheelView.itemHeight;
            final int itemSize = mWheelView.getItemSize();
            if (itemSize == 0) return 0;

            int itemIndex;
            if (mScrollOffset < 0) {
                itemIndex = (mScrollOffset - itemHeight / 2) / itemHeight;
            } else {
                itemIndex = (mScrollOffset + itemHeight / 2) / itemHeight;
            }
            int currentIndex = itemIndex % itemSize;
            if (currentIndex < 0) {
                currentIndex += itemSize;
            }
            return currentIndex;
        }

        public void setCurrentIndex(int index, boolean animated) {
            int position = index * mWheelView.itemHeight;
            int distance = position - mScrollOffset;
            if (distance == 0) return;
            if (animated) {
                isScrolling = true;
                startScroll(0, mScrollOffset, 0, distance, JUSTIFY_DURATION);
                mWheelView.invalidate();
            } else {
                doScroll(distance);
                mWheelView.invalidate();
            }
        }

        public int getItemIndex() {
            return mWheelView.itemHeight == 0 ? 0 : mScrollOffset / mWheelView.itemHeight;
        }

        public int getItemOffset() {
            return mWheelView.itemHeight == 0 ? 0 : mScrollOffset % mWheelView.itemHeight;
        }

        public void reset() {
            isScrolling = false;
            mScrollOffset = 0;
            notifyWheelChangedListener();
            forceFinished(true);
        }

        /**
         * 当滚轮结束滑行后，调整滚轮的位置，需要调用该方法
         */
        void justify() {
            final int itemHeight = mWheelView.itemHeight;
            final int offset = mScrollOffset % itemHeight;
            if (offset > 0 && offset < itemHeight / 2) {
                isScrolling = true;
                startScroll(0, mScrollOffset, 0, -offset, JUSTIFY_DURATION);
                mWheelView.invalidate();
            } else if (offset >= itemHeight / 2) {
                isScrolling = true;
                startScroll(0, mScrollOffset, 0, itemHeight - offset, JUSTIFY_DURATION);
                mWheelView.invalidate();
            } else if (offset < 0 && offset > -itemHeight / 2) {
                isScrolling = true;
                startScroll(0, mScrollOffset, 0, -offset, JUSTIFY_DURATION);
                mWheelView.invalidate();
            } else if (offset <= -itemHeight / 2) {
                isScrolling = true;
                startScroll(0, mScrollOffset, 0, -itemHeight - offset, JUSTIFY_DURATION);
                mWheelView.invalidate();
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain();
            }
            mVelocityTracker.addMovement(event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastTouchY = event.getY();
                    forceFinished(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float touchY = event.getY();
                    int deltaY = (int) (touchY - lastTouchY);
                    if (deltaY != 0) {
                        doScroll(-deltaY);
                        mWheelView.invalidate();
                    }
                    lastTouchY = touchY;
                    break;
                case MotionEvent.ACTION_UP:
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float velocityY = mVelocityTracker.getYVelocity();

                    if (Math.abs(velocityY) > 0) {
                        isScrolling = true;
                        fling(0, mScrollOffset, 0, (int) -velocityY, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        mWheelView.invalidate();
                    } else {
                        justify();
                    }
                case MotionEvent.ACTION_CANCEL:
                    // 当触发抬起、取消事件后，回收VelocityTracker
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                    break;
            }
            return true;
        }
    }

}
