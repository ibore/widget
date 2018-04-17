package me.ibore.widget.listener;

import android.view.View;

/**
 * Created by Administrator on 2018/2/27.
 */

public interface OnItemLongClickListener<P, C> {

    void onItemLongClick(P parent, int position, C child);

}
