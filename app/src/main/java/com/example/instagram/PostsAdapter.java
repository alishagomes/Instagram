package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

/**
 * adapter class
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private static final String TAG = "Error";
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        private ImageView postImage;
        private TextView textDescription;
        private TextView timestamp;
        private ImageView profileImage;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            postImage = itemView.findViewById(R.id.postImage);
            textDescription = itemView.findViewById(R.id.caption);
            timestamp = itemView.findViewById(R.id.timestamp);
            /** Setting the profile image */
            profileImage = itemView.findViewById(R.id.profileImage);
            itemView.setOnClickListener(this);
        }
        /** Bind the post data to the view elements */
        public void bind(Post post) {
            textDescription.setText(post.getDescription());
            name.setText(post.getUser().getUsername());
            Date createdAt = post.getCreatedAt();
            String timeAgo = Post.calculateTimeAgo(createdAt);
            timestamp.setText(timeAgo);

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(postImage);
            }
            /** For the profile image */
            ParseFile userImage = post.getUser().getParseFile("profilePicture");
            if (userImage != null) {
                Glide.with(context).load(userImage.getUrl()).into(profileImage);
            }

        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Post post = posts.get(position);
                Intent i = new Intent(context, PostDetails.class);
                i.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(i);
            }
        }
    }
    /** Clean all elements of the recycler */
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }
    /** Add a list of items -- change to type used */
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
