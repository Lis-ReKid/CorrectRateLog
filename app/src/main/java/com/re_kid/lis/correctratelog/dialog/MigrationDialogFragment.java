package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;

public class MigrationDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        // カスタムビューをインフレート
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.form_migration_id, null);
        // ダイアログをビルド
        var listener = new ButtonClickListener();
        builder.setTitle(R.string.dialog_title_migration)
                .setView(view)
                .setNegativeButton(R.string.btn_cancel, listener);
        this.setCancelable(false);
        return builder.create();
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            var dialog = new FirstCreateCategoryDialogFragment();
            dialog.show(getActivity().getSupportFragmentManager(), "FirstCreateCategoryDialogFragment");
        }
    }
}
