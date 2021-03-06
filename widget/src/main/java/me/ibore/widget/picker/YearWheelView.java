package me.ibore.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import me.ibore.widget.R;
import me.ibore.widget.WheelView;

public class YearWheelView extends WheelView<Integer> {

    private int mStartYear;
    private int mEndYear;

    public YearWheelView(Context context) {
        this(context, null);
    }

    public YearWheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YearWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YearWheelView);
        mStartYear = typedArray.getInt(R.styleable.YearWheelView_ywvStartYear, 1900);
        mEndYear = typedArray.getInt(R.styleable.YearWheelView_ywvEndYear, 2100);
        int selectedYear = typedArray.getInt(R.styleable.YearWheelView_ywvSelectedYear, Calendar.getInstance().get(Calendar.YEAR));
        typedArray.recycle();

        updateYear();
        setSelectedYear(selectedYear);
    }

    /**
     * 设置年份区间
     *
     * @param start 起始年份
     * @param end   结束年份
     */
    public void setYearRange(int start, int end) {
        mStartYear = start;
        mEndYear = end;
        updateYear();
    }

    /**
     * 更新年份数据
     */
    private void updateYear() {
        List<Integer> list = new ArrayList<>(1);
        for (int i = mStartYear; i <= mEndYear; i++) {
            list.add(i);
        }
        super.setData(list);
    }


    /**
     * 获取当前选中的年份
     *
     * @return 当前选中的年份
     */
    public int getSelectedYear() {
        return getSelectedItemData();
    }

    /**
     * 设置当前选中的年份
     *
     * @param selectedYear 选中的年份
     */
    public void setSelectedYear(int selectedYear) {
        setSelectedYear(selectedYear, false);
    }

    /**
     * 设置当前选中的年份
     *
     * @param selectedYear   选中的年份
     * @param isSmoothScroll 是否平滑滚动
     */
    public void setSelectedYear(int selectedYear, boolean isSmoothScroll) {
        setSelectedYear(selectedYear, isSmoothScroll, 0);
    }

    /**
     * 设置当前选中的年份
     *
     * @param selectedYear   选中的年份
     * @param isSmoothScroll 是否平滑滚动
     * @param smoothDuration 平滑滚动持续时间
     */
    public void setSelectedYear(int selectedYear, boolean isSmoothScroll, int smoothDuration) {
        if (selectedYear >= mStartYear && selectedYear <= mEndYear) {
            updateSelectedYear(selectedYear, isSmoothScroll, smoothDuration);
        }
    }

    /**
     * 更新选中的年份
     *
     * @param selectedYear   选中的年
     * @param isSmoothScroll 是否平滑滚动
     * @param smoothDuration 平滑滚动持续时间
     */
    private void updateSelectedYear(int selectedYear, boolean isSmoothScroll, int smoothDuration) {
        setSelectedItemPosition(selectedYear - mStartYear, isSmoothScroll, smoothDuration);
    }

    @Override
    public void setData(List<Integer> dataList) {
        throw new UnsupportedOperationException("You can not invoke setData method in " + YearWheelView.class.getSimpleName() + ".");
    }
}
