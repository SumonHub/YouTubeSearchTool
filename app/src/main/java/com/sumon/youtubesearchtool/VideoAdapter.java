package com.sumon.youtubesearchtool;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by SumOn on 7/4/2019.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sumon.youtubesearchtoollib.models.YouTubeVideoModel;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<YouTubeVideoModel> videoList;

    public VideoAdapter() {
    }

    public VideoAdapter(Context context, ArrayList<YouTubeVideoModel> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_card, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final YouTubeVideoModel videoModel = videoList.get(position);
        Glide.with(context).load(videoModel.getThumbnails()).into(holder._thumbnailArt);
        holder._title.setText(videoModel.getTitle());
        holder._channelName.setText("Cannel Title " + videoModel.getChannelTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(context,"tap on position " +position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView _thumbnailArt;
        public TextView _title;
        public TextView _channelName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            _thumbnailArt = itemView.findViewById(R.id.iv_thumbArt);
            _title = itemView.findViewById(R.id.tv_title);
            _channelName = itemView.findViewById(R.id.tv_owner);

        }
    }
}