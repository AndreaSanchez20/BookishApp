package com.example.bookdate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookdate.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Post> {
    private Context mContext;
    int newResourceId;
    public PlaylistAdapter(Context context, int resource, ArrayList<Post> objects){
        super(context,resource,objects);
        mContext = context;
        newResourceId = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        //String uid = getItem(position).get;
        Post itemPlaylist = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(newResourceId,parent,false);

        TextView text_view_title = convertView.findViewById(R.id.tvTitle);
        TextView text_view_author = convertView.findViewById(R.id.tvAuthor);

        text_view_title.setText(itemPlaylist.title);
        text_view_author.setText(itemPlaylist.author);

        return convertView;

    }
}
