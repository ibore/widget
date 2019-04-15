package me.ibore.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ShadowLayout extends LinearLayout {

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        int shape = ta.getInt(R.styleable.ShadowLayout_slShape, ShadowDrawable.SHAPE_ROUND);
        int bgColor = ta.getColor(R.styleable.ShadowLayout_slBgColor, Color.parseColor("#00000000"));
        int shapeRadius = (int) ta.getDimension(R.styleable.ShadowLayout_slShapeRadius, 0F);
        int shadowColor = ta.getColor(R.styleable.ShadowLayout_slShadowColor, Color.parseColor("#00000000"));
        int shadowRadius = (int) ta.getDimension(R.styleable.ShadowLayout_slShadowRadius, 0F);
        int offsetX = (int) ta.getDimension(R.styleable.ShadowLayout_slOffsetX, 0F);
        int offsetY = (int) ta.getDimension(R.styleable.ShadowLayout_slOffsetY, 0F);
        ta.recycle();
        setShadowDrawable(shape, bgColor, shapeRadius, shadowColor, shadowRadius, offsetX, offsetY);
    }

    public void setShadowDrawable(int shape, int bgColor, int shapeRadius, int shadowColor, int shadowRadius, int offsetX, int offsetY) {
        ShadowDrawable.setShadowDrawable(this, new ShadowDrawable.Builder()
                .setShape(shape)
                .setBgColor(bgColor)
                .setShapeRadius(shapeRadius)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .setOffsetX(offsetX)
                .setOffsetY(offsetY)
                .builder());
        setPadding(shadowRadius + offsetX, shadowRadius - offsetY, shadowRadius - offsetX, shadowRadius + offsetY);
    }
}
