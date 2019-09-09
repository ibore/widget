package me.ibore.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


public class TitleBar extends ConstraintLayout {

    /**
     * <attr name="tbTitleText" format="string" />
     * <attr name="tbTitleSize" format="dimension" />
     * <attr name="tbTitleColor" format="color" />
     * <attr name="tbTitleImage" format="reference" />
     *
     * <attr name="tbSubTitleText" format="string" />
     * <attr name="tbSubTitleSize" format="dimension" />
     * <attr name="tbSubTitleColor" format="color" />
     *
     * <attr name="tbLeftText" format="string" />
     * <attr name="tbLeftSize" format="dimension" />
     * <attr name="tbLeftColor" format="color" />
     * <attr name="tbLeftIcon" format="reference" />
     * <attr name="tbLeftResource" format="reference" />
     *
     * <attr name="tbRightText" format="string" />
     * <attr name="tbRightSize" format="dimension" />
     * <attr name="tbRightColor" format="color" />
     * <attr name="tbRightIcon" format="reference" />
     * <attr name="tbRightResource" format="reference" />
     */
    public int DEFAULT_PADDING = UIUtils.dp2px(getContext(), 6);

    private TextView mTitleView;
    private CharSequence tbTitleText;
    private int tbTitleSize;
    @ColorInt
    private int tbTitleColor;
    @DrawableRes
    private int tbTitleImage;
    private int tbTitlePadding;
    private int tbTitleDirection;

    private TextView tbSubTitleView;
    private CharSequence tbSubTitleText;
    private int tbSubTitleSize;
    @ColorInt
    private int tbSubTitleColor;
    @DrawableRes
    private int tbSubTitleImage;
    private int tbSubTitlePadding;
    private int tbSubTitleDirection;

    private TextView tbStartView;
    private CharSequence tbStartText;
    private int tbStartSize;
    @ColorInt
    private int tbStartColor;
    @DrawableRes
    private int tbStartImage;
    private int tbStartPadding;
    private int tbStartDirection;
    private int tbStartViewWidth;

    private TextView tbEndView;
    private CharSequence tbEndText;
    private int tbEndSize;
    @ColorInt
    private int tbEndColor;
    @DrawableRes
    private int tbEndImage;
    private int tbEndPadding;
    private int tbEndDirection;
    private int tbEndViewWidth;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        tbTitleText = ta.getText(R.styleable.TitleBar_tbTitleText);
        tbTitleSize = ta.getDimensionPixelSize(R.styleable.TitleBar_tbTitleText, 18);
        tbTitleColor = ta.getColor(R.styleable.TitleBar_tbTitleColor, Color.parseColor("#333333"));
        tbTitleImage = ta.getResourceId(R.styleable.TitleBar_tbTitleImage, 0);
        tbTitlePadding = ta.getDimensionPixelSize(R.styleable.TitleBar_tbTitlePadding, DEFAULT_PADDING);

        //mTitleView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT));

        tbSubTitleText = ta.getText(R.styleable.TitleBar_tbSubTitleText);
        tbSubTitleSize = ta.getDimensionPixelSize(R.styleable.TitleBar_tbSubTitleText, 12);
        tbSubTitleColor = ta.getColor(R.styleable.TitleBar_tbSubTitleColor, Color.parseColor("#333333"));
        tbSubTitleImage = ta.getResourceId(R.styleable.TitleBar_tbSubTitleImage, 0);
        tbSubTitlePadding = ta.getDimensionPixelSize(R.styleable.TitleBar_tbSubTitlePadding, DEFAULT_PADDING);


        tbStartText = ta.getText(R.styleable.TitleBar_tbStartText);
        tbStartSize = ta.getDimensionPixelSize(R.styleable.TitleBar_tbStartText, 15);
        tbStartColor = ta.getColor(R.styleable.TitleBar_tbStartColor, Color.parseColor("#333333"));
        tbStartImage = ta.getResourceId(R.styleable.TitleBar_tbStartImage, 0);
        tbStartPadding = ta.getDimensionPixelSize(R.styleable.TitleBar_tbStartPadding, DEFAULT_PADDING);

