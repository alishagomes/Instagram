package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * for displaying posts feed
 */
public class FeedActivity extends AppCompatActivity {

    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private RecyclerView recyclerViewPosts;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(this, allPosts);
        recyclerViewPosts.setAdapter(adapter);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        queryPosts();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                adapter.clear();
                queryPosts();
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }
    private void queryPosts() {
        /** specify what type of data we want to query - Post.class */
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        /** include data referred by user key */
        query.include(Post.KEY_USER);
         /** limit query to latest 20 items */
        query.setLimit(20);
         /** order posts by creation date (newest first) */
        query.addDescendingOrder("createdAt");
         /** start an asynchronous call for posts */
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                /** check for errors */
                if (e != null) {
                    return;
                }
                /** save received posts to list and notify adapter of new data */
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}