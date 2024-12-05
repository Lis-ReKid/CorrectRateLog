package com.re_kid.lis.correctratelog.obj;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LearnedDateTime {
    private final LearnedDate learnedDate;
    private final LearnedTime learnedTime;

    public LearnedDateTime(final LearnedDate learnedDate, final LearnedTime learnedTime) {
        this.learnedDate = learnedDate;
        this.learnedTime = learnedTime;
    }

    public static LearnedDateTime now() {
        // 現在の日時を取得してインスタンス化
        final LocalDateTime nowDateTime = LocalDateTime.now();
        final Locale locale = Locale.getDefault();
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd", locale);
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm", locale);
        final LearnedDate learnedDate = LearnedDate.parse(dateFormat.format(nowDateTime));
        final LearnedTime learnedTime = LearnedTime.parse(timeFormat.format(nowDateTime));

        // 現在の日時で初期化したオブジェクトを返す
        return new LearnedDateTime(learnedDate, learnedTime);
    }

    public String getLearnedDate() {
        return learnedDate.toString();
    }

    public String getLearnedTime() {
        return learnedTime.toString();
    }
}
