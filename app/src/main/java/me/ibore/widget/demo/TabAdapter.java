package me.ibore.widget.demo;

import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import me.ibore.widget.RecyclerTabLayout;
import me.ibore.widget.recycler.RecyclerHolder;

public class TabAdapter extends RecyclerTabLayout.Adapter<String> {


    public TabAdapter(ViewPager viewPager) {
        super(viewPager);
    }

    @Override
    protected RecyclerHolder onCreateRecyclerHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindRecyclerHolder(RecyclerHolder holder, String s, int position) {

    }
}
