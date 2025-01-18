package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;

public class CreateCategoryDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        var inflater = requireActivity().getLayoutInflater();
        builder.setTitle(R.string.create_category_title)
                .setView(inflater.inflate(R.layout.category_create_dialog, null));
        return builder.create();
    }
}
