package com.example.gardneer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentListFragment extends Fragment {

    private List<Comment> mCommentList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private String mCommentJsonString;
    private Button addCommentButton;
    private EditText addCommentEditText;
    JSONObject jsonObject;
    FirebaseAuth mAuth;
    int comments=0;
    String uid,postId;
    OnLikesUpdatedListener listener;
    public CommentListFragment() {
        // Required empty public constructor
    }
    public interface OnLikesUpdatedListener {
        void onLikesUpdated(String postId, String metadata);
    }
    public void setListener(OnLikesUpdatedListener listener) {
        this.listener = listener;
    }

    public static CommentListFragment newInstance(String commentJsonString,String mPostId) {
        CommentListFragment fragment = new CommentListFragment();
        Bundle args = new Bundle();
        args.putString("commentJsonString", commentJsonString);
        args.putString("postId",mPostId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            uid = mAuth.getCurrentUser().getUid();
        }
        if (getArguments() != null) {
            mCommentJsonString = getArguments().getString("commentJsonString");
            postId = getArguments().getString("postId");
            try {
                jsonObject = new JSONObject(mCommentJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("comments");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject commentObject = jsonArray.getJSONObject(i);
                    String author = commentObject.keys().next();
                    String comment = commentObject.getString(author);
                    mCommentList.add(new Comment(author, comment));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_list, container, false);
        mRecyclerView = view.findViewById(R.id.comment_recycler_view);
        addCommentButton = view.findViewById(R.id.addCommentButton);
        addCommentEditText = view.findViewById(R.id.addCommentEditText);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommentAdapter(mCommentList);
        mRecyclerView.setAdapter(mAdapter);
        addCommentButton.setOnClickListener(view1 -> {
            if(addCommentEditText.getText().toString().isEmpty()){
                Toast.makeText(requireActivity(), "Please enter comment", Toast.LENGTH_SHORT).show();
            }else{
                Comment comment = new Comment(addCommentEditText.getText().toString(),uid);
                mCommentList.add(comment);
                mAdapter.notifyDataSetChanged();
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put(comment.getComment(),comment.getAuthor());
                    jsonObject.getJSONArray("comments").put(jsonObject1);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String metadata = jsonObject.toString();
                DatabaseReference postRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
                postRef.child("metadata").setValue(metadata).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        postRef.child("comments").setValue(mCommentList.size()).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                Toast.makeText(requireActivity(), "Comment added", Toast.LENGTH_SHORT).show();
                                listener.onLikesUpdated(postId,metadata);
                            }
                        });

                    }else{
                        Toast.makeText(requireActivity(), "Comment failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
    public class Comment {
        private String comment;
        private String author;
        public Comment(){}

        public Comment(String comment, String author) {
            this.comment = comment;
            this.author = author;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

        private List<Comment> mComments;

        CommentAdapter(List<Comment> comments) {
            mComments = comments;
        }

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.comment_item, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            Comment comment = mComments.get(position);
            holder.bind(comment);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }
    }

    private class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView mAuthorTextView;
        private TextView mCommentTextView;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthorTextView = itemView.findViewById(R.id.comment_author_textview);
            mCommentTextView = itemView.findViewById(R.id.comment_text_textview);
        }

        void bind(Comment comment) {
            mAuthorTextView.setText(comment.getAuthor());
            mCommentTextView.setText(comment.getComment());
        }
    }
}
