package me.ibore.widget.recycler;

import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public abstract class MultiAdapter<T> extends RecyclerAdapter<T, RecyclerHolder> {

    protected abstract int getMultiItemType(T t);

    protected abstract int getMultiItemId(int viewType);

    protected abstract void convertMulti(RecyclerHolder holder, T t, int itemViewType);


    @Override
    public int getRecyclerItemViewType(List<T> mDatas, int position) {
        return getMultiItemType(mDatas.get(position));
    }

    @Override
    protected RecyclerHolder onCreateRecyclerHolder(ViewGroup parent, int viewType) {
        return RecyclerHolder.create(parent, getMultiItemId(viewType));
    }

    @Override
    protected void onBindRecyclerHolder(RecyclerHolder holder, List<T> mDatas, int position) {
        convertMulti(holder, mDatas.get(position), holder.getItemViewType());
    }
}
