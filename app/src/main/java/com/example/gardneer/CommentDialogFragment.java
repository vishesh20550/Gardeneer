package com.example.gardneer;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommentDialogFragment extends DialogFragment {
    private static final String ARG_COMMENTS = "comments";

    private String mParam1,postId;
    static CommunityPostActivity context;

    public CommentDialogFragment() {
        // Required empty public constructor
    }

    public static CommentDialogFragment newInstance(String comments, String postId,CommunityPostActivity communityPostActivity) {
        CommentDialogFragment fragment = new CommentDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMMENTS, comments);
        args.putString("postId",postId);
        context= communityPostActivity;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_COMMENTS);
            postId = getArguments().getString("postId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_dialog, container, false);
        String metadata = getArguments().getString(ARG_COMMENTS);
        String postId = getArguments().getString("postId");
        CommentListFragment commentListFragment = CommentListFragment.newInstance(metadata,postId);
        commentListFragment.setListener(context);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.comment_list_container, commentListFragment).commit();
        return view;
    }
}