package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AlertDialog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.tool.Migratable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MigrationCanIssueDialogFragment extends MigrationDialogFragment implements Migratable {
    String _ID;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        // カスタムビューをインフレート
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.dialog_data_migration, null);
        // データ移行ID発行ボタンリスナ登録
        view.findViewById(R.id.btnIssueId).setOnClickListener(v -> {
            String migrationId = null;
            // GreetingオブジェクトをJSONで取得
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(new issueIdConnection());
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
            // 入力チェック
            EditText etMigrationId = view.findViewById(R.id.etMigrationId);
            if (etMigrationId.getText().toString().isEmpty())return;
            // データを移行
            _ID = etMigrationId.getText().toString();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<Boolean> future = executorService.submit(new migrateConnection());
            // 移行可否を取得
            try {
                result = future.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // データ移行成功でメイン画面をリロード
            if (result) {
                var intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return;
            }
            // データ移行失敗でダイアログを表示
            var dialog = new MigrationFailedDialogFragment();
            dialog.show(getActivity().getSupportFragmentManager(), "MigrationDialogFragment");
        });
        // ダイアログをビルド
        builder.setTitle(R.string.dialog_title_migration)
                .setView(view);
        return builder.create();
    }

    private class issueIdConnection implements Callable<String> {
        @Override
        @WorkerThread
        public String call() throws JsonProcessingException {
            var id =  issueId(getContext());
            return id;
        }
    }

    private class migrateConnection implements Callable<Boolean>{
        @Override
        public Boolean call() throws Exception {
            return migrate(getContext(), _ID);
        }
    }

}
