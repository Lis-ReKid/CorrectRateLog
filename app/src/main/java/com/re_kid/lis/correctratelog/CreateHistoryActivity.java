package com.re_kid.lis.correctratelog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.re_kid.lis.correctratelog.model.HistoryModel;
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

        // 日時の初期値入力
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        tvLearnedDate.setText(LearnedDate.now().toString());
        tvLearnedTime.setText(LearnedTime.now().toString());


        // 日付ボタン押下時処理
        tvLearnedDate.setOnClickListener(v -> {
            DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
            Bundle args = new Bundle();
            args.putString("Date", tvLearnedDate.getText().toString());
            datePicker.setArguments(args);
            datePicker.show(getSupportFragmentManager(), "datePicker");
        });

        // 時間ボタン押下時処理
        tvLearnedTime.setOnClickListener(v -> {
            TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
            Bundle args = new Bundle();
            args.putString("Time", tvLearnedTime.getText().toString());
            timePicker.setArguments(args);
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });

        // バックボタン押下時処理
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // MainActivityを作り直してフィニッシュ
                Intent intent = new Intent(CreateHistoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(callback);

    }

    public void onCreateBtnClick(View view) {
        // viewを取得
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        EditText etCorrectNumber = findViewById(R.id.et_correct_number);
        EditText etEntireNumber = findViewById(R.id.et_entire_number);
        // 入力内容を取得
        String learnedDate = tvLearnedDate.getText().toString();
        String learnedTime = tvLearnedTime.getText().toString();
        int correctNum = Integer.parseInt(etCorrectNumber.getText().toString());
        int entireNumber = Integer.parseInt(etEntireNumber.getText().toString());
        // 正答率を取得
        CorrectRate cr = new CorrectRate(correctNum, entireNumber);

        // DB登録
        try(HistoryModel model = new HistoryModel(CreateHistoryActivity.this)) {
            History history = new History(LearnedDate.parse(learnedDate), LearnedTime.parse(learnedTime),
                    correctNum, entireNumber, cr);
            model.createHistory(history);
        } catch (Exception e) {
            Toast.makeText(this, R.string.create_failed_msg, Toast.LENGTH_SHORT).show();
        }

        // ダイアログを表示
        CreateHistoryConfirmDialogFragment dialogFragment = new CreateHistoryConfirmDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "CreateHistoryConfirmDialogFragment");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        LearnedDate date = LearnedDate.of(year, month + 1, dayOfMonth);
        tvLearnedDate.setText(date.toString());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        LearnedTime time = LearnedTime.of(hourOfDay, minute);
        tvLearnedTime.setText(time.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}