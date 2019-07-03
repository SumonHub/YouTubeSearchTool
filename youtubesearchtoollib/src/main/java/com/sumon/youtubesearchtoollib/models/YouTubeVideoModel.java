package com.sumon.youtubesearchtoollib.models;

/**
 * Created by SumOn on 7/4/2019.
 */


import androidx.annotation.NonNull;

/**
 * Created by admin on 5/16/2019.
 */

public class YouTubeVideoModel {
    private String videoId;
    private String title;
    private String description;
    private String thumbnails;
    private String channelTitle;
    private String viewCount;
    private String likeCount;

    public YouTubeVideoModel() {
    }

    public YouTubeVideoModel(String videoId, String title, String thumbnails, String channelTitle, String description, String viewCount, String likeCount) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
        this.channelTitle = channelTitle;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    public YouTubeVideoModel(String video_id, String title, String thumbnail, String owner) {
        this.videoId = video_id;
        this.title = title;
        this.thumbnails = thumbnail;
        this.channelTitle = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Video{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", thumbnails='" + thumbnails + '\'' +
                ", channelTitle='" + channelTitle + '\'' +
                ", viewCount='" + viewCount + '\'' +
                ", likeCount='" + likeCount + '\'' +
                '}';
    }

}