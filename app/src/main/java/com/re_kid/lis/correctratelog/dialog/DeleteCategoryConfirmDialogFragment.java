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
import com.re_kid.lis.correctratelog.model.CategoryModel;
import com.re_kid.lis.correctratelog.obj.Category;

public class DeleteCategoryConfirmDialogFragment extends DialogFragment {
    Category _category;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // カテゴリを取得
        _category = requireArguments().getParcelable("category");
        // ダイアログをビルド
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new DeleteCategoryConfirmDialogOnClickListener();
        builder.setTitle(R.string.dialog_delete_confirm_msg)
                .setMessage(_category.getCategoryName())
                .setPositiveButton(R.string.btn_delete, listener)
                .setNeutralButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class DeleteCategoryConfirmDialogOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE) {
                // 削除確定時処理
                // 削除処理
                var model = new CategoryModel(getActivity());
                model.delete(_category);
                // 元の画面をリフレッシュ
                var intent = new Intent(getActivity(), getActivity().getClass());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
