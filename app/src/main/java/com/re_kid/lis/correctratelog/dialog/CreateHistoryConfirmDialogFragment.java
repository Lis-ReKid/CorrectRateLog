package com.re_kid.lis.correctratelog.dialog;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;
import com.re_kid.lis.correctratelog.model.HistoryModel;
import com.re_kid.lis.correctratelog.obj.History;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateHistoryConfirmDialogFragment extends DialogFragment {
    private History _history;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        _history = requireArguments().getParcelable("history");
        var builder = new AlertDialog.Builder(getActivity());
        var listener = new CreateHistoryConfirmButtonClickListener();
        builder.setTitle(R.string.dialog_create_confirm_title);
        builder.setMessage(getText(R.string.tv_category_name) + "：" + _history.getCategory().getName() + "\n" +
                getText(R.string.tv_learned_date) + " : " + _history.getLearnedDate().toString() + " " +
                _history.getLearnedTime().toString() + "\n" +
                getText(R.string.tv_correct_number) + " : " + _history.getCorrectNum() + getText(R.string.tv_quiz_unit) + "\n" +
                getText(R.string.tv_entire_number) + " : " + _history.getEntireNum() + getText(R.string.tv_quiz_unit));
        builder.setPositiveButton(R.string.btn_create, listener);
        builder.setNegativeButton(R.string.btn_cancel, listener);
        return builder.create();
    }

    private class CreateHistoryConfirmButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 登録を確定
            if(which == DialogInterface.BUTTON_POSITIVE) {
                // DB登録
                try(var model = new HistoryModel(getActivity())) {
                    var history = new History(0, _history.getCategory(), _history.getLearnedDate(), _history.getLearnedTime(),
                            _history.getCorrectNum(), _history.getEntireNum(), _history.getCorrectRate());
                    model.createHistory(history);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.create_failed_msg, Toast.LENGTH_SHORT).show();
                }
                // 1日後に通知
                RemindNotification notification = new RemindNotification();
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.submit(notification);
                // 登録完了ダイアログを表示
                var dialogFragment = new CreateHistoryCompleteDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "CreateHistoryConfirmDialogFragment");
            }
        }
    }
    class RemindNotification implements Runnable {
        String CHANNEL_ID = "remindNotification";
        @WorkerThread
        @Override
        public void run() {
            // Android8.0以上のみ
            // 通知チャネルを作成
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        getString(R.string.notification_name),
                        NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
            // 通知タップ時のアクションを作成
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext() ,0, intent,
                    PendingIntent.FLAG_IMMUTABLE);
            // 通知のビルダを作成
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "remindNotification")
                    .setSmallIcon(R.drawable.stylus_note_24dp_e8eaed_fill0_wght400_grad0_opsz24)
                    .setContentTitle(getString(R.string.notification_title))
                    .setContentText(getString(R.string.notification_msg))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            // マネージャを作成
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
            // 通知の許可を確認
            if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            // 24時間待機
            try {
                Thread.sleep(86400000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 通知を発行
            notificationManagerCompat.notify(1001, builder.build());
        }
    }
}
