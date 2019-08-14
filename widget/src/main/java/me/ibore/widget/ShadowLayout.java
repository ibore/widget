package me.ibore.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import androidx.annotation.Nullable;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ShadowLayout extends LinearLayout {

    private int shadowColor;
    private int backgroundColor;
    private float shadowLimit;
    private float radius;
    private float offsetX;
    private float offsetY;
    private boolean showLeft;
    private boolean showRight;
    private boolean showTop;
    private boolean showBottom;
    private boolean alwaysShowRadius;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;


    private boolean mInvalidateShadowOnSizeChanged = true;
    private boolean mForceInvalidateShadow = false;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout, 0, 0);
        if (attr == null) return;
        try {
            //默认是显示
            showLeft = attr.getBoolean(R.styleable.ShadowLayout_slShowLeft, true);
            showTop = attr.getBoolean(R.styleable.ShadowLayout_slShowTop, true);
            showRight = attr.getBoolean(R.styleable.ShadowLayout_slShowRight, true);
            showBottom = attr.getBoolean(R.styleable.ShadowLayout_slShowBottom, true);
            alwaysShowRadius = attr.getBoolean(R.styleable.ShadowLayout_slAlwaysShowRadius, true);
            radius = attr.getDimension(R.styleable.ShadowLayout_slRadius, 0);
            topLeftRadius = attr.getDimension(R.styleable.ShadowLayout_slTopLeftRadius, 0);
            topRightRadius = attr.getDimension(R.styleable.ShadowLayout_slTopRightRadius, 0);
            bottomLeftRadius = attr.getDimension(R.styleable.ShadowLayout_slBottomLeftRadius, 0);
            bottomRightRadius = attr.getDimension(R.styleable.ShadowLayout_slBottomRightRadius, 0);
            shadowLimit = attr.getDimension(R.styleable.ShadowLayout_slShadowLimit, 0);
            offsetX = attr.getDimension(R.styleable.ShadowLayout_slOffsetX, 0);
            offsetY = attr.getDimension(R.styleable.ShadowLayout_slOffsetY, 0);
            shadowColor = attr.getColor(R.styleable.ShadowLayout_slShadowColor, Color.parseColor("#20000000"));
            backgroundColor = attr.getColor(R.styleable.ShadowLayout_slBackgroundColor, Color.TRANSPARENT);
        } finally {
            attr.recycle();
        }
        if (radius > 0) {
            topLeftRadius = topRightRadius = bottomRightRadius = bottomLeftRadius = radius;
        }
        int xPadding = (int) (shadowLimit + Math.abs(offsetX));
        int yPadding = (int) (shadowLimit + Math.abs(offsetY));
        super.setPadding(showLeft ? xPadding : 0, showTop ? yPadding : 0, showRight ? xPadding : 0, showBottom ? yPadding : 0);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {

    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {

    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 0;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && (getBackground() == null || mInvalidateShadowOnSizeChanged || mForceInvalidateShadow)) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(w, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mForceInvalidateShadow) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(right - left, bottom - top);
        }
    }

    public void setInvalidateShadowOnSizeChanged(boolean invalidateShadowOnSizeChanged) {
        mInvalidateShadowOnSizeChanged = invalidateShadowOnSizeChanged;
    }

    public void invalidateShadow() {
        mForceInvalidateShadow = true;
        requestLayout();
        invalidate();
    }

    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }


    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight) {
        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                showLeft ? shadowLimit : 0,
                showTop ? shadowLimit : 0,
                showRight ? shadowWidth - shadowLimit : shadowWidth,
                showBottom ? shadowHeight - shadowLimit : shadowHeight);

        if (offsetY > 0) {
            shadowRect.top += offsetY;
            shadowRect.bottom -= offsetY;
        } else if (offsetY < 0) {
            shadowRect.top += Math.abs(offsetY);
            shadowRect.bottom -= Math.abs(offsetY);
        }
        if (offsetX > 0) {
            shadowRect.left += offsetX;
            shadowRect.right -= offsetX;
        } else if (offsetX < 0) {
            shadowRect.left += Math.abs(offsetX);
            shadowRect.right -= Math.abs(offsetX);
        }
        Paint shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(backgroundColor);
        shadowPaint.setStyle(Paint.Style.FILL);
        if (!isInEditMode()) {
            shadowPaint.setShadowLayer(shadowLimit, offsetX, offsetY, shadowColor);
        }
        Path path = new Path();
        float[] radii;
        if (alwaysShowRadius) {
            radii = new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
        } else {
            float topLeft = !showLeft || !showTop ? 0 : topLeftRadius;
            float topRight = !showRight || !showTop ? 0 : topRightRadius;
            float bottomRight = !showRight || !showBottom ? 0 : bottomRightRadius;
            float bottomLeft = !showLeft || !showBottom ? 0 : bottomLeftRadius;
            radii = new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft};
        }
        path.addRoundRect(shadowRect, radii, Path.Direction.CW);
        canvas.drawPath(path, shadowPaint);
        return output;
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setShadowLimit(float shadowLimit) {
        this.shadowLimit = shadowLimit;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        this.topLeftRadius = radius;
        this.topRightRadius = radius;
        this.bottomRightRadius = radius;
        this.bottomLeftRadius = radius;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setShowLeft(boolean showLeft) {
        this.showLeft = showLeft;
    }

    public void setShowRight(boolean showRight) {
        this.showRight = showRight;
    }

    public void setShowTop(boolean showTop) {
        this.showTop = showTop;
    }

    public void setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }
}