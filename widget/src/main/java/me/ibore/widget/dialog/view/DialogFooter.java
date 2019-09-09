package me.ibore.widget.dialog.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.AlertDialog;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class DialogFooter implements IDialogView {

    private List<DialogButton> mBtnList;
    private int height;

    public static DialogFooter create(DialogButton... buttons) {
        return new DialogFooter(44, Arrays.asList(buttons));
    }

    public static DialogFooter create(int height, DialogButton... buttons) {
        return new DialogFooter(height, Arrays.asList(buttons));
    }

    public static DialogFooter create(List<DialogButton> btnList) {
        return new DialogFooter(44, btnList);
    }

    public static DialogFooter create(int height, List<DialogButton> btnList) {
        return new DialogFooter(height, btnList);
    }

    private DialogFooter(int height, List<DialogButton> btnList) {
        this.height = height;
        this.mBtnList = btnList;
    }

    public List<DialogButton> getBtnList() {
        return mBtnList;
    }

    @Override
    public View getView(final AlertDialog dialog, int cornerRadius) {
        LinearLayout footerView = new LinearLayout(dialog.getContext());
        footerView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, UIUtils.dp2px(dialog.getContext(), height)));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, MATCH_PARENT, 1);
        for (int i = 0; i < mBtnList.size(); i++) {
            if (i > 0) {
                View divider = new View(footerView.getContext());
                divider.setBackgroundColor(footerView.getResources().getColor(R.color.widget_dialog_line));
                divider.setLayoutParams(new ViewGroup.LayoutParams(2, MATCH_PARENT));
                footerView.addView(divider);
            }
            int bottomLeftRadius = 0;
            int bottomRightRadius = 0;
            if (i == 0) bottomLeftRadius = cornerRadius;
            if (i == mBtnList.size() - 1) bottomRightRadius = cornerRadius;
            View btn = mBtnList.get(i).getView(dialog, 0, 0, bottomRightRadius, bottomLeftRadius);
            footerView.addView(btn, lp);
        }
        return footerView;
    }
}
