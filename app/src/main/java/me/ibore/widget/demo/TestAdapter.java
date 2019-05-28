package me.ibore.widget.demo;

import android.view.ViewGroup;

import me.ibore.widget.recycler.RecyclerHolder;

public class TestAdapter extends RecyclerHFAdapter<String, RecyclerHolder> {
    @Override
    protected RecyclerHolder onCreateRecyclerHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindRecyclerHolder(RecyclerHolder holder, String s, int position) {

    }

}
