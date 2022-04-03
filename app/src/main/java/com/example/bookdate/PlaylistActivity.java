package com.example.bookdate;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;


import org.json.JSONArray;
import org.json.JSONException;

import com.example.bookdate.models.Post;
import com.example.bookdate.ui.Playlist;
import com.loopj.android.http.*;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import androidx.appcompat.app.AppCompatActivity;

public class PlaylistActivity extends AppCompatActivity{
    ListView as_list_view_playlist;
    public static final String BOOK_DETAIL_KEY = "playlist";
    PlaylistAdapter playlistAdapter;
    Playlist playlist;
    ArrayList<Post> arrayList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.playlist);
            as_list_view_playlist = findViewById(R.id.list_view_myplaylist);


            playlist = new Playlist(this,as_list_view_playlist);

            arrayList=playlist.RetrievePlaylist();

            setupBookSelectedListener();

        }
    private void setupBookSelectedListener() {
        as_list_view_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(PlaylistActivity.this, PlaylistDetailActivity.class);
                intent.putExtra("author", playlist.showDetails(position).author);
                intent.putExtra("title", playlist.showDetails(position).title);
                intent.putExtra("publisher", playlist.showDetails(position).publisher);
                intent.putExtra("userId", playlist.showDetails(position).uid);
                intent.putExtra("cover", playlist.showDetails(position).url);
                intent.putExtra("tag", playlist.showDetails(position).tag);
                startActivity(intent);
            }


        });
    }
    public void sharePlaylist(View view){
            String message="";
        for (int title = 0; title < arrayList.size(); title++) {
            message=message+arrayList.get(title).title+" by " + arrayList.get(title).author+"\n";
        }
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Book Recommendations!");
        i.putExtra(Intent.EXTRA_TEXT   , "I think you might like... \n" +message);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(PlaylistActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
    public void goToHome(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key4");
        Intent intent = new Intent(PlaylistActivity.this, LoginActivity.class);
        intent.putExtra("key",userInfo);
        startActivity(intent);
        finish();

    }
    public void goToSearch(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key4");
        Intent intent = new Intent(PlaylistActivity.this, BookListActivity.class);
        intent.putExtra("key3",userInfo);
        startActivity(intent);
        finish();
    }

    public void goToProfile(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key4");
        Intent intent = new Intent(PlaylistActivity.this, PlaylistActivity.class);
        intent.putExtra("key2",userInfo);
        startActivity(intent);
        finish();

    }
}
