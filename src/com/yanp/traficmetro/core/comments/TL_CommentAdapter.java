package com.yanp.traficmetro.core.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanp.traficmetro.Constants;
import com.yanp.traficmetro.R;

import java.util.ArrayList;

/**
 * Created by YPierru on 19/01/2015.
 */
public class TL_CommentAdapter extends BaseAdapter {

    private ArrayList<TL_Comment> listComments;
    private int position;
    private LayoutInflater inflater;
    private Context context;

    public TL_CommentAdapter(Context cnt, ArrayList<TL_Comment> listComments){
        this.inflater = LayoutInflater.from(cnt);
        this.context=cnt;
        this.listComments=listComments;
    }

    @Override
    public int getCount() {
        return this.listComments.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;
        position=pos;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.traficlight_item, null);

            holder.traficLight_tv = (TextView)convertView.findViewById(R.id.traficlight_tv);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.traficLight_tv.setText(this.listComments.get(position).getDate());
        holder.traficLight_tv.setBackgroundColor(this.listComments.get(position).getColor());


        return convertView;
    }

    private class ViewHolder {
        TextView traficLight_tv;
    }

}
