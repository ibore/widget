package me.ibore.widget.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * description:
 * author: Ibore Xie
 * date: 2018-01-21 13:12
 * website: ibore.me
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mPageTitles = new ArrayList<>();

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public FragmentAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    public FragmentAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<String> pageTitles) {
        super(fm);
        this.mFragments = fragments;
        this.mPageTitles = pageTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != mPageTitles) return mPageTitles.get(position);
        return super.getPageTitle(position);
    }

    public void setFragments(List<String> pageTitles, List<Fragment> fragments) {
        this.mPageTitles = pageTitles;
        this.mFragments = fragments;
    }

    /*public void addPage
    public static FragmentAdapter create(@NonNull FragmentManager fm) {

    }*/
}
