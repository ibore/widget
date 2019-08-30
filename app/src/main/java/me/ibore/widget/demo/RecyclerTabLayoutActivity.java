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
        titles.add("11111");
        titles.add("22222");
        titles.add("33333");
        titles.add("44444");

        List<View> views = new ArrayList<>();
        View view1 = new View(this);
        view1.setBackgroundResource(R.color.colorPrimary);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLayout.setCurrentItem(3, true);
            }
        });
        views.add(view1);
        View view2 = new View(this);
        view2.setBackgroundResource(R.color.colorAccent);
        views.add(view2);
        View view3 = new View(this);
        view3.setBackgroundResource(R.color.colorPrimaryDark);
        views.add(view3);
        View view4 = new View(this);
        view4.setBackgroundResource(R.color.colorAccent);
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLayout.setCurrentItem(0, true);
            }
        });
        views.add(view4);
        viewPager.setAdapter(new ViewAdapter(views, titles));
        tabLayout.setUpWithViewPager(viewPager);
        tabLayout.setPositionThreshold(1);

    }
}
