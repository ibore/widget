package me.ibore.widget.dialog;

import androidx.fragment.app.FragmentActivity;

import me.ibore.widget.R;
import me.ibore.widget.dialog.view.DialogBody;
import me.ibore.widget.dialog.view.DialogButton;
import me.ibore.widget.dialog.view.DialogHeader;

public class DialogUtils {

    public static void showDialog(FragmentActivity activity, String content) {
        DialogButton btn = new DialogButton.Builder(activity).builder();
        btn.setText("确认");

        GiantDialog.Builder.getInstance(activity)
                .setBody(new DialogBody.Builder(activity).setContent(content).builder())
                .addButton(btn)
                .setCancelOnTouchBack(false)
                .setTouchOutsideCancelable(false)
                .show();
    }
    public static void showDialog(FragmentActivity activity, String title, String content) {
        DialogButton btn = new DialogButton.Builder(activity).builder();
        btn.setText("确认");

        GiantDialog.Builder.getInstance(activity)
                .setHeader(new DialogHeader.Builder(activity).setTitle(title).builder())
                .setBody(new DialogBody.Builder(activity).setContent(content).builder().addBottomDivider())
                .addButton(btn)
                .setCancelOnTouchBack(false)
                .setTouchOutsideCancelable(false)
                .show();
    }

}
