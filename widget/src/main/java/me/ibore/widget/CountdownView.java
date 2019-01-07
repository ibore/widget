package me.ibore.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CountdownView extends View {
    private static final String TAG = "CountdownView";

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval = 1000;

    private Paint mTimeTextPaint, mSuffixTextPaint;

    // ----------------------------- attrs start --------------------------- //
    private boolean mIncludePad;
    /**
     * Suffix formats.
     * day-dd, hour-hh, minute-mm, second-ss.
     */
    private String mTimeFormat = "";

    private boolean showDays, showHours, showMinutes, showSeconds;

    // --- Time style start --- //
    private int mTimeTextSize;
    private int mTimeTextColor;
    private boolean isTimeTextBold;
    /**
     * Margin between time text letters.
     */
    private float mTimeTextLetterSpacing;
    private float mTimeTextLetterBackgroundSpacing;
    private Drawable mTimeBackground;
    private float mTimeBackgroundWidth;
    private float mTimeBackgroundHeight;
    private float mTimeBackgroundPadding;
    private float mTimeBackgroundPaddingX;
    private float mTimeBackgroundPaddingY;
    private float mTimeBackgroundPaddingLeft;
    private float mTimeBackgroundPaddingRight;
    private float mTimeBackgroundPaddingTop;
    private float mTimeBackgroundPaddingBottom;
    // --- Time style end --- //

    // --- Suffix style start --- //
    // Suffix text. Default format.
    private static final String DEFAULT_SUFFIX = ":";
    private String mSuffixDay;
    private String mSuffixHour = DEFAULT_SUFFIX;
    private String mSuffixMinute = DEFAULT_SUFFIX;
    private String mSuffixSecond;

    private int mSuffixTextSize;
    private int mSuffixTextColor;
    private boolean isSuffixTextBold;
    /**
     * Margin between the adjacent time text and suffix text.
     */
    private float mSuffixTextMargin;
    private Drawable mSuffixBackground;
    private float mSuffixBackgroundWidth;
    private float mSuffixBackgroundHeight;
    private float mSuffixBackgroundPadding;
    private float mSuffixBackgroundPaddingX;
    private float mSuffixBackgroundPaddingY;
    private float mSuffixBackgroundPaddingLeft;
    private float mSuffixBackgroundPaddingRight;
    private float mSuffixBackgroundPaddingTop;
    private float mSuffixBackgroundPaddingBottom;
    // --- Suffix style end --- //
    // ----------------------------- attrs end --------------------------- //

    // ----------------------- calculate values start -------------------- //
    private int mMeasureTotalHeight;

    private float mTimeTextMeasuredHeight;
    private float mTimeTextLetterMeasuredWidth;
    private float mTimeTextBaseline;
    private float mSuffixTextBaseline;
    private float mSuffixTextMeasuredHeight;

    private float mSuffixDayTextWidth = 0;
    private float mSuffixHourTextWidth = 0;
    private float mSuffixMinuteTextWidth = 0;
    private float mSuffixSecondTextWidth = 0;

    private float drawTimeBackgroundWidth = 0;
    private float drawTimeBackgroundHeight = 0;
    private float drawTimeBackgroundPaddingLeft = 0;
    private float drawTimeBackgroundPaddingRight = 0;
    private float drawTimeBackgroundPaddingTop = 0;
    private float drawTimeBackgroundPaddingBottom = 0;

    private boolean hasSetTimeBackgroundPadding;
    private boolean hasSetTimeBackgroundPaddingX;
    private boolean hasSetTimeBackgroundPaddingY;

    private float drawSuffixBackgroundWidth = 0;
    private float drawSuffixBackgroundHeight = 0;
    private float drawSuffixBackgroundPaddingLeft = 0;
    private float drawSuffixBackgroundPaddingRight = 0;
    private float drawSuffixBackgroundPaddingTop = 0;
    private float drawSuffixBackgroundPaddingBottom = 0;

    private boolean hasSetSuffixBackgroundPadding;
    private boolean hasSetSuffixBackgroundPaddingX;
    private boolean hasSetSuffixBackgroundPaddingY;
    // ----------------------- calculate values end -------------------- //

    private int mTimeTypeCount;
    private int mSuffixTypeCount;

    private static final String DEFAULT_TIME_FORMAT = "hh:mm:ss";

    private static final int TYPE_TIME_DAY = 0;
    private static final int TYPE_TIME_HOUR = 1;
    private static final int TYPE_TIME_MINUTE = 2;
    private static final int TYPE_TIME_SECOND = 3;
    private int mFirstTimeType;

    private boolean hasSetShowDays, hasSetShowHours, hasSetShowMinutes, hasSetShowSeconds,
            hasSetSuffixDay, hasSetSuffixHour, hasSetSuffixMinute, hasSetSuffixSecond;
    public long mDays, mHours, mMinutes, mSeconds;

    private MyCountDownTimer myCountDownTimer;

    /**
     * Time length of remain seconds.
     *
     * @since 1.1.0
     */
    private long mRemainTotalSeconds;

    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CountdownView, defStyleAttr, 0);

        mIncludePad = mTypedArray.getBoolean(R.styleable.CountdownView_cvIncludeFontPadding, false);

//        mFirstTypeTimeMinLength = mTypedArray.getInt(R.styleable.CountdownView_firstTypeTimeMinLength, 0);

