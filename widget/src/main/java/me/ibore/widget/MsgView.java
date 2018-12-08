package me.ibore.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 消息View
 */
public class MsgView extends TextView {
    private Context mContext;
    private GradientDrawable gd_background = new GradientDrawable();
    private int mBackgroundColor;
    private int mCornerRadius;
    private int mStrokeWidth;
    private int mStrokeColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    public MsgView(Context context) {
        this(context, null);
    }

    public MsgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MsgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MsgView);
        mBackgroundColor = ta.getColor(R.styleable.MsgView_mvBackgroundColor, Color.TRANSPARENT);
        mCornerRadius = ta.getDimensionPixelSize(R.styleable.MsgView_mvCornerRadius, 0);
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.MsgView_mvStrokeWidth, 0);
        mStrokeColor = ta.getColor(R.styleable.MsgView_mvStrokeColor, Color.TRANSPARENT);
        isRadiusHalfHeight = ta.getBoolean(R.styleable.MsgView_mvIsRadiusHalfHeight, false);
        isWidthHeightEqual = ta.getBoolean(R.styleable.MsgView_mvIsWidthHeightEqual, false);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isRadiusHalfHeight()) {
            setCornerRadius(getHeight() / 2);
        } else {
            setBgSelector();
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
        setBgSelector();
    }

    public void setCornerRadius(int cornerRadius) {
        this.mCornerRadius = UIUtils.dp2px(cornerRadius);
        setBgSelector();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = UIUtils.dp2px(strokeWidth);
        setBgSelector();
    }

    public void setStrokeColor(int strokeColor) {
        this.mStrokeColor = strokeColor;
        setBgSelector();
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        setBgSelector();
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        setBgSelector();
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    private void setBgSelector() {
        StateListDrawable bg = new StateListDrawable();
        setDrawable(gd_background, mBackgroundColor, mStrokeColor);
        bg.addState(new int[]{-android.R.attr.state_pressed}, gd_background);
        setBackground(bg);
    }

    private void setDrawable(GradientDrawable gd, int color, int strokeColor) {
        gd.setColor(color);
        gd.setCornerRadius(mCornerRadius);
        gd.setStroke(mStrokeWidth, strokeColor);
    }
}
