package com.example.bookdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.bookdate.ui;
import com.example.bookdate.ui.Playlist;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvPublisher;
    private TextView tvPageCount;
    private TextView tvDescription;
    private BookClient client;
    private Button as_button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_profile_page);
        // Fetch views
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        tvPageCount = (TextView) findViewById(R.id.tvPageCount);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        as_button_add = findViewById(R.id.button_add);
        // Use the book to populate the data into our views
        Book book = (Book) getIntent().getSerializableExtra(BookListActivity.BOOK_DETAIL_KEY);
        loadBook(book);

        as_button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Playlist playlist = new Playlist(getBaseContext(),null);
                playlist.submitPost(tvAuthor.getText().toString(),tvTitle.getText().toString(),tvPublisher.getText().toString(),book.getLargeCoverUrl());
                Log.d("TAG",book.getLargeCoverUrl());
            }
        });
    }

    // Populate data for the book
    private void loadBook(Book book) {
        //change activity title
        this.setTitle(book.getTitle());
        // Populate data
        Picasso.with(this).load(Uri.parse(book.getLargeCoverUrl())).error(R.drawable.ic_no_cover).into(ivBookCover);
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvDescription.setText(book.getDescription());
        tvPublisher.setText(book.getIEEEpublisher());
        // fetch extra book data from books API
        client = new BookClient();
        client.getExtraBookDetails(book.getOpenLibraryId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("publishers")) {
                        // display comma separated list of publishers
                        final JSONArray publisher = response.getJSONArray("publishers");
                        final int numPublishers = publisher.length();
                        final String[] publishers = new String[numPublishers];
                        for (int i = 0; i < numPublishers; ++i) {
                            publishers[i] = publisher.getString(i);
                        }
                        tvPublisher.setText(TextUtils.join(", ", publishers));
                    }

                    if (response.has("IEEEpublisher")) {
                        tvPublisher.setText("IEEE");
                    }
                    if(response.has("abstract")){
                        tvDescription.setText(response.getString("abstract"));
                    }
                    if (response.has("number_of_pages")) {
                        tvPageCount.setText(Integer.toString(response.getInt("number_of_pages")) + " pages");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
            setShareIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        ImageView ivImage = (ImageView) findViewById(R.id.ivBookCover);
        final TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivImage);
        // Construct a ShareIntent with link to image
        Intent shareIntent = new Intent();
        // Construct a ShareIntent with link to image
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, (String)tvTitle.getText());
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        // Launch share menu
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    // Returns the URI path to the Bitmap displayed in cover imageview
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

}
