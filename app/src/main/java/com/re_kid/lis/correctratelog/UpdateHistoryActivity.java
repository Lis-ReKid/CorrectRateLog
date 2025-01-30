package com.re_kid.lis.correctratelog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.re_kid.lis.correctratelog.dialog.DatePickerDialogFragment;
import com.re_kid.lis.correctratelog.dialog.TimePickerDialogFragment;
import com.re_kid.lis.correctratelog.dialog.UpdateHistoryConfirmDialogFragment;
import com.re_kid.lis.correctratelog.model.CategoryModel;
import com.re_kid.lis.correctratelog.obj.Category;
import com.re_kid.lis.correctratelog.obj.CorrectRate;
import com.re_kid.lis.correctratelog.obj.History;
import com.re_kid.lis.correctratelog.obj.LearnedDate;
import com.re_kid.lis.correctratelog.obj.LearnedTime;

public class UpdateHistoryActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 履歴を取得
        var intent = getIntent();
        History history = intent.getParcelableExtra("history");

        // カテゴリ選択スピナ選択肢をセット
        Spinner spnCategoryName = findViewById(R.id.spnCategoryName);
        String[] from = {"_id", "category_name"};
        int[] to = {R.id.spnCategoryIdRow, R.id.spnCategoryNameRow};
        Cursor cursor = null;
        SimpleCursorAdapter adapter;
        try (var model = new CategoryModel(UpdateHistoryActivity.this)) {
            cursor = model.selectAll();
            adapter = new SimpleCursorAdapter(UpdateHistoryActivity.this,
                    R.layout.spn_category_row,
                    cursor, from, to,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            spnCategoryName.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, R.string.toast_get_category_failed, Toast.LENGTH_SHORT).show();
        }

        // 初期値入力
        var oldCategoryId = history.getCategory().getId();
        var categoryPosition = -1;
        if(cursor.moveToFirst()) {
            do {
                var position = cursor.getColumnIndex("_id");
                var tmpId = cursor.getInt(position);
                if (tmpId == oldCategoryId) {
                    categoryPosition = cursor.getPosition();
                    break;
                }
            } while (cursor.moveToNext());
        }
        spnCategoryName.setSelection(categoryPosition);
        EditText etHistoryId = findViewById(R.id.et_history_id);
        etHistoryId.setText(String.valueOf(history.getId()));
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        tvLearnedDate.setText(history.getLearnedDate().toString());
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        tvLearnedTime.setText(history.getLearnedTime().toString());
        EditText etCorrectNumber = findViewById(R.id.et_correct_number);
        etCorrectNumber.setText(String.valueOf(history.getCorrectNum()));
        EditText etEntireNumber = findViewById(R.id.et_entire_number);
        etEntireNumber.setText(String.valueOf(history.getEntireNum()));
        System.out.println(history.getId());

        // 日付ボタン押下時処理
        tvLearnedDate.setOnClickListener(v -> {
            var datePicker = new DatePickerDialogFragment();
            var args = new Bundle();
            args.putString("Date", tvLearnedDate.getText().toString());
            datePicker.setArguments(args);
            datePicker.show(getSupportFragmentManager(), "datePicker");
        });

        // 時間ボタン押下時処理
        tvLearnedTime.setOnClickListener(v -> {
            var timePicker = new TimePickerDialogFragment();
            var args = new Bundle();
            args.putString("Time", tvLearnedTime.getText().toString());
            timePicker.setArguments(args);
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });

        // 更新ボタン押下時処理
        findViewById(R.id.btn_update_history).setOnClickListener(v -> {
            // 入力内容を取得
            TextView tvCategoryId = findViewById(R.id.spnCategoryIdRow);
            var categoryId = Integer.parseInt(tvCategoryId.getText().toString());
            TextView tvCategoryName = findViewById(R.id.spnCategoryNameRow);
            var categoryName = tvCategoryName.getText().toString();
            var learnedDate = LearnedDate.parse(tvLearnedDate.getText().toString());
            var learnedTime = LearnedTime.parse(tvLearnedTime.getText().toString());
            var textCorrectNum = etCorrectNumber.getText();
            var textEntireNum = etEntireNumber.getText();
            // カテゴリを取得
            var category = new Category(categoryId, categoryName);
            // 未入力チェック
            if(textCorrectNum.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_not_entered_msg, Toast.LENGTH_SHORT).show();
                return;
            }
            if(textEntireNum.toString().isEmpty()) {
                Toast.makeText(this, R.string.toast_not_entered_msg, Toast.LENGTH_SHORT).show();
                return;
            }
            var correctNum = Integer.parseInt(textCorrectNum.toString());
            var entireNum = Integer.parseInt(textEntireNum.toString());
            // 正答率を取得
            // 不正値チェック
            CorrectRate correctRate;
            try {
                correctRate = new CorrectRate(correctNum, entireNum);
            } catch (IllegalArgumentException e) {
                var msg = e.getMessage();
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                return;
            }
            // Historyオブジェクトを取得
            // 不正値チェック
            History newHistory;
            try {
                newHistory = new History(
                        Integer.parseInt(etHistoryId.getText().toString()),
                        category,
                        learnedDate,
                        learnedTime,
                        correctNum,
                        entireNum,
                        correctRate
                );
            } catch (IllegalArgumentException e) {
                var msg = e.getMessage();
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                return;
            }
            // 入力内容を確認ダイアログに渡す
            var bundle = new Bundle();
            bundle.putParcelable("history", newHistory);
            // 登録確認ダイアログを表示
            var dialogFragment = new UpdateHistoryConfirmDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getSupportFragmentManager(), "UpdateHistoryConfirmDialogFragment");

        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        var date = LearnedDate.of(year, month + 1, dayOfMonth);
        tvLearnedDate.setText(date.toString());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        var time = LearnedTime.of(hourOfDay, minute);
        tvLearnedTime.setText(time.toString());
    }
}