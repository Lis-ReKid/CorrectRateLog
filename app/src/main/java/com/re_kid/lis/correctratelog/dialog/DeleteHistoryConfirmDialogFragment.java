package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.model.HistoryModel;

public class DeleteHistoryConfirmDialogFragment extends DialogFragment {
    private int _id;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // IDを取得
        _id = requireArguments().getInt("id");
        // ダイアログ作成とリスナ登録
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
            // 削除ボタンを押下
            if(which == DialogInterface.BUTTON_POSITIVE) {
                // 削除を実行
                try(HistoryModel model = new HistoryModel(getActivity())) {
                    model.delete(_id);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "削除に失敗しました。", Toast.LENGTH_LONG).show();
                }
                // 画面をリフレッシュ
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "削除しました。", Toast.LENGTH_LONG).show();

            }
            // 削除をキャンセル
            else if(which == DialogInterface.BUTTON_NEGATIVE) {
                // トースト表示
                Toast.makeText(getActivity(), "キャンセルしました。", Toast.LENGTH_LONG).show();
            }
        }
    }
}
