package me.ibore.widget.dialog.view;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.AlertDialog;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DialogHeader implements IDialogView {

    private DialogButton leftButton;
    private DialogButton rightButton;

    private CharSequence titleText;
    @ColorRes
    private int titleColor;
    private int titleSize;
    @ColorRes
    private int bgColor;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    public static DialogHeader create() {
        return new DialogHeader();
    }

    public static DialogHeader create(CharSequence titleText) {
        return new DialogHeader().setTitleText(titleText);
    }

    private DialogHeader() {
        titleColor = R.color.widget_dialog_title;
        titleSize = 16;
        bgColor = android.R.color.white;
        paddingTop = 12;
        paddingBottom = 12;
        paddingLeft = 16;
        paddingRight = 16;
    }

    public DialogHeader setTitleText(CharSequence titleText) {
        this.titleText = titleText;
        return this;
    }

    public DialogHeader setTitleColor(@ColorRes int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public DialogHeader setTitleSize(int titleTextSize) {
        this.titleSize = titleTextSize;
        return this;
    }

    public DialogHeader setLeftButton(DialogButton leftButton) {
        this.leftButton = leftButton;
        return this;
    }

    public DialogHeader setRightButton(DialogButton rightButton) {
        this.rightButton = rightButton;
        return this;
    }

    public DialogHeader setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public DialogHeader setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public DialogHeader setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public DialogHeader setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public DialogHeader setBgColor(@ColorRes int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    @Override
    public View getView(AlertDialog dialog) {
        int radius = UIUtils.dp2px(dialog.getContext(), dialog.getCornerRadius());
        final int iPaddingLeft = UIUtils.dp2px(dialog.getContext(), paddingLeft);
        final int iPaddingTop = UIUtils.dp2px(dialog.getContext(), paddingTop);
        final int iPaddingRight = UIUtils.dp2px(dialog.getContext(), paddingRight);
        final int iPaddingBottom = UIUtils.dp2px(dialog.getContext(), paddingBottom);

        final RelativeLayout headerView = new RelativeLayout(dialog.getContext());
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        drawable.setColor(headerView.getResources().getColor(bgColor));
        headerView.setBackground(drawable);
        final View leftView;
        final AppCompatTextView titleView;
        final View rightView;

        if (null != leftButton) {
            leftView = leftButton.getView(dialog);
            leftView.setPadding(iPaddingLeft / 2, iPaddingTop / 2, iPaddingRight / 2, iPaddingBottom / 2);
            RelativeLayout.LayoutParams leftLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            leftLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            leftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            leftLayoutParams.setMarginStart(iPaddingLeft / 2);
            headerView.addView(leftView, leftLayoutParams);
        } else {
            leftView = null;
        }

        titleView = new AppCompatTextView(dialog.getContext());
        titleView.setText(titleText);
        titleView.setTextSize(titleSize);
        titleView.setTextColor(ContextCompat.getColor(dialog.getContext(), titleColor));
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        titleView.setGravity(Gravity.CENTER);
        headerView.addView(titleView);

        if (null != rightButton) {
            rightView = rightButton.getView(dialog);
            rightView.setPadding(iPaddingLeft / 2, iPaddingTop / 2, iPaddingRight / 2, iPaddingBottom / 2);
            RelativeLayout.LayoutParams rightLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            rightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            rightLayoutParams.setMarginEnd(iPaddingRight / 2);
            headerView.addView(rightView, rightLayoutParams);
        } else {
            rightView = null;
        }

        if (null != leftView || null != rightView) {
            headerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int margin = 0;
                    if (null != leftView && null != rightView) {
                        margin = Math.max(leftView.getWidth(), rightView.getWidth());
                    } else if (null != leftView) {
                        margin = leftView.getWidth();
                    } else {
                        margin = rightView.getWidth();
                    }

                    RelativeLayout.LayoutParams titleLayoutParams = (RelativeLayout.LayoutParams) titleView.getLayoutParams();
                    titleLayoutParams.width = MATCH_PARENT;
                    titleLayoutParams.setMargins(margin + iPaddingLeft / 2, iPaddingTop, margin + iPaddingRight / 2, iPaddingBottom);
                    titleView.setLayoutParams(titleLayoutParams);
                    headerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            RelativeLayout.LayoutParams titleLayoutParams = (RelativeLayout.LayoutParams) titleView.getLayoutParams();
            titleLayoutParams.width = MATCH_PARENT;
            titleLayoutParams.setMargins(iPaddingLeft, iPaddingTop, iPaddingRight, iPaddingBottom);
            titleView.setLayoutParams(titleLayoutParams);
        }
        return headerView;
    }

}
