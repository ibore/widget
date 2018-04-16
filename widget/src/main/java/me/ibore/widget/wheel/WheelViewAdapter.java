package me.ibore.widget.wheel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import me.ibore.widget.WheelView;
import me.ibore.widget.recycler.RecyclerHolder;

public class WheelViewAdapter extends RecyclerView.Adapter<RecyclerHolder> {

    /**
     * recyclerview 布局方向
     */
    final int orientation;
    /**
     * 每个item大小
     */
    final int itemSize;
    /**
     * wheelview 滑动时上或下的空白数量
     */
    final int itemCount;
    /**
     * wheelview 滑动时上或下空白总数量
     */
    private final int totalItemCount;

    public WheelView.WheelAdapter adapter;

    public WheelViewAdapter(int orientation, int itemSize, int itemCount) {
        this.orientation = orientation;
        this.itemSize = itemSize;
        this.itemCount = itemCount;
        this.totalItemCount = itemCount * 2;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return totalItemCount + (adapter == null ? 0 : adapter.getItemCount());
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new View(parent.getContext());
        view.setLayoutParams(WheelUtils.createLayoutParams(orientation, itemSize));
        return RecyclerHolder.create(view);
    }

    String getItemString(int index) {
        return adapter == null ? "" : adapter.getItem(index);
    }

}

