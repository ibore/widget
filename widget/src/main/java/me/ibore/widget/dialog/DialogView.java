package me.ibore.widget.dialog;

import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.dialog.view.DialogBody;
import me.ibore.widget.dialog.view.DialogButton;
import me.ibore.widget.dialog.view.DialogDateBody;
import me.ibore.widget.dialog.view.DialogFooter;
import me.ibore.widget.dialog.view.DialogHeader;
import me.ibore.widget.dialog.view.DialogPickerBody;
import me.ibore.widget.dialog.view.IDialogView;
import me.ibore.widget.listener.OnSelectListener;
import me.ibore.widget.picker.OptionsPickerView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static me.ibore.widget.dialog.view.DialogButton.NEGATIVE;
import static me.ibore.widget.dialog.view.DialogButton.POSITIVE;

public final class DialogView {

    public static void showDialog(FragmentActivity activity, CharSequence content) {
        showDialog(activity, null, content);
    }

    public static void showDialog(FragmentActivity activity, CharSequence title, CharSequence content) {
        showDialog(activity, title, content, DialogButton.create(POSITIVE));
    }

    public static void showDialog(FragmentActivity activity, CharSequence content, DialogButton... buttons) {
        showDialog(activity, null, content, buttons);
    }

    public static void showDialog(FragmentActivity activity, CharSequence title, CharSequence content, DialogButton... buttons) {
        showDialog(activity, title, DialogBody.create(content), buttons);
    }

    public static void showDialog(FragmentActivity activity, CharSequence title, IDialogView dialogBody) {
        showDialog(activity, TextUtils.isEmpty(title) ? null : DialogHeader.create(title)
                .setPaddingTop(16).setPaddingBottom(8), dialogBody, null);
    }

    public static void showDialog(FragmentActivity activity, CharSequence title, IDialogView dialogBody, DialogButton... buttons) {
        showDialog(activity, TextUtils.isEmpty(title) ? null : DialogHeader.create(title)
                .setPaddingTop(16).setPaddingBottom(8), dialogBody, DialogFooter.create(buttons));
    }

    public static void showDialog(FragmentActivity activity, IDialogView dialogBody) {
        showDialog(activity, (IDialogView) null, dialogBody, null);
    }

    public static void showDialog(FragmentActivity activity, IDialogView dialogHeader, IDialogView dialogBody, IDialogView dialogFooter) {
        showInnerDialog(activity, dialogHeader, dialogBody, dialogFooter, false);
    }

    public static void showBottomDialog(FragmentActivity activity, IDialogView dialogHeader, IDialogView dialogBody) {
        showBottomDialog(activity, dialogHeader, dialogBody, null);
    }

    public static void showBottomDialog(FragmentActivity activity, IDialogView dialogHeader, IDialogView dialogBody, IDialogView dialogFooter) {
        showInnerDialog(activity, dialogHeader, dialogBody, dialogFooter, true);
    }

    public static void showYDialog(FragmentActivity activity, final String selectDate, String startDate, String endDate, OnSelectListener<String> onSelectListener) {
        showDateDialog(activity, selectDate, startDate, endDate, DialogDateBody.YYYY, onSelectListener);
    }

    public static void showYMDialog(FragmentActivity activity, final String selectDate, String startDate, String endDate, OnSelectListener<String> onSelectListener) {
        showDateDialog(activity, selectDate, startDate, endDate, DialogDateBody.YYYY_MM, onSelectListener);
    }

    public static void showYMDDialog(FragmentActivity activity, final String selectDate, String startDate, String endDate, OnSelectListener<String> onSelectListener) {
        showDateDialog(activity, selectDate, startDate, endDate, DialogDateBody.YYYY_MM_DD, onSelectListener);
    }

    public static void showDateDialog(FragmentActivity activity, final String selectDate, String startDate, String endDate, @DialogDateBody.Mode int mode, final OnSelectListener<String> onSelectListener) {
        DialogHeader dialogHeader = getCancelConfirmHeader(activity.getText(R.string.widget_dialog_date_title), new DialogButton.OnButtonClickListener() {
            @Override
            public void buttonClick(AlertDialog dialog) {
                if (null != onSelectListener)
                    onSelectListener.select(((DialogDateBody) dialog.getDialogBody()).getSelectDate());
            }
        });
        DialogDateBody dialogDateBody = DialogDateBody.create()
                .setMode(mode)
                .setSelectDate(selectDate)
                .setStartDate(startDate)
                .setEndDate(endDate);
        showBottomDialog(activity, dialogHeader, dialogDateBody);
    }

