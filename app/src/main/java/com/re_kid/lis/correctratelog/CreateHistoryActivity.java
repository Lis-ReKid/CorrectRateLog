package com.re_kid.lis.correctratelog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.re_kid.lis.correctratelog.dialog.CreateHistoryConfirmDialogFragment;
import com.re_kid.lis.correctratelog.dialog.DatePickerDialogFragment;
import com.re_kid.lis.correctratelog.dialog.TimePickerDialogFragment;
import com.re_kid.lis.correctratelog.model.CategoryModel;
import com.re_kid.lis.correctratelog.obj.Category;
import com.re_kid.lis.correctratelog.obj.CorrectRate;
import com.re_kid.lis.correctratelog.obj.History;
import com.re_kid.lis.correctratelog.obj.LearnedDate;
import com.re_kid.lis.correctratelog.obj.LearnedTime;

public class CreateHistoryActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // カテゴリ選択スピナ選択肢をセット
        Spinner spnCategoryName = findViewById(R.id.spnCategoryName);
        String[] from = {"_id", "category_name"};
        int[] to = {R.id.spnCategoryIdRow, R.id.spnCategoryNameRow};
        Cursor cursor;
        SimpleCursorAdapter adapter;
        try (var model = new CategoryModel(CreateHistoryActivity.this)) {
            cursor = model.selectAll();
            adapter = new SimpleCursorAdapter(CreateHistoryActivity.this,
                    R.layout.spn_category_row,
                    cursor, from, to,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            spnCategoryName.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 日時の初期値入力
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        tvLearnedDate.setText(LearnedDate.now().toString());
        tvLearnedTime.setText(LearnedTime.now().toString());

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

        // バックボタン押下時処理
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // MainActivityを作り直してフィニッシュ
                var intent = new Intent(CreateHistoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);

    }

    public void onCreateBtnClick(View view) {
        // viewを取得
        TextView tvCategoryId = findViewById(R.id.spnCategoryIdRow);
        TextView tvCategoryName = findViewById(R.id.spnCategoryNameRow);
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        EditText etCorrectNumber = findViewById(R.id.et_correct_number);
        EditText etEntireNumber = findViewById(R.id.et_entire_number);
        // 入力内容を取得
        var textCategoryId = tvCategoryId.getText();
        var textCategoryName = tvCategoryName.getText();
        var textLearnedDate = tvLearnedDate.getText();
        var textLearnedTime = tvLearnedTime.getText();
        var textCorrectNum = etCorrectNumber.getText();
        var textEntireNum = etEntireNumber.getText();
        // カテゴリを取得
        var category = new Category(Integer.parseInt(textCategoryId.toString()),
                textCategoryName.toString());
        // 未入力チェック
        if(textCorrectNum.toString().isEmpty()) {
            Toast.makeText(this, R.string.toast_not_entered_msg, Toast.LENGTH_SHORT).show();
            return;
        }
        if(textEntireNum.toString().isEmpty()) {
            Toast.makeText(this, R.string.toast_not_entered_msg, Toast.LENGTH_SHORT).show();
            return;
        }
        var learnedDate = LearnedDate.parse(textLearnedDate.toString());
        var learnedTime = LearnedTime.parse(textLearnedTime.toString());
        var correctNum = Integer.parseInt(textCorrectNum.toString());
        var entireNum = Integer.parseInt(textEntireNum.toString());
        CorrectRate correctRate;
        History history;
        // 正答率を取得
        // 不正値チェック
        try {
        correctRate = new CorrectRate(correctNum, entireNum);
        history = new History(0, category, learnedDate, learnedTime, correctNum, entireNum, correctRate);
        } catch (IllegalArgumentException e) {
            var msg = e.getMessage();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        // 入力内容を確認ダイアログに渡す
        var bundle = new Bundle();
        bundle.putParcelable("history", history);

        // 登録確認ダイアログを表示
        var dialogFragment = new CreateHistoryConfirmDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "CreateHistoryConfirmDialogFragment");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}