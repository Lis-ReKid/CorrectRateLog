package com.re_kid.lis.correctratelog;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import com.re_kid.lis.correctratelog.model.HistoryModel;
import com.re_kid.lis.correctratelog.obj.CorrectRate;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HistoryListFragment extends Fragment {
    private DatabaseHelper _helper;
    public HistoryListFragment() {
        super(R.layout.fragment_history_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _helper = new DatabaseHelper(getActivity());
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

        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateHistoryActivity.class);
            startActivity(intent);
        });

        ListView lvHistory = view.findViewById(R.id.lvHistory);
        try(HistoryModel model = new HistoryModel(this.getContext())){
            Cursor cursor = model.selectAll();
            String[] from = {"_id", "learned_date", "learned_time", "correct_rate", "correct_number",
                    "entire_number"};
            int[] to = {R.id.tv_hist_tag_row_temp, R.id.tvLearnedDateRow, R.id.tvLearnedTimeRow,
                    R.id.tv_correct_rate_row, R.id.tv_correct_number_row, R.id.tv_entire_number_row};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.history_row,
                    cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            adapter.setViewBinder(new CustomViewBinder());
            lvHistory.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), R.string.get_histories_failed_msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        _helper.close();
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
                // StringBuilderの方が良いか？要検討
                String origin = cursor.getString(columnIndex);
                String strNoTag = getString(R.string.no_tag_history);
                String text = strNoTag + origin;
                ((TextView) view).setText(text);
                result = true;
            }
            return result;
        }
    }
}
