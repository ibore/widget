package me.ibore.widget.dialog.view;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorRes;

import java.util.Arrays;
import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.AlertDialog;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class DialogFooter implements IDialogView {


    public static DialogFooter create(DialogButton... buttons) {
        return new DialogFooter(Arrays.asList(buttons));
    }

    public static DialogFooter create(int height, DialogButton... buttons) {
        return new DialogFooter(Arrays.asList(buttons)).setHeight(height);
    }

    public static DialogFooter create(List<DialogButton> btnList) {
        return new DialogFooter(btnList);
    }

    public static DialogFooter create(int height, List<DialogButton> btnList) {
        return new DialogFooter(btnList).setHeight(height);
    }

    private DialogFooter(List<DialogButton> btnList) {
        this.mBtnList = btnList;
        this.height = 44;
        this.bgColor = android.R.color.white;
    }

    private List<DialogButton> mBtnList;
    private int height;

    @ColorRes
    private int bgColor;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    public DialogFooter setHeight(int height) {
        this.height = height;
        return this;
    }

    public DialogFooter setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public DialogFooter setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public DialogFooter setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public DialogFooter setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public DialogFooter setBgColor(@ColorRes int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public List<DialogButton> getBtnList() {
        return mBtnList;
    }

    @Override
    public View getView(final AlertDialog dialog) {
        int radius = UIUtils.dp2px(dialog.getContext(), dialog.getCornerRadius());
        LinearLayout footerView = new LinearLayout(dialog.getContext());
        footerView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, UIUtils.dp2px(dialog.getContext(), height)));
        footerView.setPadding(UIUtils.dp2px(dialog.getContext(), paddingLeft),
                UIUtils.dp2px(dialog.getContext(), paddingTop),
                UIUtils.dp2px(dialog.getContext(), paddingRight),
                UIUtils.dp2px(dialog.getContext(), paddingBottom));
        if (dialog.getDialogWidth() == MATCH_PARENT) {
            footerView.setBackgroundResource(bgColor);
        } else {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, radius, radius});
            drawable.setColor(footerView.getResources().getColor(bgColor));
            footerView.setBackground(drawable);
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, MATCH_PARENT, 1);
        for (int i = 0; i < mBtnList.size(); i++) {
            if (i > 0) {
                View divider = new View(footerView.getContext());
                divider.setBackgroundColor(footerView.getResources().getColor(R.color.widget_dialog_line));
                divider.setLayoutParams(new ViewGroup.LayoutParams(2, MATCH_PARENT));
                footerView.addView(divider);
            }

            View btn;
            if (paddingLeft > 0 || paddingTop > 0 || paddingRight > 0 || paddingBottom > 0) {
                btn = mBtnList.get(i).getView(dialog, radius, radius, radius, radius);
            } else if (dialog.getDialogWidth() == MATCH_PARENT) {
                btn = mBtnList.get(i).getView(dialog, 0, 0, 0, 0);
            } else {
                btn = mBtnList.get(i).getView(dialog, 0, 0, i == 0 ? radius : 0, i == mBtnList.size() - 1 ? radius : 0);
            }
            footerView.addView(btn, lp);
        }
        return footerView;
    }
}