//        showLeadingZero = mTypedArray.getBoolean(R.styleable.CountdownView_showLeadingZero, true);

        setTimeFormat(mTypedArray.getString(R.styleable.CountdownView_cvTimeFormat));
        setShowDays(mTypedArray.getBoolean(R.styleable.CountdownView_cvShowDays, false));
        setShowHours(mTypedArray.getBoolean(R.styleable.CountdownView_cvShowHours, false));
        setShowMinutes(mTypedArray.getBoolean(R.styleable.CountdownView_cvShowMinutes, false));
        setShowSeconds(mTypedArray.getBoolean(R.styleable.CountdownView_cvShowSeconds, false));

        mTimeTextSize = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeTextSize, UIUtils.sp2px(getContext(), 13));
        mTimeTextColor = mTypedArray.getColor(R.styleable.CountdownView_cvTimeTextColor, Color.BLACK);
        isTimeTextBold = mTypedArray.getBoolean(R.styleable.CountdownView_cvIsTimeTextBold, false);

        mTimeTextLetterSpacing = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeTextLetterSpacing, 0);
        mTimeTextLetterBackgroundSpacing = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeTextLetterBackgroundSpacing, 0);

        mTimeBackground = mTypedArray.getDrawable(R.styleable.CountdownView_cvTimeBackground);
        mTimeBackgroundWidth = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundWidth, 0);
        mTimeBackgroundHeight = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundHeight, 0);

        mTimeBackgroundPadding = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundPadding, 0);
        mTimeBackgroundPaddingX = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundPaddingX, 0);
        mTimeBackgroundPaddingY = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundPaddingY, 0);
        mTimeBackgroundPaddingLeft = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundPaddingLeft, 0);
        mTimeBackgroundPaddingRight = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundPaddingRight, 0);
        mTimeBackgroundPaddingTop = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundPaddingTop, 0);
        mTimeBackgroundPaddingBottom = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvTimeBackgroundPaddingBottom, 0);

        // 1.1.0
        if (mTypedArray.getString(R.styleable.CountdownView_cvSuffixDayText) != null) {
            setSuffixDay(mTypedArray.getString(R.styleable.CountdownView_cvSuffixDayText));
        }
        // 1.1.0
        if (mTypedArray.getString(R.styleable.CountdownView_cvSuffixHourText) != null) {
            setSuffixHour(mTypedArray.getString(R.styleable.CountdownView_cvSuffixHourText));
        }
        // 1.1.0
        if (mTypedArray.getString(R.styleable.CountdownView_cvSuffixMinuteText) != null) {
            setSuffixMinute(mTypedArray.getString(R.styleable.CountdownView_cvSuffixMinuteText));
        }
        // 1.1.0
        if (mTypedArray.getString(R.styleable.CountdownView_cvSuffixSecondText) != null) {
            setSuffixSecond(mTypedArray.getString(R.styleable.CountdownView_cvSuffixSecondText));
        }

        mSuffixTextSize = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixTextSize, UIUtils.sp2px(getContext(), 16));
        mSuffixTextColor = mTypedArray.getColor(R.styleable.CountdownView_cvSuffixTextColor, Color.BLACK);
        isSuffixTextBold = mTypedArray.getBoolean(R.styleable.CountdownView_cvIsSuffixTextBold, false);

        mSuffixTextMargin = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixTextMargin, 0);

        mSuffixBackground = mTypedArray.getDrawable(R.styleable.CountdownView_cvSuffixBackground);
        mSuffixBackgroundWidth = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundWidth, 0);
        mSuffixBackgroundHeight = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundHeight, 0);

        mSuffixBackgroundPadding = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundPadding, 0);
        mSuffixBackgroundPaddingX = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundPaddingX, 0);
        mSuffixBackgroundPaddingY = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundPaddingY, 0);
        mSuffixBackgroundPaddingLeft = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundPaddingLeft, 0);
        mSuffixBackgroundPaddingRight = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundPaddingRight, 0);
        mSuffixBackgroundPaddingTop = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundPaddingTop, 0);
        mSuffixBackgroundPaddingBottom = mTypedArray.getDimensionPixelSize(R.styleable.CountdownView_cvSuffixBackgroundPaddingBottom, 0);

        mTypedArray.recycle();

        initView();
    }

    /**
     * Start the countdown.
     * If the time format has not been set, it will be set to the default value of "hh:mm:ss".
     * You can set the time format by call {@link #setTimeFormat(String)} and then call {@link #refreshView()} to apply it,
     * or just call {@link #start(long, String)}.
     * You can also set the suffixes individually by call {@link #setSuffixDay(String)}, {@link #setSuffixHour(String)},
     * {@link #setSuffixMinute(String)} (String)}, {@link #setSuffixSecond(String)} (String)}, then call {@link #start(long)}.
     * <p>
     * All of the above Setters can be set in layout xml.
     *
     * @param millisInFuture Time length of milliseconds.
     * @since 1.1.0
     */
    public void start(long millisInFuture) {
        if (isNullOrEmptyAfterTrim(mTimeFormat) && !hasSetSuffix()) {
            mTimeFormat = DEFAULT_TIME_FORMAT;
        }
        start(millisInFuture, mTimeFormat);
    }

    /**
     * Start the countdown with the default time format "hh:mm:ss" or not.
     *
     * @param millisInFuture       Time length of milliseconds.
     * @param useDefaultTimeFormat
     * @since 1.1.0
     */
    public void start(long millisInFuture, boolean useDefaultTimeFormat) {
        if (useDefaultTimeFormat) {
            mTimeFormat = DEFAULT_TIME_FORMAT;
        }
        start(millisInFuture, mTimeFormat);
    }

    /**
     * Start the countdown.
     *
     * @param millisInFuture Time length of milliseconds.
     * @param timeFormat     Eg:"dddayhh:mm:ss", "hh小时mm分钟ss秒"
     */
    public void start(long millisInFuture, String timeFormat) {
        Log.d("test", "start");

        stopCountdown();

        this.mTimeFormat = timeFormat;

        // TODO: 2018/3/7
        initView();

        updateTimeByMillis(millisInFuture);

        myCountDownTimer = new MyCountDownTimer(millisInFuture, mCountdownInterval);
        myCountDownTimer.start();
    }

    /**
     * @param millisecond
     * @since 1.0.0 Get all time type.
     * 1.1.0 Auto convert time unit.
     */
    private void updateTimeByMillis(long millisecond) {
        if (millisecond <= 0) {
            resetZero();
            return;
        }

        // TODO: 2018/3/7
        Long lastRemainTotalSeconds = mRemainTotalSeconds;

        mRemainTotalSeconds = getSecondsByMillis(millisecond);

        if (mRemainTotalSeconds - lastRemainTotalSeconds > 1) {
            Log.e(TAG, "mRemainTotalSeconds - lastRemainTotalSeconds = " + (mRemainTotalSeconds - lastRemainTotalSeconds));
        }
        Log.i(TAG, "onTick - millisecond = " + millisecond);
        Log.i(TAG, "onTick - mRemainTotalSeconds = " + mRemainTotalSeconds);

        updateTimeBySeconds();
    }

    private long getSecondsByMillis(long millisecond) {
        return Math.round((double) millisecond / 1000);
    }

    /**
     * 1.1.0 Auto convert time unit.
     */
    private void updateTimeBySeconds() {
        if (showDays) { // Show all time type.
            mDays = mRemainTotalSeconds / (60 * 60 * 24);
            mHours = (mRemainTotalSeconds / (60 * 60)) % 24;
            mMinutes = (mRemainTotalSeconds / 60) % 60;
            mSeconds = mRemainTotalSeconds % 60;
        } else { // Not show days.
            mDays = 0;

            if (showHours) {
                mHours = mRemainTotalSeconds / 60 / 60; // Total hours.
                mMinutes = (mRemainTotalSeconds / 60) % 60;
                mSeconds = mRemainTotalSeconds % 60;
            } else { // Not show hours.
                mHours = 0;

                if (showMinutes) {
                    mMinutes = mRemainTotalSeconds / 60; // Total minutes.
                    mSeconds = mRemainTotalSeconds % 60;
                } else { // Not show minutes.
                    mMinutes = 0;
                    mSeconds = mRemainTotalSeconds; // Total seconds.
                }
            }
        }


        Log.d("test", "updateTimeBySeconds");
    }

    private void initView() {
        initPaint();

        initPadding();

        initSuffix();
    }

    private void initPadding() {
        // Time background padding.
        if (hasSetTimeBackgroundPadding) {
            mTimeBackgroundPaddingX = 0;
            mTimeBackgroundPaddingY = 0;
            mTimeBackgroundPaddingLeft = 0;
            mTimeBackgroundPaddingRight = 0;
            mTimeBackgroundPaddingTop = 0;
            mTimeBackgroundPaddingBottom = 0;

            hasSetTimeBackgroundPadding = false;
        } else {
            if (hasSetTimeBackgroundPaddingX) {
                mTimeBackgroundPaddingLeft = 0;
                mTimeBackgroundPaddingRight = 0;

                hasSetTimeBackgroundPaddingX = false;
            }
            if (hasSetTimeBackgroundPaddingY) {
                mTimeBackgroundPaddingTop = 0;
                mTimeBackgroundPaddingBottom = 0;

                hasSetTimeBackgroundPaddingY = false;
            }
        }

        mTimeBackgroundPaddingLeft = getTimePadding(mTimeBackgroundPaddingLeft, "x");
        mTimeBackgroundPaddingRight = getTimePadding(mTimeBackgroundPaddingRight, "x");
        mTimeBackgroundPaddingTop = getTimePadding(mTimeBackgroundPaddingTop, "y");
        mTimeBackgroundPaddingBottom = getTimePadding(mTimeBackgroundPaddingBottom, "y");

        // Suffix background padding.
        if (hasSetSuffixBackgroundPadding) {
            mSuffixBackgroundPaddingX = 0;
            mSuffixBackgroundPaddingY = 0;
            mSuffixBackgroundPaddingLeft = 0;
            mSuffixBackgroundPaddingRight = 0;
            mSuffixBackgroundPaddingTop = 0;
            mSuffixBackgroundPaddingBottom = 0;

            hasSetSuffixBackgroundPadding = false;
        } else {
            if (hasSetSuffixBackgroundPaddingX) {
                mSuffixBackgroundPaddingLeft = 0;
                mSuffixBackgroundPaddingRight = 0;

                hasSetSuffixBackgroundPaddingX = false;
            }
            if (hasSetSuffixBackgroundPaddingY) {
                mSuffixBackgroundPaddingTop = 0;
                mSuffixBackgroundPaddingBottom = 0;

                hasSetSuffixBackgroundPaddingY = false;
            }
        }
        mSuffixBackgroundPaddingLeft = getSuffixPadding(mSuffixBackgroundPaddingLeft, "x");
        mSuffixBackgroundPaddingRight = getSuffixPadding(mSuffixBackgroundPaddingRight, "x");
        mSuffixBackgroundPaddingTop = getSuffixPadding(mSuffixBackgroundPaddingTop, "y");
        mSuffixBackgroundPaddingBottom = getSuffixPadding(mSuffixBackgroundPaddingBottom, "y");
    }

    private float getTimePadding(float padding, String type) {
        if (padding == 0)
            switch (type) {
                case "x":
                    return mTimeBackgroundPaddingX == 0 ? mTimeBackgroundPadding : mTimeBackgroundPaddingX;
                case "y":
                    return mTimeBackgroundPaddingY == 0 ? mTimeBackgroundPadding : mTimeBackgroundPaddingY;
            }

        return padding;
    }

    private float getSuffixPadding(float padding, String type) {
        if (padding == 0)
            switch (type) {
                case "x":
                    return mSuffixBackgroundPaddingX == 0 ? mSuffixBackgroundPadding : mSuffixBackgroundPaddingX;
                case "y":
                    return mSuffixBackgroundPaddingY == 0 ? mSuffixBackgroundPadding : mSuffixBackgroundPaddingY;
            }

        return padding;
    }

    private void initPaint() {
        // Time text paint.
        mTimeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextPaint.setColor(mTimeTextColor);
        mTimeTextPaint.setTextSize(mTimeTextSize);
        mTimeTextPaint.setTextAlign(Paint.Align.CENTER);
        if (isTimeTextBold) {
            mTimeTextPaint.setFakeBoldText(true);
        }

        // Suffix text paint.
        mSuffixTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSuffixTextPaint.setColor(mSuffixTextColor);
        mSuffixTextPaint.setTextSize(mSuffixTextSize);
        mSuffixTextPaint.setTextAlign(Paint.Align.CENTER);
        if (isSuffixTextBold) {
            mSuffixTextPaint.setFakeBoldText(true);
        }
    }

    private void initSuffix() {
        Log.d("test", "initSuffix");

        mTimeTypeCount = 0;
        mSuffixTypeCount = 0;
        mSuffixDayTextWidth = 0;
        mSuffixHourTextWidth = 0;
        mSuffixMinuteTextWidth = 0;
        mSuffixSecondTextWidth = 0;

        int indexDay = mTimeFormat.indexOf("dd");
        int indexHour = mTimeFormat.indexOf("hh");
        int indexMinute = mTimeFormat.indexOf("mm");
        int indexSecond = mTimeFormat.indexOf("ss");

        // Day.
        if (showDays) { // Set time format individually.
            mTimeTypeCount++;

            if (hasSetShowDays && hasSetSuffixDay) { // Set suffixes individually.
                if (!TextUtils.isEmpty(mSuffixDay)) {
                    measureSuffixDayText();
                    mSuffixTypeCount++;
                }
            } else if (indexDay > -1) { // From the string of time format.
                initSuffixDay(indexDay, indexHour);
            }
        } else if (!hasSetShowDays && indexDay > -1) { // From the string of time format.
            showDays = true;
            mTimeTypeCount++;

            initSuffixDay(indexDay, indexHour);
        }

        // Hour.
        if (showHours) { // Set time format individually.
            mTimeTypeCount++;

            if (hasSetShowHours && hasSetSuffixHour) { // Set suffixes individually.
                if (!TextUtils.isEmpty(mSuffixHour)) {
                    measureSuffixHourText();
                    mSuffixTypeCount++;
                }
            } else if (indexHour > -1) { // From the string of time format.
                initSuffixHour(indexHour, indexMinute);
            }
        } else if (!hasSetShowHours && indexHour > -1) { // From the string of time format.
            showHours = true;
            mTimeTypeCount++;

            initSuffixHour(indexHour, indexMinute);
        }

        // Minute.
        if (showMinutes) { // Set time format individually.
            mTimeTypeCount++;

            if (hasSetShowMinutes && hasSetSuffixMinute) { // Set suffixes individually.
                if (!TextUtils.isEmpty(mSuffixMinute)) {
                    measureSuffixMinuteText();
                    mSuffixTypeCount++;
                }
            } else if (indexMinute > -1) { // From the string of time format.
                initSuffixMinute(indexMinute, indexSecond);
            }
        } else if (!hasSetShowMinutes && indexMinute > -1) { // From the string of time format.
            showMinutes = true;
            mTimeTypeCount++;

            initSuffixMinute(indexMinute, indexSecond);
        }

        // Second.
        if (showSeconds) { // Set time format individually.
            mTimeTypeCount++;

            if (hasSetShowSeconds && hasSetSuffixSecond) { // Set suffixes individually.
                if (!TextUtils.isEmpty(mSuffixSecond)) {
                    measureSuffixSecondText();
                    mSuffixTypeCount++;
                }
            } else if (indexSecond > -1) { // From the string of time format.
                initSuffixSecond(indexSecond);
            }
        } else if (!hasSetShowSeconds && indexSecond > -1) { // From the string of time format.
            showSeconds = true;
            mTimeTypeCount++;

            initSuffixSecond(indexSecond);
        }
    }

    private void measureSuffixDayText() {
        mSuffixDayTextWidth = mSuffixTextPaint.measureText(mSuffixDay);
    }

    private void measureSuffixHourText() {
        mSuffixHourTextWidth = mSuffixTextPaint.measureText(mSuffixHour);
    }

    private void measureSuffixMinuteText() {
        mSuffixMinuteTextWidth = mSuffixTextPaint.measureText(mSuffixMinute);
    }

    private void measureSuffixSecondText() {
        mSuffixSecondTextWidth = mSuffixTextPaint.measureText(mSuffixSecond);
    }

    private void initSuffixDay(int indexDay, int indexHour) {
        if (indexDay + 2 < indexHour) { // Has day suffix.
            mSuffixDay = mTimeFormat.substring(indexDay + 2, indexHour);
            measureSuffixDayText();
            mSuffixTypeCount++;
        }
    }

    private void initSuffixHour(int indexHour, int indexMinute) {
        if (indexHour + 2 < indexMinute) { // Has hour suffix.
            mSuffixHour = mTimeFormat.substring(indexHour + 2, indexMinute);
            measureSuffixHourText();
            mSuffixTypeCount++;
        }
    }

    private void initSuffixMinute(int indexMinute, int indexSecond) {
        if (indexMinute + 2 < indexSecond) { // Has minute suffix.
            mSuffixMinute = mTimeFormat.substring(indexMinute + 2, indexSecond);
            measureSuffixMinuteText();
            mSuffixTypeCount++;
        }
    }

    private void initSuffixSecond(int indexSecond) {
        if (indexSecond + 2 < mTimeFormat.length()) { // Has second suffix.
            mSuffixSecond = mTimeFormat.substring(indexSecond + 2, mTimeFormat.length());
            measureSuffixSecondText();
            mSuffixTypeCount++;
        }
    }

    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    private void measureDrawValues() {
        // Time values.
        drawTimeBackgroundPaddingLeft = mTimeBackgroundPaddingLeft;
        drawTimeBackgroundPaddingRight = mTimeBackgroundPaddingRight;
        drawTimeBackgroundPaddingTop = mTimeBackgroundPaddingTop;
        drawTimeBackgroundPaddingBottom = mTimeBackgroundPaddingBottom;

        // Suffix values.
        drawSuffixBackgroundPaddingLeft = mSuffixBackgroundPaddingLeft;
        drawSuffixBackgroundPaddingRight = mSuffixBackgroundPaddingRight;
        drawSuffixBackgroundPaddingTop = mSuffixBackgroundPaddingTop;
        drawSuffixBackgroundPaddingBottom = mSuffixBackgroundPaddingBottom;

        // Time height values.
        if (mTimeBackgroundHeight > 0) { // If the size of @timeBackgroundHeight has been specified.
            if (mTimeBackgroundHeight < mTimeTextMeasuredHeight) {
                drawTimeBackgroundHeight = mTimeTextMeasuredHeight;
                drawTimeBackgroundPaddingTop = drawTimeBackgroundPaddingBottom = 0;
            } else {
                /*
                 * If the size of {@link #mTimeBackgroundHeight} has been specified by users,
                 * the padding will be recalculated regardless of the values specified by users.
                 */
                drawTimeBackgroundHeight = mTimeBackgroundHeight;
                drawTimeBackgroundPaddingTop = drawTimeBackgroundPaddingBottom = (mTimeBackgroundHeight - mTimeTextMeasuredHeight) / 2;
            }
        } else {
            /*
             * If the size of {@link #mTimeBackgroundHeight} has not been specified,
             * the {@link #mTimeBackground} will be drawn according to its {@link #mTimeTextMeasuredHeight} and padding.
             */
            drawTimeBackgroundHeight = mTimeTextMeasuredHeight + mTimeBackgroundPaddingTop + mTimeBackgroundPaddingBottom;
        }

        // Suffix height values.
        if (mSuffixBackgroundHeight > 0) { // If the size of @suffixBackgroundHeight has been specified.
            if (mSuffixBackgroundHeight < mSuffixTextMeasuredHeight) {
                drawSuffixBackgroundHeight = mSuffixTextMeasuredHeight;
                drawSuffixBackgroundPaddingTop = drawSuffixBackgroundPaddingBottom = 0;
            } else {
                /*
                 * If the size of {@link #mSuffixBackgroundHeight} has been specified by users,
                 * the padding will be recalculated.
                 */
                drawSuffixBackgroundHeight = mSuffixBackgroundHeight;
                drawSuffixBackgroundPaddingTop = drawSuffixBackgroundPaddingBottom = (mSuffixBackgroundHeight - mSuffixTextMeasuredHeight) / 2;
            }
        } else {
            /*
             * If the size of {@link #mSuffixBackgroundHeight} has not been specified,
             * the {@link #mSuffixBackground} will be drawn according to its {@link #mSuffixTextMeasuredHeight} and padding.
             */
            drawSuffixBackgroundHeight = mSuffixTextMeasuredHeight + mSuffixBackgroundPaddingTop + mSuffixBackgroundPaddingBottom;
        }
    }

    private void measureDrawWidthValuesWhenSplitting() {
        // If the size of @timeBackgroundWidth has been specified.
        if (mTimeBackgroundWidth > 0) {
            if (mTimeBackgroundWidth < mTimeTextLetterMeasuredWidth) {
                drawTimeBackgroundWidth = mTimeTextLetterMeasuredWidth;
                drawTimeBackgroundPaddingLeft = drawTimeBackgroundPaddingRight = 0;
            } else {
                /*
                 * If the size of {@link #mTimeBackgroundWidth} has been specified by users,
                 * the padding will be recalculated.
                 */
                drawTimeBackgroundWidth = mTimeBackgroundWidth;
                drawTimeBackgroundPaddingLeft = drawTimeBackgroundPaddingRight = (mTimeBackgroundWidth - mTimeTextLetterMeasuredWidth) / 2;
            }
        } else {
            /*
             * If the size of {@link #mTimeBackgroundWidth} has not been specified,
             * the {@link #mTimeBackground} will be drawn according to its {@link #mTimeTextLetterMeasuredWidth} and padding.
             */
            drawTimeBackgroundWidth = mTimeTextLetterMeasuredWidth + mTimeBackgroundPaddingLeft + mTimeBackgroundPaddingRight;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initTextBounds();

        int width = measureSize(1, getMeasuredTotalWidth(), widthMeasureSpec);
        mMeasureTotalHeight = measureSize(2, getMeasuredTotalHeight(), heightMeasureSpec);

        measureDrawValues();

        setBaseline();

        setMeasuredDimension(width, mMeasureTotalHeight);
        Log.d("test", "onMeasure");
    }

    private void initTextBounds() {
        // Width of time letters.
        mTimeTextLetterMeasuredWidth = 0;
        for (int i = 0; i < 10; i++) {
            Rect r = getTextBounds(String.valueOf(i), mTimeTextPaint);
            if (mTimeTextLetterMeasuredWidth < r.width()) {
                mTimeTextLetterMeasuredWidth = r.width();
            }
        }

        // Height.
        Paint.FontMetrics mTimeTextPaintFontMetrics = mTimeTextPaint.getFontMetrics();
        Paint.FontMetrics mSuffixTextPaintFontMetrics = mSuffixTextPaint.getFontMetrics();
        if (mIncludePad) {
            mTimeTextMeasuredHeight = mTimeTextPaintFontMetrics.bottom - mTimeTextPaintFontMetrics.top;
            mTimeTextBaseline = -mTimeTextPaintFontMetrics.top;

            mSuffixTextMeasuredHeight = mSuffixTextPaintFontMetrics.bottom - mSuffixTextPaintFontMetrics.top;
            mSuffixTextBaseline = -mSuffixTextPaintFontMetrics.top;
        } else {
            mTimeTextMeasuredHeight = mTimeTextPaintFontMetrics.descent - mTimeTextPaintFontMetrics.ascent;
            mTimeTextBaseline = -mTimeTextPaintFontMetrics.ascent;

            mSuffixTextMeasuredHeight = mSuffixTextPaintFontMetrics.descent - mSuffixTextPaintFontMetrics.ascent;
            mSuffixTextBaseline = -mSuffixTextPaintFontMetrics.ascent;
        }
    }

    private void setBaseline() {
        mTimeTextBaseline += drawTimeBackgroundPaddingTop;
        mSuffixTextBaseline += drawSuffixBackgroundPaddingTop;
    }

    private float getMeasuredTotalWidth() {
        // Time width.
        float totalTimeWidth = 0;
        long firstTypeTime = 0;
        int firstTypeTimeStringLength = 0;

        if (showDays) {
            firstTypeTime = mDays;
            mFirstTimeType = TYPE_TIME_DAY;
            firstTypeTimeStringLength = 1;
        } else if (showHours) {
            firstTypeTime = mHours;
            mFirstTimeType = TYPE_TIME_HOUR;
            firstTypeTimeStringLength = 2;
        } else if (showMinutes) {
            firstTypeTime = mMinutes;
            mFirstTimeType = TYPE_TIME_MINUTE;
            firstTypeTimeStringLength = 2;
        } else if (showSeconds) {
            firstTypeTime = mSeconds;
            mFirstTimeType = TYPE_TIME_SECOND;
            firstTypeTimeStringLength = 2;
        }

        if (mTimeTextLetterBackgroundSpacing > 0) { // Split timeBackground.
            measureDrawWidthValuesWhenSplitting();

            if (mTimeTypeCount > 0) {
                // First type of time
                totalTimeWidth = getTimeString(firstTypeTime, firstTypeTimeStringLength).length() * drawTimeBackgroundWidth
                        + (getTimeString(firstTypeTime, firstTypeTimeStringLength).length() - 1) * mTimeTextLetterBackgroundSpacing;

                if (mTimeTypeCount > 1) {
                    totalTimeWidth += (mTimeTypeCount - 1) * (2 * drawTimeBackgroundWidth + mTimeTextLetterBackgroundSpacing);
                }
            }
        } else { // Time text is a whole.
            if (mTimeTypeCount > 0) {
                // First type of time
                totalTimeWidth = measureTimeWidthWhenWhole(getTimeString(firstTypeTime, firstTypeTimeStringLength).length());

                if (mTimeTypeCount > 1) {
                    totalTimeWidth += (mTimeTypeCount - 1) * measureTimeWidthWhenWhole(2);
                }
            }
        }

        // Suffix width.
        int suffixTextMarginCount = mTimeTypeCount + mSuffixTypeCount - 1;
        if (suffixTextMarginCount < 0) {
            suffixTextMarginCount = 0;
        }
        float totalSuffixWidth = measureSuffixWidth(mSuffixDayTextWidth)
                + measureSuffixWidth(mSuffixHourTextWidth)
                + measureSuffixWidth(mSuffixMinuteTextWidth)
                + measureSuffixWidth(mSuffixSecondTextWidth)
                + suffixTextMarginCount * mSuffixTextMargin;

        Log.d("test", "getMeasuredTotalWidth = " + (totalTimeWidth + totalSuffixWidth));

        return (totalTimeWidth + totalSuffixWidth) < 0 ? 0 : (totalTimeWidth + totalSuffixWidth);
    }

    private float measureSuffixWidth(float suffixWidth) {
        if (suffixWidth <= 0) {
            return 0;
        }

        if (mSuffixBackgroundWidth > 0) {
            if (mSuffixBackgroundWidth < suffixWidth) {
                drawSuffixBackgroundWidth = suffixWidth;
                drawSuffixBackgroundPaddingLeft = drawSuffixBackgroundPaddingRight = 0;
            } else {
                /*
                 * If the size of {@link #mSuffixBackgroundWidth} has been specified by users,
                 * the padding will be recalculated regardless of the values specified by users.
                 */
                drawSuffixBackgroundWidth = mSuffixBackgroundWidth;
                drawSuffixBackgroundPaddingLeft = drawSuffixBackgroundPaddingRight = (mSuffixBackgroundWidth - suffixWidth) / 2;
            }
        } else {
            /*
             * If the size of {@link #mSuffixBackgroundWidth} has not been specified,
             * the @suffixBackground will be drawn according to its @suffixTextWidth and @padding.
             */
            drawSuffixBackgroundWidth = suffixWidth + mSuffixBackgroundPaddingLeft + mSuffixBackgroundPaddingRight;
        }

        return drawSuffixBackgroundWidth;
    }

    private float getMeasuredTotalHeight() {
        return drawTimeBackgroundHeight > drawSuffixBackgroundHeight ? drawTimeBackgroundHeight : drawSuffixBackgroundHeight;
    }

    /**
     * Measure view size.
     *
     * @param specType    1 width, 2 height
     * @param contentSize all content's size
     * @param measureSpec spec
     * @return measureSize
     */
    private int measureSize(int specType, float contentSize, int measureSpec) {
        double size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            size = Math.max(contentSize, specSize);
        } else {
            size = contentSize;

            if (specType == 1) {
                // width
                size += (getPaddingLeft() + getPaddingRight());
            } else {
                // height
                size += (getPaddingTop() + getPaddingBottom());
            }
        }

        return (int) Math.ceil(size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float left = (canvas.getWidth() - getMeasuredTotalWidth()) / 2;
        Log.d("test", "onDraw");

        switch (mFirstTimeType) {
            case TYPE_TIME_DAY:
                // Day.
                if (showDays) {
                    left = drawDayItem(canvas, left, 1);
                }

                // Hour
                if (showHours) {
                    left = drawHourItem(canvas, left, 2);
                }

                // Minute
                if (showMinutes) {
                    left = drawMinuteItem(canvas, left, 2);
                }

                // Second
                if (showSeconds) {
                    drawSecondItem(canvas, left, 2);
                }
                break;
            case TYPE_TIME_HOUR:
                // Hour
                if (showHours) {
                    left = drawHourItem(canvas, left, 2);
                }

                // Minute
                if (showMinutes) {
                    left = drawMinuteItem(canvas, left, 2);
                }

                // Second
                if (showSeconds) {
                    drawSecondItem(canvas, left, 2);
                }
                break;
            case TYPE_TIME_MINUTE:
                // Minute
                if (showMinutes) {
                    left = drawMinuteItem(canvas, left, 2);
                }

                // Second
                if (showSeconds) {
                    drawSecondItem(canvas, left, 2);
                }
                break;
            case TYPE_TIME_SECOND:
                // Second
                if (showSeconds) {
                    drawSecondItem(canvas, left, 2);
                }
                break;
        }
    }

    private float drawDayItem(Canvas canvas, float left, int minLength) {
        return drawTimeSuffixItem(canvas, left, getTimeString(mDays, minLength), mSuffixDay, mSuffixDayTextWidth);
    }

    private float drawHourItem(Canvas canvas, float left, int minLength) {
        return drawTimeSuffixItem(canvas, left, getTimeString(mHours, minLength), mSuffixHour, mSuffixHourTextWidth);
    }

    private float drawMinuteItem(Canvas canvas, float left, int minLength) {
        return drawTimeSuffixItem(canvas, left, getTimeString(mMinutes, minLength), mSuffixMinute, mSuffixMinuteTextWidth);
    }

    private float drawSecondItem(Canvas canvas, float left, int minLength) {
        return drawTimeSuffixItem(canvas, left, getTimeString(mSeconds, minLength), mSuffixSecond, mSuffixSecondTextWidth);
    }

    /**
     * Get the string of time.
     * If the length of time string is less than minLength, leading zeros will be padded on the left of time string.
     *
     * @param time
     * @param minLength The minimum length of time String.
     * @return
     */
    private String getTimeString(long time, int minLength) {
        if (minLength > 0) {
            return String.format("%0" + minLength + "d", time);
        }

        if (time > 0) {
            return String.valueOf(time);
        }

        return "";
    }

    private float drawTimeSuffixItem(Canvas canvas, float left, String timeString, String suffix, float suffixTextWidth) {
        if (isNullOrEmptyAfterTrim(timeString)) {
            return left;
        }

        // Draw time content.
        if (mTimeTextLetterBackgroundSpacing > 0) { // Split timeBackground.
            for (int i = 0; i < timeString.length(); i++) {
                // Draw time background.
                if (mTimeBackground != null) {
                    mTimeBackground.setBounds((int) left,
                            (int) (canvas.getHeight() - drawTimeBackgroundHeight) / 2,
                            (int) (left + drawTimeBackgroundWidth),
                            (int) (canvas.getHeight() + drawTimeBackgroundHeight) / 2);
                    mTimeBackground.draw(canvas);
                }

                // Draw time text.
                canvas.drawText(String.valueOf(timeString.charAt(i)),
                        left + drawTimeBackgroundPaddingLeft + (mTimeTextLetterMeasuredWidth) / 2,
//                        (canvas.getHeight() + mTimeTextMeasuredHeight) / 2,
                        mTimeTextBaseline + (canvas.getHeight() - drawTimeBackgroundHeight) / 2,
                        mTimeTextPaint);

                left += drawTimeBackgroundWidth;
                if (i < timeString.length() - 1) {
                    left += mTimeTextLetterBackgroundSpacing;
                }
            }
        } else { // Time text is a whole.
            measureTimeWidthWhenWhole(timeString.length());

            // Draw time background.
            if (mTimeBackground != null) {
                mTimeBackground.setBounds((int) left,
                        (int) (canvas.getHeight() - drawTimeBackgroundHeight) / 2,
                        (int) (left + drawTimeBackgroundWidth),
                        (int) (canvas.getHeight() + drawTimeBackgroundHeight) / 2);
                mTimeBackground.draw(canvas);
            }

            // Draw time text.
            left += drawTimeBackgroundPaddingLeft;
            for (int i = 0; i < timeString.length(); i++) {
                canvas.drawText(String.valueOf(timeString.charAt(i)),
                        left + (mTimeTextLetterMeasuredWidth) / 2,
//                        (canvas.getHeight() + mTimeTextMeasuredHeight) / 2,
                        mTimeTextBaseline + (canvas.getHeight() - drawTimeBackgroundHeight) / 2,
                        mTimeTextPaint);

                left += mTimeTextLetterMeasuredWidth;
                if (i < timeString.length() - 1) {
                    left += mTimeTextLetterSpacing;
                } else { // The last letter.
                    left += drawTimeBackgroundPaddingRight;
                }
            }
        }

        // Draw suffix content.
        left += mSuffixTextMargin;
        if (TextUtils.isEmpty(suffix)) {
            return left;
        }

        // Width values.
        if (mSuffixBackgroundWidth > 0) {
            if (mSuffixBackgroundWidth < suffixTextWidth) {
                drawSuffixBackgroundWidth = suffixTextWidth;
                drawSuffixBackgroundPaddingLeft = drawSuffixBackgroundPaddingRight = 0;
            } else {
                /*
                 * If the size of @suffixBackgroundWidth has been specified by users,
                 * the padding will be recalculated.
                 */
                drawSuffixBackgroundWidth = mSuffixBackgroundWidth;
                drawSuffixBackgroundPaddingLeft = drawSuffixBackgroundPaddingRight = (mSuffixBackgroundWidth - suffixTextWidth) / 2;
            }
        } else {
            /*
             * If the size of @suffixBackgroundWidth has not been specified,
             * the @suffixBackground will be drawn according to its @suffixTextWidth and @padding.
             */
            drawSuffixBackgroundWidth = suffixTextWidth + mSuffixBackgroundPaddingLeft + mSuffixBackgroundPaddingRight;
        }

        // Draw suffix background.
        if (mSuffixBackground != null) {
            mSuffixBackground.setBounds((int) left,
                    (int) (canvas.getHeight() - drawSuffixBackgroundHeight) / 2,
                    (int) (left + drawSuffixBackgroundWidth),
                    (int) (canvas.getHeight() + drawSuffixBackgroundHeight) / 2);
            mSuffixBackground.draw(canvas);
        }

        // Draw suffix text.
        float suffixBaseline = mSuffixTextBaseline + (canvas.getHeight() - drawSuffixBackgroundHeight) / 2;
        if (":".equals(suffix)) { // If the suffix text is ":", it will be centered.
            float deltY = 0;

            if (!TextUtils.isEmpty(suffix)) {
                Rect minRect = getTextBounds(suffix, mSuffixTextPaint);

                /*
                 * The size of Rect is different between different languages.
                 */
                if (mSuffixTextMeasuredHeight < minRect.height()) {
                    mSuffixTextMeasuredHeight = minRect.height();
                }

                /*
                 * The position of baseline is different between different languages.
                 */
                deltY = minRect.height() / 2 + minRect.top;
            }

            suffixBaseline = mMeasureTotalHeight / 2 - deltY;
        }
        canvas.drawText(suffix,
                left + drawSuffixBackgroundPaddingLeft + suffixTextWidth / 2,
                suffixBaseline,
                mSuffixTextPaint);

        left += drawSuffixBackgroundWidth + mSuffixTextMargin;

        return left;
    }

    private float measureTimeWidthWhenWhole(int letterCount) {
        if (letterCount < 1) {
            return 0;
        }

        if (mTimeBackgroundWidth > 0) {
            if (mTimeBackgroundWidth < letterCount * mTimeTextLetterMeasuredWidth + (letterCount - 1) * mTimeTextLetterSpacing) {
                drawTimeBackgroundWidth = letterCount * mTimeTextLetterMeasuredWidth + (letterCount - 1) * mTimeTextLetterSpacing;
                drawTimeBackgroundPaddingLeft = drawTimeBackgroundPaddingRight = 0;
            } else {
                /*
                 * If the size of @timeBackgroundWidth has been specified by users,
                 * the padding will be recalculated regardless of the values specified by users.
                 */
                drawTimeBackgroundWidth = mTimeBackgroundWidth;
                drawTimeBackgroundPaddingLeft = drawTimeBackgroundPaddingRight
                        = (mTimeBackgroundWidth - letterCount * mTimeTextLetterMeasuredWidth - (letterCount - 1) * mTimeTextLetterSpacing) / 2;
            }
        } else {
            /*
             * If the size of @timeBackgroundWidth has not been specified,
             * the @timeBackground will be drawn according to its @mTimeTextLetterMeasuredWidth and @padding.
             */
            drawTimeBackgroundWidth = letterCount * mTimeTextLetterMeasuredWidth
                    + (letterCount - 1) * mTimeTextLetterSpacing
                    + mTimeBackgroundPaddingLeft + mTimeBackgroundPaddingRight;
        }

        return drawTimeBackgroundWidth;
    }

    class MyCountDownTimer extends CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            refreshLayout();

            Log.i(TAG, "start - mSeconds = " + mSeconds);
        }

        @Override
        public void onFinish() {
            cancel();
            resetZero();
            refreshLayout();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            updateTimeByMillis(millisUntilFinished);
            refreshLayout();
        }
    }

    private void refreshLayout() {
        // TODO: 2018/3/7
//        postInvalidate();
        invalidate();
        requestLayout();
    }

    public void refreshView() {
        initView();
        updateTimeBySeconds();
        refreshLayout();
    }

    private void resetZero() {
        mDays = 0;
        mHours = 0;
        mMinutes = 0;
        mSeconds = 0;
        mRemainTotalSeconds = 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopCountdown();
    }

    private void stopCountdown() {
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
            myCountDownTimer = null;
        }
    }

    private boolean hasSetSuffix() {
        return hasSetSuffixDay && hasSetSuffixHour && hasSetSuffixMinute && hasSetSuffixSecond;
    }

    // ---------------------------- Getter start ----------------------------- //

    public boolean isIncludePad() {
        return mIncludePad;
    }

    /**
     * @return mTimeFormat
     */
    public String getTimeFormat() {
        return mTimeFormat;
    }

    public int getTimeTextSize() {
        return UIUtils.px2dp(getContext(), mTimeTextSize);
    }

    public int getTimeTextColor() {
        return mTimeTextColor;
    }

    public boolean isTimeTextBold() {
        return isTimeTextBold;
    }

    public float getTimeTextLetterSpacing() {
        return UIUtils.px2dp(getContext(), mTimeTextLetterSpacing);
    }

    public float getTimeTextLetterBackgroundSpacing() {
        return UIUtils.px2dp(getContext(), mTimeTextLetterBackgroundSpacing);
    }

    public Drawable getTimeBackground() {
        return mTimeBackground;
    }

    public float getTimeBackgroundWidth() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundWidth);
    }

    public float getTimeBackgroundHeight() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundHeight);
    }

    public float getTimeBackgroundPadding() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundPadding);
    }

    public float getTimeBackgroundPaddingX() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundPaddingX);
    }

    public float getTimeBackgroundPaddingY() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundPaddingY);
    }

    public float getTimeBackgroundPaddingLeft() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundPaddingLeft);
    }

    public float getTimeBackgroundPaddingRight() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundPaddingRight);
    }

    public float getTimeBackgroundPaddingTop() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundPaddingTop);
    }

    public float getTimeBackgroundPaddingBottom() {
        return UIUtils.px2dp(getContext(), mTimeBackgroundPaddingBottom);
    }

    public int getSuffixTextSize() {
        return UIUtils.px2sp(getContext(), mSuffixTextSize);
    }

    public int getSuffixTextColor() {
        return mSuffixTextColor;
    }

    public boolean isSuffixTextBold() {
        return isSuffixTextBold;
    }

    public float getSuffixTextMargin() {
        return UIUtils.px2dp(getContext(), mSuffixTextMargin);
    }

    public Drawable getSuffixBackground() {
        return mSuffixBackground;
    }

    public float getSuffixBackgroundWidth() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundWidth);
    }

    public float getSuffixBackgroundHeight() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundHeight);
    }

    public float getSuffixBackgroundPadding() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundPadding);
    }

    public float getSuffixBackgroundPaddingX() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundPaddingX);
    }

    public float getSuffixBackgroundPaddingY() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundPaddingY);
    }

    public float getSuffixBackgroundPaddingLeft() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundPaddingLeft);
    }

    public float getSuffixBackgroundPaddingRight() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundPaddingRight);
    }

    public float getSuffixBackgroundPaddingTop() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundPaddingTop);
    }

    public float getSuffixBackgroundPaddingBottom() {
        return UIUtils.px2dp(getContext(), mSuffixBackgroundPaddingBottom);
    }

    public String getSuffixDay() {
        return mSuffixDay;
    }

    public String getSuffixHour() {
        return mSuffixHour;
    }

    public String getSuffixMinute() {
        return mSuffixMinute;
    }

    public String getSuffixSecond() {
        return mSuffixSecond;
    }

    public long getRemainTotalSeconds() {
        return mRemainTotalSeconds;
    }

    public boolean isShowDays() {
        return showDays;
    }

    public boolean isShowHours() {
        return showHours;
    }

    public boolean isShowMinutes() {
        return showMinutes;
    }

    public boolean isShowSeconds() {
        return showSeconds;
    }

    // ---------------------------- Getter end ----------------------------- //

    // ---------------------------- Setter start ----------------------------- //

    public void setIncludePad(boolean includePad) {
        this.mIncludePad = includePad;
    }

    public void setTimeTextSize(int timeTextSize) {
        this.mTimeTextSize = UIUtils.sp2px(getContext(), timeTextSize);
    }

    public void setTimeTextColor(int timeTextColor) {
        this.mTimeTextColor = timeTextColor;
    }

    public void setTimeTextBold(boolean timeTextBold) {
        isTimeTextBold = timeTextBold;
    }

    public void setTimeTextLetterSpacing(float timeTextLetterSpacing) {
        this.mTimeTextLetterSpacing = UIUtils.dp2px(getContext(), timeTextLetterSpacing);
    }

    public void setTimeTextLetterBackgroundSpacing(float timeTextLetterBackgroundSpacing) {
        this.mTimeTextLetterBackgroundSpacing = UIUtils.dp2px(getContext(), timeTextLetterBackgroundSpacing);
    }

    public void setTimeBackground(Drawable timeBackground) {
        this.mTimeBackground = timeBackground;
    }

    public void setTimeBackgroundWidth(float timeBackgroundWidth) {
        this.mTimeBackgroundWidth = UIUtils.dp2px(getContext(), timeBackgroundWidth);
    }

    public void setTimeBackgroundHeight(float timeBackgroundHeight) {
        this.mTimeBackgroundHeight = UIUtils.dp2px(getContext(), timeBackgroundHeight);
    }

    public void setTimeBackgroundPadding(float timeBackgroundPadding) {
        hasSetTimeBackgroundPadding = true;
        this.mTimeBackgroundPadding = UIUtils.dp2px(getContext(), timeBackgroundPadding);
    }

    public void setTimeBackgroundPaddingX(float timeBackgroundPaddingX) {
        hasSetTimeBackgroundPaddingX = true;
        this.mTimeBackgroundPaddingX = UIUtils.dp2px(getContext(), timeBackgroundPaddingX);
    }

    public void setTimeBackgroundPaddingY(float timeBackgroundPaddingY) {
        hasSetTimeBackgroundPaddingY = true;
        this.mTimeBackgroundPaddingY = UIUtils.dp2px(getContext(), timeBackgroundPaddingY);
    }

    public void setTimeBackgroundPaddingLeft(float timeBackgroundPaddingLeft) {
        this.mTimeBackgroundPaddingLeft = UIUtils.dp2px(getContext(), timeBackgroundPaddingLeft);
    }

    public void setTimeBackgroundPaddingRight(float timeBackgroundPaddingRight) {
        this.mTimeBackgroundPaddingRight = UIUtils.dp2px(getContext(), timeBackgroundPaddingRight);
    }

    public void setTimeBackgroundPaddingTop(float timeBackgroundPaddingTop) {
        this.mTimeBackgroundPaddingTop = UIUtils.dp2px(getContext(), timeBackgroundPaddingTop);
    }

    public void setTimeBackgroundPaddingBottom(float timeBackgroundPaddingBottom) {
        this.mTimeBackgroundPaddingBottom = UIUtils.dp2px(getContext(), timeBackgroundPaddingBottom);
    }

    public void setSuffixTextSize(int suffixTextSize) {
        this.mSuffixTextSize = UIUtils.sp2px(getContext(), suffixTextSize);
    }

    public void setSuffixTextColor(int suffixTextColor) {
        this.mSuffixTextColor = suffixTextColor;
    }

    public void setSuffixTextBold(boolean suffixTextBold) {
        isSuffixTextBold = suffixTextBold;
    }

    public void setSuffixTextMargin(float suffixTextMargin) {
        this.mSuffixTextMargin = UIUtils.dp2px(getContext(), suffixTextMargin);
    }

    public void setSuffixBackground(Drawable suffixBackground) {
        this.mSuffixBackground = suffixBackground;
    }

    public void setSuffixBackgroundWidth(float suffixBackgroundWidth) {
        this.mSuffixBackgroundWidth = UIUtils.dp2px(getContext(), suffixBackgroundWidth);
    }

    public void setSuffixBackgroundHeight(float suffixBackgroundHeight) {
        this.mSuffixBackgroundHeight = UIUtils.dp2px(getContext(), suffixBackgroundHeight);
    }

    public void setSuffixBackgroundPadding(float suffixBackgroundPadding) {
        this.mSuffixBackgroundPadding = UIUtils.dp2px(getContext(), suffixBackgroundPadding);
        hasSetSuffixBackgroundPadding = true;
    }

    public void setSuffixBackgroundPaddingX(float suffixBackgroundPaddingX) {
        this.mSuffixBackgroundPaddingX = UIUtils.dp2px(getContext(), suffixBackgroundPaddingX);
        hasSetSuffixBackgroundPaddingX = true;
    }

    public void setSuffixBackgroundPaddingY(float suffixBackgroundPaddingY) {
        this.mSuffixBackgroundPaddingY = UIUtils.dp2px(getContext(), suffixBackgroundPaddingY);
        hasSetSuffixBackgroundPaddingY = true;
    }

    public void setSuffixBackgroundPaddingLeft(float suffixBackgroundPaddingLeft) {
        this.mSuffixBackgroundPaddingLeft = UIUtils.dp2px(getContext(), suffixBackgroundPaddingLeft);
    }

    public void setSuffixBackgroundPaddingRight(float suffixBackgroundPaddingRight) {
        this.mSuffixBackgroundPaddingRight = UIUtils.dp2px(getContext(), suffixBackgroundPaddingRight);
    }

    public void setSuffixBackgroundPaddingTop(float suffixBackgroundPaddingTop) {
        this.mSuffixBackgroundPaddingTop = UIUtils.dp2px(getContext(), suffixBackgroundPaddingTop);
    }

    public void setSuffixBackgroundPaddingBottom(float suffixBackgroundPaddingBottom) {
        this.mSuffixBackgroundPaddingBottom = UIUtils.dp2px(getContext(), suffixBackgroundPaddingBottom);
    }

    public void setSuffixDay(String suffixDay) {
        this.mSuffixDay = suffixDay;
        hasSetSuffixDay = true;
        if (showDays) {
            hasSetShowDays = true;
        }
    }

    public void setSuffixHour(String suffixHour) {
        this.mSuffixHour = suffixHour;
        hasSetSuffixHour = true;
        if (showHours) {
            hasSetShowHours = true;
        }
    }

    public void setSuffixMinute(String suffixMinute) {
        this.mSuffixMinute = suffixMinute;
        hasSetSuffixMinute = true;
        if (showMinutes) {
            hasSetShowMinutes = true;
        }
    }

    public void setSuffixSecond(String suffixSecond) {
        this.mSuffixSecond = suffixSecond;
        hasSetSuffixSecond = true;
        if (showSeconds) {
            hasSetShowSeconds = true;
        }
    }

    public void setShowDays(boolean showDays) {
        this.showDays = showDays;
        hasSetShowDays = true;
    }

    public void setShowHours(boolean showHours) {
        this.showHours = showHours;
        hasSetShowHours = true;
    }

    public void setShowMinutes(boolean showMinutes) {
        this.showMinutes = showMinutes;
        hasSetShowMinutes = true;
    }

    public void setShowSeconds(boolean showSeconds) {
        this.showSeconds = showSeconds;
        hasSetShowSeconds = true;
    }

    public void setTimeFormat(String timeFormat) {
        this.mTimeFormat = timeFormat;

        resetSuffixValues();
    }

    private void resetSuffixValues() {
        Log.d("test", "resetSuffixValues");

        showDays = false;
        showHours = false;
        showMinutes = false;
        showSeconds = false;

        hasSetShowDays = false;
        hasSetShowHours = false;
        hasSetShowMinutes = false;
        hasSetShowSeconds = false;

        hasSetSuffixDay = false;
        hasSetSuffixHour = false;
        hasSetSuffixMinute = false;
        hasSetSuffixSecond = false;

        mSuffixDay = "";
        mSuffixHour = "";
        mSuffixMinute = "";
        mSuffixSecond = "";
    }

    public static boolean isNullOrEmptyAfterTrim(String s) {
        if (s == null || "".equals(s.trim()) || s.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}