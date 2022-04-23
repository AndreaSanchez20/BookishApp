package com.example.bookdate;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class PlaylistActivity extends AppCompatActivity {
    public ListView as_list_view_playlist;
    public static final String BOOK_DETAIL_KEY = "playlist";
    PlaylistAdapter playlistAdapter;
    public Playlist playlist;
    public ArrayList<Post> arrayList;
    Spinner spinner;

    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.playlist);
            as_list_view_playlist = findViewById(R.id.list_view_myplaylist);

            playlist = new Playlist(this,as_list_view_playlist);

            // arrayList=playlist.RetrievePlaylist();

            ArrayList<String> Tags ;

            setupBookSelectedListener();

            //retrieve the tags from the database

            Tags=playlist.RetrieveTags();

            //default to display all books
            Tags.add(0,"All");

            spinner = (Spinner) findViewById(R.id.spinner);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Tags);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            tagListener();

    }

    private void tagListener(){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String choice;
                choice =(String) adapterView.getItemAtPosition(i);

                Log.d("Choice: ", choice);

                if(choice.equals("All")){
                    arrayList=playlist.RetrievePlaylist();
                }
                else {
                    //use string param to only show certain tag in the listview

                    arrayList=playlist.RetrievePlaylist(choice);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
                intent.putExtra("key", playlist.showDetails(position).key);
                intent.putExtra("tag", playlist.showDetails(position).tag);
                startActivity(intent);
            }
        });
    }
    public void sharePlaylist(){
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

//search IEEE API with new button
   /* public void goToSearchIEEE(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        Intent intent = new Intent(PlaylistActivity.this, BookListActivity.class);
        intent.putExtra("key3",userInfo);
        startActivity(intent);
        finish();
    }*/

    public void goToProfile(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key4");
        Intent intent = new Intent(PlaylistActivity.this, UserProfile.class);
        intent.putExtra("key2",userInfo);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_share) {
            sharePlaylist();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void goToSearchOpenLibrary(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key");
        Intent intent = new Intent(PlaylistActivity.this, BookListActivity.class);
        intent.putExtra("key3",userInfo); // para que es esto?
        intent.putExtra("source","OpenLibrary");
        startActivity(intent);
    }

    public void goToSearchIEEE(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key");
        Intent intent = new Intent(PlaylistActivity.this, BookListActivity.class);
        intent.putExtra("key3",userInfo);
        intent.putExtra("source","IEEE");
        startActivity(intent);
    }
}
