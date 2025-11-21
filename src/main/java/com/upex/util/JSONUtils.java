package com.upex.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.math.BigDecimal;

public class JSONUtils {
    private static final SerializeConfig CONFIG = new SerializeConfig();

    static {
        CONFIG.put(BigDecimal.class, (serializer, object, fieldName, fieldType, features) -> {
            if (object == null) {
                serializer.writeNull();
                return;
            }
            BigDecimal decimal = (BigDecimal) object;
            BigDecimal stripped = decimal.stripTrailingZeros();
            String result = stripped.scale() < 0 ?
                    stripped.setScale(0).toPlainString() :
                    stripped.toPlainString();
            serializer.write(result);
        });
    }

    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj, CONFIG);
    }
}
