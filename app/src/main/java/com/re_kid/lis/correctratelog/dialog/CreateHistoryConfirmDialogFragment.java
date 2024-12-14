package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;

public class CreateHistoryConfirmDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new CreateHistoryConfirmButtonClickListener();
        builder.setTitle(R.string.dialog_create_confirm_title);
        builder.setMessage("入力内容を表示");
        builder.setPositiveButton(R.string.btn_create, listener);
        builder.setNegativeButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class CreateHistoryConfirmButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 登録を確定
            if(which == DialogInterface.BUTTON_POSITIVE) {
                // 登録完了ダイアログを表示
                var dialogFragment = new CreateHistoryjCompleteDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "CreateHistoryConfirmDialogFragment");
            }
        }
    }
}
