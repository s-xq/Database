package com.sxq.database.util;

import java.math.BigDecimal;

/**
 * Created by shixiaoqiangsx on 2017/3/24.
 */

public class DecimalUtil {

    public static float floatScale(float f) {
        return floatScale(f, 2);
    }

    public static float floatScale(float f, int scale) {
        BigDecimal decimal = new BigDecimal(f);
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static double doubleScale(float f) {
        return doubleScale(f, 2);
    }

    public static double doubleScale(float f, int scale) {
        BigDecimal decimal = new BigDecimal((double) f);
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
