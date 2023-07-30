package com.example.tiktok;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class TiktokAdapter extends RecyclerView.Adapter<TiktokAdapter.ViewHolder> {
    //private int[] videos = {R.raw.girls, R.raw.littlegirl};
    //http://120.25.206.148:3000/video/littlegirl.mp4
    private List<String> videos;
    private int indexnum;
    private boolean isBug;
            //{"https://v3-web.douyinvod.com/0e668d7d6eb7fbba8b06e7b6eac9c1f5/64bf4352/video/tos/cn/tos-cn-ve-15c001-alinc2/oQhMECKtW7vIjMAeVxUytQfKBTFz2igAAvEWiB/?a=6383&ch=224&cr=3&dr=0&lr=all&cd=0%7C0%7C0%7C3&cv=1&br=1902&bt=1902&cs=0&ds=6&ft=GN7rKGVVywSyRKJoImo~ySqTeaApRO8d6vrKh9L4mto0g3&mime_type=video_mp4&qs=0&rc=OTU1OjpkNGc7M2dkOGZnNkBpamlodzc6ZmRmbDMzNGkzM0AzM2ExX14uXy8xMzZiXjQuYSMtc2hqcjRvXmVgLS1kLS9zcw%3D%3D&l=2023072510355651001E92D2FF264FC617&btag=e00020000&dy_q=1690252557","https://z4sjho2h4yxji8xccaj2afqirm6zxaygcw8almedlzdyli4xkm.free-lbv3.idouyinvod.com/v3-web.douyinvod.com/0ccb33913c01404cdc896ac2ae481707/64bf4347/video/tos/cn/tos-cn-ve-15c001-alinc2/oI1AyTz6fIhILGaV7eztABpAxBdIECQtgyQSCh/?a=6383&ch=224&cr=3&dr=0&lr=all&cd=0%7C0%7C0%7C3&cv=1&br=1738&bt=1738&cs=0&ds=3&ft=GN7rKGVVywSyRKJoImo~ySqTeaApRO8d6vrKh9L4mto0g3&mime_type=video_mp4&qs=0&rc=Z2c1NjQ4O2QzZzRmPDpnOUBpajRwbjQ6Zjc0azMzNGkzM0BfYGExYC4uXzUxMS9iMmA2YSNqZ2dscjRfZmFgLS1kLS9zcw%3D%3D&l=2023072510355651001E92D2FF264FC617&btag=e00018000&dy_q=1690252557&ali_redirect_ex_hot=66666810&ali_redirect_ex_beacon=1"};
    //private int[] imgs = {R.drawable.search, R.drawable.search,R.drawable.search,R.drawable.search,R.drawable.search};
    //private List<String> mTitles = new ArrayList<>();
    //private List<String> mMarqueeList = new ArrayList<>();
    private Context mContext;

    public TiktokAdapter(Context context,List<String> videos) {
        Log.d("videos","TiktokAdapter1");
        this.mContext = context;
        this.videos=videos;
        this.indexnum=0;
        isBug=true;
        Log.d("videos","videos"+videos);

        //mTitles.add("@乔布奇\nAndroid仿抖音主界面UI效果,\n一起来学习Android开发啊啊啊啊啊\n#Android高级UIAndroid开发");
       // mTitles.add("@乔布奇\nAndroid RecyclerView自定义\nLayoutManager的使用方式，仿抖音效果哦");
       // mTitles.add("@乔布奇\nAndroid RecyclerView自定义\nLayoutManager的使用方式，仿抖音效果哦");
        //mMarqueeList.add("哈哈创作的原声-乔布奇");
        //mMarqueeList.add("哈哈创作的原声-乔布奇");
       // mMarqueeList.add("嘿嘿创作的原声-Jarchie");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("videos","TiktokAdapter2");
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_tiktok_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int pos) {
        //第一种方式：获取视频第一帧作为封面图片
//        MediaMetadataRetriever media = new MediaMetadataRetriever();
//        media.setDataSource(mContext,Uri.parse("android.resource://" + mContext.getPackageName() + "/" + videos[pos % 2]));
//        holder.mThumb.setImageBitmap(media.getFrameAtTime());
        //第二种方式：使用固定图片作为封面图片
        Log.d("videos","pospospospos         ???????????"+Integer.toString(pos));
        //holder.mThumb.setImageResource(imgs[pos]);
        //holder.mVideoView.setVideoURI(Uri.parse("android.resource://" + mContext.getPackageName() + "/" + videos[pos % 2]));
        Log.d("setVideoURI", videos.get(this.indexnum));
        holder.mVideoView.setVideoURI(Uri.parse(videos.get(this.indexnum)));
        if(this.indexnum==2&&isBug){
            this.indexnum-=1;
            isBug=false;
        }
        this.indexnum+=1;

        //holder.mTitle.setText(mTitles.get(pos));
        //holder.mMarquee.setText(mMarqueeList.get(pos));
     //   holder.mMarquee.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
        //return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mRootView;
        ImageView mThumb;
        ImageView mPlay;
        TextView mTitle;
        TextView mMarquee;
        CusVideoView mVideoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRootView = itemView.findViewById(R.id.mRootView);
         //   mThumb = itemView.findViewById(R.id.mThumb);
            mPlay = itemView.findViewById(R.id.mPlay);
            mVideoView = itemView.findViewById(R.id.mVideoView);
         //   mTitle = itemView.findViewById(R.id.mTitle);
          //  mMarquee = itemView.findViewById(R.id.mMarquee);
        }
    }

}