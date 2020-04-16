package com.bb.mybooksapi;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask<String, Void, String> {

    private TextView titleB;
    private TextView authorB;

    public FetchBook(TextView titleB, TextView authorB) {
        this.titleB = titleB;
        this.authorB = authorB;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsarray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsarray.length(); i++){
                JSONObject book = itemsarray.getJSONObject(i);
                String title = null;
                String author = null;
                JSONObject volumeinfo = book.getJSONObject("volumeinfo");
                try{
                    title = volumeinfo.getString("title");
                    author = volumeinfo.getString("author");
                } catch (Exception e){
                    e.printStackTrace();
                }
                if (title != null && author != null){
                    titleB.setText(title);
                    authorB.setText(author);
                    return;
                }
            }
            titleB.setText("No Results Found");
            authorB.setText(" No author available.");
        } catch (Exception e){
            titleB.setText("No Results Found");
            authorB.setText(" No author available.");
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }
}
