package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.objects.Treasure;
import com.detroitlabs.icandigit.services.InventoryService;

import java.util.ArrayList;

/**
 * Created by aditishetty on 10/23/14.
 */
public class InventoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.inventory_title);
    }

    public abstract class InventoryAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private ArrayList<Treasure> mTreasure;
        private Context context;

        public InventoryAdapter(Context context, ArrayList<Treasure> treasure) {
            this.context = context;
            mInflater = LayoutInflater.from(context);
            mTreasure = treasure;
        }

        @Override
        public int getCount() {
            return mTreasure.size();
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
            //holder.icon.setImageBitmap(treasure.getIcon());
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_treasure));
            holder.multiple.setText("X");
            holder.counter.setText(treasure.getItemAmount());
            holder.treasure.setText(treasure.getItemType());

            return view;
        }

        private class ViewHolder {
            public ImageView icon;
            public TextView multiple, counter, treasure;
        }

    }


}
