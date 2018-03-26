package me.ibore.widget.recycler;

import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class CommonAdapter<T> extends RecyclerAdapter<T, RecyclerHolder> {

    @Override
    protected RecyclerHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        return RecyclerHolder.create(parent, getLayoutId());
    }

    @Override
    protected void onBindRecyclerViewHolder(RecyclerHolder holder, List<T> mDatas, int position) {
        convert(holder, mDatas.get(position), position);
    }

    protected abstract int getLayoutId();

    protected abstract void convert(RecyclerHolder holder, T t, int position);

}
