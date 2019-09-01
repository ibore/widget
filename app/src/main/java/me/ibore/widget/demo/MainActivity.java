package me.ibore.widget.demo;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import me.ibore.widget.demo.bannerview.BannerViewActivity;
import me.ibore.widget.demo.wheel.WheelActivity;
import me.ibore.widget.dialog.DialogUtils;
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
        mAdapter.addData(new MainItem("RecyclerTabLayout", RecyclerTabLayoutActivity.class));
        mAdapter.addData(new MainItem("RecyclerView", RecyclerViewActivity.class));
        mAdapter.addData(new MainItem("WheelView", WheelActivity.class));
        mAdapter.addData(new MainItem("BannerView", BannerViewActivity.class));
        mAdapter.addData(new MainItem("HtmlText", HtmlTextActivity.class));
        mAdapter.addData(new MainItem("CountdownView", CountdownActivity.class));
        mAdapter.addData(new MainItem("RadarProgressView", RadarProgressActivity.class));
        mAdapter.addData(new MainItem("ShadowLayout", ShadowLayoutActivity.class));
        mAdapter.addData(new MainItem("StateButton", StateButtonActivity.class));

        DialogUtils.showDialog(this, "橄榄球世界杯", "下个月，橄榄球世界杯将在日本举行，日本现在已进入节日的状态。我找了半天，怎么韩国没有派球队参加呢？据说韩国的橄榄球不也是很厉害的吗？当然只是据说，具体厉害不厉害，我也不知道。想想2002年的足球世界杯，本来日本是举办国，遇到韩国这么一个无赖，自己没有举办能力，不要脸皮地要和日本一起举办，日本看着韩国可怜，也就同意了。没想到韩国利用自己是举办国的优势，大搞黑色足球，黑掉对手，买通裁判，搞得比赛很不光彩，使得日本终于明白，今后再也不会和韩国一起举办什么球赛了。");
    }
}
