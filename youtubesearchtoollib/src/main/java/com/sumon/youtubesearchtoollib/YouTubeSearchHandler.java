package com.sumon.youtubesearchtoollib;

/**
 * Created by SumOn on 7/4/2019.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.sumon.youtubesearchtoollib.models.YouTubeVideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 5/17/2019.
 */

public class YouTubeSearchHandler extends AsyncTask<String, Void, JSONObject> {
    private static final String TAG = "YouTubeSearchHandler";

    public static final int SEARCH_BY_KEYWORD = 1;
    public static final int SEARCH_BY_VIDEO_ID = 2;
    public static final int SEARCH_BY_PLAYLIST_ID = 3;
    public static final String ORDER_BY_DATE = "&order=date";
    public static final String ORDER_BY_VIEW_COUNT = "&order=viewcount";
    public static final String ORDER_BY_DEFAULT = "&order=relevance";

    private String searchParam;
    private int searchType;
    private int maxResult;
    private String orderType;
    private String apiKey;

    private OnTaskCompleted onComplete;
    private ArrayList<YouTubeVideoModel> videos = new ArrayList<>();


    private YouTubeSearchHandler(final Builder builder) {
        this.searchParam = builder.searchParam;
        this.searchType = builder.searchType;
        this.maxResult = builder.maxResult;
        this.orderType = builder.orderType;
        this.apiKey = builder.apiKey;
    }

    public String getUrl() {
        String SEARCH_BY_KEYWORD_DEFAULT_URL = "https://www.googleapis.com/youtube/v3/search?&part=snippet";
        String SEARCH_BY_KEYWORD_EXTRA_URL = "&type=video&videoDuration=any&videoEmbeddable=true&videoLicense=youtube&videoSyndicated=true";
        String SEARCH_BY_VIDEO_ID_DEFAULT_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics";
        String SEARCH_BY_VIDEO_ID_EXTRAT_URL = "&fields=items(id%2Csnippet(channelTitle%2Cdescription%2Cthumbnails(maxres%2Furl%2Cstandard%2Furl)%2Ctitle)%2Cstatistics(likeCount%2CviewCount))";

        String url = "";

        switch (searchType) {
            case SEARCH_BY_KEYWORD:
                url = SEARCH_BY_KEYWORD_DEFAULT_URL + "&maxResults=" + maxResult + orderType + "&q=" + searchParam + SEARCH_BY_KEYWORD_EXTRA_URL + "&key=" + apiKey;
                break;
            case SEARCH_BY_VIDEO_ID:
                url = SEARCH_BY_VIDEO_ID_DEFAULT_URL + "&id=" + searchParam + SEARCH_BY_VIDEO_ID_EXTRAT_URL + "&key=" + apiKey;
                break;
            case SEARCH_BY_PLAYLIST_ID:
                // return default_url + "&maxResults=" + maxResult + "&order=viewcount" + "&q=" + searchParam + rule + "&key=" + apiKey;
        }
        return url;
    }

    public void onFinish(OnTaskCompleted onComplete) {
        this.onComplete = onComplete;
    }

    @Override
    protected JSONObject doInBackground(String... url) {

        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url[0])
                .build();

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                //   Log.d(TAG, ">------------- doInBackground: Response " + result);
                assert response.body() != null;
                return new JSONObject(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
            onComplete.onError();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (result != null) {
            switch (searchType) {
                case SEARCH_BY_KEYWORD:
                    _searchByKeyWord(result);
                    break;
                case SEARCH_BY_VIDEO_ID:
                    _searchByVideoId(result);
                    break;
                case SEARCH_BY_PLAYLIST_ID:
                    _searchByPlaylistId(result);
                    break;
            }
        } else
            onComplete.onError();
    }

    private void _searchByPlaylistId(JSONObject result) {

    }

    private void _searchByVideoId(JSONObject result) {
        try {
            JSONObject item = result.getJSONArray("items").getJSONObject(0);
            String videoId = item.getString("id");
            String title = item.getJSONObject("snippet").getString("title");
            String description = item.getJSONObject("snippet").getString("description");
            String thumbnails = item.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("standard").getString("url");
            String channelTitle = item.getJSONObject("snippet").getString("channelTitle");
            String viewCount = item.getJSONObject("statistics").getString("viewCount");
            String likeCount = item.getJSONObject("statistics").getString("likeCount");
            YouTubeVideoModel temp_video = new YouTubeVideoModel(videoId, title, thumbnails, channelTitle, description, viewCount, likeCount);
            videos.add(temp_video);

            onComplete.onTaskCompleted(videos);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void _searchByKeyWord(JSONObject result) {
        try {
            JSONArray itemList = result.getJSONArray("items");
            for (int i = 0; i < itemList.length(); i++) {
                JSONObject item = itemList.getJSONObject(i);
                String video_id = item.getJSONObject("id").getString("videoId");
                String title = item.getJSONObject("snippet").getString("title");
                String thumbnail = item.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
                String owner = item.getJSONObject("snippet").getString("channelTitle");
                YouTubeVideoModel temp_video = new YouTubeVideoModel(video_id, title, thumbnail, owner);
                videos.add(temp_video);
                Log.d(TAG, "onPostExecute: add video id - " + temp_video.toString());
            }
            Log.d(TAG, ">------------ Youtube data parsed correctly!");
            onComplete.onTaskCompleted(videos);

        } catch (Exception e) {
            e.printStackTrace();
            onComplete.onError();
        }
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(ArrayList<YouTubeVideoModel> list);

        void onError();
    }

    public static class Builder {
        String searchParam;
        int searchType;
        int maxResult;
        String orderType;
        String apiKey;

        public Builder setSearchParam(String searchParam) {
            this.searchParam = searchParam;
            return this;
        }

        public Builder setSearchType(int searchType) {
            this.searchType = searchType;
            return this;
        }

        public Builder setMaxResult(int maxResult) {
            this.maxResult = maxResult;
            return this;
        }

        public Builder setOrderType(String orderType) {
            this.orderType = orderType;
            return this;
        }

        public Builder setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public YouTubeSearchHandler create() {
            YouTubeSearchHandler searchHandler = new YouTubeSearchHandler(this);
            if (searchHandler.searchParam == null) {
                throw new IllegalStateException("searchParam can not be empty!");
            } else if (searchHandler.searchType == 0) {
                throw new IllegalStateException("searchType can not be empty!");
            } else if (searchHandler.apiKey == null) {
                throw new IllegalStateException("apiKey can not be empty!");
            } else if (searchHandler.maxResult > 50 || searchHandler.maxResult == 0) {
                throw new IllegalStateException("maxResult must be 1-50");
            }
            return searchHandler;
        }
    }
}