package com.valhalla.technicalexam.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valhalla.technicalexam.R;
import com.valhalla.technicalexam.ui.post.Post;
import com.valhalla.technicalexam.ui.post.PostAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter {

    public static final int POST_VIEW_TYPE = 0;
    public static final int COMMENT_VIEW_TYPE = 1;

    private List<Comments> mCommentsList;
    private final Post mPost;

    public CommentsAdapter(List<Comments> commentsList, Post post) {
        this.mCommentsList = commentsList;
        this.mPost = post;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == POST_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_post_item, parent, false);
            return new PostAdapter.ViewHolder(view);
        } else {
            // Create a new view, which defines the UI of the list item
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_comment_item, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostAdapter.ViewHolder) {
            PostAdapter.ViewHolder viewHolder = (PostAdapter.ViewHolder) holder;
            viewHolder.tvPostTitle.setText(mPost.getTitle());
            viewHolder.tvPostBody.setText(mPost.getBody());
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            // deduct 1 due to first post item in the list
            Comments comments = mCommentsList.get(position-1);
            viewHolder.tvName.setText(comments.name);
            viewHolder.tvEmail.setText(comments.email);
            viewHolder.tvBody.setText(comments.body);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return POST_VIEW_TYPE;
        } else {
            return COMMENT_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size() + 1;
    }

    public void updateCommentsList(List<Comments> commentsList) {
        mCommentsList = commentsList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmail;
        public TextView tvBody;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName = Objects.requireNonNull(itemView).findViewById(R.id.tvName);
            tvEmail = Objects.requireNonNull(itemView).findViewById(R.id.tvEmail);
            tvBody = Objects.requireNonNull(itemView).findViewById(R.id.tvBody);
        }

    }
}
