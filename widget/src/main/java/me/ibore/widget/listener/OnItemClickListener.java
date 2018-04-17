package me.ibore.widget.listener;

import android.view.View;

/**
 * Created by Administrator on 2018/2/27.
 */

public interface OnItemClickListener<P, C> {

    void onItemClick(P parent, int position, C child);

}
