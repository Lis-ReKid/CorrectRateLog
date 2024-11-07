package com.re_kid.lis.accuracychecker;

import static java.lang.Long.parseLong;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateHistoryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseHelper _helper;

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

        _helper = new DatabaseHelper(CreateHistoryActivity.this);

        // 日時の初期値入力
        final Locale locale = Locale.getDefault();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", locale);
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm", locale);
        final Date nowDateTime = new Date(System.currentTimeMillis());
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        tvLearnedDate.setText(dateFormat.format(nowDateTime));
        tvLearnedTime.setText(timeFormat.format(nowDateTime));

        // イベントリスナ登録
        CreateHistoryListener listener = new CreateHistoryListener();
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);
        tvLearnedDate.setOnClickListener(listener);
        tvLearnedTime.setOnClickListener(listener);

    }

    public void onCreateBtnClick(View view) {
        // 入力内容を取得
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        String strLearnedDate = tvLearnedDate.getText().toString();
        EditText etAccurateNumber = findViewById(R.id.et_accurate_number);
        String strAccurateNumber = etAccurateNumber.getText().toString();
        EditText etEntireNumber = findViewById(R.id.et_entire_number);
        String strEntireNumber = etEntireNumber.getText().toString();

        // 正答率の算出
        double accuracyRate = AccuracyRate.calcRate(strAccurateNumber, strEntireNumber);

        SQLiteDatabase db = _helper.getWritableDatabase();

        // SQLを作成
        String sqlInsert = "INSERT INTO Histories " +
                "(history_datetime, accurate_number, entire_number, accuracy_rate)" +
                "VALUES(?, ?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sqlInsert);
        stmt.bindString(1, strLearnedDate);
        stmt.bindLong(2, parseLong(strAccurateNumber));
        stmt.bindLong(3, parseLong(strEntireNumber));
        stmt.bindDouble(4, accuracyRate);

        // SQLを実行
        stmt.executeInsert();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        System.out.println("onDataSet() called.");
        System.out.println("year : " + year);
        System.out.println("month : " + month);
        System.out.println("dayOfMonth : " + dayOfMonth);
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        tvLearnedDate.setText(String.format("%d/%02d/%02d", year, month + 1, dayOfMonth));
    }

    private class CreateHistoryListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (vId == R.id.btn_back) {
                _helper.close();
                finish();
            } else if(vId == R.id.tv_learned_date) {
                DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
                Bundle args = new Bundle();
                TextView temp = (TextView)v;
                args.putString("Date", temp.getText().toString());
                datePicker.setArguments(args);
                datePicker.show(getSupportFragmentManager(), "datePicker");
            } else if(vId == R.id.tv_learned_time) {
                TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
                Bundle args = new Bundle();
                args.putString("Time", findViewById(R.id.tv_learned_time).toString());
                timePicker.setArguments(args);
                timePicker.show(getSupportFragmentManager(), "timePicker");
            }
        }
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }
}