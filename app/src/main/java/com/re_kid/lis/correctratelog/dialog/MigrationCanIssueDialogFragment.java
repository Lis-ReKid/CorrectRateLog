package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AlertDialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.obj.Greeting;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MigrationCanIssueDialogFragment extends MigrationDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        // カスタムビューをインフレート
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.dialog_data_migration, null);
        // データ移行ID発行ボタンリスナ登録
        view.findViewById(R.id.btnIssueId).setOnClickListener(v -> {
            String migrationId = "testpass";
            // GreetingオブジェクトをJSONで取得
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(new GreetingConnection());
            try {
                migrationId = future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            var dialog = new MigrationIdIssuedDialogFragment();
            var args = new Bundle();
            args.putString("migrationId", migrationId);
            dialog.setArguments(args);
            dialog.show(getActivity().getSupportFragmentManager(), "MigragionIdIssuedDialogFragment");
        });
        // データ移行ID確定ボタンリスナ登録
        view.findViewById(R.id.btnConfirmMigrationId).setOnClickListener(v -> {
            var result = false;
            // 何らかの処理
            EditText etMigrationId = view.findViewById(R.id.etMigrationId);
            if (etMigrationId.getText().toString().isEmpty())return;
            // データ移行に成功したらメイン画面をリロード
            if (etMigrationId.getText().toString().equals("testpass")) result = true;
            if (result) {
                var intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return;
            }
            var dialog = new MigrationFailedDialogFragment();
            dialog.show(getActivity().getSupportFragmentManager(), "MigrationDialogFragment");
        });
        // ダイアログをビルド
        builder.setTitle(R.string.dialog_title_migration)
                .setView(view);
        return builder.create();
    }

    private class GreetingConnection implements Callable<String> {
        @Override
        @WorkerThread
        public String call() {
            JSONObject json;
            HttpURLConnection con = null;
            InputStream is = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/greet");
                con = (HttpURLConnection) url.openConnection();
//                con.setConnectTimeout(3000);
//                con.setReadTimeout(3000);
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                Greeting greeting = mapper.readValue(is, Greeting.class);
                return greeting.getGreeting();
            } catch (MalformedURLException e) {
                Log.e("MalformedURLException", "URL変換エラー");
                return "connection failed";
            } catch (IOException e) {
                Log.e("IOException", "入出力エラー", e);
                return "connection failed";
            }
        }
    }

}