    public static <T> void showPickDialog(FragmentActivity activity, CharSequence title, List<T> data1, int selectPosition1,
                                          final OptionsPickerView.OnOptionsSelectedListener<T> onOptionsSelectedListener) {
        showPickDialog(activity, title, data1, selectPosition1, null, 0, onOptionsSelectedListener);
    }

    public static <T> void showPickDialog(FragmentActivity activity, CharSequence title, List<T> data1, int selectPosition1,
                                          List<T> data2, int selectPosition2,
                                          final OptionsPickerView.OnOptionsSelectedListener<T> onOptionsSelectedListener) {
        showPickDialog(activity, title, data1, selectPosition1, data2, selectPosition2, null, 0, onOptionsSelectedListener);
    }

    public static <T> void showPickDialog(FragmentActivity activity, CharSequence title, List<T> data1, int selectPosition1,
                                          List<T> data2, int selectPosition2, List<T> data3, int selectPosition3,
                                          final OptionsPickerView.OnOptionsSelectedListener<T> onOptionsSelectedListener) {
        final DialogPickerBody<T> dialogPickerBody = DialogPickerBody.<T>create()
                .setData(data1, selectPosition1, data2, selectPosition2, data3, selectPosition3);
        DialogHeader dialogHeader = getCancelConfirmHeader(title, new DialogButton.OnButtonClickListener() {
            @Override
            public void buttonClick(AlertDialog dialog) {
                if (null != onOptionsSelectedListener) {
                    onOptionsSelectedListener.onOptionsSelected(
                            dialogPickerBody.getOpt1SelectedPosition(), dialogPickerBody.getOpt1SelectedData(),
                            dialogPickerBody.getOpt2SelectedPosition(), dialogPickerBody.getOpt2SelectedData(),
                            dialogPickerBody.getOpt3SelectedPosition(), dialogPickerBody.getOpt3SelectedData());
                }
            }
        });
        showBottomDialog(activity, dialogHeader, dialogPickerBody);
    }

    public static <T> void showLinkagePickDialog(FragmentActivity activity, CharSequence title, List<T> linkageData1, int selectPosition1,
                                                 List<List<T>> linkageData2, int selectPosition2,
                                                 final OptionsPickerView.OnOptionsSelectedListener<T> onOptionsSelectedListener) {
        showLinkagePickDialog(activity, title, linkageData1, selectPosition1, linkageData2, selectPosition2, null, 0, onOptionsSelectedListener);
    }

    public static <T> void showLinkagePickDialog(FragmentActivity activity, CharSequence title,
                                                 List<T> linkageData1, int selectPosition1,
                                                 List<List<T>> linkageData2, int selectPosition2,
                                                 List<List<List<T>>> linkageData3, int selectPosition3,
                                                 final OptionsPickerView.OnOptionsSelectedListener<T> onOptionsSelectedListener) {

        final DialogPickerBody<T> dialogPickerBody = DialogPickerBody.<T>create().setLinkageData(linkageData1, selectPosition1,
                linkageData2, selectPosition2, linkageData3, selectPosition3);
        DialogHeader dialogHeader = getCancelConfirmHeader(title, new DialogButton.OnButtonClickListener() {
            @Override
            public void buttonClick(AlertDialog dialog) {
                if (null != onOptionsSelectedListener) {
                    onOptionsSelectedListener.onOptionsSelected(
                            dialogPickerBody.getOpt1SelectedPosition(), dialogPickerBody.getOpt1SelectedData(),
                            dialogPickerBody.getOpt2SelectedPosition(), dialogPickerBody.getOpt2SelectedData(),
                            dialogPickerBody.getOpt3SelectedPosition(), dialogPickerBody.getOpt3SelectedData());
                }
            }
        });
        showBottomDialog(activity, dialogHeader, dialogPickerBody);
    }


    private static void showInnerDialog(FragmentActivity activity, IDialogView dialogHeader, IDialogView dialogBody, IDialogView dialogFooter, boolean isBottom) {
        AlertDialog.create(activity)
                .setHeader(dialogHeader)
                .setBody(dialogBody)
                .setFooter(dialogFooter)
                .setCancelOnTouchBack(false)
                .setTouchOutsideCancelable(false)
                .setDialogWidth(isBottom ? MATCH_PARENT : 260)
                .show();
    }

    public static DialogHeader getCancelConfirmHeader(CharSequence title, DialogButton.OnButtonClickListener onButtonClickListener) {
        return DialogHeader.create(title)
                .setLeftButton(DialogButton.create(NEGATIVE)
                        .setBgColor(android.R.color.transparent)
                        .setBgColorPressed(android.R.color.transparent))
                .setRightButton(DialogButton.create(POSITIVE)
                        .setBgColor(android.R.color.transparent)
                        .setBgColorPressed(android.R.color.transparent)
                        .setOnButtonClickListener(onButtonClickListener));
    }

}
