package com.re_kid.lis.correctratelog;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.re_kid.lis.correctratelog.dialog.HistoryDetailDialogFragment;
import com.re_kid.lis.correctratelog.obj.CorrectRate;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HistoryListFragment extends Fragment {
    private Cursor historiesCursor;
    public HistoryListFragment() {
        super(R.layout.fragment_history_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static HistoryListFragment newInstance(Cursor cursor) {
        HistoryListFragment fragment = new HistoryListFragment();
        fragment.setCursor(cursor);
        return fragment;
    }
    private void setCursor(Cursor cursor) {
        this.historiesCursor = cursor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 登録画面遷移ボタンリスナ登録
        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateHistoryActivity.class);
            startActivity(intent);
        });

        // 履歴リストを生成
        ListView lvHistory = view.findViewById(R.id.lvHistory);
        String[] from = {"_id", "_id", "learned_date", "learned_time", "correct_rate", "correct_number",
                "entire_number"};
        int[] to = {R.id.tv_history_id, R.id.tv_hist_tag_row_temp, R.id.tvLearnedDateRow, R.id.tvLearnedTimeRow,
                R.id.tv_correct_rate_row, R.id.tv_correct_number_row, R.id.tv_entire_number_row};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.history_row,
                historiesCursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        adapter.setViewBinder(new CustomViewBinder());
        lvHistory.setAdapter(adapter);

        // 履歴リストにイベントリスナを登録
        lvHistory.setOnItemClickListener(((parent, view1, position, id) -> {
            // リストの内容を取得
            SQLiteCursor parentText = (SQLiteCursor)parent.getItemAtPosition(position);
            int historyId = parentText.getInt(parentText.getColumnIndex("_id"));
            String date = parentText.getString(parentText.getColumnIndex("learned_date"));
            String time = parentText.getString(parentText.getColumnIndex("learned_time"));
            int correctNum = parentText.getInt(parentText.getColumnIndex("correct_number"));
            int entireNum = parentText.getInt(parentText.getColumnIndex("entire_number"));
            double correctRate = parentText.getDouble(parentText.getColumnIndex("correct_rate"));

            // ダイアログを取得
            HistoryDetailDialogFragment detailDialog = new HistoryDetailDialogFragment();
            Bundle args = new Bundle();
            args.putInt("id", historyId);
            args.putString("date", date);
            args.putString("time", time);
            args.putInt("correctNum", correctNum);
            args.putInt("entireNum", entireNum);
            args.putDouble("correctRate", correctRate);
            detailDialog.setArguments(args);
            detailDialog.show(getActivity().getSupportFragmentManager(), "DetailDialog");
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class CustomViewBinder implements ViewBinder {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            boolean result = false;
            // 正答率整形
            if (view.getId() == R.id.tv_correct_rate_row) {
                CorrectRate cr = new CorrectRate(cursor.getDouble(columnIndex));
                ((TextView) view).setText(cr.toString());
                result = true;
            }
            // 履歴タイトル整形
            if (view.getId() == R.id.tv_hist_tag_row_temp) {
                String text = getText(R.string.no_tag_history) + cursor.getString(columnIndex);
                ((TextView) view).setText(text);
                result = true;
            }
            return result;
        }
    }
}
