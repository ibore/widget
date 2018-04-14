package me.ibore.widget.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

public abstract class HFRecyclerAdapter<T, VH extends RecyclerHolder> extends RecyclerAdapter<T, VH> {

    private static final int TYPE_HEADER = 0x1001;
    private static final int TYPE_FOOTER = 0x1002;

    private LinearLayout mHeaderView;
    private LinearLayout mFooterView;

    public void addData(T data) {
        getDatas().add(data);
        notifyItemChanged(getDatas().size() - (hasHeaderView() ? 2 : 1));
    }

    public void addData(T data, int position) {
        getDatas().add(position, data);
        notifyItemInserted(hasHeaderView() ? position + 1 : position);
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (isHeaderView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
            if (isFooterView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeaderView(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (isFooterView(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerHolder holder;
        switch (viewType) {
            case TYPE_HEADER:
                holder = RecyclerHolder.create(mHeaderView);
                break;
            case TYPE_FOOTER:
                holder = RecyclerHolder.create(mFooterView);
                break;
            default:
                holder = super.onCreateViewHolder(parent, viewType);
                break;
        }
        return (VH) holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (isHeaderView(position)) return;
        if (isFooterView(position)) return;
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int allPosition = super.getItemCount();
        if (!hasLoadView()) {
            if (hasHeaderView()) allPosition++;
            if (hasFooterView()) allPosition++;
        }
        return allPosition;
    }

    @Override
    public int getRecyclerPosition(int position) {
        if (hasHeaderView()) {
            position = position - 1;
        }
        return super.getRecyclerPosition(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) return TYPE_HEADER;
        if (isFooterView(position)) return TYPE_FOOTER;
        return super.getItemViewType(position);
    }

    @Override
    public boolean isLoadMoreView(int position) {
        if (hasLoadMoreView()) {
            if (hasHeaderView()) position--;
            if (hasFooterView()) position--;
        }
        return super.isLoadMoreView(position);
    }

    public void addHeaderView(View headerView) {
        mHeaderView = new LinearLayout(headerView.getContext());
        mHeaderView.setOrientation(LinearLayout.VERTICAL);
        mHeaderView.addView(headerView);
    }

    public View getHeaderView() {
        if (null == mHeaderView) throw new NullPointerException("The HeaderView can't empty View");
        return mHeaderView;
    }

    public void removeHeaderView() {
        if (hasHeaderView()) mHeaderView.removeAllViews();
        mHeaderView = null;
        notifyDataSetChanged();
    }

    public boolean hasHeaderView() {
        return null != mHeaderView;
    }

    public boolean isHeaderView(int position) {
        if (hasHeaderView()) {
            int otherPosition = 1;
            return position + 1 == otherPosition;
        }
        return false;
    }

    public void addFooterView(View footerView) {
        mFooterView = new LinearLayout(footerView.getContext());
        mFooterView.setOrientation(LinearLayout.VERTICAL);
        mFooterView.addView(footerView);
    }

    public View getFooterView() {
        if (null == mFooterView) throw new NullPointerException("The FooterView can't empty View");
        return mFooterView;
    }

    public void removeFooterView() {
        if (hasHeaderView()) mFooterView.removeAllViews();
        mFooterView = null;
        notifyDataSetChanged();
    }

    public boolean hasFooterView() {
        return null != mFooterView;
    }

    public boolean isFooterView(int position) {
        if (hasFooterView()) {
            int otherPosition = 1;
            if (hasHeaderView()) otherPosition++;
            otherPosition = otherPosition + getRecyclerItemCount();
            return position + 1 == otherPosition;
        }
        return false;
    }

}
