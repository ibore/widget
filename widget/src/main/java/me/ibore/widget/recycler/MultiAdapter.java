package me.ibore.widget.recycler;

import android.view.ViewGroup;

import java.util.List;

public abstract class MultiAdapter<T> extends RecyclerAdapter<T, RecyclerHolder> {

    protected abstract int getMultiItemType(T t);

    protected abstract int getMultiItemId(int viewType);

    protected abstract void convertMulti(RecyclerHolder holder, T t, int position, int itemViewType);

    @Override
    protected void onBindRecyclerHolder(RecyclerHolder holder, T t, int position) {
        convertMulti(holder, t, position, getMultiItemType(t));
    }

    @Override
    protected RecyclerHolder onCreateRecyclerHolder(ViewGroup parent, int viewType) {
        return RecyclerHolder.create(parent, getMultiItemId(viewType));
    }
    
    @Override
    public int getRecyclerItemViewType(T t, int position) {
        return getMultiItemType(t);
    }
}
