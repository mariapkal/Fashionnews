package com.example.android.fashionnews;

/**
 * Created by Maria on 22/5/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An {@link FashionAdapter} knows how to create a list item layout for each news article
 * in the data source (a list of {@link Fashion} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class FashionAdapter extends ArrayAdapter<Fashion> {

    /**
     * Constructs a new {@link FashionAdapter}.
     *
     * @param context of the app
     * @param news is the list of news, which is the data source of the adapter
     */
    public FashionAdapter(Context context, List<Fashion> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the article at the given position
     * in the list of articles.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.fashion_list_item, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
        }

        // Find the article at the given position in the list of articles
        Fashion currentNews = getItem(position);

        // Create a ViewHolder object
        ViewHolder viewHolder = new ViewHolder(convertView);

        // Display the title of the current article in that TextView
        viewHolder.title.setText(currentNews.getWebTitle());

        // Display the time of the current article in that TextView
        viewHolder.timeView.setText(currentNews.getTime());

        //Display the url in that TextView
        viewHolder.url.setText(currentNews.getUrl());

        //Display the section name in that TextView
        viewHolder.sectionName.setText(currentNews.getSectionName());

        //Display the section name in that TextView
        viewHolder.author.setText(currentNews.getAuthor());

        // Return the list item view that is now showing the appropriate data
        return convertView;
    }
    static class ViewHolder {
        // Create variables from TextViews
        @BindView(R.id.title) TextView title;
        @BindView(R.id.time) TextView timeView;
        @BindView(R.id.url) TextView url;
        @BindView(R.id.section_name) TextView sectionName;
        @BindView(R.id.author) TextView author;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
