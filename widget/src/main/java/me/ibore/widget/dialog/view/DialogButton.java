package me.ibore.widget.dialog.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;

import androidx.annotation.ColorRes;
import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;

import static me.ibore.widget.dialog.view.DialogButton.Style.CANCEL;
import static me.ibore.widget.dialog.view.DialogButton.Style.DEFAULT;
import static me.ibore.widget.dialog.view.DialogButton.Style.DESTRUCTIVE;

public class DialogButton extends AppCompatTextView {

    @Style
    private int style;
    private CharSequence text;
    private int buttonHeight;
    private int textColor;
    private int textSize;
    private int bgColor;
    private int bgColorPressed;
    private int gravity;
    private OnClickListener listener;
    private GradientDrawable drawablePressed;
    private GradientDrawable drawableNormal;
    private StateListDrawable stateListDrawable;

    public DialogButton(Builder builder) {
        super(builder.context);
        this.style = builder.style;
        this.text = builder.text;
        this.buttonHeight = builder.buttonHeight;
        this.textColor = builder.textColor;
        this.textSize = builder.textSize;
        this.bgColor = builder.bgColor;
        this.bgColorPressed = builder.bgColorPressed;
        this.gravity = builder.gravity;
        this.listener = builder.listener;
        initView();
    }

    private void initView() {
        setHeight(UIUtils.dp2px(getContext(), buttonHeight));
        setText(text);
        setTextColor(getResources().getColor(textColor));
        setTextSize(textSize);
        setClickable(true);
        setGravity(gravity);
        setStyle(style);
        setMaxLines(1);

        drawablePressed = new GradientDrawable();
        drawablePressed.setColor(getResources().getColor(bgColorPressed));

        drawableNormal = new GradientDrawable();
        drawableNormal.setColor(getResources().getColor(bgColor));

        stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, drawablePressed);
        stateListDrawable.addState(new int[]{}, drawableNormal);

        setBackground(stateListDrawable);
    }

    public OnClickListener getListener() {
        return listener;
    }

    /**
     * set last button corner radius
     * @param cornerRadius
     */
    void setCornerRadiusLast(int cornerRadius) {
        float[] corners = new float[]{
                0, 0,
                0, 0,
                UIUtils.dp2px(getContext(), cornerRadius), UIUtils.dp2px(getContext(), cornerRadius),
                0, 0};
        drawableNormal.setCornerRadii(corners);
        drawablePressed.setCornerRadii(corners);
        this.setBackground(stateListDrawable);
    }

    /**
     * set normal button corner radius
     * @param cornerRadius
     */
    void setCornerRadiusOnly(int cornerRadius) {
        float[] corners = new float[]{
                0, 0,
                0, 0,
                UIUtils.dp2px(getContext(), cornerRadius), UIUtils.dp2px(getContext(), cornerRadius),
                UIUtils.dp2px(getContext(), cornerRadius), UIUtils.dp2px(getContext(), cornerRadius)};
        drawableNormal.setCornerRadii(corners);
        drawablePressed.setCornerRadii(corners);
        this.setBackground(stateListDrawable);
    }

    /**
     * set first button corner radius
     * @param cornerRadius
     */
    void setCornerRadiusFirst(int cornerRadius) {
        float[] corners = new float[]{
                0, 0,
                0, 0,
                0, 0,
                UIUtils.dp2px(getContext(), cornerRadius), UIUtils.dp2px(getContext(), cornerRadius)};
        drawableNormal.setCornerRadii(corners);
        drawablePressed.setCornerRadii(corners);
        this.setBackground(stateListDrawable);
    }

    /**
     * set button click listener
     * @param listener
     */
    public void setClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * set button text style
     * @param style
     */
    public void setStyle(@Style int style) {
        switch (style) {
            case DEFAULT:
                setTextColor(getResources().getColor(textColor));
                setTypeface(Typeface.DEFAULT);
                break;
            case DESTRUCTIVE:
                setTextColor(getResources().getColor(android.R.color.holo_red_light));
                setTypeface(Typeface.DEFAULT);
                break;
            case CANCEL:
                setTextColor(getResources().getColor(android.R.color.darker_gray));
                setTypeface(Typeface.DEFAULT);
                break;
        }
    }



    @IntDef({DEFAULT, DESTRUCTIVE, CANCEL})
    @Retention(value = RetentionPolicy.SOURCE)
    public @interface Style {

        int DEFAULT = 1;
        int DESTRUCTIVE = 2;
        int CANCEL = 3;

    }

    public interface OnClickListener {

        void onClick();

    }

    public static class Builder {

        private Context context;
        @Style
        private int style;
        private CharSequence text;
        private int buttonHeight;
        private int textColor;
        private int textSize;
        private int bgColor;
        private int bgColorPressed;
        private int gravity;
        private OnClickListener listener;

        public Builder(Context context) {
            this.context = context;
            this.style = DEFAULT;
            this.buttonHeight = 44;
            this.bgColor = android.R.color.white;
            this.bgColorPressed = android.R.color.darker_gray;
            this.textSize = 16;
            this.textColor = R.color.comm_titlebar_text_selector;
            this.gravity = Gravity.CENTER;
            this.listener = null;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setStyle(@Style int style) {
            this.style = style;
            return this;
        }

        public Builder setText(CharSequence text) {
            this.text = text;
            return this;
        }

        public Builder setButtonHeight(int buttonHeight) {
            this.buttonHeight = buttonHeight;
            return this;
        }

        public void setTextColor(@ColorRes int textColor) {
            this.textColor = textColor;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setBgColor(@ColorRes int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Builder setBgColorPressed(@ColorRes int bgColorPressed) {
            this.bgColorPressed = bgColorPressed;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public void setListener(OnClickListener listener) {
            this.listener = listener;
        }

        public DialogButton builder() {
            return new DialogButton(this);
        }
    }

}
