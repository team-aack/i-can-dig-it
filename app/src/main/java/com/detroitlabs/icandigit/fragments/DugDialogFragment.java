package com.detroitlabs.icandigit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.detroitlabs.icandigit.R;

/**
 * Created by kyleofori on 10/23/14.
 */
public class DugDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.you_found);
            // Create the AlertDialog object and return it
            return builder.create();
        }

}
