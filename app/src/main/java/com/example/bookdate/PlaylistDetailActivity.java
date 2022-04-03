package com.example.bookdate;

import static com.google.android.gms.wearable.DataMap.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookdate.models.*;
import com.example.bookdate.ui.Playlist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

import cz.msebera.android.httpclient.Header;

public class PlaylistDetailActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvPublisher;
    private TextView tvPageCount;
    private BookClient client;
    private Button as_button_remove;
    private ImageView ivBookCover;
    private TextView text_view_tag;
    private EditText edit_view_tag;
    private Button button_submit;
    String TAG="";
    String tag_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        tvPageCount = (TextView) findViewById(R.id.tvPageCount);
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        as_button_remove = findViewById(R.id.button_add);

        text_view_tag = (TextView) findViewById(R.id.text_view_tag_name);
        edit_view_tag = (EditText) findViewById(R.id.edit_text_tag);
        button_submit = (Button) findViewById(R.id.button_submit);
        // Use the post to populate the data into our views
        String author = (String) getIntent().getSerializableExtra("author");
        tvAuthor.setText(author);
        String title = (String) getIntent().getSerializableExtra("title");
        tvTitle.setText(title);
        String publisher = (String) getIntent().getSerializableExtra("publisher");
        tvPublisher.setText(publisher);
        String userId=(String) getIntent().getSerializableExtra("userId");
        String cover = (String) getIntent().getSerializableExtra("cover");
        Picasso.with(this).load(Uri.parse(cover)).error(R.drawable.ic_no_cover).into(ivBookCover);

        tag_text = (String) getIntent().getSerializableExtra("tag");
        //TAG



        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG = edit_view_tag.getText().toString();
                tag_text = TAG;
                //DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                //Query databaseQuery = ref.child("user-playlist").child(userId).orderByChild("tag").equalTo(TAG);
            }
        });

        as_button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query databaseQuery = ref.child("user-playlist").child(userId).orderByChild("title").equalTo(title);

                databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot datasSnapshot: dataSnapshot.getChildren()) {
                            datasSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
                Intent intent = new Intent(PlaylistDetailActivity.this, PlaylistActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}