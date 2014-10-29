package com.detroitlabs.icandigit.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.objects.Treasure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditishetty on 10/28/14.
 */
public class InventoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Treasure> mTreasure;
    private Context context;

    public InventoryAdapter(Context context, List<Treasure> treasure) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mTreasure = treasure;
    }

    @Override
    public int getCount() {
        return mTreasure.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder;

        if(convertView == null) {
            view = mInflater.inflate(R.layout.inventory_list_row, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView)view.findViewById(R.id.treasureIcon);
            holder.multiple = (TextView)view.findViewById(R.id.multipleSign);
            holder.counter = (TextView)view.findViewById(R.id.treasureCounter);
            holder.treasure = (TextView)view.findViewById(R.id.treasureItem);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        Treasure treasure = mTreasure.get(position);
        if(treasure != null) {
            //holder.icon.setImageBitmap(treasure.getIcon());
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_treasure));
            holder.multiple.setText("X");
            holder.counter.setText(String.valueOf(treasure.getItemAmount()));
            holder.treasure.setText(treasure.getItemType());

        }

        return view;
    }

    private class ViewHolder {
        public ImageView icon;
        public TextView multiple, counter, treasure;
    }

}
