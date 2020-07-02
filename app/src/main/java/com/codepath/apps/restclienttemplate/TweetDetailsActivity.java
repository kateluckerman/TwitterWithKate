package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import okhttp3.Headers;

public class TweetDetailsActivity extends AppCompatActivity {

    public static final String TAG = "TweetDetailsActivity";

    ImageView ivProfileImage;
    TextView tvName;
    TextView tvScreenName;
    TextView tvBody;
    TextView tvTime;
    ImageView ivFavorite;
    ImageView ivRetweet;
    Tweet tweet;

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvName = findViewById(R.id.tvName);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvBody = findViewById(R.id.tvBody);
        tvTime = findViewById(R.id.tvTime);
        ivFavorite = findViewById(R.id.ivFavorite);
        ivRetweet = findViewById(R.id.ivRetweet);

        client = new TwitterClient(this);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvName.setText(tweet.user.name);
        tvScreenName.setText(tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvTime.setText(tweet.createdAt);
        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick of favorite button");

                client.favoriteTweet(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess of tweet favorite");
                        ivFavorite.setImageDrawable(getDrawable(R.drawable.ic_vector_heart));
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure of tweet favorite", throwable);
                    }
                }, tweet.id);
            }
        });

        ivRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick of favorite button");

                client.retweetTweet(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess of tweet retweet");
                        ivRetweet.setImageDrawable(getDrawable(R.drawable.ic_vector_retweet));
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure of tweet retweet:" + response, throwable);
                    }
                }, tweet.id);
            }
        });

    }
}