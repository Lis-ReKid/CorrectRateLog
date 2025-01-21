package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FirstCreateCategoryDialogFragment extends CreateCategoryDialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.setCancelable(false);
        return super.onCreateDialog(savedInstanceState);
    }
}
