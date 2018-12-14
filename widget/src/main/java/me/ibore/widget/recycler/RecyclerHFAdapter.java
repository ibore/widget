package me.ibore.widget.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-11-26 00:24
 * website: ibore.me
 */

public abstract class RecyclerHFAdapter<T, VH extends RecyclerHolder> extends RecyclerAdapter<T, VH> {

    public static final int TYPE_LOAD = 0x1000;
    public static final int TYPE_HEADER = 0x1001;
    public static final int TYPE_FOOTER = 0x1002;
    public static final int TYPE_LOAD_MORE = 0x1003;
    private FrameLayout mLoadView;
    private FrameLayout mLoadMoreView;
    private LinearLayout mHeaderView;
    private LinearLayout mFooterView;
    private boolean mIsShowContent = false;

    private OnLoadListener mOnLoadListener;
    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.mOnLoadListener = onLoadListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setDatas(List<T> datas) {
        if (null != datas && datas.size() > 0) {
            mIsShowContent = true;
            if (null != mLoadMoreView) showLoadingMoreView();
        } else {
            if (null != mLoadView) showEmptyView();
        }
        super.setDatas(datas);
    }

    @Override
    public void addData(T data) {
        super.addData(data);
    }

    public void clearDatas() {
        mIsShowContent = false;
        super.clearDatas();
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (hasLoadView()) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
            if (isHeaderView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
            if (isFooterView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
            if (isLoadMoreView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (null == layoutManager) return;
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (hasLoadView()) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (isHeaderView(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (isFooterView(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (isLoadMoreView(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        onScrollListener(recyclerView, layoutManager);
    }

    private void onScrollListener(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (hasLoadMoreView() && mOnLoadMoreListener != null
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLoadMoreView.getChildAt(0).getVisibility() == View.VISIBLE) {
                    int lastVisibleItem = findLastCompletelyVisibleItemPosition(layoutManager);
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (hasLoadMoreView() && mOnLoadMoreListener != null
                        && mLoadMoreView.getChildAt(0).getVisibility() == View.VISIBLE) {
                    int lastVisibleItem = findLastCompletelyVisibleItemPosition(layoutManager);
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    private int findLastCompletelyVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            int max = lastVisibleItemPositions[0];
            for (int value : lastVisibleItemPositions) {
                if (value > max) max = value;
            }
            return max;
        }
        return -1;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOAD:
                return (VH) RecyclerHolder.create(mLoadView);
            case TYPE_HEADER:
                return (VH) RecyclerHolder.create(mHeaderView);
            case TYPE_FOOTER:
                return (VH) RecyclerHolder.create(mFooterView);
            case TYPE_LOAD_MORE:
                return (VH) RecyclerHolder.create(mLoadMoreView);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        if (hasLoadView()) return;
        if (isHeaderView(position)) return;
        if (isFooterView(position)) return;
        if (isLoadMoreView(position)) return;
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        int allPosition = 0;
        if (hasLoadView()) {
            allPosition++;
        } else {
            allPosition = allPosition + getRecyclerItemCount();
            if (hasHeaderView()) allPosition++;
            if (hasFooterView()) allPosition++;
            if (hasLoadMoreView()) allPosition++;
        }
        return allPosition;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasLoadView()) return TYPE_LOAD;

        if (isHeaderView(position)) return TYPE_HEADER;

        if (isFooterView(position)) return TYPE_FOOTER;

        if (isLoadMoreView(position)) return TYPE_LOAD_MORE;

        return super.getItemViewType(position);
    }

    /**************************************** LoadView ***************************************/

    public void setLoadView(Context context, int loadingView, int emptyView, int errorView) {
        if (null == mLoadView) {
            mLoadView = new FrameLayout(context);
            mLoadView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else mLoadView.removeAllViews();
        mLoadView.addView(LayoutInflater.from(context).inflate(loadingView, mLoadView, false));
        mLoadView.addView(LayoutInflater.from(context).inflate(emptyView, mLoadView, false));
        mLoadView.addView(LayoutInflater.from(context).inflate(errorView, mLoadView, false));
        showLoadingView();
    }

    public void removeLoadView() {
        mLoadView = null;
        notifyDataSetChanged();
    }

    public boolean hasLoadView() {
        if (!mIsShowContent && null != mLoadView) {
            return true;
        } else {
            return false;
        }
    }

    public View showLoadingView() {
        clearDatas();
        return visibleView(mLoadView, 0, true);
    }

    public void showContentView() {
        mIsShowContent = true;
        notifyDataSetChanged();
    }

    public View showEmptyView() {
        clearDatas();
        return visibleView(mLoadView, 1, true);
    }

    public View showErrorView() {
        clearDatas();
        View view = visibleView(mLoadView, 2, true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnLoadListener) mOnLoadListener.onLoadError();
            }
        });
        return view;
    }

    public void setLoadMoreView(Context context, int loadingView, int emptyView, int errorView) {
        if (null == mLoadMoreView) {
            mLoadMoreView = new FrameLayout(context);
            mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else mLoadMoreView.removeAllViews();
        mLoadMoreView.addView(LayoutInflater.from(context).inflate(loadingView, mLoadMoreView, false));
        mLoadMoreView.addView(LayoutInflater.from(context).inflate(emptyView, mLoadMoreView, false));
        mLoadMoreView.addView(LayoutInflater.from(context).inflate(errorView, mLoadMoreView, false));
        showLoadingMoreView();
    }

    public void removeLoadMoreView() {
        mLoadMoreView = null;
        notifyDataSetChanged();
    }

    public boolean hasLoadMoreView() {
        if (mIsShowContent && null != mLoadMoreView) {
            return true;
        } else {
            return 0 != getRecyclerItemCount() && null != mLoadMoreView;
        }
    }

    public boolean isLoadMoreView(int position) {
        if (hasLoadMoreView()) {
            int otherPosition = 1;
            if (hasHeaderView()) otherPosition++;
            if (hasFooterView()) otherPosition++;
            return position + 1 == getRecyclerItemCount() + otherPosition;
        }
        return false;
    }

    public View showLoadingMoreView() {
        return visibleView(mLoadMoreView, 0, false);
    }

    public View showEmptyMoreView() {
        return visibleView(mLoadMoreView, 1, false);
    }

    public View showErrorMoreView() {
        View view = visibleView(mLoadMoreView, 2, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnLoadMoreListener) mOnLoadMoreListener.onLoadError();
            }
        });
        return view;
    }

    private View visibleView(FrameLayout frameLayout, int position, boolean isLoadView) {
        if (null == frameLayout) {
            if (isLoadView) {
                throw new NullPointerException("Please invoking setLoadView() initialize LoadView");
            } else {
                throw new NullPointerException("Please invoking setLoadMoreView() initialize LoadMoreView");
            }
        }
        View view = null;
        for (int i = 0; i < frameLayout.getChildCount(); i++) {
            if (position == i) view = frameLayout.getChildAt(i);
            frameLayout.getChildAt(i).setVisibility(i == position ? View.VISIBLE : View.INVISIBLE);
        }
        return view;
    }

    public void addHeaderView(View headerView) {
        mHeaderView = new LinearLayout(headerView.getContext());
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

    @Override
    protected boolean hasHeaderView() {
        return null != mHeaderView;
    }

    public boolean isHeaderView(int position) {
        if (hasHeaderView()) {
            return position == 0;
        }
        return false;
    }

    public void addFooterView(View footerView) {
        mFooterView = new LinearLayout(footerView.getContext());
        mFooterView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

    public interface OnLoadListener {
        void onLoadEmpty();

        void onLoadError();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();

        void onLoadError();
    }

}
