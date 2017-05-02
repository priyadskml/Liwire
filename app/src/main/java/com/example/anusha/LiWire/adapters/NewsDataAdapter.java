package com.example.anusha.LiWire.adapters;

/**
 * Created by VAISHNAVI.S.G on 24-05-2016.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.example.anusha.LiWire.WebViewActivity;
import com.example.anusha.today.R;
import com.example.anusha.LiWire.models.RSSFeed;

import java.util.List;

public class NewsDataAdapter extends RecyclerView.Adapter<NewsDataAdapter.FeedListRowHolder> {

    private List<RSSFeed> feedItemList;
    private Context mContext;

    public NewsDataAdapter(Context context, List<RSSFeed> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_row, null);
        FeedListRowHolder mh = new FeedListRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(FeedListRowHolder feedListRowHolder, int i) {
        RSSFeed feedItem = feedItemList.get(i);

        feedListRowHolder.rssFeed = feedItem;
        feedListRowHolder.title.setText(feedItem.getTitle());
        feedListRowHolder.description.setText(feedItem.getDescription());
        feedListRowHolder.pubDate.setText(feedItem.getPubDate());

        Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class FeedListRowHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView description;
        protected TextView pubDate;
        protected ImageView thumbView;

        protected RSSFeed rssFeed;

        public FeedListRowHolder(View view) {
            super(view);

            this.title = (TextView) view.findViewById(R.id.tvtitle);
            this.description = (TextView)  view.findViewById(R.id.tvdescription);
            this.pubDate = (TextView) view.findViewById(R.id.tvpubdate);
            this.thumbView = (ImageView) view.findViewById(R.id.thumbView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent webIntent = new Intent(v.getContext(), WebViewActivity.class);

                    webIntent.putExtra("title", rssFeed.getTitle().toString());

                    webIntent.putExtra("url", rssFeed.getLink().toString());

                    // String url=mRssFeedList.get(position).getLink().toString();

                    //   Log.i("onlcik Recycler",url);

                    v.getContext().startActivity(webIntent);
                }
            });
        }

    }
}