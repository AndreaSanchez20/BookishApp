package com.example.bookdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.bookdate.ui.Playlist;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);


    }
    public void goToProfile(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key");
        Intent intent = new Intent(LoginActivity.this, UserProfile.class);
        intent.putExtra("key2",userInfo);
        startActivity(intent);

    }
    public void goToSearch(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key");
        Intent intent = new Intent(LoginActivity.this, BookListActivity.class);
        intent.putExtra("key3",userInfo);
        startActivity(intent);
    }

    public void goToPlaylist(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key");
        Intent intent = new Intent(LoginActivity.this, PlaylistActivity.class);
        intent.putExtra("key4",userInfo);
        startActivity(intent);

    }

}