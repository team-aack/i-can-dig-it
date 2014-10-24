package com.detroitlabs.icandigit;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.detroitlabs.icandigit.fragments.InventoryFragment;


public class InventoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();

        // Create new fragments and transaction
        InventoryFragment inventoryFragment = new InventoryFragment();

        // Commit the transaction
        mFragmentTransaction.add(R.id.fragment_container, inventoryFragment);
        mFragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
