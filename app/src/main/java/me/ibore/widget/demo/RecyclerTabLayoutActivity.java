package me.ibore.widget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ibore.widget.RecyclerTabLayout;
import me.ibore.widget.adapter.ViewAdapter;

public class RecyclerTabLayoutActivity extends AppCompatActivity {

    private RecyclerTabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclertablayout);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        List<String> titles = new ArrayList<>();
        titles.add("1111");
        titles.add("2222");
        titles.add("3333");
        List<View> views = new ArrayList<>();
        View view1 = new View(this);
        view1.setBackgroundResource(R.color.colorPrimary);
        views.add(view1);
        View view2 = new View(this);
        view2.setBackgroundResource(R.color.colorAccent);
        views.add(view2);
        View view3 = new View(this);
        view3.setBackgroundResource(R.color.colorPrimaryDark);
        views.add(view3);
        viewPager.setAdapter(new ViewAdapter(views, titles));
        tabLayout.setUpWithViewPager(viewPager);
    }
}
