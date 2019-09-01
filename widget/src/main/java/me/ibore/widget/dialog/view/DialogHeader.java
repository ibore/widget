package me.ibore.widget.dialog.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;

public class DialogHeader extends FrameLayout {

    private CharSequence title;
    @ColorRes
    private int titleColor;
    private int titleTextSize;
    private int typeface;
    private int gravity;
    @ColorRes
    private int bgColor;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    public DialogHeader(Builder builder) {
        super(builder.context);
        this.title = builder.title;
        this.titleColor = builder.titleColor;
        this.titleTextSize = builder.titleTextSize;
        this.typeface = builder.typeface;
        this.gravity = builder.gravity;
        this.bgColor = builder.bgColor;
        this.paddingTop = builder.paddingTop;
        this.paddingBottom = builder.paddingBottom;
        this.paddingLeft = builder.paddingLeft;
        this.paddingRight = builder.paddingRight;
        initView();
    }

    private void initView() {
        TextView tv = new TextView(getContext());
        tv.setText(title);
        tv.setTextSize(titleTextSize);
        tv.setTextColor(getResources().getColor(titleColor));
        tv.setTypeface(Typeface.DEFAULT, typeface);
        // Gravity
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = gravity;
        tv.setLayoutParams(lp);
        // Padding
        tv.setPadding(UIUtils.dp2px(getContext(), paddingLeft), UIUtils.dp2px(getContext(), paddingTop),
                UIUtils.dp2px(getContext(), paddingRight), UIUtils.dp2px(getContext(), paddingBottom));
        addView(tv);
        setBackgroundResource(bgColor);
    }

    public void setCornerRadius(int cornerRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(new float[]{
                UIUtils.dp2px(getContext(), cornerRadius), UIUtils.dp2px(getContext(), cornerRadius),
                UIUtils.dp2px(getContext(), cornerRadius), UIUtils.dp2px(getContext(), cornerRadius),
                0, 0,
                0, 0});
        drawable.setColor(getResources().getColor(bgColor));
        this.setBackground(drawable);
    }

    public static class Builder {

        private Context context;
        private CharSequence title;
        @ColorRes
        private int titleColor;
        private int titleTextSize;
        private int typeface;
        private int gravity;
        @ColorRes
        private int bgColor;
        private int paddingTop;
        private int paddingBottom;
        private int paddingLeft;
        private int paddingRight;

        public Builder(Context context) {
            this.context = context;
            this.typeface = Typeface.BOLD;
            this.title = "";
            this.titleColor = R.color.comm_titlebar_text_selector;
            this.titleTextSize = 16;
            this.bgColor = android.R.color.white;
            this.gravity = Gravity.CENTER;
            this.paddingTop = 16;
            this.paddingBottom = 0;
            this.paddingLeft = 0;
            this.paddingRight = 0;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setPaddingTop(int paddingTop) {
            this.paddingTop = paddingTop;
            return this;
        }

        public Builder setPaddingBottom(int paddingBottom) {
            this.paddingBottom = paddingBottom;
            return this;
        }

        public Builder setPaddingLeft(int paddingLeft) {
            this.paddingLeft = paddingLeft;
            return this;
        }

        public Builder setPaddingRight(int paddingRight) {
            this.paddingRight = paddingRight;
            return this;
        }

        public Builder setBgColor(@ColorRes int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setTitleColor(@ColorRes int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setTitleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public Builder setTypeface(int typeface) {
            this.typeface = typeface;
            return this;
        }

        public DialogHeader builder() {
            return new DialogHeader(this);
        }
    }

}
