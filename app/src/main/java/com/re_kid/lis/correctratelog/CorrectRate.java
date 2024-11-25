package com.re_kid.lis.correctratelog;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CorrectRate {
    private final int correctNum;
    private final int entireNum;
    public CorrectRate(int correctNum, int entireNum) {
        this.correctNum = correctNum;
        this.entireNum = entireNum;
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