        tbStartViewWidth = ta.getDimensionPixelSize(R.styleable.TitleBar_tbStartViewWidth, 0);

        tbEndText = ta.getText(R.styleable.TitleBar_tbEndText);
        tbEndSize = ta.getDimensionPixelSize(R.styleable.TitleBar_tbEndText, 15);
        tbEndColor = ta.getColor(R.styleable.TitleBar_tbEndColor, Color.parseColor("#333333"));
        tbEndImage = ta.getResourceId(R.styleable.TitleBar_tbEndImage, 0);
        tbEndPadding = ta.getDimensionPixelSize(R.styleable.TitleBar_tbEndPadding, DEFAULT_PADDING);

        //tbEndViewWidth = ta.getDimensionPixelSize(R.styleable.TitleBar_tbEndViewWidth, 0);

        ta.recycle();

    }

    public void initTitleView() {
        if (0 == tbTitleImage) {
            mTitleView = new TextView(getContext());
            mTitleView.setText(tbTitleText);
            mTitleView.setTextColor(tbTitleColor);
            mTitleView.setSingleLine();
            mTitleView.setMarqueeRepeatLimit(-1);
            mTitleView.setHorizontallyScrolling(true);
            mTitleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mTitleView.setCompoundDrawablePadding(tbTitlePadding);
            mTitleView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    tbTitleDirection == 0 ? tbTitleImage : 0, tbTitleDirection == 1 ? tbTitleImage : 0,
                    tbTitleDirection == 2 ? tbTitleImage : 0, tbTitleDirection == 3 ? tbTitleImage : 0);
        }
    }

    public void initSubTitleView() {
        if (0 == tbSubTitleImage) {
            tbSubTitleView = new TextView(getContext());
            tbSubTitleView.setText(tbSubTitleText);
            tbSubTitleView.setTextColor(tbSubTitleColor);
            tbSubTitleView.setSingleLine();
            tbSubTitleView.setMarqueeRepeatLimit(-1);
            tbSubTitleView.setHorizontallyScrolling(true);
            tbSubTitleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tbSubTitleView.setCompoundDrawablePadding(tbSubTitlePadding);
            tbSubTitleView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    tbSubTitleDirection == 0 ? tbSubTitleImage : 0, tbSubTitleDirection == 1 ? tbSubTitleImage : 0,
                    tbSubTitleDirection == 2 ? tbSubTitleImage : 0, tbSubTitleDirection == 3 ? tbSubTitleImage : 0);
        } else {

        }
    }

    public void initStartView() {
        if (0 == tbStartImage) {
            tbStartView = new TextView(getContext());
            tbStartView.setText(tbStartText);
            tbStartView.setTextColor(tbStartColor);
            tbStartView.setSingleLine();
            tbStartView.setMarqueeRepeatLimit(-1);
            tbStartView.setHorizontallyScrolling(true);
            tbStartView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tbStartView.setCompoundDrawablePadding(tbStartPadding);
            tbStartView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    tbStartDirection == 0 ? tbStartImage : 0, tbStartDirection == 1 ? tbStartImage : 0,
                    tbStartDirection == 2 ? tbStartImage : 0, tbStartDirection == 3 ? tbStartImage : 0);
        } else {

        }
    }

    public void initEndView() {
        if (0 == tbEndImage) {
            tbEndView = new TextView(getContext());
            tbEndView.setText(tbEndText);
            tbEndView.setTextColor(tbEndColor);
            tbEndView.setSingleLine();
            tbEndView.setMarqueeRepeatLimit(-1);
            tbEndView.setHorizontallyScrolling(true);
            tbEndView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tbEndView.setCompoundDrawablePadding(tbEndPadding);
            tbEndView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    tbEndDirection == 0 ? tbEndImage : 0, tbEndDirection == 1 ? tbEndImage : 0,
                    tbEndDirection == 2 ? tbEndImage : 0, tbEndDirection == 3 ? tbEndImage : 0);
        } else {

        }
    }

    public void initView() {
        ConstraintSet set = new ConstraintSet();
        // 设置宽高
        set.clone(this);


    }

}
