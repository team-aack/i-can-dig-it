package com.detroitlabs.icandigit.fragments;


import android.app.ListFragment;
import android.os.Bundle;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.adapters.InventoryAdapter;
import com.detroitlabs.icandigit.services.InventoryService;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDivider(null);
    }

    private void setAdapter() {
        mInventoryAdapter = new InventoryAdapter(getActivity(), InventoryService.itemInventory);
        this.setListAdapter(mInventoryAdapter);


    }


}
