package com.re_kid.lis.correctratelog.obj;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LearnedTime implements Parcelable {
    private final LocalTime learnedTime;
    private static final Locale _LOCALE = Locale.getDefault();
    private static final DateTimeFormatter _FORMATTER = DateTimeFormatter.ofPattern("HH:mm", _LOCALE);

    private LearnedTime(final String learnedTime) {
        this.learnedTime = LocalTime.parse(learnedTime, _FORMATTER);
    }
    private LearnedTime(final int hourOfDay, final int minute) {
        this.learnedTime = LocalTime.of(hourOfDay, minute);
    }

    protected LearnedTime(Parcel in) {
        learnedTime = LocalTime.parse(in.readString(), _FORMATTER);
    }

    public static final Creator<LearnedTime> CREATOR = new Creator<>() {
        @Override
        public LearnedTime createFromParcel(Parcel in) {
            return new LearnedTime(in);
        }

        @Override
        public LearnedTime[] newArray(int size) {
            return new LearnedTime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.toString());
    }

    /**
     * 指定の時刻で初期化したLearnedDateインスタンスを取得
     * @param learnedTime 時間（HH:mm）
     * @return LearnedTimeインスタンス
     */
    public static LearnedTime parse(final String learnedTime) {
        return new LearnedTime(learnedTime);
    }

    /**
     * 指定の時刻で初期化したLearnedTimeインスタンスを取得
     * @param hourOfDay 時
     * @param minute 分
     * @return LearnedTimeインスタンス
     */
    public static LearnedTime of(final int hourOfDay, final int minute) {
        return new LearnedTime(hourOfDay, minute);
    }

    /**
     * 現在の時刻で初期化したLearnedTimeインスタンスを取得
     * @return LearnedTimeインスタンス
     */
    public static LearnedTime now() {
        final var nowTime = LocalDateTime.now();
        return new LearnedTime(_FORMATTER.format(nowTime));
    }
    @NonNull
    @Override
    public String toString() {
        return learnedTime.format(_FORMATTER);
    }
}
