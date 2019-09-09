package me.ibore.widget.dialog.view;

import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.AlertDialog;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class DialogBody implements IDialogView {

    private int gravity;
    private int layoutGravity;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    private int bgColor;

    // Content
    private CharSequence content;
    private int contentColor;
    private int contentSize;
    private FrameLayout bodyView;

    public static DialogBody create() {
        return new DialogBody();
    }

    public static DialogBody create(String content) {
        return new DialogBody().setContent(content);
    }

    private DialogBody() {
        this.gravity = Gravity.CENTER;
        this.layoutGravity = Gravity.CENTER;
        this.paddingTop = 16;
        this.paddingBottom = 16;
        this.paddingLeft = this.paddingRight = 26;
        this.bgColor = android.R.color.white;
        this.content = "";
        this.contentColor = R.color.widget_dialog_content;
        this.contentSize = 14;
    }

    public DialogBody setContent(CharSequence content) {
        this.content = content;
        return this;
    }

    public DialogBody setContentColor(int contentColor) {
        this.contentColor = contentColor;
        return this;
    }

    public DialogBody setContentSize(int contentSize) {
        this.contentSize = contentSize;
        return this;
    }

    public DialogBody setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public DialogBody setLayoutGravity(int layoutGravity) {
        this.layoutGravity = layoutGravity;
        return this;
    }

    public DialogBody setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public DialogBody setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public DialogBody setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public DialogBody setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public DialogBody setBgColor(int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public void addBottomDivider() {
        View divider = new View(bodyView.getContext());
        divider.setBackgroundColor(bodyView.getResources().getColor(R.color.widget_dialog_line));
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(MATCH_PARENT, 2);
        lp.gravity = Gravity.BOTTOM;
        divider.setLayoutParams(lp);
        bodyView.addView(divider);
    }

    @Override
    public View getView(AlertDialog dialog, int cornerRadius) {
        int radius = UIUtils.dp2px(dialog.getContext(), cornerRadius);

        bodyView = new FrameLayout(dialog.getContext());

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        drawable.setColor(bodyView.getResources().getColor(bgColor));
        bodyView.setBackground(drawable);

        TextView tv = new TextView(dialog.getContext());
        tv.setText(content);
        tv.setTextSize(contentSize);
        tv.setTextColor(dialog.getContext().getResources().getColor(contentColor));
        tv.setGravity(gravity);

        // LayoutGravity
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = layoutGravity;
        tv.setLayoutParams(lp);

        // Padding
        tv.setPadding(UIUtils.dp2px(dialog.getContext(), paddingLeft),
                UIUtils.dp2px(dialog.getContext(), paddingTop),
                UIUtils.dp2px(dialog.getContext(), paddingRight),
                UIUtils.dp2px(dialog.getContext(),paddingBottom));

        bodyView.addView(tv);
        addBottomDivider();
        return bodyView;
    }
}
