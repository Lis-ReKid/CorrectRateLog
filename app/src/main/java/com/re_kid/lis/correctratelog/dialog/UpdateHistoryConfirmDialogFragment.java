package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.DatabaseHelper;
import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.model.HistoryModel;
import com.re_kid.lis.correctratelog.obj.History;

public class UpdateHistoryConfirmDialogFragment extends DialogFragment {
    private History _history;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        _history = requireArguments().getParcelable("history");
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new UpdateHistoryConfirmDialogFragment.UpdateHistoryConfirmButtonClickListener();
        builder.setTitle(R.string.dialog_update_confirm_title);
        builder.setMessage(getText(R.string.tv_category_name) + "：" + _history.getCategory().getCategoryName() + "\n" +
                getText(R.string.tv_learned_date) + " : " + _history.getLearnedDate().toString() + " " +
                _history.getLearnedTime().toString() + "\n" +
                getText(R.string.tv_correct_number) + " : " + _history.getCorrectNum() + getText(R.string.tv_quiz_unit) + "\n" +
                getText(R.string.tv_entire_number) + " : " + _history.getEntireNum() + getText(R.string.tv_quiz_unit));
        builder.setPositiveButton(R.string.btn_update, listener);
        builder.setNegativeButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class UpdateHistoryConfirmButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 更新を確定
            if (which == DialogInterface.BUTTON_POSITIVE) {
                // DB更新
                try {
                    var model = new HistoryModel(DatabaseHelper.getSQLiteDatabase(getActivity()));
                    var history = new History(_history.getId(), _history.getCategory(), _history.getLearnedDate(), _history.getLearnedTime(),
                            _history.getCorrectNum(), _history.getEntireNum(), _history.getCorrectRate());
                    model.update(history);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.update_failed_msg, Toast.LENGTH_SHORT).show();
                }
                // 更新完了ダイアログを表示
                var dialogFragment = new UpdateHistoryCompleteDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "UpdateHistoryConfirmDialogFragment");
            }
        }
    }
}
