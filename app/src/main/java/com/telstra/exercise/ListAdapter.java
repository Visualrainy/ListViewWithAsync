package com.telstra.exercise;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.telstra.exercise.model.NewsItem;
import com.telstra.exercise.network.VolleyManager;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<NewsItem> listData;
    private LayoutInflater inflater;

    public ListAdapter(Context context, List<NewsItem> data) {
        listData = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder.titleTv = (TextView) convertView.findViewById(R.id.item_title);
            holder.descriptionTv = (TextView) convertView.findViewById(R.id.item_description);
            holder.itemIv = (ImageView) convertView.findViewById(R.id.item_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setData(position, holder);
        return convertView;
    }

    private void setData(int position, ViewHolder holder) {
        setTextViewData(holder.titleTv, listData.get(position).getTitle());
        setTextViewData(holder.descriptionTv, listData.get(position).getDescription());
        String imageHref = listData.get(position).getImageHref();

        holder.itemIv.setTag(imageHref);
        //reset itemIv
        holder.itemIv.setImageBitmap(null);
        if(!TextUtils.isEmpty(imageHref)) {
            VolleyManager.getInstance().loadImage(holder.itemIv,imageHref, 200, 200);
        }
    }

    private void setTextViewData(TextView tv, String content) {
        if (TextUtils.isEmpty(content)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(content);
        }
    }

    private class ViewHolder {
        TextView titleTv;
        TextView descriptionTv;
        ImageView itemIv;
    }
}
