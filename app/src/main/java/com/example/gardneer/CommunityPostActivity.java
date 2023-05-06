package com.example.gardneer;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CommunityPostActivity extends AppCompatActivity implements CommentListFragment.OnLikesUpdatedListener{
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button addNewPostButton,postButton,cancelButton;
    TextView backButtonCPActivity;
    EditText detailsEditText,titleEditText,usernameEditText;
    Dialog popupAddPost;
    String tempUsername="";
    private RecyclerView mRecyclerView;
    ArrayList<CommunityPost> communityPosts;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String communityPostsUrl = "https://sheets.googleapis.com/v4/spreadsheets/1MpuSYBwdZQCcae4bgIFK_azQ1LnA-ahpA0EvF8aLsf0/values/Sheet3?alt=json&key=AIzaSyD-P_Sam9yUOlWAigZt4pSJidXwKKBZFKQ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_post);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mRecyclerView = findViewById(R.id.community_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        addNewPostButton= findViewById(R.id.addNewPostButton);
        backButtonCPActivity= findViewById(R.id.backButtonCPActivity);
        backButtonCPActivity.setOnClickListener(view -> {onBackPressed();});

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        loadData();
        initializePopup();
        addNewPostButton.setOnClickListener(view -> {
            titleEditText.setText("");
            usernameEditText.setText(tempUsername);
            detailsEditText.setText("");
            popupAddPost.show();
        });
        communityPosts= new ArrayList<>();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, communityPostsUrl, null,
//                response -> {
//                    try {
//                        JSONArray rows = response.getJSONArray("values");
//                        for (int i = 1; i < rows.length(); i++) {
//                            JSONArray row = rows.getJSONArray(i);
//                            String id = row.getString(0);
//                            String title = row.getString(1);
//                            String details = row.getString(2);
//                            String postedBy = row.getString(3);
//                            String metadata = row.getString(4);
//
//                            communityPosts.add(new CommunityPost(title, details, postedBy, metadata));
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                },
//                error -> Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show());
//
//        requestQueue.add(jsonObjectRequest);
    }

    private void loadData() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                communityPosts.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CommunityPost object = childSnapshot.getValue(CommunityPost.class);
                    communityPosts.add(object);
                    Log.i("CommentsNumber",object.getComments()+"");
                }
                mAdapter = new CommunityPostAdapter(communityPosts);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CommunityPostActivity.this, "Fetching posts failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initializePopup(){
        popupAddPost = new Dialog(this);
        popupAddPost.setContentView(R.layout.popoup_add_post);
        popupAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popupAddPost.getWindow().getAttributes().gravity = Gravity.TOP;
        postButton= popupAddPost.findViewById(R.id.postButton);
        cancelButton= popupAddPost.findViewById(R.id.cancelButton);
        detailsEditText= popupAddPost.findViewById(R.id.detailsEditText);
        titleEditText= popupAddPost.findViewById(R.id.titleEditText);
        usernameEditText = popupAddPost.findViewById(R.id.usernameEditText);
        cancelButton.setOnClickListener(view -> {
            titleEditText.setText("");
            usernameEditText.setText(tempUsername);
            detailsEditText.setText("");
            popupAddPost.dismiss();
        });
        postButton.setOnClickListener(view -> {
            if(titleEditText.getText().toString().isEmpty() || detailsEditText.getText().toString().isEmpty() || usernameEditText.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter title and description to post", Toast.LENGTH_SHORT).show();
            }
            else{
                String metadata = "{\"comments\": []}";
                tempUsername = usernameEditText.getText().toString();
                CommunityPost post = new CommunityPost(titleEditText.getText().toString(),detailsEditText.getText().toString(),usernameEditText.getText().toString(),metadata,0);
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Posts").push();
                String postKey = ref.getKey();
                post.setPostId(postKey);
                ref.setValue(post).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(CommunityPostActivity.this, "Posted", Toast.LENGTH_SHORT).show();
                        popupAddPost.dismiss();
                    }else{
                        Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void showCommentDialog(String metadata, String postId) {
        FragmentManager fm = getSupportFragmentManager();
        CommentDialogFragment dialogFragment = CommentDialogFragment.newInstance(metadata,postId,this);
        dialogFragment.show(fm, "Comment Dialog");
    }

    @Override
    public void onLikesUpdated(String postId, String metadata) {
        int comments=0;
        // find the index of the updated post in the list
        int updatedPostIndex = -1;
        for (int i = 0; i < communityPosts.size(); i++) {
            if (communityPosts.get(i).getPostId().equals(postId)) {
                updatedPostIndex = i;
                break;
            }
        }
        if (updatedPostIndex != -1) {
            // update the post object in the list
            communityPosts.get(updatedPostIndex).setMetadata(metadata);
            try {
                JSONObject metadataJson = new JSONObject(metadata);
                JSONArray commentsJson = metadataJson.getJSONArray("comments");
                comments = commentsJson.length();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            communityPosts.get(updatedPostIndex).setComments(comments);
            mAdapter.notifyItemChanged(updatedPostIndex);
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
//            public Button mShareButton;

            public CommunityPostViewHolder(View itemView) {
                super(itemView);
                mTitleView = itemView.findViewById(R.id.title);
                mDetailsView = itemView.findViewById(R.id.details);
                mPostedByView = itemView.findViewById(R.id.posted_by);
                mLikeButton = itemView.findViewById(R.id.like_button);
                mCommentButton = itemView.findViewById(R.id.comment_button);
//                mShareButton = itemView.findViewById(R.id.share_button);
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
            int p = holder.getAdapterPosition();
            CommunityPost post = mCommunityPosts.get(p);
            holder.mTitleView.setText(post.getTitle());
            holder.mDetailsView.setText(post.getDetails());
            holder.mPostedByView.setText(post.getPostedBy());
            holder.mLikeButton.setText(String.valueOf(post.getLikes()));
            holder.mCommentButton.setText(String.valueOf(post.getComments()));

            holder.mLikeButton.setOnClickListener(v -> {
                // Handle like button click event
                String postId = post.getPostId();
                DatabaseReference postRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
                postRef.child("likes").runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer currentLikes = mutableData.getValue(Integer.class);
                        if (currentLikes == null) {
                            mutableData.setValue(1);
                        } else {
                            mutableData.setValue(currentLikes + 1);
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            // handle error
                            Toast.makeText(CommunityPostActivity.this, "Like failed", Toast.LENGTH_SHORT).show();
                        } else {
                            // update local arraylist and UI
                            int newLikes = dataSnapshot.getValue(Integer.class);
                            post.setLikes(newLikes);
                            communityPosts.get(p).setLikes(newLikes);
                            mAdapter.notifyItemChanged(p);
                        }
                    }
                });
            });

            holder.mCommentButton.setOnClickListener(v -> {
                // Handle comment button click event
                showCommentDialog(post.getMetadata(),post.getPostId());
            });

//            holder.mShareButton.setOnClickListener(v -> {
                // Handle share button click event
//            });
        }

        @Override
        public int getItemCount() {
            return mCommunityPosts.size();
        }
    }

    //        @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        this.finishAffinity();
//        finish();
//    }
}