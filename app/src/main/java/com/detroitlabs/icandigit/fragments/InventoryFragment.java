package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.os.Bundle;

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
}
