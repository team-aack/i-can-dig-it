package com.detroitlabs.icandigit;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.detroitlabs.icandigit.fragments.InventoryFragment;


public class InventoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();

        // Create new fragments and transaction
        InventoryFragment inventoryFragment = new InventoryFragment();

        // Commit the transaction
        mFragmentTransaction.add(R.id.fragment_container, inventoryFragment);
        mFragmentTransaction.commit();
    }


}
