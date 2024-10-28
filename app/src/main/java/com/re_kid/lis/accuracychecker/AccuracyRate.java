package com.re_kid.lis.accuracychecker;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccuracyRate {
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
