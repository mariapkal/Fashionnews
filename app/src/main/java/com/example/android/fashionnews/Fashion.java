package com.example.android.fashionnews;

/**
 * Created by Maria on 22/5/2018.
 */

/**
 * An {@link Fashion} object contains information related to a single news article.
 */
public class Fashion {

    /** Title of article */
    private String mWebTitle;

    /** Time of the publication */
    private String mTime;

    /** Website URL of the article */
    private String mUrl;

    /** Section name of the article */
    private String mSectionName;

    /** Author of the article */
    private String mAuthor;

    /**
     * Constructs a new {@link Fashion} object.
     *
     * @param webTitle is the title of the article
     * @param time is the time in milliseconds (from the Epoch) when the
     *                           article was published
     * @param url is the website URL to find more details about the article
     * @param sectionName is the section name of the article
     * @param author is the author of the article
     */
    public Fashion(String webTitle, String time, String url, String sectionName, String
                   author) {
        mWebTitle = webTitle;
        mTime = time;
        mUrl = url;
        mSectionName = sectionName;
        mAuthor = author;
    }

    /**
     * Returns the title of the article.
     */
    public String getWebTitle() {
        return mWebTitle;
    }

    /**
     * Returns the time of the publication.
     */
    public String getTime() {
        return mTime;
    }

    /**
     * Returns the website URL to find more information about the article.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Returns the section name of the article
     */
    public String getSectionName() {return mSectionName;}

    /**
     * Returns the author of the article
     */
    public String getAuthor() {return mAuthor;}
}