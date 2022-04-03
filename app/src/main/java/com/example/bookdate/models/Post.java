package com.example.bookdate.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    public String uid;
    public String author;
    public String title;
    public String publisher;
    public int starCount = 0;
    public String url;
    public String tag;
    public String key;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String publisher, int starCount, String url, String key, String tag) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.starCount = starCount;
        this.url=url;
        this.key=key;
        this.tag = tag;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("publisher", publisher);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("url",url);
        result.put("key",key);
        result.put("tag",tag);

        return result;
    }

}
