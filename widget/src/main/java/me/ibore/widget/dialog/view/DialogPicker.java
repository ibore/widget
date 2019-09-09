package me.ibore.widget.dialog.view;

import android.view.View;

import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.dialog.AlertDialog;
import me.ibore.widget.picker.OptionsPickerView;

public class DialogPicker<T> implements IDialogView {

    public static DialogPicker create() {
        return new DialogPicker<>();
    }


    private boolean isLinkage;
    private List<T> data1, data2, data3;
    private List<T> linkageData1;
    private List<List<T>> linkageData2;
    private List<List<List<T>>> linkageData3;

    private DialogPicker() {

    }

    /**
     * 设置不联动数据
     *
     * @param data 数据
     * @return
     */
    public DialogPicker<T> setData(List<T> data) {
        setData(data, null, null);
        return this;
    }

    /**
     * 设置不联动数据
     *  @param data1 数据1
     * @param data2 数据2
     * @return
     */
    public DialogPicker<T> setData(List<T> data1, List<T> data2) {
        setData(data1, data2, null);
        return this;
    }

    /**
     * 设置不联动数据
     *  @param data1 数据1
     * @param data2 数据2
     * @param data3 数据3
     * @return
     */
    public DialogPicker<T> setData(List<T> data1, List<T> data2, List<T> data3) {
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        return this;
    }


    /**
     * 设置联动数据
     *  @param linkageData1 联动数据1
     * @param linkageData2 联动数据2
     * @return
     */
    public DialogPicker<T> setLinkageData(List<T> linkageData1, List<List<T>> linkageData2) {
        this.linkageData1 = linkageData1;
        this.linkageData2 = linkageData2;
        return this;
    }

    /**
     * 设置联动数据
     * @param linkageData1 联动数据1
     * @param linkageData2 联动数据2
     * @param linkageData3 联动数据3
     * @return
     */
    public DialogPicker<T> setLinkageData(List<T> linkageData1, List<List<T>> linkageData2, List<List<List<T>>> linkageData3) {
        isLinkage = true;
        this.linkageData1 = linkageData1;
        this.linkageData2 = linkageData2;
        this.linkageData3 = linkageData3;
        return this;
    }

    @Override
    public View getView(AlertDialog dialog, int cornerRadius) {
        OptionsPickerView<T> pickerView = new OptionsPickerView<>(dialog.getContext());
        pickerView.setBackgroundResource(android.R.color.white);
        pickerView.setAutoFitTextSize(true);
        pickerView.setDividerHeight(1, true);
        pickerView.setDividerColorRes(R.color.widget_dialog_line);
        pickerView.setVisibleItems(5);
        pickerView.setLineSpacing(16, true);
        pickerView.setTextBoundaryMargin(16, true);
        pickerView.setShowDivider(true);
        pickerView.setTextSize(18, true);
        if (isLinkage) {
            pickerView.setLinkageData(linkageData1, linkageData2, linkageData3);
        } else {
            pickerView.setData(data1, data2, data3);

        }
        return pickerView;
    }
}
