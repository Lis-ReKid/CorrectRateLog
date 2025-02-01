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
import com.re_kid.lis.correctratelog.obj.History;

public class CreateHistoryConfirmDialogFragment extends DialogFragment {
    private History _history;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        _history = requireArguments().getParcelable("history");
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new CreateHistoryConfirmButtonClickListener();
        builder.setTitle(R.string.dialog_create_confirm_title);
        builder.setMessage(getText(R.string.tv_category_name) + "：" + _history.getCategory().getName() + "\n" +
                getText(R.string.tv_learned_date) + " : " + _history.getLearnedDate().toString() + " " +
                _history.getLearnedTime().toString() + "\n" +
                getText(R.string.tv_correct_number) + " : " + _history.getCorrectNum() + getText(R.string.tv_quiz_unit) + "\n" +
                getText(R.string.tv_entire_number) + " : " + _history.getEntireNum() + getText(R.string.tv_quiz_unit));
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
                    var history = new History(0, _history.getCategory(), _history.getLearnedDate(), _history.getLearnedTime(),
                            _history.getCorrectNum(), _history.getEntireNum(), _history.getCorrectRate());
                    model.createHistory(history);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.create_failed_msg, Toast.LENGTH_SHORT).show();
                }
                // 登録完了ダイアログを表示
                var dialogFragment = new CreateHistoryCompleteDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "CreateHistoryConfirmDialogFragment");
            }
        }
    }
}
