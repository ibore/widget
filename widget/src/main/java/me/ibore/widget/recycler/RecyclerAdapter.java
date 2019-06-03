package me.ibore.widget.recycler;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

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
    private boolean mIsLoadingMoreView = false;

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

    public T getData(int position) {
        return mDatas.get(position);
    }

    public void addData(T data) {
        if (null == data) return;
        if (mDatas.size() == 0) {
            ArrayList<T> list = new ArrayList<>();
            list.add(data);
            setDatas(list);
        } else {
            mDatas.add(data);
            notifyItemChanged(hasHeaderView() ? mDatas.size() : mDatas.size() - 1);
        }
    }

    public void addData(T data, int position) {
        if (null == data || position > mDatas.size()) return;
        if (mDatas.size() == 0) {
            addData(data);
        } else {
            mDatas.add(position, data);
            notifyItemInserted(hasHeaderView() ? position + 1 : position);
        }
    }

    public void addDatas(List<T> datas) {
        if (mDatas.size() == 0) {
            setDatas(datas);
        } else {
            ArrayList<T> list = new ArrayList<>(datas);
            mDatas.addAll(list);
            notifyItemRangeRemoved(hasHeaderView() ? mDatas.size() - datas.size() + 1 : mDatas.size() - datas.size(), datas.size());
        }
    }

    public void removeData(RecyclerHolder holder, T t) {
        mDatas.remove(t);
        notifyItemRemoved(holder.getAdapterPosition());
    }

    public void removeData(RecyclerHolder holder) {
        mDatas.remove(hasHeaderView() ? holder.getAdapterPosition() - 1 : holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
    }

    public void clearDatas() {
        mDatas.clear();
        mIsShowContent = false;
        notifyDataSetChanged();
    }

    public void setAnimatorFirstOnly(boolean isFirstOnly) {
        this.isAnimatorFirstOnly = isFirstOnly;
    }

    public void setAnimator(AnimatorType animatorType) {
        this.setAnimator(animatorType, 300);
    }

    public void setAnimator(AnimatorType animatorType, long duration) {
        this.setAnimator(animatorType, duration, null);
    }

    public void setAnimator(AnimatorType animatorType, long duration, Interpolator value) {
        this.mAnimatorType = animatorType;
        this.mAnimatorDuration = duration;
        this.mAnimatorInterpolator = value;
    }

    protected void clearAnimator(View v) {
        v.setAlpha(1);
        v.setScaleY(1);
        v.setScaleX(1);
        v.setTranslationY(0);
        v.setTranslationX(0);
        v.setRotation(0);
        v.setRotationY(0);
        v.setRotationX(0);
        v.setPivotY(v.getMeasuredHeight() / 2f);
        v.setPivotX(v.getMeasuredWidth() / 2f);
        v.animate().setInterpolator(null).setStartDelay(0);
    }

    protected Animator[] getAnimators(View view) {
        Animator[] animators;
        switch (mAnimatorType) {
            case SCALEIN:
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
                animators = new ObjectAnimator[]{scaleX, scaleY};
                break;
            case SLIDEINTOP:
                animators = new Animator[]{
                        ObjectAnimator.ofFloat(view, "translationY", -view.getMeasuredHeight(), 0)
                };
                break;
            case SLIDEINBOTTOM:
                animators = new Animator[]{
                        ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
                };
                break;
            case SLIDEINLEFT:
                animators = new Animator[]{
                        ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
                };
                break;
            case SLIDEINRIGHT:
                animators = new Animator[]{
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
    public void onViewAttachedToWindow(@NonNull VH holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (isLoadView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            } else if (isHeaderView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            } else if (isFooterView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            } else if (isLoadMoreView(holder.getLayoutPosition())) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            } else {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(isStaggeredFullSpan());
            }

        }
    }

    protected boolean isStaggeredFullSpan() {
        return false;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
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
                    } else {
                        int realPosition = hasHeaderView() ? position - 1 : position;
                        return getGridSpanSize(getData(realPosition), realPosition);
                    }
                }
            });
        }
        onScrollListener(recyclerView, layoutManager);
    }

    protected int getGridSpanSize(T data, int position) {
        return 1;
    }

    private void onScrollListener(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (hasLoadMoreView() && mOnLoadMoreListener != null &&
                        newState == RecyclerView.SCROLL_STATE_IDLE &&
                        mLoadMoreView.getChildAt(0).getVisibility() == View.VISIBLE &&
                        !mIsLoadingMoreView) {
                    int lastVisibleItem = findLastCompletelyVisibleItemPosition(layoutManager);
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (hasLoadMoreView() && mOnLoadMoreListener != null && mLoadMoreView.getChildAt(0).getVisibility() == View.VISIBLE) {
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
    public @NonNull
    VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                holder = onCreateRecyclerHolder(parent, viewType);
                break;
        }
        return (VH) holder;
    }

    protected abstract RecyclerHolder onCreateRecyclerHolder(ViewGroup parent, int viewType);

    protected abstract void onBindRecyclerHolder(VH holder, T t, int position);

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        if (isLoadView(position)) return;
        if (isHeaderView(position)) return;
        if (isFooterView(position)) return;
        if (isLoadMoreView(position)) return;
        final int realPosition = hasHeaderView() ? position - 1 : position;
        if (null != onItemClickListener) {
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder, getData(realPosition), realPosition);
                }
            });
        }
        if (null != onItemLongClickListener) {
            holder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClickListener.onItemLongClick(holder, getData(realPosition), realPosition);
                }
            });
        }
        onBindRecyclerHolder(holder, getData(realPosition), realPosition);
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
        if (isLoadView(position)) return TYPE_LOAD;
        if (isHeaderView(position)) return TYPE_HEADER;
        if (isFooterView(position)) return TYPE_FOOTER;
        if (isLoadMoreView(position)) return TYPE_LOADMORE;
        final int realPosition = hasHeaderView() ? position - 1 : position;
        return getRecyclerItemViewType(getData(realPosition), realPosition);
    }

    public int getRecyclerItemCount() {
        if (null != mDatas) {
            return mDatas.size();
        }
        return 0;
    }

    public int getRecyclerItemViewType(T t, int position) {
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
            int otherPosition = 1;
            if (hasHeaderView()) otherPosition++;
            if (hasFooterView()) otherPosition++;
            return position + 1 == getRecyclerItemCount() + otherPosition;
        }
        return false;
    }

    public View showLoadingMoreView() {
        mIsLoadingMoreView = true;
        return visibleView(mLoadMoreView, 0);
    }

    public View showEmptyMoreView() {
        mIsLoadingMoreView = false;
        return visibleView(mLoadMoreView, 1);
    }

    public View showErrorMoreView() {
        mIsLoadingMoreView = false;
        View view = visibleView(mLoadMoreView, 2);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnLoadMoreListener) mOnLoadMoreListener.onLoadError();
            }
        });
        return view;
    }

    private View visibleView(FrameLayout frameLayout, int position) {
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
            if (i == position) {
                view = frameLayout.getChildAt(i);
            }
            frameLayout.getChildAt(i).setVisibility(i == position ? View.VISIBLE : View.INVISIBLE);
        }
        return view;
    }

    /**************************************** LoadMoreView ***************************************/
    /**************************************** HeaderFooter ***************************************/

    public void addHeaderView(View headerView) {
        addHeaderView(headerView, LinearLayout.VERTICAL);
    }

    public void addHeaderView(View headerView, int orientation) {
        mHeaderView = new LinearLayout(headerView.getContext());
        if (orientation == LinearLayout.VERTICAL) {
            mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mHeaderView.setOrientation(LinearLayout.VERTICAL);
        } else {
            mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mHeaderView.setOrientation(LinearLayout.HORIZONTAL);
        }
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
        addFooterView(footerView, LinearLayout.VERTICAL);
    }

    public void addFooterView(View footerView, int orientation) {
        mFooterView = new LinearLayout(footerView.getContext());
        if (orientation == LinearLayout.VERTICAL) {
            mFooterView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mFooterView.setOrientation(LinearLayout.VERTICAL);
        } else {
            mFooterView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFooterView.setOrientation(LinearLayout.HORIZONTAL);
        }
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

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener<T> onItemClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private OnItemLongClickListener<T> onItemLongClickListener;

    /*************************************** 监听事件 ******************************************/

    public interface OnItemClickListener<T> {
        void onItemClick(RecyclerHolder holder, T t, int position);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(RecyclerHolder holder, T t, int position);
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
