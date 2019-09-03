package me.ibore.widget.state;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class StateView extends ConstraintLayout implements IStateView<StateHelper> {

    private StateHelper<StateView> mStateHelper;

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStateHelper = new StateHelper<>(context, this, attrs);
    }

    @Override
    public StateHelper getStateHelper() {
        return mStateHelper;
    }
}
