package me.ibore.widget.recycler;

import android.view.ViewGroup;

import java.util.List;

public abstract class CommonAdapter<T> extends RecyclerAdapter<T, RecyclerHolder> {

    @Override
    protected RecyclerHolder onCreateRecyclerHolder(ViewGroup parent, int viewType) {
        return RecyclerHolder.create(parent, getLayoutId());
    }

    @Override
    protected void onBindRecyclerHolder(RecyclerHolder holder, T t, int position) {
        convert(holder, t, position);
    }

    protected abstract int getLayoutId();

    protected abstract void convert(RecyclerHolder holder, T t, int position);

}
