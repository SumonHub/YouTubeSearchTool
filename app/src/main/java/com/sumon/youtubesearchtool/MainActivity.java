package com.sumon.youtubesearchtool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sumon.youtubesearchtoollib.YouTubeSearchHandler;
import com.sumon.youtubesearchtoollib.models.YouTubeVideoModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String SEARCH_PARAM = "songs";
    final static String API_KEY = "your api key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YouTubeSearchHandler searchHandler = new YouTubeSearchHandler.Builder()
                .setSearchParam(SEARCH_PARAM)
                .setSearchType(YouTubeSearchHandler.SEARCH_BY_KEYWORD)
                .setOrderType(YouTubeSearchHandler.ORDER_BY_DEFAULT)
                .setApiKey(API_KEY)
                .setMaxResult(50)
                .create();
        String myUrl = searchHandler.getUrl();

        searchHandler.execute(myUrl);
        searchHandler.onFinish(new YouTubeSearchHandler.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<YouTubeVideoModel> list) {
                RecyclerView recyclerView = findViewById(R.id.video_card);
                VideoAdapter videoAdapter = new VideoAdapter(getApplicationContext(), list);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(videoAdapter);
            }

            @Override
            public void onError() {

            }

        });

    }

}
