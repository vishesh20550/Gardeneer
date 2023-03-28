package com.example.gardneer;

import android.os.Bundle;

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

    private String mParam1;

    public CommentDialogFragment() {
        // Required empty public constructor
    }

    public static CommentDialogFragment newInstance(String comments) {
        CommentDialogFragment fragment = new CommentDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMMENTS, comments);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_COMMENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_dialog, container, false);
        String metadata = getArguments().getString(ARG_COMMENTS);
        CommentListFragment commentListFragment = CommentListFragment.newInstance(metadata);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.comment_list_container, commentListFragment).commit();
        return view;
    }
}