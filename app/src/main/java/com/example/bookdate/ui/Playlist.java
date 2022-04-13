package com.example.bookdate.ui;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookdate.PlaylistAdapter;
import com.example.bookdate.R;

import com.example.bookdate.models.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;

public class Playlist {
    Context context;
    FirebaseDatabase db;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String URLDB = "https://bookdate-6a2ab-default-rtdb.firebaseio.com/";
    private static final String TAG = "NewPlayList";
    public ArrayList<Post> resultplaylist = new ArrayList<Post>();
    ListView mListView;
    public ArrayList<String>tags= new ArrayList<>();

    public Playlist() {
    }
    public Playlist(Context context,ListView list_view_playlist) {
        this.context = context;
        db = FirebaseDatabase.getInstance(URLDB);
        mDatabase = db.getReference();
        mAuth = FirebaseAuth.getInstance();
        mListView = list_view_playlist;
    }

    public void submitPost(String author, String title, String body,String url) {

        Toast.makeText(context, "Posting...", Toast.LENGTH_SHORT).show();
        final String userId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        writeNewPost(userId, author, title, body,url, dataSnapshot.getKey(), "something");
//                        User user = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    private void writeNewPost(String userId, String author, String title, String body,String url,String id, String tag) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, author, title, body, 0,url, key, "All");
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-playlist/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);

    }
    public Post showDetails(int position){
       PlaylistAdapter adapter = new PlaylistAdapter(context, R.layout.playlist_book, resultplaylist);
        mListView.setAdapter(adapter);
        return adapter.getItem(position);
    }

    public void ShowPlaylist() {
          PlaylistAdapter adapter = new PlaylistAdapter(context, R.layout.playlist_book, resultplaylist);
        mListView.setAdapter(adapter);
    }

    public ArrayList<Post> RetrievePlaylist() {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = db.getReference("/user-playlist/" + userId + "/" );

        resultplaylist.clear();

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playlist: dataSnapshot.getChildren()) {
                    Post post = playlist.getValue(Post.class);
                   // Post post = playlist.child("tag");
                    resultplaylist.add(post);
                }
                //cuando se ejecuta este metodo y este full el post, se ejecuta el metodo q va a mostrar los datos en la pantalla
                ShowPlaylist();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return resultplaylist;
    }

    //retrieve playlist filtered by tag
    public ArrayList<Post> RetrievePlaylist(String tag) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = db.getReference("/user-playlist/" + userId + "/" );

        resultplaylist.clear();
// Attach a listener to read the data at our posts reference

        ref.orderByChild("tag").equalTo(tag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot playlist: dataSnapshot.getChildren()) {
                    Post post = playlist.getValue(Post.class);
                    resultplaylist.add(post);
                }

                ShowPlaylist();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return resultplaylist;
    }


    public ArrayList<String> RetrieveTags() {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = db.getReference("/user-playlist/" + userId + "/" );

// Attach a listener to read the tag data at the posts reference

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot playlist: dataSnapshot.getChildren()) {
                    String tag = (String) playlist.child("tag").getValue();  //get the names of the tags

                    //add the tag if it is unique

                    if (!tags.contains(tag))
                    {
                    tags.add(tag);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // return list of all tags
        return tags;
}
}




