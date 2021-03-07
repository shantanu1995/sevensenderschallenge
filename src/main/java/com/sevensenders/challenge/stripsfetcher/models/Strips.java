package com.sevensenders.challenge.stripsfetcher.models;

import java.util.Date;

public class Strips {

    private final String pictureurl;
    private final String title;
    private final String weburl;
    private final Date publishingDate;

    public Strips(String pictureurl, String title, String weburl, Date publishingDate) {
       this.pictureurl=pictureurl;
       this.title=title;
       this.weburl=weburl;
       this.publishingDate=publishingDate;
    }

    public Date getPublishingDate() {
        return publishingDate;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public String getTitle() {
        return title;
    }

    public String getWeburl() {
        return weburl;
    }
}
