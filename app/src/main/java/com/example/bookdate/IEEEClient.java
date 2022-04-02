package com.example.bookdate;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class IEEEClient {
    private static final String API_BASE_URL = "https://ieeexploreapi.ieee.org/api/v1/search/articles?apikey=4uh37am5k2hcgy6vpw467fk6";
    private AsyncHttpClient client;

    public IEEEClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getBooks(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("&querytext=");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    //
    // Method for accessing books API to get publisher and no. of pages in a book.
    public void getExtraBookDetailsX(String openLibraryId, JsonHttpResponseHandler handler) {
        String url = getApiUrl("books/");
        client.get(url + openLibraryId + ".json", handler);
    }
}
