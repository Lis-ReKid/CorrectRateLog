package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.re_kid.lis.correctratelog.CorrectRateFragment;
import com.re_kid.lis.correctratelog.HistoryListFragment;
import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.model.CategoryModel;
import com.re_kid.lis.correctratelog.model.HistoryModel;
import com.re_kid.lis.correctratelog.obj.Category;

public class FilterHistoryByCategoryDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
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

        // 選択ボタンリスナ登録
        view.findViewById(R.id.btnSelectCategory).setOnClickListener(v -> {
            // カテゴリ取得
            TextView textCategoryId = spnCategoryName.findViewById(R.id.spnCategoryIdRow);
            TextView textCategoryName = spnCategoryName.findViewById(R.id.spnCategoryNameRow);
            var category = new Category(Integer.parseInt(textCategoryId.getText().toString()),
                    textCategoryName.getText().toString());
            // フラグメント再生成
            try (var model = new HistoryModel(getActivity())) {
                Cursor newHistoryCursor = model.selectByCategory(category);
                int count = newHistoryCursor.getCount();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setReorderingAllowed(true);
                // 検索結果1件以上の時
                transaction.replace(R.id.top_fragment_container, CorrectRateFragment.newInstance(newHistoryCursor), null);
                transaction.replace(R.id.bottom_fragment_container, HistoryListFragment.newInstance(newHistoryCursor), null);
                transaction.commit();
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // リセットボタンリスナ登録
        view.findViewById(R.id.btnFilterReset).setOnClickListener(v -> {
            Cursor allHistoryCursor;
            try {
                var model = new HistoryModel(getActivity());
                allHistoryCursor = model.selectAll();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.top_fragment_container, CorrectRateFragment.newInstance(allHistoryCursor), null);
                transaction.replace(R.id.bottom_fragment_container, HistoryListFragment.newInstance(allHistoryCursor), null);
                transaction.commit();
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // ダイアログをビルド
        builder.setTitle(R.string.title_filter_by_category)
                .setView(view);
        return builder.create();
    }
}
