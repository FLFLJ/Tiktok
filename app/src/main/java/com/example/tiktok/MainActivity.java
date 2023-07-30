package com.example.tiktok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecycler;
    private CustomLayoutManager mLayoutManager;

    public List<String> videos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("videos","-2");
        okhttpGet();
        //initView();
        //initListener();
    }
    public void okhttpGet(){
        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();
        Log.d("videos","-1");
        // 创建HTTP请求
        Request request = new Request.Builder()
                .url("http://120.25.206.148:3000/api") // 替换为您要请求的URL
                .build();
        Log.d("videos","0");

        // 发送异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("videos","1");
                // 处理成功响应
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    // 在此处处理响应数据
                    // 创建Gson实例
                    Log.d("videos","2");
                    Gson gson = new Gson();

                    // 解析JSON数据
                    JsonElement jsonElement = JsonParser.parseReader(new StringReader(jsonData));

                    // 转换为JsonArray
                    JsonArray jsonArray = jsonElement.getAsJsonArray();
                    Log.d("videos","3");
                    videos= new ArrayList<>();
                    // 遍历JsonArray中的每个元素
                    for (JsonElement element : jsonArray) {
                        // 转换为JsonObject
                        JsonObject jsonObject = element.getAsJsonObject();

                        // 使用Gson从JsonObject中提取链接字段
                        String link = jsonObject.get("link").getAsString();
                        Log.d("videos",link);
                        videos.add(link);
                    }
                    Log.d("videos",videos.get(1));
                    Log.d("videos","执行initView(videos);");
                    runOnUiThread(() -> {
                        initView(videos);
                        Log.d("videos","执行initListener();");
                        initListener();
                    });

                } else {
                    // 处理响应失败
                    Log.d("videos","处理响应失败");
                    System.out.println("Response Failed");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // 处理请求失败
                e.printStackTrace();
                Log.d("videos","请求失败");
            }
        });
    }
    //初始化监听
    private void initListener() {
        mLayoutManager.setOnPageSlideListener(new OnPageSlideListener() {

            @Override
            public void onPageRelease(boolean isNext, int position) {
                int index;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position, boolean isNext) {
                playVideo();
            }
        });
    }
    //初始化View
    private void initView(List<String> videos) {
        mRecycler = findViewById(R.id.mRecycler);
        mLayoutManager = new CustomLayoutManager(this, OrientationHelper.VERTICAL, false);
        TiktokAdapter mAdapter = new TiktokAdapter(this,videos);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }


    //播放
    private void playVideo() {
        View itemView = mRecycler.getChildAt(0);
        final CusVideoView mVideoView = itemView.findViewById(R.id.mVideoView);
        final ImageView mPlay = itemView.findViewById(R.id.mPlay);
//        final ImageView mThumb = itemView.findViewById(R.id.mThumb);
        final MediaPlayer[] mMediaPlayer = new MediaPlayer[1];
        mVideoView.start();

        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mMediaPlayer[0] = mp;
                mp.setLooping(true);
 //               mThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });

        //暂停控制
        mPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;

            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mPlay.animate().alpha(1f).start();
                    mVideoView.pause();
                    isPlaying = false;
                } else {
                    mPlay.animate().alpha(0f).start();
                    mVideoView.start();
                    isPlaying = true;
                }
            }
        });
    }

    //释放
    private void releaseVideo(int index) {
        View itemView = mRecycler.getChildAt(index);
        final CusVideoView mVideoView = itemView.findViewById(R.id.mVideoView);
       // final ImageView mThumb = itemView.findViewById(R.id.mThumb);
        final ImageView mPlay = itemView.findViewById(R.id.mPlay);
        mVideoView.stopPlayback();
       // mThumb.animate().alpha(1).start();
        mPlay.animate().alpha(0f).start();
    }
}