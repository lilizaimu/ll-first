package com.example.administrator.newkj;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.newkj.adapter.MyRecycleAdapter;
import com.example.administrator.newkj.bean.bean.Pic;
import com.example.administrator.newkj.constant.constant;
import com.example.administrator.newkj.util.DensityUtils;
import com.example.administrator.newkj.util.LogUtils;
import com.example.administrator.newkj.util.ScreenUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int sss=12;
    int picsize=2;
    private int page=1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MyRecycleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        picsize=ScreenUtils.getScreenWidth(this)- DensityUtils.dp2px(this,8);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        setDrawerLayout();
        setNavigationMenuView();
        setRecyclerView();
        getNet(constant.URL,true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                getNet(constant.URL+page,true);
            }
        });
    }

    public void setDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
    }

    public void setNavigationMenuView(){
        navigationView= (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_fz:
                        Snackbar.make(drawerLayout,"要复制嘛",Snackbar.LENGTH_LONG).setAction("要", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"要",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    public void setRecyclerView(){
        recyclerView= (RecyclerView) findViewById(R.id.recycle);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        setAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount()-1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom =  recyclerView.getBottom()-recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition  = recyclerView.getLayoutManager().getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
                if(lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount()-1 ){
                    page++;
                    getNet(constant.URL+page,false);
                }
            }

        });
    }

    public void setAdapter(){
        adapter=new MyRecycleAdapter(this);
    }

    public void getNet(String url, final boolean ifClear){
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                LogUtils.d(e.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
                LogUtils.d(response);
                Gson gson=new Gson();
                Pic pic=gson.fromJson(response, Pic.class);
                ArrayList<Pic.Body.MyPic> pics=pic.getShowapi_res_body().getNewslist();
                adapter.addDatas(pics,ifClear);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
        return true;
    }

}
