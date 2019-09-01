package me.ibore.widget.dialog;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.view.DialogBody;
import me.ibore.widget.dialog.view.DialogButton;
import me.ibore.widget.dialog.view.DialogFooter;
import me.ibore.widget.dialog.view.DialogHeader;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public final class GiantDialog extends DialogFragment {

    public static GiantDialog newInstance() {
        return new GiantDialog();
    }

    private DialogHeader mHeaderView;
    private DialogBody mBodyView;
    private DialogFooter mFooterView;
    private int mCornerRadius;
    private int mDialogWidth;
    private boolean mCanceledOnTouchOutside;
    private boolean mCancelOnTouchBack;
    private boolean mHasShade;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.base_dialog);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);

        // CornerRadius
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(UIUtils.dp2px(getContext(), mCornerRadius));
        rootView.setBackground(drawable);

        if (!mHasShade) {
            getDialog().getWindow().setDimAmount(0f);
        }

        // Touch Cancelable
        getDialog().setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mCancelOnTouchBack) {
                        return false;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });

        int index = 0;
        if (mHeaderView != null) {
            // Add Header
            rootView.addView(mHeaderView, index++);
            mHeaderView.setCornerRadius(mCornerRadius);
        } else {
            mBodyView.setCornerRadius(mCornerRadius);
        }

        // Add Body
        rootView.addView(mBodyView, index++);

        // Add footer
        if (mFooterView != null) {
            List<DialogButton> btnList = mFooterView.getBtnList();
            for (final DialogButton btn : btnList) {
                if (!btn.hasOnClickListeners()) {
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (btn.getListener() != null) {
                                btn.getListener().onClick();
                            }
                            GiantDialog.this.dismiss();
                        }
                    });
                }
            }
            mFooterView.setCornerRadius(mCornerRadius);
            rootView.addView(mFooterView, index);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(UIUtils.dp2px(getContext(), mDialogWidth), WRAP_CONTENT);
    }

    public GiantDialog setHeaderView(DialogHeader headerView) {
        this.mHeaderView = headerView;
        return this;
    }

    public GiantDialog setBodyView(DialogBody bodyView) {
        this.mBodyView = bodyView;
        return this;
    }

    public GiantDialog setFooterView(DialogFooter footerView) {
        this.mFooterView = footerView;
        return this;
    }

    public GiantDialog setDialogWidth(int width) {
        this.mDialogWidth = width;
        return this;
    }

    public GiantDialog setCornerRadius(int cornerRadius) {
        this.mCornerRadius = cornerRadius;
        return this;
    }

    public GiantDialog setCanceledOnTouchOutside(boolean cancelable) {
        this.mCanceledOnTouchOutside = cancelable;
        return this;
    }

    public GiantDialog setCancelOnTouchBack(boolean cancelable) {
        this.mCancelOnTouchBack = cancelable;
        return this;
    }

    public GiantDialog setHasShade(boolean hasShade) {
        this.mHasShade = hasShade;
        return this;
    }

    public static class Builder {

        private FragmentActivity mActivity;

        private DialogHeader mDialogHeader;
        private DialogBody mDialogBody;
        private List<DialogButton> mFooterBtnList;

        private int mCornerRadius;
        private int mDialogWidth;
        private boolean mTouchOutsideCancelable;
        private boolean mTouchBackCancelable;
        private boolean mHasShade;

        private String mDialogTag;

        public static Builder getInstance(FragmentActivity activity) {
            return create(activity);
        }

        public static Builder create(FragmentActivity activity) {
            return new Builder(activity);
        }

        private Builder(FragmentActivity activity) {
            mActivity = activity;
            mDialogWidth = 260;
            mCornerRadius = 10;
            mDialogTag = "giant_dialog";
            mTouchOutsideCancelable = true;
            mTouchBackCancelable = true;
            mHasShade = true;

            mFooterBtnList = new ArrayList<>();
        }

        public Builder setHeader(DialogHeader builder) {
            this.mDialogHeader = builder;
            return this;
        }

        public Builder setBody(DialogBody builder) {
            this.mDialogBody = builder;
            return this;
        }

        public Builder addButton(DialogButton btn) {
            this.mFooterBtnList.add(btn);
            return this;
        }

        public Builder setDialogTag(String tag) {
            if (tag != null) {
                this.mDialogTag = tag;
            }
            return this;
        }

        public Builder setCornerRadius(int radius) {
            this.mCornerRadius = radius;
            return this;
        }

        public Builder setTouchOutsideCancelable(boolean cancelable) {
            this.mTouchOutsideCancelable = cancelable;
            return this;
        }

        public Builder setCancelOnTouchBack(boolean cancelable) {
            this.mTouchBackCancelable = cancelable;
            return this;
        }

        public Builder setHasShade(boolean hasShade) {
            this.mHasShade = hasShade;
            return this;
        }

        public Builder setDialogWidth(int width) {
            this.mDialogWidth = width;
            return this;
        }

        public void show() {
            DialogFooter dialogFooter = new DialogFooter(mActivity, mFooterBtnList);
            GiantDialog.newInstance()
                    .setHeaderView(mDialogHeader)
                    .setBodyView(mDialogBody)
                    .setFooterView(dialogFooter)
                    .setCornerRadius(mCornerRadius)
                    .setCanceledOnTouchOutside(mTouchOutsideCancelable)
                    .setCancelOnTouchBack(mTouchBackCancelable)
                    .setHasShade(mHasShade)
                    .setDialogWidth(mDialogWidth)
                    .show(mActivity.getSupportFragmentManager(), mDialogTag);
        }
    }
}
