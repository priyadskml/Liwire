package com.example.anusha.LiWire;


import com.example.anusha.today.R;

import android.os.Bundle;
import android.widget.TextView;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class HashTags extends ListActivity {


    private ProgressDialog pDialog;
    private ListActivity activity;
    final static String ScreenName = "twitterapi";
    final static String LOG_TAG = "rnc";
    private static final String TAG_TRENDS = "trends";
    private static final String TAG_NAME = "name";
    private static final String TAG_URL = "url";
    private static final String TAG_PROMOTED_CONTENT = "promoted_content";
    private static final String TAG_QUERY = "query";
    private static final String TAG_TWEET_VOLUME = "tweet_volume";

    JSONArray trends = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtags);
        contactList = new ArrayList<HashMap<String, String>>();
        ListView lv = getListView();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)  {
                // getting values from selected ListItem
                String selected =  ((TextView)view.findViewById(R.id.url)).getText().toString();
                Intent intent= new Intent(getApplicationContext(), Web_view.class);
                intent.putExtra("link", selected);
                startActivity(intent);
            }
        });

        //activity = this;

        downloadTweets();
        Log.e("myapp", "inoncreate");
        // new GetContacts().execute();

    }

    // download twitter timeline after first checking to see if there is a network connection
    public void downloadTweets() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadTwitterTask().execute(ScreenName);
        } else {
            Log.v(LOG_TAG, "No network connection available.");
        }
    }


    // Uses an AsyncTask to download a Twitter user's timeline
    private class DownloadTwitterTask extends AsyncTask<String, Void, String> {
        final static String CONSUMER_KEY = "PHZX9b9Glk7lNTBl0xQ9dHYRl";
        final static String CONSUMER_SECRET = "ppyOp46nV5UTT7DRrIVqsA0l4KlwlDwACfLHb0CkpgUYOWGuwk";
        final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
        final static String TwitterStreamURL = "https://api.twitter.com/1.1/trends/place.json?id=2295420";

        @Override
        protected String doInBackground(String... screenNames) {
            String result = null;
            String jsonStr = null;

            if (screenNames.length > 0) {
                jsonStr = getTwitterStream(screenNames[0]);
            }
            result = jsonStr;
            jsonStr = jsonStr.substring(1);
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    trends = jsonObj.getJSONArray(TAG_TRENDS);

                    // looping through All Contacts
                    for (int i = 0; i < trends.length(); i++) {
                        JSONObject c = trends.getJSONObject(i);

                        String url = c.getString(TAG_URL);
                        String name = c.getString(TAG_NAME);
                        String promoted_content = c.getString(TAG_PROMOTED_CONTENT);
                        String query = c.getString(TAG_QUERY);
                        String tweet_volume = c.getString(TAG_TWEET_VOLUME);

                        // Phone node is JSON Object
                        //  JSONObject phone = c.getJSONObject(TAG_PHONE);
                        //  String mobile = phone.getString(TAG_PHONE_MOBILE);
                        //  String home = phone.getString(TAG_PHONE_HOME);
                        //  String office = phone.getString(TAG_PHONE_OFFICE);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value

                        contact.put(TAG_NAME, name);
                        contact.put(TAG_URL, url);
                        contact.put(TAG_PROMOTED_CONTENT, promoted_content);
                        contact.put(TAG_QUERY, query);
                        contact.put(TAG_TWEET_VOLUME, tweet_volume);

                        // adding contact to contact list_item
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return result;
            // Log.e("myapp",result);
            // return result;
        }

        // onPostExecute convert the JSON results into a Twitter object (which is an Array list_item of tweets
        @Override
        protected void onPostExecute(String result) {
            // Log.e("Result" ,result);

            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(
                    HashTags.this, contactList,
                    R.layout.list_item, new String[]{TAG_NAME, TAG_URL/**,
                    TAG_PROMOTED_CONTENT, TAG_QUERY, TAG_TWEET_VOLUME**/}, new int[]{R.id.name,
                    R.id.url/**, R.id.promoted_content, R.id.query, R.id.tweet_volume**/});

            setListAdapter(adapter);

        }

        // converts a string of JSON data into a Twitter object
        /** private Twitter jsonToTwitter(String result) {
         Log.e("myapp", result);
         Twitter twits = null;
         if (result != null && result.length() > 0) {
         try {
         Gson gson = new Gson();
         twits = gson.fromJson(result, Twitter.class);
         } catch (IllegalStateException ex) {
         // just eat the exception
         }
         }
         Log.e("myapp", "twits");
         return twits;
         }**/

        // convert a JSON authentication object into an Authenticated object
        private Authenticated jsonToAuthenticated(String rawAuthorization) {
            Authenticated auth = null;
            Log.e("myapp", rawAuthorization);
            if (rawAuthorization != null && rawAuthorization.length() > 0) {
                try {
                    Gson gson = new Gson();
                    auth = gson.fromJson(rawAuthorization, Authenticated.class);
                } catch (IllegalStateException ex) {
                    // just eat the exception
                }
            }
            Log.e("myapp", "auth");
            return auth;
        }

        private String getResponseBody(HttpRequestBase request) {
            StringBuilder sb = new StringBuilder();
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpResponse response = httpClient.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();
                String reason = response.getStatusLine().getReasonPhrase();

                if (statusCode == 200) {

                    HttpEntity entity = response.getEntity();
                    InputStream inputStream = entity.getContent();

                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sb.append(line);
                    }
                } else {
                    sb.append(reason);
                }
            } catch (UnsupportedEncodingException ex) {
            } catch (ClientProtocolException ex1) {
            } catch (IOException ex2) {
            }
            Log.e("myapp", sb.toString());
            return sb.toString();
        }

        private String getTwitterStream(String screenName) {
            Log.e("myapp", screenName);
            String results = null;

            // Step 1: Encode consumer key and secret
            try {
                // URL encode the consumer key and secret
                String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
                String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");

                // Concatenate the encoded consumer key, a colon character, and the
                // encoded consumer secret
                String combined = urlApiKey + ":" + urlApiSecret;

                // Base64 encode the string
                String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

                // Step 2: Obtain a bearer token
                HttpPost httpPost = new HttpPost(TwitterTokenURL);
                httpPost.setHeader("Authorization", "Basic " + base64Encoded);
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
                String rawAuthorization = getResponseBody(httpPost);
                Authenticated auth = jsonToAuthenticated(rawAuthorization);

                // Applications should verify that the value associated with the
                // token_type key of the returned object is bearer
                if (auth != null && auth.token_type.equals("bearer")) {

                    // Step 3: Authenticate API requests with bearer token
                    HttpGet httpGet = new HttpGet(TwitterStreamURL);

                    // construct a normal HTTPS request and include an Authorization
                    // header with the value of Bearer <>
                    httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
                    httpGet.setHeader("Content-Type", "application/json");
                    // update the results with the body of the response
                    results = getResponseBody(httpGet);
                }
            } catch (UnsupportedEncodingException ex) {
            } catch (IllegalStateException ex1) {
            }
            Log.e("myapp", results);
            return results;
        }
    }
}