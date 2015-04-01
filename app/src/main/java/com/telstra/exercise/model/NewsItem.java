package com.telstra.exercise.model;

import android.text.TextUtils;

public class NewsItem {

    private String title;
    private String description;
    private String imageHref;

    public boolean isEmpty() {
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(description)
                && TextUtils.isEmpty(imageHref)) {
            return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }
}
