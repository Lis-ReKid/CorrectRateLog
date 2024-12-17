package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.model.HistoryModel;
import com.re_kid.lis.correctratelog.obj.CorrectRate;
import com.re_kid.lis.correctratelog.obj.History;
import com.re_kid.lis.correctratelog.obj.LearnedDate;
import com.re_kid.lis.correctratelog.obj.LearnedTime;

public class CreateHistoryConfirmDialogFragment extends DialogFragment {
    private History _history;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var date = requireArguments().getString("date");
        var time = requireArguments().getString("time");
        var correctNum = requireArguments().getInt("correctNum");
        var entireNum = requireArguments().getInt("entireNum");
        var correctRate = requireArguments().getDouble("correctRate");
        _history = new History(LearnedDate.parse(date), LearnedTime.parse(time), correctNum, entireNum, new CorrectRate(correctRate));
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new CreateHistoryConfirmButtonClickListener();
        builder.setTitle(R.string.dialog_create_confirm_title);
        builder.setMessage(getText(R.string.tv_learned_date) + " : " + date + " " + time + "\n" +
                getText(R.string.tv_correct_number) + " : " + correctNum + getText(R.string.tv_quiz_unit) + "\n" +
                getText(R.string.tv_entire_number) + " : " + entireNum + getText(R.string.tv_quiz_unit));
        builder.setPositiveButton(R.string.btn_create, listener);
        builder.setNegativeButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class CreateHistoryConfirmButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 登録を確定
            if(which == DialogInterface.BUTTON_POSITIVE) {
                // DB登録
                try(var model = new HistoryModel(getActivity())) {
                    var history = new History(_history.getLearnedDate(), _history.getLearnedTime(),
                            _history.getCorrectNum(), _history.getEntireNum(), _history.getCorrectRate());
                    model.createHistory(history);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.create_failed_msg, Toast.LENGTH_SHORT).show();
                }
                // 登録完了ダイアログを表示
                var dialogFragment = new CreateHistoryjCompleteDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "CreateHistoryConfirmDialogFragment");
            }
        }
    }
}
