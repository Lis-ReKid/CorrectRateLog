package com.re_kid.lis.correctratelog.obj;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class CorrectRate {
    private final double correctRate;
    public CorrectRate(final String correctNum, final String entireNum) {
        final int intCorrect = Integer.parseInt(correctNum);
        final int intEntire = Integer.parseInt(entireNum);
        // 正答数0未満でエラー
        if (intCorrect < 0) throw new IllegalArgumentException("正答数は0以上の数値を指定してください。");
        // 問題数1未満でエラー
        if (intEntire < 1) throw new IllegalArgumentException("問題数は1以上の数値を指定してください。");
        // 正答数>問題数でエラー
        if (intCorrect > intEntire) throw new IllegalArgumentException("正答数が問題数を超えています。");

        // 正答率を初期化
        // 小数点以下3桁のdouble型
        BigDecimal decDividend = new BigDecimal(intCorrect);
        BigDecimal decDivisor = new BigDecimal(intEntire);
        this.correctRate =  decDividend.divide(decDivisor, 3, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * double型で正答率を返します。
     * @return 率（小数点以下３桁）
     */
    public double getCorrectRate() {
        return correctRate;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%.1f", this.correctRate * 100);
    }
}
