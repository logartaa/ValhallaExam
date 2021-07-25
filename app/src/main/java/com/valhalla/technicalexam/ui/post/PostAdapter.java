package com.valhalla.technicalexam.ui.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valhalla.technicalexam.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPostList;
    private PostListener mPostListener;

    public PostAdapter(List<Post> postList, PostListener postListener) {
        this.mPostList = postList;
        this.mPostListener = postListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.tvPostTitle.setText(mPostList.get(position).title);
        holder.tvPostBody.setText(mPostList.get(position).body);

        holder.lContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPostListener.checkPostDetails(mPostList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public void updatePostList(List<Post> postList){
        mPostList = postList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPostTitle;
        public TextView tvPostBody;
        public LinearLayout lContainer;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvPostTitle = Objects.requireNonNull(itemView).findViewById(R.id.post_title);
            tvPostBody = Objects.requireNonNull(itemView).findViewById(R.id.post_body);
            lContainer = Objects.requireNonNull(itemView).findViewById(R.id.container);
        }

    }
}
