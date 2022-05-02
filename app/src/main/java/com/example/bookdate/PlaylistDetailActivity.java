package com.example.bookdate;

import static com.google.android.gms.wearable.DataMap.TAG;



import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;



import android.app.Dialog;

import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;

import android.text.TextUtils;

import android.util.Log;

import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ImageView;

import android.widget.TextView;

import android.widget.Toast;



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

import java.util.HashMap;



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
    Dialog dialog;

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

        //dialog for deleting confirmation

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.deleting_confirmation); //set the layout (display) of the dialogue
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background)); //set the background of the dialogue
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //set the size of the dialogue
        dialog.setCancelable(false); //By setting this value to false, the user can not close a dialogue by clicking anywhere outside of the dialogue
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation; //Set the animation of the dialogue as fade in/fade out when it's opened/closed

        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);


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
        String key=(String) getIntent().getSerializableExtra("key");
        String cover = (String) getIntent().getSerializableExtra("cover");

        Picasso.with(this).load(Uri.parse(cover)).error(R.drawable.ic_no_cover).into(ivBookCover);

        // Two options are provided in the dialogue: Yes to remove the book from a book list, and No to cancel the deleting action
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
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


                Toast.makeText(getApplicationContext(), "Removed", Toast.LENGTH_SHORT).show();
            }
        });


        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });



        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG = edit_view_tag.getText().toString();

                //add book to default list if no tag present
                if (TAG.equals("")){
                    TAG="All";
                }

                //navigate to child node of specific book, tag attribute

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                ref.child("user-playlist").child(userId).child(key).child("tag").setValue(TAG);
                edit_view_tag.getText().clear();

                Intent intent = new Intent(PlaylistDetailActivity.this, PlaylistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        as_button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            } //Display the dialogue when user wants to remove a book from a book list
        });
    }
}

