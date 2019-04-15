package me.ibore.widget.demo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.ibore.widget.recycler.CommonAdapter;
import me.ibore.widget.recycler.RecyclerAdapter;
import me.ibore.widget.recycler.RecyclerHFAdapter;
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
            protected void convert(final RecyclerHolder holder, final String s, final int position) {
                holder.getTextView(R.id.title).setText(s);
                holder.onClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.getDatas().remove(position);
                        mAdapter.notifyItemRemoved(holder.getAdapterPosition());
//                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(RecyclerHolder holder, int position) {
                Toast.makeText(getApplicationContext(), mAdapter.getData(position), Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.addHeaderView(getLayoutInflater().inflate(R.layout.header, null));
        mAdapter.addFooterView(getLayoutInflater().inflate(R.layout.footer, null));
        mAdapter.setLoadView(this, R.layout.loading, R.layout.empty, R.layout.error);
        mAdapter.setLoadMoreView(this, R.layout.loading, R.layout.empty, R.layout.error);
        /*mAdapter.getHeaderView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mAdapter.hasHeaderView() + "ddd", Toast.LENGTH_SHORT).show();
            }
        });*/
        mAdapter.setOnLoadMoreListener(new RecyclerHFAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                /*Log.d("----", "onLoadMore");
                mAdapter.addData("9999999");
                mAdapter.addData("9999999");
                mAdapter.addData("9999999");
                mAdapter.addData("9999999");
                mAdapter.addData("9999999");*/
            }

            @Override
            public void onLoadError() {
                Log.d("----", "onLoadError");
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
                List<String> strings = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    strings.add("Position:" + i);
                }
                mAdapter.setDatas(strings);
            }
        });
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.showEmptyView().setOnClickListener(new View.OnClickListener() {
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
                mAdapter.showLoadingMoreView();
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
