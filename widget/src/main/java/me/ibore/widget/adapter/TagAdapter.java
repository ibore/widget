package me.ibore.widget.adapter;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.ibore.widget.FlowLayout;
import me.ibore.widget.TagLayout;

public abstract class TagAdapter<T> {

    private List<T> mTagDatas;

    private OnDataChangedListener mOnDataChangedListener;

    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    public TagAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    public TagAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    public interface OnDataChangedListener {
        void onChanged();
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
        setSelectedList(set);
    }

    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    public HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }


    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(TagLayout parent, int position, T t);


    public void onSelected(int position, View view){
        Log.d("zhy","onSelected " + position);
    }

    public void unSelected(int position, View view){
        Log.d("zhy","unSelected " + position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }

}