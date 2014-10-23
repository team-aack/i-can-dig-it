package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.detroitlabs.icandigit.R;

/**
 * Created by aditishetty on 10/23/14.
 */
public class InventoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.inventory_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(R.layout.inventory_fragment, container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
