package me.ibore.widget.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import me.ibore.widget.recycler.CommonAdapter;
import me.ibore.widget.recycler.RecyclerHolder;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new CommonAdapter<String>() {
            @Override
            protected int getLayoutId() {
                return R.layout.item_main;
            }

            @Override
            protected void convert(RecyclerHolder holder, String s, int position) {
                TextView title = (TextView) holder.getView(R.id.title);
            }
        };
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.addData("RecyclerView");
    }
}
