package me.ibore.widget.dialog.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;

import me.ibore.widget.UIUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class DialogBody extends FrameLayout {

    private int gravity;
    private int layoutGravity;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    @ColorRes
    private int bgColor;
    // Content
    private CharSequence content;
    @ColorRes
    private int contentTextColor;
    private int contentTextSize;
    private boolean bottomDivider;

    private DialogBody(Builder builder) {
        super(builder.context);
        this.gravity = builder.gravity;
        this.layoutGravity = builder.layoutGravity;
        this.paddingTop = builder.paddingTop;
        this.paddingBottom = builder.paddingBottom;
        this.paddingLeft = builder.paddingLeft;
        this.paddingRight = builder.paddingRight;
        this.bgColor = builder.bgColor;
        // Content
        this.content = builder.content;
        this.contentTextColor = builder.contentTextColor;
        this.contentTextSize = builder.contentTextSize;
        this.bottomDivider = builder.bottomDivider;
        initView();
    }

    private void initView() {
        this.setBackgroundResource(bgColor);

        TextView tv = new TextView(getContext());
        tv.setText(content);
        tv.setTextSize(contentTextSize);
        tv.setTextColor(getResources().getColor(contentTextColor));
        tv.setGravity(gravity);

        // LayoutGravity
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = layoutGravity;
        tv.setLayoutParams(lp);

        // Padding
        tv.setPadding(UIUtils.dp2px(getContext(), paddingLeft), UIUtils.dp2px(getContext(), paddingTop),
                UIUtils.dp2px(getContext(), paddingRight), UIUtils.dp2px(getContext(), paddingBottom));

        addView(tv);

        if (bottomDivider) {
            View divider = new View(getContext());
            //divider.setBackgroundColor(getResources().getColor(R.color.gray_line));
            LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, UIUtils.dp2px(getContext(), 1f));
            layoutParams.gravity = Gravity.BOTTOM;
            divider.setLayoutParams(layoutParams);
            addView(divider);
        }
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

    public DialogBody addBottomDivider() {
        View divider = new View(getContext());
        divider.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        LayoutParams lp =
                new LayoutParams(MATCH_PARENT, UIUtils.dp2px(getContext(), 1f));
        lp.gravity = Gravity.BOTTOM;
        divider.setLayoutParams(lp);
        this.addView(divider);
        return this;
    }

    public static class Builder {

        private Context context;
        private int gravity;
        private int layoutGravity;
        private int paddingTop;
        private int paddingBottom;
        private int paddingLeft;
        private int paddingRight;
        @ColorRes
        private int bgColor;
        // Content
        private CharSequence content;
        @ColorRes
        private int contentTextColor;
        private int contentTextSize;
        private boolean bottomDivider;

        public Builder(Context context) {
            this.context = context;
            this.gravity = Gravity.CENTER;
            this.layoutGravity = Gravity.CENTER;
            this.paddingTop = 16;
            this.paddingBottom = 16;
            this.paddingLeft = 16;
            this.paddingRight = 16;
            this.bgColor = android.R.color.white;
            // Content
            this.content = "";
            this.contentTextColor = android.R.color.tab_indicator_text;
            this.contentTextSize = 14;
            this.bottomDivider = true;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setLayoutGravity(int layoutGravity) {
            this.layoutGravity = layoutGravity;
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

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setContentTextColor(@ColorRes int contentTextColor) {
            this.contentTextColor = contentTextColor;
            return this;
        }

        public Builder setContentTextSize(int contentTextSize) {
            this.contentTextSize = contentTextSize;
            return this;
        }

        public void setBottomDivider(boolean bottomDivider) {
            this.bottomDivider = bottomDivider;
        }

        public DialogBody builder() {
            return new DialogBody(this);
        }
    }
}
