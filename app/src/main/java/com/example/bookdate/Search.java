package com.example.bookdate;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.bookdate.databinding.ActivitySearchBinding;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void goToProfile(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key");
        Intent intent = new Intent(Search.this, UserProfile.class);
        intent.putExtra("key2",userInfo);
        startActivity(intent);

    }
    public void goToHome(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key");
        Intent intent = new Intent(Search.this, LoginActivity.class);
        intent.putExtra("key2",userInfo);
        startActivity(intent);
    }

    public void goToPlaylist(View view){
        Intent intent = new Intent(Search.this, PlaylistActivity.class);
        startActivity(intent);

    }
}