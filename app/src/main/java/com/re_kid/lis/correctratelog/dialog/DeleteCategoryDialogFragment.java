package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.obj.Category;

public class DeleteCategoryDialogFragment extends DialogFragment {
    private Category _category;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        _category = requireArguments().getParcelable("category");
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new DeleteCategoryDialogOnClickListener();
        builder.setMessage(_category.getName())
                .setPositiveButton(R.string.btn_delete, listener)
                .setNeutralButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class DeleteCategoryDialogOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE) {
                var deleteCategoryConfirmDialogFragment = new DeleteCategoryConfirmDialogFragment();
                var args = new Bundle();
                args.putParcelable("category", _category);
                deleteCategoryConfirmDialogFragment.setArguments(args);
                deleteCategoryConfirmDialogFragment.show(getActivity().getSupportFragmentManager(),
                        "DeleteCategoryConfirmDialogFragment");
            }
        }
    }
}
