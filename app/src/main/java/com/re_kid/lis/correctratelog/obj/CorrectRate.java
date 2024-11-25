package com.re_kid.lis.correctratelog.obj;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CorrectRate {
    private final int correctNum;
    private final int entireNum;
    private final double correctRate;
    public CorrectRate(final int correctNum, final int entireNum) {
        // 正答数0未満でエラー
        if (correctNum < 0) throw new IllegalArgumentException("正答数は0以上の数値を指定してください。");
        // 問題数1未満でエラー
        if (entireNum < 1) throw new IllegalArgumentException("問題数は1以上の数値を指定してください。");
        // 正答数>問題数でエラー
        if (correctNum > entireNum) throw new IllegalArgumentException("正答数が問題数を超えています。");

        this.correctNum = correctNum;
        this.entireNum = entireNum;

        // 正答率を初期化
        // 小数点以下3桁のdouble型
        BigDecimal decDividend = new BigDecimal(correctNum);
        BigDecimal decDivisor = new BigDecimal(entireNum);
        this.correctRate =  decDividend.divide(decDivisor, 3, RoundingMode.HALF_UP).doubleValue();
    }
    /**
     *
     * @param dividend 割られる数
     * @param divisor 割る数
     * @return 率（小数点以下３桁）
     */
    public static double calcRate(String dividend, String divisor) {
        BigDecimal decDividend = new BigDecimal(dividend);
        BigDecimal decDivisor = new BigDecimal(divisor);
        return decDividend.divide(decDivisor, 3, RoundingMode.HALF_UP).doubleValue();
    }
}
