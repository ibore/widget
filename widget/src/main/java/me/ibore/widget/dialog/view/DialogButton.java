package me.ibore.widget.dialog.view;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.AlertDialog;

public class DialogButton {

    /**
     * 肯定的按钮
     */
    public static final int POSITIVE = 1;
    /**
     * 中立的按钮
     */
    public static final int NEUTRAL = 2;
    /**
     * 否定的按钮
     */
    public static final int NEGATIVE = 3;

    public static DialogButton create() {
        return new DialogButton();
    }

    public static DialogButton create(@Style int style) {
        return new DialogButton().setStyle(style);
    }


    @IntDef({POSITIVE, NEUTRAL, NEGATIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    @Style
    private int style = POSITIVE;
    private CharSequence text;
    @ColorRes
    private int textColor = R.color.widget_dialog_neutral;
    private int textSize = 15;
    private boolean textBold;
    @ColorRes
    private int bgColor = android.R.color.transparent;
    @ColorRes
    private int bgColorPressed = android.R.color.transparent;
    private OnButtonClickListener onButtonClickListener;

    private GradientDrawable drawablePressed;
    private GradientDrawable drawableNormal;
    private StateListDrawable stateListDrawable;

    private DialogButton() {
    }

    public DialogButton setText(CharSequence text) {
        this.text = text;
        return this;
    }

    public DialogButton setTextColor(@ColorRes int textColor) {
        this.textColor = textColor;
        return this;
    }

    public DialogButton setTextBold(boolean textBold) {
        this.textBold = textBold;
        return this;
    }

    public DialogButton setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public DialogButton setBgColor(@ColorRes int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public DialogButton setBgColorPressed(int bgColorPressed) {
        this.bgColorPressed = bgColorPressed;
        return this;
    }

    public DialogButton setOnButtonClickListener(OnButtonClickListener clickListener) {
        this.onButtonClickListener = clickListener;
        return this;
    }

    public DialogButton setStyle(@Style int style) {
        this.style = style;
        if (style == POSITIVE) {
            textColor = R.color.widget_dialog_positive;
            textSize = 15;
            textBold = true;
        } else if (style == NEUTRAL) {
            textColor = R.color.widget_dialog_neutral;
            textSize = 15;
        } else if (style == NEGATIVE) {
            textColor = R.color.widget_dialog_negative;
            textSize = 15;
        }
        bgColor = R.color.widget_dialog_button_bg;
        bgColorPressed = R.color.widget_dialog_button_bg_pressed;
        return this;
    }

    public View getView(AlertDialog dialog) {
        return getView(dialog, 0, 0, 0, 0);
    }

    public View getView(final AlertDialog dialog, int topLeftRadius, int topRightRadius, int bottomRightRadius, int bottomLeftRadius) {
        int topLeft = UIUtils.dp2px(dialog.getContext(), topLeftRadius);
        int topRight = UIUtils.dp2px(dialog.getContext(), topRightRadius);
        int bottomRight = UIUtils.dp2px(dialog.getContext(), bottomRightRadius);
        int bottomLeft = UIUtils.dp2px(dialog.getContext(), bottomLeftRadius);
        AppCompatTextView btn = new AppCompatTextView(dialog.getContext());
        if (style == POSITIVE) {
            if (TextUtils.isEmpty(text)) text = dialog.getText(R.string.widget_dialog_positive);
        } else if (style == NEGATIVE) {
            if (TextUtils.isEmpty(text)) text = dialog.getText(R.string.widget_dialog_negative);
        }
        btn.setText(text);
        btn.setTextColor(dialog.getResources().getColor(textColor));
        btn.setTextSize(textSize);
        btn.setTypeface(textBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        btn.setClickable(true);
        btn.setGravity(Gravity.CENTER);
        btn.setMaxLines(1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onButtonClickListener) {
                    onButtonClickListener.buttonClick(dialog);
                }
                if (style == POSITIVE || style == NEUTRAL || style == NEGATIVE) {
                    dialog.dismiss();
                }
            }
        });
        drawablePressed = new GradientDrawable();
        drawablePressed.setColor(dialog.getResources().getColor(bgColorPressed));
        drawableNormal = new GradientDrawable();
        drawableNormal.setColor(dialog.getResources().getColor(bgColor));
        stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, drawablePressed);
        stateListDrawable.addState(new int[]{}, drawableNormal);
        float[] corners = new float[]{topLeft, topLeft, topRight, topRight,
                bottomRight, bottomRight, bottomLeft, bottomLeft};
        drawableNormal.setCornerRadii(corners);
        drawablePressed.setCornerRadii(corners);
        btn.setBackground(stateListDrawable);
        return btn;
    }

    public interface OnButtonClickListener {

        void buttonClick(AlertDialog dialog);

    }
}
