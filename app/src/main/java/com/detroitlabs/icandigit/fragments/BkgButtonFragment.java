package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.services.InventoryService;

/**
 * Created by kyleofori on 10/27/14.
 */
public class BkgButtonFragment extends Fragment {
    private Button mButton;

    public Button getButton() {
        return mButton;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_bkgbutton, container, false);

        mButton = (Button) rootView.findViewById(R.id.fragment_container_button);
        mButton.setBackgroundColor(getResources().getColor(R.color.trans_white));
        mButton.setEnabled(false);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setBackgroundColor(getResources().getColor(R.color.trans_gold));
//                            fragmentTransaction.detach(youFoundFragment);
//                            fragmentTransaction.detach(bkgButtonFragment);
//                            fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}
