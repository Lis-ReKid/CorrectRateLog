package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;

public class CreateCategoryConfirmDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var categoryName = requireArguments().getString("categoryName");
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new ButtonClickListener();
        builder.setTitle(R.string.dialog_create_category_confirm_msg)
                .setMessage(categoryName)
                .setPositiveButton(R.string.btn_create, listener)
                .setNeutralButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
}