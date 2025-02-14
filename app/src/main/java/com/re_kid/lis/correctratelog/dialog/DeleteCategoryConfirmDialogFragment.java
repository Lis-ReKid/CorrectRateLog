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

public class DeleteCategoryConfirmDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // カテゴリを取得
        Category category = requireArguments().getParcelable("category");
        // ダイアログをビルド
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new DeleteCategoryConfirmDialogOnClickListener();
        builder.setTitle(R.string.dialog_delete_confirm_msg)
                .setMessage(category.getName())
                .setPositiveButton(R.string.btn_delete, listener)
                .setNeutralButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class DeleteCategoryConfirmDialogOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE) {
                // 削除確定時処理
            }
        }
    }
}
