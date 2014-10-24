package com.detroitlabs.icandigit;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class InventoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        //getActivity().setTitle(R.string.inventory_title);

        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();

        // Create new fragments and transaction
        InventoryFragment inventoryFragment = new InventoryFragment();

        // Commit the transaction
        mFragmentTransaction.add(R.id.fragment_container, inventoryFragment);
        mFragmentTransaction.commit();
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        inflater.inflate(R.layout.inventory_fragment, container,false);
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
}
