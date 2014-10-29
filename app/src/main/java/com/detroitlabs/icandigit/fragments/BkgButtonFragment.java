package com.detroitlabs.icandigit.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.detroitlabs.icandigit.MainActivity;
import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.services.InventoryService;

/**
 * Created by kyleofori on 10/27/14.
 */
public class BkgButtonFragment extends Fragment {
    private Button mButton;
    private TextView mTextView;
    private RelativeLayout mRelativeLayout;

    public Button getButton() {
        return mButton;
    }
    public TextView getTextView() {
        return mTextView;
    }
    public RelativeLayout getRelativeLayout() { return mRelativeLayout; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.dialog_view, container, false);

        mButton = (Button) rootView.findViewById(R.id.fragment_container_button);
        mButton.setBackgroundColor(getResources().getColor(R.color.trans_white));
        mButton.setVisibility(View.GONE);
        mButton.setClickable(true);

        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.frag_co);
        mRelativeLayout.setVisibility(View.VISIBLE);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mButton.setVisibility(View.GONE);
               mRelativeLayout.setVisibility(View.GONE);
            }
        });

        return rootView;
    }
}
