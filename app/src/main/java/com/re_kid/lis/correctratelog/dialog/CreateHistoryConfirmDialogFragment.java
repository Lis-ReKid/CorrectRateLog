package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.CreateHistoryActivity;
import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;

public class CreateHistoryConfirmDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CreateDialogButtonClickListener listener = new CreateDialogButtonClickListener();
        builder.setTitle(R.string.dialog_create_title);
        builder.setPositiveButton(R.string.dialog_create_positive, listener);
        builder.setNeutralButton(R.string.dialog_create_neutral, listener);
        return builder.create();
    }
    public class CreateDialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 続けて登録
            if(which == DialogInterface.BUTTON_POSITIVE) {
                // 入力フォームをリフレッシュ
                Intent intent = new Intent(getActivity(), CreateHistoryActivity.class);
                startActivity(intent);
            }
            // ホームに戻る
            else if(which == DialogInterface.BUTTON_NEUTRAL) {
                // ホーム画面をリフレッシュして遷移
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
