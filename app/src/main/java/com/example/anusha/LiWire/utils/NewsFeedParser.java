package com.example.anusha.LiWire.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.example.anusha.LiWire.models.RSSFeed;

public class NewsFeedParser {
    private InputStream urlStream;
    private XmlPullParserFactory factory;
    private XmlPullParser parser;

    private List<RSSFeed> rssFeedList;
    private RSSFeed rssFeed;

    private String urlString;
    private String tagName;
    private String title;
    private String link;
   
    private String imageUrl;


    public static final String ITEM = "item";
    public static final String CHANNEL = "channel";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    


    public NewsFeedParser(String urlString) {
        this.urlString = urlString;
    }

    public static InputStream downloadStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }




    public List<RSSFeed> parseXmlData() {
        try {
            int count = 0;
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            urlStream = downloadStream(urlString);
            parser.setInput(urlStream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            rssFeed = new RSSFeed();
            rssFeedList = new ArrayList<RSSFeed>();
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equals(ITEM)) {
                            rssFeed = new RSSFeed();
                        }
                        if (tagName.equals(TITLE)) {
                            title = parser.nextText().toString();
                        }
                        if (tagName.equals(LINK)) {
                            link = parser.nextText().toString();
                        }
                        
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals(CHANNEL)) {
                            done = true;
                        } else if (tagName.equals(ITEM)) {
                            Html.ImageGetter imageGetter = null;
                            Html.TagHandler tagHandler = null;
                            String noHtml = Html.fromHtml(description).toString().replace('\n', (char) 32)
                                    .replace((char) 160, (char) 32).replace((char) 65532, (char) 32).trim();
                            rssFeed = new RSSFeed(title, link, noHtml, category, pubDate,
                                    guid,
                                    feedburner, imageUrl);

                            rssFeedList.add(rssFeed);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("RSSCount", " : "+Integer.toString(rssFeedList.size()).toString());
        return rssFeedList;

    }
}
