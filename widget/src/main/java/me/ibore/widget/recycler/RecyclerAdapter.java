package me.ibore.widget.recycler;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-11-26 00:24
 * website: ibore.me
 */

public abstract class RecyclerAdapter<T, VH extends RecyclerHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mDatas;
    private static final int TYPE_LOAD = 0x1000;
    private static final int TYPE_HEADER = 0x1001;
    private static final int TYPE_FOOTER = 0x1002;
    private static final int TYPE_LOADMORE = 0x1003;
    private FrameLayout mLoadView;
    private FrameLayout mLoadMoreView;
    private LinearLayout mHeaderView;
    private LinearLayout mFooterView;
    private boolean mIsShowContent = false;

    private AnimatorType mAnimatorType = AnimatorType.NOANIMATOR;
    private int mLastPosition = -1;
    private boolean isAnimatorFirstOnly = true;
    private long mAnimatorDuration = 300;
    private Interpolator mAnimatorInterpolator = new LinearInterpolator();

    public enum AnimatorType {
        NOANIMATOR, SCALEIN, SLIDEINTOP, SLIDEINBOTTOM, SLIDEINLEFT, SLIDEINRIGHT
    }

    public RecyclerAdapter() {
        this.mDatas = new ArrayList<>();
    }

    public void setDatas(List<T> datas) {
        if (null == datas) {
            clearDatas();
        } else {
            mDatas = datas;
            if (mDatas.size() > 0) {
                mIsShowContent = true;
                if (null != mLoadMoreView) showLoadingMoreView();
            } else {
                if (null != mLoadView) showEmptyView();
            }
        }
        notifyDataSetChanged();
    }
    public List<T> getDatas() {
        return mDatas;
    }

    public T getData(int postion) {
        return mDatas.get(postion);
    }

    public void addData(T data) {
        mDatas.add(data);
        notifyItemChanged(mDatas.size() - 1);
    }

    public void addData(T data, int postion) {
        mDatas.add(postion, data);
        notifyItemInserted(hasHeaderView() ? postion + 1 : postion);
    }

    public void addDatas(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearDatas() {
        mDatas.clear();
        mIsShowContent = false;
        notifyDataSetChanged();
    }

    public void setAnimatorFirstOnly(boolean isFirstOnly) {
        this.isAnimatorFirstOnly = isFirstOnly;
    }

    public void setAnimator(AnimatorType animatorType){
        this.setAnimator(animatorType, 300);
    }

    public void setAnimator(AnimatorType animatorType, long duration){
        this.setAnimator(animatorType, duration, null);
    }

    public void setAnimator(AnimatorType animatorType, long duration, Interpolator value){
        this.mAnimatorType = animatorType;
        this.mAnimatorDuration = duration;
        this.mAnimatorInterpolator = value;
    }

    public void clearAnimator(View v) {
        ViewCompat.setAlpha(v, 1);
        ViewCompat.setScaleY(v, 1);
        ViewCompat.setScaleX(v, 1);
        ViewCompat.setTranslationY(v, 0);
        ViewCompat.setTranslationX(v, 0);
        ViewCompat.setRotation(v, 0);
        ViewCompat.setRotationY(v, 0);
        ViewCompat.setRotationX(v, 0);
        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
        ViewCompat.animate(v).setInterpolator(null).setStartDelay(0);
    }

    protected Animator[] getAnimators(View view) {
        Animator[] animators;
        switch (mAnimatorType) {
            case SCALEIN:
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
                animators = new ObjectAnimator[] { scaleX, scaleY };
                break;
            case SLIDEINTOP:
                animators = new Animator[] {
                        ObjectAnimator.ofFloat(view, "translationY", -view.getMeasuredHeight(), 0)
                };
                break;
            case SLIDEINBOTTOM:
                animators = new Animator[] {
                        ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
                };
                break;
            case SLIDEINLEFT:
                animators = new Animator[] {
                        ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
                };
                break;
            case SLIDEINRIGHT:
                animators = new Animator[] {
                        ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
                };
                break;
            case NOANIMATOR:
            default:
                animators = new Animator[0];
                break;
        }
        return animators;
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (isLoadView(holder.getLayoutPosition())) {
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
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isLoadView(position)) {
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
        RecyclerHolder holder;
        switch (viewType) {
            case TYPE_LOAD:
                holder = RecyclerHolder.create(mLoadView);
                break;
            case TYPE_HEADER:
                holder = RecyclerHolder.create(mHeaderView);
                break;
            case TYPE_FOOTER:
                holder = RecyclerHolder.create(mFooterView);
                break;
            case TYPE_LOADMORE:
                holder = RecyclerHolder.create(mLoadMoreView);
                break;
            default:
                holder = onCreateRecyclerViewHolder(parent, viewType);
                break;
        }
        return (VH) holder;
    }

    protected abstract RecyclerHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindRecyclerViewHolder(VH holder, List<T> mDatas, int position);

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        if (isLoadView(position)) return;
        if (isHeaderView(position)) return;
        if (isFooterView(position)) return;
        if (isLoadMoreView(position)) return;
        if (null != onItemClickListener) {
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder, hasHeaderView() ? position - 1 : position);
                }
            });
        }
        if (null != onItemLongClickListener){
            holder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClickListener.onLongClick(holder, hasHeaderView() ? position - 1 : position);
                }
            });
        }
        onBindRecyclerViewHolder(holder, mDatas, hasHeaderView() ? position - 1 : position);
        int adapterPosition = holder.getAdapterPosition();
        if (!isAnimatorFirstOnly || adapterPosition > mLastPosition) {
            for (Animator anim : getAnimators(holder.itemView)) {
                anim.setDuration(mAnimatorDuration).start();
                anim.setInterpolator(mAnimatorInterpolator);
            }
            mLastPosition = adapterPosition;
        } else {
            clearAnimator(holder.itemView);
        }
    }

    @Override
    public int getItemCount() {
        int allPostion = 0;
        if (hasLoadView()) {
            allPostion++;
        } else {
            allPostion = allPostion + getRecyclerItemCount();
            if (hasHeaderView()) allPostion++;
            if (hasFooterView()) allPostion++;
            if (hasLoadMoreView()) allPostion++;
        }
        return allPostion;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadView(position)) return TYPE_LOAD;

        if (isHeaderView(position)) return TYPE_HEADER;

        if (isFooterView(position)) return TYPE_FOOTER;

        if (isLoadMoreView(position)) return TYPE_LOADMORE;

        return getRecyclerItemViewType(mDatas, position);
    }

    public int getRecyclerItemCount() {
        if (null != mDatas) {
            return mDatas.size();
        }
        return 0;
    }

    public int getRecyclerItemViewType(List<T> mDatas, int position) {
        return 0;
    }

    /**************************************** LoadView ***************************************/

    public void setLoadView(Context context, int loadingView, int emptyView, int errorView) {
        if (null == mLoadView) {
            mLoadView = new FrameLayout(context);
            mLoadView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else mLoadView.removeAllViews();
        mLoadView.addView(LayoutInflater.from(context).inflate(loadingView, null));
        mLoadView.addView(LayoutInflater.from(context).inflate(emptyView, null));
        mLoadView.addView(LayoutInflater.from(context).inflate(errorView, null));
        showLoadingView();
    }

    public boolean hasLoadView() {
        if (!mIsShowContent && null != mLoadView) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLoadView(int position) {
        return hasLoadView();
    }

    public View showLoadingView() {
        clearDatas();
        return visibleView(mLoadView, 0);
    }

    public void showContentView() {
        mIsShowContent = true;
        notifyDataSetChanged();
    }

    public View showEmptyView() {
        clearDatas();
        return visibleView(mLoadView, 1);
    }

    public View showErrorView() {
        clearDatas();
        View view = visibleView(mLoadView, 2);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnLoadListener) mOnLoadListener.onLoadError();
            }
        });
        return view;
    }

    /**************************************** LoadView ***************************************/
    /**************************************** LoadMoreView ***************************************/

    public void setLoadMoreView(Context context, int loadingView, int emptyView, int errorView) {
        if (null == mLoadMoreView) {
            mLoadMoreView = new FrameLayout(context);
            mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else mLoadMoreView.removeAllViews();
        mLoadMoreView.addView(LayoutInflater.from(context).inflate(loadingView, null));
        mLoadMoreView.addView(LayoutInflater.from(context).inflate(emptyView, null));
        mLoadMoreView.addView(LayoutInflater.from(context).inflate(errorView, null));
        showLoadingMoreView();
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
            int otherPostion = 1;
            if (hasHeaderView()) otherPostion++;
            if (hasFooterView()) otherPostion++;
            return position + 1 == getRecyclerItemCount() + otherPostion;
        }
        return false;
    }

    public View showLoadingMoreView() {
        return visibleView(mLoadMoreView, 0);
    }

    public View showEmptyMoreView() {
        return visibleView(mLoadMoreView, 1);
    }

    public View showErrorMoreView() {
        View view = visibleView(mLoadMoreView, 2);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnLoadMoreListener) mOnLoadMoreListener.onLoadError();
            }
        });
        return view;
    }

    private View visibleView(FrameLayout frameLayout, int postion) {
        View view = null;
        if (null == frameLayout) {
            String message = null;
            if (frameLayout == mLoadView) {
                message = "Please invoking setLoadView() initialize LoadView";
            } else if (frameLayout == mLoadMoreView) {
                message = "Please invoking setLoadMoreView() initialize LoadMoreView";
            }
            throw new NullPointerException(message);
        }
        for (int i = 0; i < frameLayout.getChildCount(); i++) {
            view = i == postion ? frameLayout.getChildAt(i) : null;
            frameLayout.getChildAt(i).setVisibility(i == postion ? View.VISIBLE : View.INVISIBLE);
        }
        return view;
    }

    /**************************************** LoadMoreView ***************************************/
    /**************************************** HeaderFooter ***************************************/

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
    public boolean hasHeaderView() {
        return null != mHeaderView;
    }
    public boolean isHeaderView(int position) {
        if (hasHeaderView()) {
            int otherPostion = 1;
            return position + 1 == otherPostion;
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
            int otherPostion = 1;
            if (hasHeaderView()) otherPostion++;
            otherPostion = otherPostion + getRecyclerItemCount();
            return position + 1 == otherPostion;
        }
        return false;
    }

    /**************************************** HeaderFooter ***************************************/
    /*************************************** 监听事件 ******************************************/
    private OnLoadListener mOnLoadListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    public void setOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }
    /*************************************** 监听事件 ******************************************/
    /*************************************** 监听事件 ******************************************/

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
    private OnItemLongClickListener onItemLongClickListener;

    /*************************************** 监听事件 ******************************************/

    public interface OnItemClickListener {
        void onClick(RecyclerHolder holder, int postion);
    }

    public interface OnItemLongClickListener {
        boolean onLongClick(RecyclerHolder holder, int postion);
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
