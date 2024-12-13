package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.obj.CorrectRate;
import com.re_kid.lis.correctratelog.obj.LearnedDate;
import com.re_kid.lis.correctratelog.obj.LearnedTime;

public class HistoryDetailDialogFragment extends DialogFragment {
    private int _id;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 引数を取得
        _id = requireArguments().getInt("id");
        var date = LearnedDate.parse(requireArguments().getString("date"));
        var time = LearnedTime.parse(requireArguments().getString("time"));
        var correctNum = requireArguments().getInt("correctNum");
        var entireNum = requireArguments().getInt("entireNum");
        var correctRate = new CorrectRate(requireArguments().getDouble("correctRate"));
        // 詳細メッセージを作成
        var detailMsg = getText(R.string.tv_learned_date) + " : " + date + " " + time + "\n" +
                getText(R.string.correct_rate_title) + " : " + correctRate + " " +
                "(" + correctNum + "/" + entireNum + ")";

        // ダイアログをビルド
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new HistoryDetailDialogButtonClickListener();
        builder.setTitle(R.string.dialog_history_detail_title);
        builder.setMessage(detailMsg);
        builder.setNegativeButton(R.string.btn_delete, listener);
        builder.setNeutralButton(R.string.btn_close, listener);

        return builder.create();
    }

    private class HistoryDetailDialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 削除ボタン押下時処理
            if(which == DialogInterface.BUTTON_NEGATIVE) {
                // 削除確認ダイアログを表示
                DeleteHistoryConfirmDialogFragment deleteHistoryConfirmDialog = new DeleteHistoryConfirmDialogFragment();
                Bundle args = new Bundle();
                args.putInt("id", _id);
                deleteHistoryConfirmDialog.setArguments(args);
                deleteHistoryConfirmDialog.show(getActivity().getSupportFragmentManager(), "deleteHistoryConfirmDialog");
            }
        }
    }
}
