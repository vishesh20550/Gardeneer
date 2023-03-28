package com.example.gardneer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CommunityPostActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String communityPostsUrl = "https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet3?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_post);

        mRecyclerView = findViewById(R.id.community_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<CommunityPost> communityPosts = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, communityPostsUrl, null,
                response -> {
                    try {
                        JSONArray rows = response.getJSONArray("values");
                        for (int i = 1; i < rows.length(); i++) {
                            JSONArray row = rows.getJSONArray(i);
                            String id = row.getString(0);
                            String title = row.getString(1);
                            String details = row.getString(2);
                            String postedBy = row.getString(3);
                            String metadata = row.getString(4);

                            communityPosts.add(new CommunityPost(title, details, postedBy, metadata));
                        }
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonObjectRequest);
        mAdapter = new CommunityPostAdapter(communityPosts);
        mRecyclerView.setAdapter(mAdapter);
    }
    private class CommunityPost {
        private String mTitle;
        private String mDetails;
        private String mPostedBy;
        private String mMetadata;
        public CommunityPost(String title, String details, String postedBy, String metadata) {
            mTitle = title;
            mDetails = details;
            mPostedBy = postedBy;
            mMetadata = metadata;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getDetails() {
            return mDetails;
        }

        public String getPostedBy() {
            return mPostedBy;
        }

        public String getMetadata() {
            return mMetadata;
        }
    }
    private class CommunityPostAdapter extends RecyclerView.Adapter<CommunityPostAdapter.CommunityPostViewHolder> {

        private ArrayList<CommunityPost> mCommunityPosts;

        public class CommunityPostViewHolder extends RecyclerView.ViewHolder {
            public TextView mTitleView;
            public TextView mDetailsView;
            public TextView mPostedByView;
            public Button mLikeButton;
            public Button mCommentButton;
            public Button mShareButton;

            public CommunityPostViewHolder(View itemView) {
                super(itemView);
                mTitleView = itemView.findViewById(R.id.title);
                mDetailsView = itemView.findViewById(R.id.details);
                mPostedByView = itemView.findViewById(R.id.posted_by);
                mLikeButton = itemView.findViewById(R.id.like_button);
                mCommentButton = itemView.findViewById(R.id.comment_button);
                mShareButton = itemView.findViewById(R.id.share_button);
            }
        }

        public CommunityPostAdapter(ArrayList<CommunityPost> communityPosts) {
            mCommunityPosts = communityPosts;
        }

        @Override
        public CommunityPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.community_post_item, parent, false);

            return new CommunityPostViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CommunityPostViewHolder holder, int position) {
            CommunityPost post = mCommunityPosts.get(position);

            holder.mTitleView.setText(post.getTitle());
            holder.mDetailsView.setText(post.getDetails());
            holder.mPostedByView.setText(post.getPostedBy());

            holder.mLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle like button click event
                }
            });

            holder.mCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle comment button click event
                }
            });

            holder.mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle share button click event
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCommunityPosts.size();
        }
    }
        @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
        finish();
    }
}