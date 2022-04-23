package com.example.bookdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookdate.models.User;
import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        ImageView imageView = findViewById(R.id.imageView);
        Uri personPhoto = Uri.parse(userInfo[5]);
        Picasso.with(this).load(personPhoto).into(imageView);
        TextView textView = findViewById(R.id.textView2);
        textView.setText(userInfo[0]);
        TextView textView2 = findViewById(R.id.textView3);
        textView2.setText(userInfo[1]);
        TextView textView3 = findViewById(R.id.textView4);
        textView3.setText(userInfo[2]);
        TextView textView4 = findViewById(R.id.textView5);
        textView4.setText(userInfo[3]);


    }
    public void goToHome(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        Intent intent = new Intent(UserProfile.this, LoginActivity.class);
        intent.putExtra("key",userInfo);
        startActivity(intent);
        finish();

    }

    /*public void goToSearch(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        Intent intent = new Intent(UserProfile.this, BookListActivity.class);
        intent.putExtra("key3",userInfo);
        startActivity(intent);
        finish();
    }*/
    public void goToSearchOpenLibrary(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key");
        Intent intent = new Intent(UserProfile.this, BookListActivity.class);
        intent.putExtra("key3",userInfo); // para que es esto?
        intent.putExtra("source","OpenLibrary");
        startActivity(intent);
    }

    //IEEE API new button
    public void goToSearchIEEE(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        Intent intent = new Intent(UserProfile.this, BookListActivity.class);
        intent.putExtra("key3",userInfo);
        intent.putExtra("source","IEEE");
        startActivity(intent);
    }

    public void goToPlaylist(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        Intent intent = new Intent(UserProfile.this, PlaylistActivity.class);
        intent.putExtra("key4",userInfo);
        startActivity(intent);
        finish();

    }


}