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
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 引数を取得
//        int id = requireArguments().getInt("id");
        LearnedDate date = LearnedDate.parse(requireArguments().getString("date"));
        LearnedTime time = LearnedTime.parse(requireArguments().getString("time"));
        int correctNum = requireArguments().getInt("correctNum");
        int entireNum = requireArguments().getInt("entireNum");
        CorrectRate correctRate = new CorrectRate(requireArguments().getDouble("correctRate"));
        // 詳細メッセージを作成
        String detailMsg = getText(R.string.tv_learned_date) + " : " + date + " " + time + "\n" +
                getText(R.string.correct_rate_title) + " : " + correctRate + " " +
                "(" + correctNum + "/" + entireNum + ")";

        // ダイアログをビルド
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_history_detail_title);
        builder.setMessage(detailMsg);
        builder.setNegativeButton(R.string.btn_delete, new HistoryDetailDialogButtonClickListener());

        return builder.create();
    }

    private class HistoryDetailDialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 削除ボタン押下時処理
            if(which == DialogInterface.BUTTON_NEGATIVE) {
                System.out.println("Delete button pushed.");
                // 削除確認ダイアログを表示
            }
        }
    }
}
