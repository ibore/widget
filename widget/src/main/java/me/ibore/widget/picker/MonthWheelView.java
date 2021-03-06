package me.ibore.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.ibore.widget.R;
import me.ibore.widget.WheelView;

public class MonthWheelView extends WheelView<Integer> {

    public MonthWheelView(Context context) {
        this(context, null);
    }

    public MonthWheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MonthWheelView);
        int selectedMonth = typedArray.getInt(R.styleable.MonthWheelView_mwvSelectedMonth, Calendar.getInstance().get(Calendar.MONTH) + 1);
        typedArray.recycle();
        initData();
        setSelectedMonth(selectedMonth);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        List<Integer> list = new ArrayList<>(1);
        for (int i = 1; i <= 12; i++) {
            list.add(i);
        }
        super.setData(list);
    }

    /**
     * 获取选中的月
     *
     * @return 选中的月
     */
    public int getSelectedMonth() {
        return getSelectedItemData();
    }

    /**
     * 获取选中的月
     *
     * @return 选中的月
     */
    public String getSelectedMonthString() {
        return getSelectedItemData() < 10 ? "0" + getSelectedItemData() : String.valueOf(getSelectedItemData());
    }

    /**
     * 设置选中的月
     *
     * @param selectedMonth 选中的月
     */
    public void setSelectedMonth(int selectedMonth) {
        setSelectedMonth(selectedMonth, false);
    }

    /**
     * 设置选中的月
     *
     * @param selectedMonth  选中的月
     * @param isSmoothScroll 是否平滑滚动
     */
    public void setSelectedMonth(int selectedMonth, boolean isSmoothScroll) {
        setSelectedMonth(selectedMonth, isSmoothScroll, 0);
    }

    /**
     * 设置选中的月
     *
     * @param selectedMonth  选中的月
     * @param isSmoothScroll 是否平滑滚动
     * @param smoothDuration 平滑滚动持续时间
     */
    public void setSelectedMonth(int selectedMonth, boolean isSmoothScroll, int smoothDuration) {
        if (selectedMonth >= 1 && selectedMonth <= 12) {
            updateSelectedMonth(selectedMonth, isSmoothScroll, smoothDuration);
        }
    }

    /**
     * 更新选中的月份
     *
     * @param selectedMonth  选中的月份
     * @param isSmoothScroll 是否平滑滚动
     * @param smoothDuration 平滑滚动持续时间
     */
    private void updateSelectedMonth(int selectedMonth, boolean isSmoothScroll, int smoothDuration) {
        setSelectedItemPosition(selectedMonth - 1, isSmoothScroll, smoothDuration);
    }

    @Override
    public void setData(List<Integer> dataList) {
        throw new UnsupportedOperationException("You can not invoke setData method in " + MonthWheelView.class.getSimpleName() + ".");
    }
}
