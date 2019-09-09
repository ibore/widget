package me.ibore.widget.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                if (null == mainItem) return;
                holder.getTextView(R.id.title).setText(mainItem.getTitle());
                holder.onClickListener(new View.OnClickListener() {
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
        mAdapter.addData(new MainItem("AlertDialog", AlertDialogActivity.class));
        mAdapter.addData(new MainItem("RecyclerTabLayout", RecyclerTabLayoutActivity.class));
        mAdapter.addData(new MainItem("RecyclerView", RecyclerViewActivity.class));
        mAdapter.addData(new MainItem("WheelView", WheelActivity.class));
        mAdapter.addData(new MainItem("BannerView", BannerViewActivity.class));
        mAdapter.addData(new MainItem("HtmlText", HtmlTextActivity.class));
        mAdapter.addData(new MainItem("CountdownView", CountdownActivity.class));
        mAdapter.addData(new MainItem("RadarProgressView", RadarProgressActivity.class));
        mAdapter.addData(new MainItem("ShadowLayout", ShadowLayoutActivity.class));
        mAdapter.addData(new MainItem("StateButton", StateButtonActivity.class));


    }
}
