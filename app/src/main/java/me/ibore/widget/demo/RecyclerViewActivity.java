package me.ibore.widget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.ibore.widget.recycler.CommonAdapter;
import me.ibore.widget.recycler.RecyclerHolder;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommonAdapter<String> mAdapter;
    private Button loading, content, empty, error;
    private Button loading_more, content_more, empty_more, error_more;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new CommonAdapter<String>() {
            @Override
            protected int getLayoutId() {
                return R.layout.item_main;
            }


            @Override
            protected void convert(RecyclerHolder holder, final String s, int position) {
                holder.getTextView(R.id.title).setText(s);
                holder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.remove(0);
//                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        mAdapter.addHeaderView(getLayoutInflater().inflate(R.layout.header, null));
        mAdapter.addFooterView(getLayoutInflater().inflate(R.layout.footer, null));
        mAdapter.setLoadView(this, R.layout.loading, R.layout.empty, R.layout.error);
        mAdapter.setLoadMoreView(this, R.layout.loading, R.layout.empty, R.layout.error);
        mAdapter.getHeaderView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mAdapter.hasHeaderView() + "ddd", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(mAdapter);
        loading = findViewById(R.id.loading);
        content = findViewById(R.id.content);
        empty = findViewById(R.id.empty);
        error = findViewById(R.id.error);

        loading_more = findViewById(R.id.loading_more);
        content_more = findViewById(R.id.content_more);
        empty_more = findViewById(R.id.empty_more);
        error_more = findViewById(R.id.error_more);
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.showLoadingView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "showLoadingView", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addData("111111111");
                mAdapter.addData("222222222");
                mAdapter.addData("333333333");
                mAdapter.addData("444444444");
                mAdapter.addData("555555555");
                mAdapter.addData("666666666");
                mAdapter.addData("777777777");
            }
        });
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.showEmptyMoreView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "showEmptyView", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.showErrorView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "showErrorView", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        loading_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.showLoadingMoreView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "showLoadingView", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        content_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addData("9999999999");
            }
        });
        empty_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.showEmptyMoreView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "showEmptyView", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        error_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.showErrorMoreView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "showErrorView", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}