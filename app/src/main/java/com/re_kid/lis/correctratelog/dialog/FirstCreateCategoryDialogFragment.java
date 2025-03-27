package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.re_kid.lis.correctratelog.R;

public class FirstCreateCategoryDialogFragment extends CreateCategoryDialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.setCancelable(false);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    protected void setMigrationButton() {
        var listener = new ButtonClickListener();
        builder.setPositiveButton(R.string.btn_migration, listener);
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            var dialog = new MigrationDialogFragment();
            dialog.show(getActivity().getSupportFragmentManager(), "MigrationDialogFragment");
        }
    }
}
