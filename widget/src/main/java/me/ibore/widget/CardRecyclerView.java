package me.ibore.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import me.ibore.widget.recycler.RecyclerAdapter;
import me.ibore.widget.recycler.RecyclerHolder;


/**
 * Created by Administrator on 2017/12/26.
 */

public class CardRecyclerView extends RecyclerView {

    public CardRecyclerView(Context context) {
        this(context, null);
    }

    public CardRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getVisibleCardCount() {
        if (getAdapter().getVisibleCardCount() <= 0)
            throw new IllegalArgumentException("visibleCardCount must be over zero !!!");
        return getAdapter().getVisibleCardCount();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof CardAdapter)
            super.setAdapter(adapter);
        else
            throw new IllegalArgumentException("");
    }

    @Override
    public CardAdapter getAdapter() {
        return (CardAdapter) super.getAdapter();
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof CardManager) {
            super.setLayoutManager(layout);
        }

        else
            throw new IllegalArgumentException("");
    }

    @Override
    public CardManager getLayoutManager() {
        return (CardManager) super.getLayoutManager();
    }

    public void dropCard(int orientation) {
        if (getChildCount() > 0)
            getLayoutManager().dropCardNoTouch(orientation);
    }

    public void dropCard() {
        if (getAdapter().isEnableDataRecycle()) {
            getAdapter().recycleData();
        } else
            getAdapter().delItem(0);
    }

    public static abstract class CardAdapter<T> extends RecyclerAdapter<T, RecyclerHolder> {

        private List<T> tempDatas;

        public CardAdapter() {
            tempDatas = new ArrayList<>();
        }

        protected void delItem(int position) {
            if (null != getDatas() && getDatas().size() > 0) {
                getDatas().remove(position);
                notifyItemRemoved(position);
            }
        }

        protected void recycleData() {
            if (isEnableDataRecycle()) {
                if (getDatas().size() > getVisibleCardCount() + 1) {
                    tempDatas.add(getDatas().get(0));
                    delItem(0);
                } else {
                    tempDatas.add(getDatas().get(0));
                    getDatas().remove(0);
                    notifyItemRemoved(0);
                    int start = getDatas().size();
                    getDatas().addAll(tempDatas);
                    notifyItemRangeInserted(start, tempDatas.size());
                    tempDatas.clear();
                }
            }
        }

        public int getVisibleCardCount() {
            return 3;
        }

        protected boolean isEnableDataRecycle() {
            return false;
        }

    }

    public static abstract class CardManager extends RecyclerView.LayoutManager implements OnTouchListener {

        protected int mScreenWidth;
        protected int mScreenHeight;
        protected OnCardDragListener mListener;
        protected CardRecyclerView mRecyclerView;

        public CardManager(Context context, CardRecyclerView recyclerView) {
            this.mRecyclerView = recyclerView;
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = wm.getDefaultDisplay().getWidth();
            mScreenHeight = wm.getDefaultDisplay().getHeight();
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
        }

        public abstract void onLayoutCards(RecyclerView.Recycler recycler, RecyclerView.State state);

        public abstract void dropCardNoTouch(int orientation);

        @Override
        public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            if (getItemCount() <= 0) {
                removeAndRecycleAllViews(recycler);
                return;
            }
            // remove all attached child views
            detachAndScrapAttachedViews(recycler);
            // re-layout
            onLayoutCards(recycler, state);

        }
        public void setOnCardDragListener(OnCardDragListener listener) {
            this.mListener = listener;
        }
    }

    public interface OnCardDragListener {

        void onCardDrag(View view, boolean isDragging, boolean isDragged, float offsetX, float offsetY);

    }

}
