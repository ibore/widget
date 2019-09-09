package me.ibore.widget.dialog;

import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.dialog.view.DialogBody;
import me.ibore.widget.dialog.view.DialogButton;
import me.ibore.widget.dialog.view.DialogDateView;
import me.ibore.widget.dialog.view.DialogFooter;
import me.ibore.widget.dialog.view.DialogHeader;
import me.ibore.widget.dialog.view.DialogPicker;
import me.ibore.widget.listener.OnSelectListener;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static me.ibore.widget.dialog.view.DialogButton.NEGATIVE;
import static me.ibore.widget.dialog.view.DialogButton.POSITIVE;

public final class DialogView {

    public static void showDialog(FragmentActivity activity, String content) {
        showDialog(activity, null, content);
    }

    public static void showDialog(FragmentActivity activity, String title, String content) {
        showDialog(activity, title, content, DialogButton.create(POSITIVE));
    }

    public static void showDialog(FragmentActivity activity, String content, DialogButton... buttons) {
        showDialog(activity, null, content, buttons);
    }

    public static void showDialog(FragmentActivity activity, String title, String content, DialogButton... buttons) {
        AlertDialog.create(activity)
                .setHeader(TextUtils.isEmpty(title) ? null : DialogHeader.create(title))
                .setBody(DialogBody.create(content))
                .setFooter(DialogFooter.create(buttons))
                .setCancelOnTouchBack(false)
                .setTouchOutsideCancelable(false)
                .show();
    }

    public static void showYDialog(FragmentActivity activity, final String selectDate, String startDate, String endDate, OnSelectListener<String> onSelectListener) {
        showDateDialog(activity, selectDate, startDate, endDate, DialogDateView.YYYY, onSelectListener);
    }

    public static void showYMDialog(FragmentActivity activity, final String selectDate, String startDate, String endDate, OnSelectListener<String> onSelectListener) {
        showDateDialog(activity, selectDate, startDate, endDate, DialogDateView.YYYY_MM, onSelectListener);
    }

    public static void showYMDDialog(FragmentActivity activity, final String selectDate, String startDate, String endDate, OnSelectListener<String> onSelectListener) {
        showDateDialog(activity, selectDate, startDate, endDate, DialogDateView.YYYY_MM_DD, onSelectListener);
    }

    public static void showDateDialog(FragmentActivity activity, final String selectDate, String startDate, String endDate, @DialogDateView.Mode int mode, final OnSelectListener<String> onSelectListener) {
        AlertDialog.create(activity)
                .setHeader(DialogHeader.create(activity.getText(R.string.widget_dialog_date_title))
                        .setLeftButton(DialogButton.create(NEGATIVE)
                                .setBgColor(android.R.color.transparent)
                                .setBgColorPressed(android.R.color.transparent))
                        .setRightButton(DialogButton.create(POSITIVE)
                                .setBgColor(android.R.color.transparent)
                                .setBgColorPressed(android.R.color.transparent)
                                .setOnButtonClickListener(new DialogButton.OnButtonClickListener() {
                                    @Override
                                    public void buttonClick(AlertDialog dialog) {
                                        if (null != onSelectListener)
                                            onSelectListener.select(((DialogDateView) dialog.getDialogBody()).getSelectDate());
                                    }
                                })))
                .setBody(DialogDateView.create()
                        .setMode(mode)
                        .setSelectDate(selectDate)
                        .setStartDate(startDate)
                        .setEndDate(endDate))
                .setCancelOnTouchBack(false)
                .setTouchOutsideCancelable(false)
                .setDialogWidth(MATCH_PARENT)
                .show();
    }

    public static void showPickDialog(FragmentActivity activity, CharSequence title, List<?> data1, List<?> data2, List<?> data3) {
        AlertDialog.create(activity)
                .setHeader(DialogHeader.create(title)
                        .setLeftButton(DialogButton.create(NEGATIVE)
                                .setBgColor(android.R.color.transparent)
                                .setBgColorPressed(android.R.color.transparent))
                        .setRightButton(DialogButton.create(POSITIVE)
                                .setBgColor(android.R.color.transparent)
                                .setBgColorPressed(android.R.color.transparent)
                                .setOnButtonClickListener(new DialogButton.OnButtonClickListener() {
                                    @Override
                                    public void buttonClick(AlertDialog dialog) {

                                    }
                                })))
                .setBody(DialogPicker.create().setData(data1, data2, data3))
                .setCancelOnTouchBack(false)
                .setTouchOutsideCancelable(false)
                .setDialogWidth(MATCH_PARENT)
                .show();
    }

    public static void showLinkagePickDialog(FragmentActivity activity, CharSequence title, List<?> linkageData1, List<List<?>> linkageData2, List<List<List<?>>> linkageData3) {
        AlertDialog.create(activity)
                .setHeader(DialogHeader.create(title)
                        .setLeftButton(DialogButton.create(NEGATIVE)
                                .setBgColor(android.R.color.transparent)
                                .setBgColorPressed(android.R.color.transparent))
                        .setRightButton(DialogButton.create(POSITIVE)
                                .setBgColor(android.R.color.transparent)
                                .setBgColorPressed(android.R.color.transparent)
                                .setOnButtonClickListener(new DialogButton.OnButtonClickListener() {
                                    @Override
                                    public void buttonClick(AlertDialog dialog) {

                                    }
                                })))
                .setBody(DialogPicker.create().setLinkageData(linkageData1, linkageData2, linkageData3))
                .setCancelOnTouchBack(false)
                .setTouchOutsideCancelable(false)
                .setDialogWidth(MATCH_PARENT)
                .show();
    }

    public static void showRegionDialog(FragmentActivity activity) {

    }
}
