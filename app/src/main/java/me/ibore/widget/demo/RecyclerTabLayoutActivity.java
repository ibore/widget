package me.ibore.widget.demo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
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
        titles.add("222222");
        titles.add("33333333333333333333333");
        titles.add("1111");
        titles.add("222222333333333");
        titles.add("333333333333333333");
        titles.add("1111");
        titles.add("222222");
        titles.add("3333333");
        titles.add("1111");
        titles.add("22222ddddd2");
        titles.add("3333ddddddd333");
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
        View view4 = new View(this);
        view4.setBackgroundResource(R.color.colorPrimary);
        views.add(view4);
        View view5 = new View(this);
        view5.setBackgroundResource(R.color.colorAccent);
        views.add(view5);
        View view6 = new View(this);
        view6.setBackgroundResource(R.color.colorPrimaryDark);
        views.add(view6);

        View view7 = new View(this);
        view7.setBackgroundResource(R.color.colorPrimary);
        views.add(view7);
        View view8 = new View(this);
        view8.setBackgroundResource(R.color.colorAccent);
        views.add(view8);
        View view9 = new View(this);
        view9.setBackgroundResource(R.color.colorPrimaryDark);
        views.add(view9);
        viewPager.setAdapter(new ViewAdapter(views, titles));
        tabLayout.setUpWithViewPager(viewPager);
        tabLayout.setPositionThreshold(1);

    }
}
