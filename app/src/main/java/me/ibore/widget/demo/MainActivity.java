package me.ibore.widget.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.ibore.widget.demo.bannerview.BannerViewActivity;
import me.ibore.widget.demo.wheel.WheelActivity;
import me.ibore.widget.recycler.CommonAdapter;
import me.ibore.widget.recycler.RecyclerHolder;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommonAdapter<MainItem> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new CommonAdapter<MainItem>() {
            @Override
            protected int getLayoutId() {
                return R.layout.item_main;
            }

            @Override
            protected void convert(RecyclerHolder holder, final MainItem mainItem, int position) {
                holder.getTextView(R.id.title).setText(mainItem.getTitle());
                holder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), mainItem.getClazz());
                        startActivity(intent);
                    }
                });

            }
        };
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.addData(new MainItem("RecyclerView", RecyclerViewActivity.class));
        mAdapter.addData(new MainItem("WheelView", WheelActivity.class));
        mAdapter.addData(new MainItem("BannerView", BannerViewActivity.class));
        mAdapter.addData(new MainItem("HtmlText", HtmlTextActivity.class));
    }
}
