package me.ibore.widget.dialog.view;

import android.annotation.SuppressLint;
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
import me.ibore.widget.listener.OnInitViewListener;
import me.ibore.widget.picker.BaseDatePickerView;
import me.ibore.widget.picker.DatePickerView;

public class DialogDateBody implements IDialogView {

    public static final int YYYY = 1;
    public static final int YYYY_MM = 2;
    public static final int YYYY_MM_DD = 3;

    @IntDef({YYYY, YYYY_MM, YYYY_MM_DD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public static DialogDateBody create() {
        return new DialogDateBody();
    }

    private String selectDate;
    private String startDate;
    private String endDate;
    @Mode
    private int mode = YYYY_MM_DD;
    @ColorRes
    private int bgColor;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    protected DatePickerView datePickerView;
    private OnInitViewListener<DatePickerView> onInitViewListener;

    private DialogDateBody() {
        bgColor = android.R.color.white;
        paddingLeft = 16;
        paddingTop = 16;
        paddingRight = 16;
        paddingBottom = 22;
    }

    public DialogDateBody setSelectDate(String selectDate) {
        this.selectDate = selectDate;
        return this;
    }

    public DialogDateBody setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public DialogDateBody setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public DialogDateBody setMode(@Mode int mode) {
        this.mode = mode;
        return this;
    }

    public DialogDateBody setBgColor(int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public DialogDateBody setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public DialogDateBody setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public DialogDateBody setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public DialogDateBody setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }


    public DialogDateBody setOnInitViewListener(OnInitViewListener<DatePickerView> onInitViewListener) {
        this.onInitViewListener = onInitViewListener;
        return this;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(AlertDialog dialog) {
        datePickerView = new DatePickerView(dialog.getContext());
        datePickerView.setPadding(UIUtils.dp2px(dialog.getContext(), paddingLeft), UIUtils.dp2px(dialog.getContext(), paddingTop),
                UIUtils.dp2px(dialog.getContext(), paddingRight), UIUtils.dp2px(dialog.getContext(), paddingBottom));
        datePickerView.setBackgroundResource(bgColor);
        if (null != onInitViewListener) {
            onInitViewListener.initView(this.datePickerView);
        } else {
            datePickerView.setAutoFitTextSize(true);
            datePickerView.setDividerHeight(1, true);
            datePickerView.setDividerColorRes(R.color.widget_dialog_line);
            datePickerView.setVisibleItems(5);
            datePickerView.setLineSpacing(16, true);
            datePickerView.setTextBoundaryMargin(16, true);
            datePickerView.setShowDivider(true);
            datePickerView.setTextSize(18, true);
        }

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
            datePickerView.setYearRange(Integer.parseInt(starts[0]), Integer.parseInt(ends[0]));
        } catch (Exception ignored) {
        }
        switch (mode) {
            case YYYY:
                datePickerView.hideMonthItem();
                datePickerView.hideDayItem();
                try {
                    datePickerView.setSelectedYear(Integer.parseInt(selects[0]));
                } catch (Exception ignored) {
                }

                break;
            case YYYY_MM:
                datePickerView.hideDayItem();
                try {
                    datePickerView.setSelectedYear(Integer.parseInt(selects[0]));
                    datePickerView.setSelectedMonth(Integer.parseInt(selects[1]));
                } catch (Exception ignored) {
                }
                break;
            case YYYY_MM_DD:
                try {
                    datePickerView.setSelectedYear(Integer.parseInt(selects[0]));
                    datePickerView.setSelectedMonth(Integer.parseInt(selects[1]));
                    datePickerView.setSelectedDay(Integer.parseInt(selects[2]));
                } catch (Exception ignored) {
                }
                break;
        }
        datePickerView.setOnDateSelectedListener(new BaseDatePickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(BaseDatePickerView baseDatePickerView, int year, int month, int day, @Nullable Date date) {
                if (mode == YYYY) {
                    selectDate = datePickerView.getSelectedYearString();
                } else if (mode == YYYY_MM) {
                    selectDate = datePickerView.getSelectedYearString() + "-" + datePickerView.getSelectedMonthString();
                } else {
                    selectDate = datePickerView.getSelectedDate();
                }
            }
        });
        return datePickerView;
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
