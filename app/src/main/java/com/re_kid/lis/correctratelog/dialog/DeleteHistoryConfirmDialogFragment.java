package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;

public class DeleteHistoryConfirmDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DeleteHistoryConfirmDialogButtonClickListener listener = new DeleteHistoryConfirmDialogButtonClickListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_confirm_msg);
        builder.setPositiveButton(R.string.btn_delete, listener);
        builder.setNegativeButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class DeleteHistoryConfirmDialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 削除を実行
            if(which == DialogInterface.BUTTON_POSITIVE) {
                System.out.println("Delete confirm button pushed.");
                // 削除確認ダイアログを表示
            }
            // 削除をキャンセル
            else if(which == DialogInterface.BUTTON_NEGATIVE) {
                System.out.println("Cancel button pushed.");
            }
        }
    }
}
