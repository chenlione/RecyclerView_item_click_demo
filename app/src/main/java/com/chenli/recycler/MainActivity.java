package com.chenli.recycler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnchildClickListener {

    private RecyclerView recycler;
    private MyAdapter adapter;
    private List<String> list;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initAdapter();
    }

    private void initAdapter() {
        adapter = new MyAdapter(this, list);
        recycler.setAdapter(adapter);

        //设置RecyclerView的布局样式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager   gridLayoutManager   = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 3;
                }
                if (position == 2) {
                    return 2;
                }
                return 1;
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //动画系统
        //        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        MyItemAnimator animator = new MyItemAnimator();
        animator.setSupportsChangeAnimations(true);//默认不支持
        //        animator.setRemoveDuration(3000);
        //        animator.setMoveDuration(3000);
        //        animator.setAddDuration(3000);
        animator.setChangeDuration(3000);
        recycler.setItemAnimator(animator);

        recycler.setLayoutManager(linearLayoutManager);

        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                c.drawColor(Color.BLUE);
            }

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                c.drawBitmap(bitmap,50,50,null);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);

                outRect.set(0,5+position,0,5);
            }
        });

        adapter.setOnchildClickListener(this);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
//            list.add(String.format(Locale.CHINA, "第%09d条数据%s", i, i % 2 == 0 ? "" : "------------------"));
            list.add(String.format(Locale.CHINA, "第%09d条数据", i));
        }

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    private void initView() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    @Override
    public void onChildClick(RecyclerView parent, View view, int position, String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
//        adapter.remove(position);
//        adapter.add(position,"新增加的数据");
        adapter.change(position,"新增加的数据");
    }
}
