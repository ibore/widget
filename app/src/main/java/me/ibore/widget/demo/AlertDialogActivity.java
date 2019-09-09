package me.ibore.widget.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.ibore.widget.dialog.AlertDialog;
import me.ibore.widget.dialog.DialogView;
import me.ibore.widget.dialog.view.DialogButton;
import me.ibore.widget.listener.OnSelectListener;

public class AlertDialogActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);
    }

    public void onTipDialogClick(View view) {
        DialogView.showDialog(this, "向争取民主自由的港人致敬! 你们的抗争照亮了全世界! 我的心❤和你们在一起! 祝参与抗争的港人中秋快乐！\uD83D\uDC96");
    }

    public void onTitleTipDialogClick(View view) {
        DialogView.showDialog(this, "向争取民主自由的港人致敬! ", "向争取民主自由的港人致敬! 你们的抗争照亮了全世界! 我的心❤和你们在一起! 祝参与抗争的港人中秋快乐！\uD83D\uDC96");
    }

    public void onSelectDialogClick(View view) {
        DialogButton btnNegative = DialogButton.create().setText("取消")
                .setStyle(DialogButton.NEGATIVE)
                .setOnButtonClickListener(new DialogButton.OnButtonClickListener() {
                    @Override
                    public void buttonClick(AlertDialog dialog) {
                        Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
                    }
                });

        DialogButton btnPositive = DialogButton.create()
                .setText("确认")
                .setOnButtonClickListener(new DialogButton.OnButtonClickListener() {
                    @Override
                    public void buttonClick(AlertDialog dialog) {
                        Toast.makeText(getApplicationContext(), "确认", Toast.LENGTH_SHORT).show();
                    }
                });
        DialogView.showDialog(this, "向争取民主自由的港人致敬! 你们的抗争照亮了全世界! 我的心❤和你们在一起! 祝参与抗争的港人中秋快乐！\uD83D\uDC96",
                btnNegative, btnPositive);
    }

    public void onTitleSelectDialogClick(View view) {
        /*DialogButton btnNegative = DialogButton.create();
        btnNegative.setText(getText(R.string.negative_button_text));
        btnNegative.setStyle(DialogButton.CANCEL);
        btnNegative.setOnButtonClickListener(new DialogButton.OnButtonClickListener() {
            @Override
            public void buttonClick(AlertDialog dialog) {
                Toast.makeText(getApplicationContext(),
                        getResources().getText(R.string.negative_button_text),
                        Toast.LENGTH_SHORT).show();
            }
        });
        DialogButton btnPositive = DialogButton.create();
        btnPositive.setText(getText(R.string.positive_button_text));
        btnPositive.setOnButtonClickListener(new DialogButton.OnButtonClickListener() {
            @Override
            public void buttonClick(AlertDialog dialog) {
                Toast.makeText(getApplicationContext(),
                        getResources().getText(R.string.positive_button_text),
                        Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog.showDialog(this, "向争取民主自由的港人致敬! ", "向争取民主自由的港人致敬! 你们的抗争照亮了全世界! 我的心❤和你们在一起! 祝参与抗争的港人中秋快乐！\uD83D\uDC96"
                , btnNegative, btnPositive);*/

    }

    public void onYMDDialogClick(View view) {
        DialogView.showYMDDialog(this, "2019-05-05", "", "", new OnSelectListener<String>() {
            @Override
            public void select(String select) {
                Toast.makeText(getApplicationContext(), select, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onYMDialogClick(View view) {
        DialogView.showYMDialog(this, "2019-05", "", "", new OnSelectListener<String>() {
            @Override
            public void select(String select) {
                Toast.makeText(getApplicationContext(), select, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onYDialogClick(View view) {
        DialogView.showYDialog(this, "2019", "", "", new OnSelectListener<String>() {
            @Override
            public void select(String select) {
                Toast.makeText(getApplicationContext(), select, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onPickerDialogClick(View view) {
        DialogView.showPickDialog(this, "测试", null, null, null);
    }
}
