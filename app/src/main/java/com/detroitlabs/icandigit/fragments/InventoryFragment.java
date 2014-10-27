package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.objects.Treasure;
import com.detroitlabs.icandigit.services.InventoryService;

import java.util.ArrayList;

/**
 * Created by aditishetty on 10/23/14.
 */
public class InventoryFragment extends Fragment {

    private ArrayAdapter<Treasure> mTreasureAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.inventory_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mTreasureAdapter = new ArrayAdapter<Treasure>(getActivity(), R.layout.inventory_list_row, R.id.treasureCounter, InventoryService.itemInventory);
        View rootView = inflater.inflate(R.layout.inventory_fragment, container,false);
        ListView listView = (ListView) rootView.findViewById(R.id.inventory_list);
        listView.setAdapter(mTreasureAdapter);

        return rootView;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }


}
