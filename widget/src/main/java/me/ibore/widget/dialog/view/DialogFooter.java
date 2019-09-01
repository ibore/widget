package me.ibore.widget.dialog.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import me.ibore.widget.UIUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DialogFooter extends LinearLayout {

    private List<DialogButton> mBtnList;

    public DialogFooter(Context context, List<DialogButton> btnList) {
        super(context);
        this.mBtnList = btnList;
        initView();
    }

    private void initView() {
        LayoutParams lp = new LayoutParams(0, WRAP_CONTENT, 1);

        int index = 0;
        for (DialogButton btn : mBtnList) {
            ViewGroup parentViewGroup = (ViewGroup) btn.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeView(btn);
            }
            if (index > 0) {
                addDivider();
            }
            this.addView(btn, lp);
            index++;
        }
    }

    public List<DialogButton> getBtnList() {
        return mBtnList;
    }

    public void setCornerRadius(int cornerRadius) {
        if (mBtnList.size() == 1) {
            mBtnList.get(0).setCornerRadiusOnly(cornerRadius);
        } else {
            mBtnList.get(0).setCornerRadiusFirst(cornerRadius);
            mBtnList.get(mBtnList.size() - 1).setCornerRadiusLast(cornerRadius);
        }
    }

    public void addDivider() {
        View divider = new View(getContext());
        divider.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        ViewGroup.LayoutParams lp =
                new ViewGroup.LayoutParams(UIUtils.dp2px(getContext(), 1f), MATCH_PARENT);
        divider.setLayoutParams(lp);
        this.addView(divider);
    }

}
