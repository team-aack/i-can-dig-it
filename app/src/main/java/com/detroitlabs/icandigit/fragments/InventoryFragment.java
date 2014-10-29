package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.app.ListFragment;
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
import com.detroitlabs.icandigit.fragments.adapters.InventoryAdapter;
import com.detroitlabs.icandigit.objects.Treasure;
import com.detroitlabs.icandigit.services.InventoryService;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by aditishetty on 10/23/14.
 */
public class InventoryFragment extends ListFragment {

    InventoryAdapter mInventoryAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.inventory_title);
        setAdapter();

    }

    private void setAdapter() {
        mInventoryAdapter = new InventoryAdapter(getActivity(), InventoryService.itemInventory);
        this.setListAdapter(mInventoryAdapter);


    }


}
