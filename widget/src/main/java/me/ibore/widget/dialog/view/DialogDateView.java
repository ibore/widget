package me.ibore.widget.dialog.view;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.ibore.widget.R;
import me.ibore.widget.UIUtils;
import me.ibore.widget.dialog.AlertDialog;
import me.ibore.widget.picker.BaseDatePickerView;
import me.ibore.widget.picker.DatePickerView;

public class DialogDateView implements IDialogView {

    public static DialogDateView create() {
        return new DialogDateView();
    }

    public static final int YYYY = 1;
    public static final int YYYY_MM = 2;
    public static final int YYYY_MM_DD = 3;

    @IntDef({YYYY, YYYY_MM, YYYY_MM_DD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    private static final String pattern = "((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$";
    private String selectDate;
    private String startDate;
    private String endDate;
    @Mode
    private int mode = YYYY_MM_DD;
    @ColorRes
    private int bgColor = android.R.color.white;
    private int paddingTop = 16;
    private int paddingBottom = 22;
    private int paddingLeft = 16;
    private int paddingRight = 16;


    public DialogDateView setSelectDate(String selectDate) {
        this.selectDate = selectDate;
        return this;
    }

    public DialogDateView setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public DialogDateView setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public DialogDateView setMode(@Mode int mode) {
        this.mode = mode;
        return this;
    }

    public DialogDateView setBgColor(int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public DialogDateView setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public DialogDateView setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public DialogDateView setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public DialogDateView setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(AlertDialog dialog, int cornerRadius) {
        final DatePickerView pickerView = new DatePickerView(dialog.getContext());
        pickerView.setBackgroundResource(bgColor);
        pickerView.setPadding(UIUtils.dp2px(dialog.getContext(), paddingLeft), UIUtils.dp2px(dialog.getContext(), paddingTop),
                UIUtils.dp2px(dialog.getContext(), paddingRight), UIUtils.dp2px(dialog.getContext(), paddingBottom));
        pickerView.setGravity(Gravity.CENTER);
        pickerView.setBackgroundResource(android.R.color.white);
        pickerView.setAutoFitTextSize(true);
        pickerView.setDividerHeight(1, true);
        pickerView.setDividerColorRes(R.color.widget_dialog_line);
        pickerView.setVisibleItems(5);
        pickerView.setLineSpacing(16, true);
        pickerView.setTextBoundaryMargin(16, true);
        pickerView.setShowDivider(true);
        pickerView.setTextSize(18, true);
        String dateFormat;
        if (mode == YYYY) {
            dateFormat = "yyyy";
        } else if (mode == YYYY_MM) {
            dateFormat = "yyyy-MM";
        } else {
            dateFormat = "yyyy-MM-dd";
        }
        String[] selects = (isValidDate(selectDate, dateFormat) ? selectDate : new SimpleDateFormat("yyyy-MM-dd").format(new Date())).split("-");
        String[] starts = (isValidDate(startDate, dateFormat) ? startDate : "1900-01-01").split("-");
        String[] ends = (isValidDate(endDate, dateFormat) ? endDate : "2099-01-01").split("-");
        try {
            pickerView.setYearRange(Integer.parseInt(starts[0]), Integer.parseInt(ends[0]));
        } catch (Exception ignored) {
        }
        switch (mode) {
            case YYYY:
                pickerView.hideMonthItem();
                pickerView.hideDayItem();
                try {
                    pickerView.setSelectedYear(Integer.parseInt(selects[0]));
                } catch (Exception ignored) {
                }

                break;
            case YYYY_MM:
                pickerView.hideDayItem();
                try {
                    pickerView.setSelectedYear(Integer.parseInt(selects[0]));
                    pickerView.setSelectedMonth(Integer.parseInt(selects[1]));
                } catch (Exception ignored) {
                }
                break;
            case YYYY_MM_DD:
                try {
                    pickerView.setSelectedYear(Integer.parseInt(selects[0]));
                    pickerView.setSelectedMonth(Integer.parseInt(selects[1]));
                    pickerView.setSelectedDay(Integer.parseInt(selects[2]));
                } catch (Exception ignored) {
                }
                break;
        }
        pickerView.setOnDateSelectedListener(new BaseDatePickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(BaseDatePickerView datePickerView, int year, int month, int day, @Nullable Date date) {
                if (mode == YYYY) {
                    selectDate = pickerView.getSelectedYearString();
                } else if (mode == YYYY_MM) {
                    selectDate = pickerView.getSelectedYearString() + "-" + pickerView.getSelectedMonthString();
                } else {
                    selectDate = pickerView.getSelectedDate();
                }
            }
        });
        return pickerView;
    }

    public String getSelectDate() {
        return selectDate;
    }

    /**
     * 判断时间格式 格式必须为“YYYY-MM-dd”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public boolean isValidDate(String str, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        formatter.setLenient(false);
        try {
            Date date = formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }
}
