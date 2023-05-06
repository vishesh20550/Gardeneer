package com.example.gardneer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommunityPost {
    private String title;
    private String details;

    private String postId;
    private String postedBy;
    private String metadata;
    private int likes;
    private int comments;
    public CommunityPost(){}
    public CommunityPost(String title, String details, String postedBy, String metadata,int likes) {
        this.title = title;
        this.details = details;
        this.postedBy = postedBy;
        this.metadata = metadata;
        this.likes = likes;
        try {
            JSONObject metadataJson = new JSONObject(metadata);
            JSONArray commentsJson = metadataJson.getJSONArray("comments");
            this.comments = commentsJson.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
