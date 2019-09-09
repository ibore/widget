package me.ibore.widget.dialog;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.view.DialogBody;
import me.ibore.widget.dialog.view.IDialogView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class AlertDialog extends DialogFragment {

    public static AlertDialog create(FragmentActivity activity) {
        return new AlertDialog(activity);
    }

    private FragmentActivity activity;
    private IDialogView dialogHeader;
    private IDialogView dialogBody;
    private IDialogView dialogFooter;
    private int cornerRadius;
    private int dialogWidth;
    private boolean touchOutsideCancelable;
    private boolean touchBackCancelable;
    private boolean hasShade;
    private String dialogTag;

    private AlertDialog(FragmentActivity activity) {
        this.activity = activity;
        this.dialogWidth = 260;
        this.cornerRadius = 10;
        dialogTag = "AlertDialog";
        touchOutsideCancelable = true;
        touchBackCancelable = true;
        hasShade = true;
        dialogBody = DialogBody.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Widget_AlertDialog);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);
        // CornerRadius
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(UIUtils.dp2px(getContext(), cornerRadius));
        rootView.setBackground(drawable);
        if (!hasShade) {
            getDialog().getWindow().setDimAmount(0f);
        }
        getDialog().setCanceledOnTouchOutside(touchOutsideCancelable);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (touchBackCancelable) {
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });
        int index = 0;
        if (dialogHeader != null) {
            rootView.addView(dialogHeader.getView(this, cornerRadius), index++);
        }
        rootView.addView(dialogBody.getView(this, null == dialogHeader ? cornerRadius : 0), index++);
        if (dialogFooter != null) {
            rootView.addView(dialogFooter.getView(this, cornerRadius), index);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dialogWidth == MATCH_PARENT) {
            getDialog().getWindow().setGravity(Gravity.BOTTOM);
            getDialog().getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);
        } else if (dialogWidth == WRAP_CONTENT) {
            getDialog().getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
        } else {
            getDialog().getWindow().setLayout(UIUtils.dp2px(getContext(), dialogWidth), WRAP_CONTENT);
        }
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public AlertDialog setHeader(IDialogView dialogHeader) {
        this.dialogHeader = dialogHeader;
        return this;
    }

    public AlertDialog setBody(IDialogView dialogBody) {
        this.dialogBody = dialogBody;
        return this;
    }

    public AlertDialog setFooter(IDialogView dialogFooter) {
        this.dialogFooter = dialogFooter;
        return this;
    }

    public AlertDialog setDialogTag(String tag) {
        if (tag != null) {
            this.dialogTag = tag;
        }
        return this;
    }

    public AlertDialog setCornerRadius(int radius) {
        this.cornerRadius = radius;
        return this;
    }

    public AlertDialog setTouchOutsideCancelable(boolean cancelable) {
        this.touchOutsideCancelable = cancelable;
        return this;
    }

    public AlertDialog setCancelOnTouchBack(boolean cancelable) {
        this.touchBackCancelable = cancelable;
        return this;
    }

    public AlertDialog setHasShade(boolean hasShade) {
        this.hasShade = hasShade;
        return this;
    }

    public AlertDialog setDialogWidth(int width) {
        this.dialogWidth = width;
        return this;
    }

    public void show() {
        show(activity.getSupportFragmentManager(), dialogTag);
    }

    public IDialogView getDialogHeader() {
        return dialogHeader;
    }

    public IDialogView getDialogBody() {
        return dialogBody;
    }

    public IDialogView getDialogFooter() {
        return dialogFooter;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public int getDialogWidth() {
        return dialogWidth;
    }

    public boolean isTouchOutsideCancelable() {
        return touchOutsideCancelable;
    }

    public boolean isTouchBackCancelable() {
        return touchBackCancelable;
    }

    public boolean isHasShade() {
        return hasShade;
    }

    public String getDialogTag() {
        return dialogTag;
    }
}
