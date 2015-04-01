package com.telstra.exercise.model;

import java.util.Iterator;
import java.util.List;

public class News {

    private String title;
    private List<NewsItem> rows;

    public void filterData() {
        Iterator<NewsItem> iterator = rows.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().isEmpty()) {
                iterator.remove();
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NewsItem> getRows() {
        return rows;
    }

    public void setRows(List<NewsItem> rows) {
        this.rows = rows;
    }
}
