package com.telstra.exercise;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.telstra.exercise.constant.IConstant;
import com.telstra.exercise.model.News;
import com.telstra.exercise.model.NewsItem;
import com.telstra.exercise.network.VolleyManager;
import com.telstra.exercise.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final String TAG = "MainActivity";
    private List<NewsItem> listData;
    private ListAdapter adapter;

    private ListView listView;
    private ProgressBar centerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        prepareLoad();
    }

    protected  void init() {
        initView();
        listData = new ArrayList<NewsItem>();
        adapter = new ListAdapter(this, listData);
        listView.setAdapter(adapter);
    }

    protected void initView() {
        listView = (ListView)findViewById(R.id.list_view);
        centerProgress = (ProgressBar) findViewById(R.id.center_progress);
    }

    protected void prepareLoad() {
        JsonObjectRequest request = new JsonObjectRequest(IConstant.FACTS_URL, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                centerProgress.setVisibility(View.INVISIBLE);
                parseResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                centerProgress.setVisibility(View.INVISIBLE);
                Log.d(TAG, "get data error: " + error.getMessage());
            }
        });

        centerProgress.setVisibility(View.VISIBLE);
        VolleyManager.getInstance().addToRequestQueue(request);
    }

    protected void parseResponse(JSONObject response) {
        News news = JsonUtils.fromJson(response.toString(), News.class);

        news.filterData();
        refreshUI(news);
    }

    protected void refreshUI(News news) {
        if(!listData.isEmpty()) {
            listData.clear();
        }
        listData.addAll(news.getRows());
        adapter.notifyDataSetChanged();
        getSupportActionBar().setTitle(news.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reload_btn:
                if(centerProgress.getVisibility() == View.INVISIBLE) {
                    prepareLoad();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyManager.getInstance().cancelPendingRequests();
    }
}
