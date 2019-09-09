package me.ibore.widget.dialog.view;

import android.view.View;

import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.dialog.AlertDialog;
import me.ibore.widget.picker.OptionsPickerView;

public class DialogPicker<T> implements IDialogView {

    public static DialogPicker create() {
        return new DialogPicker();
    }

    private List<T> data1, data2, data3;
    private DialogPicker() {

    }

    /**
     * 设置不联动数据
     *
     * @param data 数据
     */
    public void setData(List<T> data) {
        setData(data, null, null);
    }



    @Override
    public View getView(AlertDialog dialog, int cornerRadius) {
        OptionsPickerView<T> pickerView = new OptionsPickerView<>(dialog.getContext());
        pickerView.setBackgroundResource(android.R.color.white);
        pickerView.setData();
        return pickerView;
    }
}
