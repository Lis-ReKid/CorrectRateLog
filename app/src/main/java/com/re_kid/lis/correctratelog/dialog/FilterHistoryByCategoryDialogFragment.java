package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.model.CategoryModel;

public class FilterHistoryByCategoryDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new onBtnClickListener();
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.filter_by_category_dialog, null);
        // カテゴリ選択スピナ選択肢をセット
        Spinner spnCategoryName = view.findViewById(R.id.spnCategory);
        String[] from = {"_id", "category_name"};
        int[] to = {R.id.spnCategoryIdRow, R.id.spnCategoryNameRow};
        Cursor cursor;
        SimpleCursorAdapter adapter;
        try (var model = new CategoryModel(getActivity())) {
            cursor = model.selectAll();
            adapter = new SimpleCursorAdapter(getActivity(),
                    R.layout.spn_category_row,
                    cursor, from, to,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            spnCategoryName.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.toast_get_category_failed, Toast.LENGTH_SHORT).show();
        }
        builder.setTitle(R.string.title_filter_by_category)
                .setView(view)
                .setNegativeButton(R.string.btn_cancel, listener)
                .setPositiveButton(R.string.btn_select, listener);
        return builder.create();
    }

    private class onBtnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // フィルタリング
            if (which == DialogInterface.BUTTON_POSITIVE) {
                // Cursorを取得してフラグメントを再発行
            }
        }
    }
}
