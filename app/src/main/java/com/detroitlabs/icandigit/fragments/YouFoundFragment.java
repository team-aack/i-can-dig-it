package com.detroitlabs.icandigit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.detroitlabs.icandigit.R;
import com.detroitlabs.icandigit.services.InventoryService;

/**
 * Created by kyleofori on 10/23/14.
 */
public class YouFoundFragment extends Fragment {

    private TextView mTextView;
    private RelativeLayout mRelativeLayout;

    public TextView getTextView() {
        return mTextView;
    }
    public RelativeLayout getRelativeLayout() { return mRelativeLayout; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.dialog_view, container, false);




        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.dialog_relative_layout);
        mRelativeLayout.setVisibility(View.INVISIBLE);

        mTextView = (TextView) rootView.findViewById(R.id.dialog_text_view);
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText("Nothing yet, ma'am.");

        return rootView;
    }
}
