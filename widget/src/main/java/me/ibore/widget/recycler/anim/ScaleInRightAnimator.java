package me.ibore.widget.recycler.anim;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.animation.Interpolator;

public class ScaleInRightAnimator extends BaseItemAnimator {

  public ScaleInRightAnimator() {
  }

  public ScaleInRightAnimator(Interpolator interpolator) {
    mInterpolator = interpolator;
  }

  @Override
  protected void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {
    ViewCompat.setPivotX(holder.itemView, holder.itemView.getWidth());
  }

  @Override
  protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
    ViewCompat.animate(holder.itemView)
        .scaleX(0)
        .scaleY(0)
        .setDuration(getRemoveDuration())
        .setInterpolator(mInterpolator)
        .setListener(new DefaultRemoveVpaListener(holder))
        .setStartDelay(getRemoveDelay(holder))
        .start();
  }

  @Override
  protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
    ViewCompat.setPivotX(holder.itemView, holder.itemView.getWidth());
    ViewCompat.setScaleX(holder.itemView, 0);
    ViewCompat.setScaleY(holder.itemView, 0);
  }

  @Override
  protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
    ViewCompat.animate(holder.itemView)
        .scaleX(1)
        .scaleY(1)
        .setDuration(getAddDuration())
        .setInterpolator(mInterpolator)
        .setListener(new DefaultAddVpaListener(holder))
        .setStartDelay(getAddDelay(holder))
        .start();
  }
}
