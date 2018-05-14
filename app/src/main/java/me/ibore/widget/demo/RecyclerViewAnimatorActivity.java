package me.ibore.widget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.ibore.widget.recycler.RecyclerAdapter;
import me.ibore.widget.recycler.RecyclerHolder;
import me.ibore.widget.recycler.RecyclerViewAdapter;
import me.ibore.widget.recycler.anim.ScaleInRightAnimator;

public class RecyclerViewAnimatorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter<String, RecyclerHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_animator);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter<String, RecyclerHolder>() {

            @Override
            protected RecyclerHolder onCreateRecyclerHolder(ViewGroup parent, int viewType) {
                return RecyclerHolder.create(parent, R.layout.item_main);
            }

            @Override
            protected void onBindRecyclerHolder(RecyclerHolder holder, String data, int position) {
                holder.getTextView(R.id.title).setText(data);
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new ScaleInRightAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onClick(RecyclerHolder holder, String data, int position) {
                adapter.remove(position);
            }
        });
        adapter.setAnimator(RecyclerAdapter.AnimatorType.SLIDEINBOTTOM);
        adapter.setAnimatorFirstOnly(false);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("----------" + i + "----------");
        }
        adapter.setDatas(strings);

    }
}
