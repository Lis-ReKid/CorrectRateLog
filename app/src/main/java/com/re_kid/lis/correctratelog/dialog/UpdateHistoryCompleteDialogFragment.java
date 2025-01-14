package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;

public class UpdateHistoryCompleteDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_update_complete_title);
        builder.setNeutralButton(R.string.dialog_create_complete_neutral, (dialogInterface, which) -> {
            onClose();
        });
        return builder.create();
    }

    /**
     * ダイアログを閉じる時に呼ぶ
     * ホーム画面をリフレッシュして遷移
     */
    private void onClose() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        onClose();
    }
}
