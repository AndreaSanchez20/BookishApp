package com.example.bookdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bookdate.models.User;
import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    TextView textView, textView2, textView3, textView4;
    SeekBar seekBarRed, seekBarGreen, seekBarBlue, seekBarAlpha, seekBarSize;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int textSize, red, green, blue, alpha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        ImageView imageView = findViewById(R.id.imageView);
        Uri personPhoto = Uri.parse(userInfo[5]);
        Picasso.with(this).load(personPhoto).into(imageView);
        textView = findViewById(R.id.textView2);
        textView.setText(userInfo[0]);
        textView2 = findViewById(R.id.textView3);
        textView2.setText(userInfo[1]);
        textView3 = findViewById(R.id.textView4);
        textView3.setText(userInfo[2]);
        textView4 = findViewById(R.id.textView5);
        textView4.setText(userInfo[3]);

        seekBarRed = (SeekBar) findViewById(R.id.seekBarRed);
        seekBarGreen = (SeekBar) findViewById(R.id.seekBarGreen);
        seekBarBlue = (SeekBar) findViewById(R.id.seekBarBlue);
        seekBarAlpha = (SeekBar)findViewById(R.id.seekBarAlpha);
        seekBarSize = (SeekBar) findViewById(R.id.seekBarSize);

        seekBarRed.setOnSeekBarChangeListener(this);
        seekBarGreen.setOnSeekBarChangeListener(this);
        seekBarBlue.setOnSeekBarChangeListener(this);
        seekBarAlpha.setOnSeekBarChangeListener(this);
        seekBarSize.setOnSeekBarChangeListener(this);

        sharedPreferences = getSharedPreferences("preference", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        red = sharedPreferences.getInt("red", 0);
        green = sharedPreferences.getInt("green", 0);
        blue = sharedPreferences.getInt("blue", 0);
        alpha = sharedPreferences.getInt("alpha", 0);
        textSize = sharedPreferences.getInt("textSize", 15);

        seekBarRed.setProgress(red);
        seekBarGreen.setProgress(green);
        seekBarBlue.setProgress(blue);
        seekBarAlpha.setProgress(alpha);
        seekBarSize.setProgress(textSize);



        setColors();


    }
    public void goToHome(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        Intent intent = new Intent(UserProfile.this, LoginActivity.class);
        intent.putExtra("key",userInfo);
        startActivity(intent);
        finish();

    }
    public void goToSearch(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        Intent intent = new Intent(UserProfile.this, BookListActivity.class);
        intent.putExtra("key3",userInfo);
        startActivity(intent);
        finish();
    }

    public void goToPlaylist(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
        Intent intent = new Intent(UserProfile.this, PlaylistActivity.class);
        intent.putExtra("key4",userInfo);
        startActivity(intent);
        finish();

    }

    public void goToSearchOpenLibrary(View view){
        String[] userInfo=getIntent().getStringArrayExtra("key2");
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


    @Override
    public void onProgressChanged(SeekBar sBar, int progress, boolean fromUser) {

        if (fromUser) {

            textSize = seekBarSize.getProgress();
            red = seekBarRed.getProgress();
            green = seekBarGreen.getProgress();
            blue = seekBarBlue.getProgress();
            alpha = seekBarAlpha.getProgress();

            setColors();

            editor.putInt("red", seekBarRed.getProgress());
            editor.putInt("green", seekBarGreen.getProgress());
            editor.putInt("blue", seekBarBlue.getProgress());
            editor.putInt("alpha", seekBarAlpha.getProgress());
            editor.putInt("textSize", seekBarSize.getProgress());
            editor.commit();
        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setColors(){
        int color = Color.argb(alpha, red, green, blue);
        textView.setTextColor(color);
        textView2.setTextColor(color);
        textView3.setTextColor(color);
        textView4.setTextColor(color);

        textView.setTextSize((float) textSize);
        textView2.setTextSize((float) textSize);
        textView3.setTextSize((float) textSize);
        textView4.setTextSize((float) textSize);


    }
}