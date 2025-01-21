package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;

public class CreateCategoryDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.category_create_dialog, null);
        view.findViewById(R.id.btCategoryCreate).setOnClickListener(v -> {
            // 入力内容を取得
            EditText etCategoryName = view.findViewById(R.id.etCategoryName);
            var categoryName = etCategoryName.getText().toString();
            // 未入力なら何もしない
            if (categoryName.isEmpty()) return;
            // 確認ダイアログを表示
            var bundle = new Bundle();
            bundle.putString("categoryName", categoryName);
            var dialog = new CreateCategoryConfirmDialogFragment();
            dialog.setArguments(bundle);
            dialog.show(getActivity().getSupportFragmentManager(), "CreateCategoryConfirmDialog");
        });
        builder.setTitle(R.string.create_category_title)
                .setView(view);
        return builder.create();
    }
}
