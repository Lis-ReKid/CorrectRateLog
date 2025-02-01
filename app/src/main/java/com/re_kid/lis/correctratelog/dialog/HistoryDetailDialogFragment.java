package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.UpdateHistoryActivity;
import com.re_kid.lis.correctratelog.obj.History;

public class HistoryDetailDialogFragment extends DialogFragment {
    private History _history;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 引数を取得
        _history = requireArguments().getParcelable("history");
        // 詳細メッセージを作成
        var detailMsg = getText(R.string.tv_category_name) + "：" + _history.getCategory().getName() + "\n" +
                getText(R.string.tv_learned_date) + " : " + _history.getLearnedDate().toString() + " " +
                _history.getLearnedTime().toString() + "\n" +
                getText(R.string.correct_rate_title) + " : " + _history.getCorrectRate().toString() + " " +
                "(" + _history.getCorrectNum() + "/" + _history.getEntireNum() + ")";

        // ダイアログをビルド
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new HistoryDetailDialogButtonClickListener();
        builder.setTitle(R.string.dialog_history_detail_title);
        builder.setMessage(detailMsg);
        builder.setPositiveButton(R.string.btn_update, listener);
        builder.setNegativeButton(R.string.btn_delete, listener);
        builder.setNeutralButton(R.string.btn_close, listener);

        return builder.create();
    }

    private class HistoryDetailDialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 更新ボタン押下時処理
            if(which == DialogInterface.BUTTON_POSITIVE) {
                // 更新画面に遷移
                var intent = new Intent(getActivity(), UpdateHistoryActivity.class);
                intent.putExtra("history", _history);
                startActivity(intent);
            }
            // 削除ボタン押下時処理
            else if (which == DialogInterface.BUTTON_NEGATIVE) {
                // 削除確認ダイアログを表示
                var deleteHistoryConfirmDialog = new DeleteHistoryConfirmDialogFragment();
                var args = new Bundle();
                args.putInt("id", _history.getId());
                deleteHistoryConfirmDialog.setArguments(args);
                deleteHistoryConfirmDialog.show(getActivity().getSupportFragmentManager(),
                        "deleteHistoryConfirmDialog");
            }
        }
    }
}
