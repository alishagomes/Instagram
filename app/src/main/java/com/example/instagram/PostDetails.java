package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;

/**
 * for displaying details page after user clicks on a post
 */

public class PostDetails extends AppCompatActivity {

    Post post;
    TextView description;
    TextView timestamp;
    ImageView postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        description = findViewById(R.id.caption);
        postImage = findViewById(R.id.postImage);
        timestamp = findViewById(R.id.timestamp);

        /** for timestamp */
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        timestamp.setText(timeAgo);

        description.setText(post.getDescription());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(postImage);
        }

    }
}


