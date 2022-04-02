package com.example.bookdate;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class IEEEBook implements Serializable {
    private String openLibraryId;
    private String author;
    private String title;
    private String description;
    private String IEEEpublisher;

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher(){return IEEEpublisher;}

    // Get medium sized book cover from covers API
    public String getCoverUrl() {
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-M.jpg?default=false";
    }

    // Get large sized book cover from covers API
    public String getLargeCoverUrl() {
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-L.jpg?default=false";
    }
    // Returns a Book given the expected JSON
    public static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();
        try {
            // Deserialize json into object fields
            // Check if a cover edition is available
            if (jsonObject.has("doi"))  {
                book.openLibraryId = jsonObject.getString("doi");
            }
            book.title = jsonObject.has("title") ? jsonObject.getString("title") : "";
            book.author = getAuthor(jsonObject);
            book.description = jsonObject.has("abstract") ? jsonObject.getString("abstract"):""; //abstract for IEEE research
            book.IEEEpublisher = jsonObject.has("publisher") ? jsonObject.getString("publisher"):"";
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return book;
    }

    // Return comma separated author list when there is more than one author
    private static String getAuthor(final JSONObject jsonObject) {
        try {
            //final JSONArray authors = jsonObject.getJSONArray("authors");
            JSONArray jsonArrayAuthors = jsonObject.getJSONObject("authors").getJSONArray("authors");
            int numAuthors = jsonArrayAuthors.length();
            final String[] authorStrings = new String[numAuthors];
            JSONObject author = new JSONObject();
            // JSONArray JSONArrayAuthors = jsonObjectOneMemberInfo.getJSONArray("occupation");
            for (int i = 0; i < numAuthors; ++i) {
                //author.getJSONArray("authors");
                author = jsonArrayAuthors.getJSONObject(i);
                authorStrings[i] = author.getString("full_name");
            }
            return TextUtils.join(", ", authorStrings);
        } catch (JSONException e) {
            return "";
        }
    }
    // Decodes array of book json results into business model objects
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<Book>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Book book = IEEEBook.fromJson(bookJson);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }
}
