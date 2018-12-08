package me.ibore.widget.recycler;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import me.ibore.widget.recycler.anim.helper.ViewHelper;

public abstract class RecyclerAdapter<T, VH extends RecyclerHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mDatas;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private AnimatorType mAnimatorType = AnimatorType.NOANIMATOR;
    private int mLastPosition = -1;
    private boolean isAnimatorFirstOnly = true;
    private long mAnimatorDuration = 300;
    private Interpolator mAnimatorInterpolator = new LinearInterpolator();

    public RecyclerAdapter() {
        this.mDatas = new ArrayList<>();
    }

    public void setDatas(List<T> datas) {
        if (null != datas) {
            mDatas = datas;
            notifyDataSetChanged();
        } else {
            clearDatas();
        }
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public T getData(int position) {
        return mDatas.get(position);
    }

    public void addData(T data) {
        addData(data, mDatas.size());
    }

    public void addData(T data, int position) {
        mDatas.add(position, data);
        notifyItemInserted(getRecyclerPosition(position));
    }

    public void addDatas(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearDatas() {
        mDatas.clear();
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
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
        ViewHelper.clear(v);
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

    protected abstract VH onCreateRecyclerHolder(ViewGroup parent, int viewType);

    protected abstract void onBindRecyclerHolder(VH holder, T t, int position);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateRecyclerHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        if (null != onItemClickListener) {
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder, getRecyclerPosition(position));
                }
            });
        }
        if (null != onItemLongClickListener){
            holder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClickListener.onLongClick(holder, getRecyclerPosition(position));
                }
            });
        }
        onBindRecyclerHolder(holder, mDatas.get(getRecyclerPosition(position)), getRecyclerPosition(position));
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

    protected int getRecyclerPosition(int position) {
        return position;
    }

    protected int getRecyclerItemViewType(T t, int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getRecyclerItemViewType(mDatas.get(getRecyclerPosition(position)), getRecyclerPosition(position));
    }

    protected int getRecyclerItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemCount() {
        return getRecyclerItemCount();
    }

    public enum AnimatorType {
        NOANIMATOR, SCALEIN, SLIDEINTOP, SLIDEINBOTTOM, SLIDEINLEFT, SLIDEINRIGHT;
    }

    public interface OnItemClickListener {
        void onClick(RecyclerHolder holder, int position);
    }

    public interface OnItemLongClickListener {
        boolean onLongClick(RecyclerHolder holder, int position);
    }

}
