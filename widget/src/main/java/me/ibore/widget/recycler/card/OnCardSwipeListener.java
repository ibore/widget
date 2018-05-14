package me.ibore.widget.recycler.card;

import android.support.v7.widget.RecyclerView;

public interface OnCardSwipeListener<T> {

    /**
     * 卡片还在滑动时回调
     * @param viewHolder 该滑动卡片的viewHolder
     * @param ratio 滑动进度的比例
     * @param direction
     */
    void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    /**
     * 卡片完全滑出时回调
     * @param viewHolder 该滑出卡片的viewHolder
     * @param t 该滑出卡片的数据
     * @param direction
     */
    void onSwiped(RecyclerView.ViewHolder viewHolder, T t, int direction);

    /**
     * 所有的卡片全部滑出时回调
     */
    void onSwipedClear();

}
