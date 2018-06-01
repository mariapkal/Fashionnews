package com.example.android.fashionnews;

/**
 * Created by Maria on 22/5/2018.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of articles by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class FashionLoader extends AsyncTaskLoader<List<Fashion>> {

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link FashionLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public FashionLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Fashion> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of articles.
        List<Fashion> news = QueryUtils.fetchFashionData(mUrl);
        return news;
    }
}