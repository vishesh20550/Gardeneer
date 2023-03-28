package com.example.gardneer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public CommentListFragment() {
        // Required empty public constructor
    }

    public static CommentListFragment newInstance(String commentJsonString) {
        CommentListFragment fragment = new CommentListFragment();
        Bundle args = new Bundle();
        args.putString("commentJsonString", commentJsonString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCommentJsonString = getArguments().getString("commentJsonString");
            try {
                JSONObject jsonObject = new JSONObject(mCommentJsonString);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommentAdapter(mCommentList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
    public class Comment {
        private String comment;
        private String author;

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
