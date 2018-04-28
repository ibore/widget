package me.ibore.widget.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class LoopAdapter extends PagerAdapter {

    public final PagerAdapter pa;

    private SparseArray<Object> recycler = new SparseArray<Object>();


    public LoopAdapter(PagerAdapter adapter) {
        this.pa = adapter;
    }

    @Override
    public void notifyDataSetChanged() {
        recycler = new SparseArray<Object>();
        super.notifyDataSetChanged();
    }

    public int getRealPosition(int position) {
        return toRealPosition(position, pa.getCount());
    }

    private static int toRealPosition(int position, int count) {
        if (count <= 1) {
            return 0;
        }
        return (position - 1 + count) % count;
    }

    public int getRealCount() {
        return pa.getCount();
    }

    @Override
    public int getCount() {
        int realCount = getRealCount();
        return realCount > 1 ? (realCount + 2) : realCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int real = getRealPosition(position);

        Object destroy = recycler.get(position);
        if (destroy != null) {
            recycler.remove(position);
            return destroy;
        }
        return pa.instantiateItem(container, real);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int first = 1, last = getRealCount();

        if (position == first || position == last) {
            recycler.put(position, object);
        } else {
            pa.destroyItem(container, getRealPosition(position), object);
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        pa.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return pa.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        pa.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return pa.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        pa.startUpdate(container);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        pa.setPrimaryItem(container, position, object);
    }
}