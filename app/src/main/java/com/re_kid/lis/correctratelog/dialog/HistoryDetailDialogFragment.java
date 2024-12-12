package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.obj.CorrectRate;

public class HistoryDetailDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 引数を取得
//        int id = requireArguments().getInt("id");
//        String date = requireArguments().getString("date");
//        String time = requireArguments().getString("time");
//        int correctNum = requireArguments().getInt("correctNum");
//        int entireNum = requireArguments().getInt("entireNum");
        CorrectRate correctRate = new CorrectRate(requireArguments().getDouble("correctRate"));
        // 詳細メッセージを作成
        StringBuilder detailMsg = new StringBuilder();
//        detailMsg.append(R.string.tv_learned_date + " : " + date + " " + time + "\n");
        detailMsg.append(getText(R.string.correct_rate_title) + " : " + correctRate + "\n");

        // ダイアログをビルド
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_history_detail_title);
        builder.setMessage(detailMsg.toString());

        return builder.create();
    }
}
