package co.kr.pcninc.data.marketcore.util;

public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
    public static final Object NULL = null;

    public static boolean isNull(final Object obj) {
        return obj == NULL;
    }

    public static boolean isNotNull(final Object obj) {
        return !isNull(obj);
    }
    public static boolean anyNull(String... obj) {
        for (final Object o : obj) {
            if (isNull(o)) {
                return true;
            }
        }
        return false;
    }
    public static boolean anyEmpty(String... obj) {
        for (final Object o : obj) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }
}
