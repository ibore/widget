package me.ibore.widget.dialog.view;

import android.view.View;

import androidx.annotation.ColorRes;

import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.AlertDialog;
import me.ibore.widget.listener.OnInitViewListener;
import me.ibore.widget.picker.OptionsPickerView;

public class DialogPickerBody<T> implements IDialogView {

    public static <T> DialogPickerBody<T> create() {
        return new DialogPickerBody<T>();
    }

    private boolean isLinkage;
    private List<T> data1, data2, data3;
    private List<T> linkageData1;
    private List<List<T>> linkageData2;
    private List<List<List<T>>> linkageData3;
    private int selectPosition1, selectPosition2, selectPosition3;

    @ColorRes
    private int bgColor;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    protected OptionsPickerView<T> pickerView;
    private OnInitViewListener<OptionsPickerView<T>> onInitViewListener;

    public DialogPickerBody() {
        bgColor = android.R.color.white;
        paddingLeft = 16;
        paddingTop = 16;
        paddingRight = 16;
        paddingBottom = 22;
    }


    public DialogPickerBody setBgColor(int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public DialogPickerBody setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public DialogPickerBody setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public DialogPickerBody setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public DialogPickerBody setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    /**
     * 设置不联动数据
     *
     * @return
     */
    public DialogPickerBody<T> setData(List<T> data, int selectPosition1) {
        return setData(data, selectPosition1, null, 0, null, 0);
    }

    /**
     * 设置不联动数据
     *
     * @return
     */
    public DialogPickerBody<T> setData(List<T> data1, int selectPosition1, List<T> data2, int selectPosition2) {
        return setData(data1, selectPosition1, data2, selectPosition2, null, 0);
    }

    /**
     * 设置不联动数据
     * @return
     */
    public DialogPickerBody<T> setData(List<T> data1, int selectPosition1, List<T> data2, int selectPosition2, List<T> data3, int selectPosition3) {
        isLinkage = false;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.selectPosition1 = selectPosition1;
        this.selectPosition2 = selectPosition2;
        this.selectPosition3 = selectPosition3;
        return this;
    }


    /**
     * 设置联动数据
     *
     * @return
     */
    public DialogPickerBody<T> setLinkageData(List<T> linkageData1, int selectPosition1, List<List<T>> linkageData2, int selectPosition2) {
        return setLinkageData(linkageData1, selectPosition1, linkageData2, selectPosition2);
    }

    /**
     * 设置联动数据
     *
     * @return
     */
    public DialogPickerBody<T> setLinkageData(List<T> linkageData1, int selectPosition1, List<List<T>> linkageData2, int selectPosition2,
                                              List<List<List<T>>> linkageData3, int selectPosition3) {
        isLinkage = true;
        this.linkageData1 = linkageData1;
        this.linkageData2 = linkageData2;
        this.linkageData3 = linkageData3;
        this.selectPosition1 = selectPosition1;
        this.selectPosition2 = selectPosition2;
        this.selectPosition3 = selectPosition3;
        return this;
    }

    public DialogPickerBody<T> setOnInitViewListener(OnInitViewListener<OptionsPickerView<T>> onInitViewListener) {
        this.onInitViewListener = onInitViewListener;
        return this;
    }

    public T getOpt1SelectedData() {
        return null == pickerView ? null : pickerView.getOpt1SelectedData();
    }

    public T getOpt2SelectedData() {
        return null == pickerView ? null : pickerView.getOpt2SelectedData();
    }

    public T getOpt3SelectedData() {
        return null == pickerView ? null : pickerView.getOpt3SelectedData();
    }

    public int getOpt1SelectedPosition() {
        return null == pickerView ? 0 : pickerView.getOpt1SelectedPosition();
    }

    public int getOpt2SelectedPosition() {
        return null == pickerView ? 0 : pickerView.getOpt2SelectedPosition();
    }

    public int getOpt3SelectedPosition() {
        return null == pickerView ? 0 : pickerView.getOpt3SelectedPosition();
    }

    @Override
    public View getView(AlertDialog dialog) {
        pickerView = new OptionsPickerView<>(dialog.getContext());
        pickerView.setPadding(UIUtils.dp2px(dialog.getContext(), paddingLeft), UIUtils.dp2px(dialog.getContext(), paddingTop),
                UIUtils.dp2px(dialog.getContext(), paddingRight), UIUtils.dp2px(dialog.getContext(), paddingBottom));
        pickerView.setBackgroundResource(bgColor);

        if (null != onInitViewListener) {
            onInitViewListener.initView(pickerView);
        } else {
            pickerView.setAutoFitTextSize(true);
            pickerView.setDividerHeight(1, true);
            pickerView.setDividerColorRes(R.color.widget_dialog_line);
            pickerView.setVisibleItems(5);
            pickerView.setLineSpacing(16, true);
            pickerView.setTextBoundaryMargin(16, true);
            pickerView.setShowDivider(true);
            pickerView.setTextSize(18, true);
        }
        if (isLinkage) {
            pickerView.setLinkageData(linkageData1, linkageData2, linkageData3);
        } else {
            pickerView.setData(data1, data2, data3);
        }
        pickerView.setOpt1SelectedPosition(selectPosition1);
        pickerView.setOpt2SelectedPosition(selectPosition2);
        pickerView.setOpt3SelectedPosition(selectPosition3);
        return pickerView;
    }


}
