package co.kr.pcninc.data.marketcore.util;

import java.util.Arrays;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String QUESTIONMARK = "?";
    public static final String EQUAL = "=";
    public static final String AMPERSAND = "&";
    public static final String DOUBLE_QUOTATION = "\"";
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static boolean isNull(final String str) {
        return str == null;
    }

    public static boolean isNotNull(final String str) {
        return !isNull(str);
    }

    public static boolean anyNull(String... str) {
        return Arrays.stream(str).anyMatch(StringUtils::isNull);
    }

    public static boolean anyEmpty(String... str) {
        return Arrays.stream(str).anyMatch(StringUtils::isEmpty);
    }

    @Deprecated
    public static String concat(String delimeter, String... str) {
        return String.join(delimeter, str);
    }

    /**
     * @fn nullTo
     * @remark
     * - 문자열이 null 이면 '' 를 리턴
     * @param str
     * @return
     */
    public static String nullTo(String str) {
        return emptyTo(str, EMPTY);
    }

    /**
     * @fn nullTo
     * @remark
     * - 문자열이 null 이면 '' 를 리턴
     * @param str
     * @return
     */
    public static String nullTo(String str, String defaultValue) {
        return isNull(str) ? defaultValue : str;
    }


    /**
     * @fn emptyTo
     * @remark
     * - 문자열이 null 이거나 empty 이면 '' 를 리턴
     * @param str
     * @return
     */
    public static String emptyTo(String str) {
        return emptyTo(str, EMPTY);
    }

    /**
     * @fn emptyTo
     * @remark
     * - 문자열이 null 이거나 empty 이면 defaultValue 를 리턴
     * @param str
     * @param defaultValue
     * @return
     */
    public static String emptyTo(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str.toString();
    }

    /**
     * @fn includeValue
     * @remark
     * - 문자열에 주어진 문자열중 한개라도 존재하는자 판별
     * @param str
     * @param instr
     * @return
     */
    public static boolean includeValue(String str, String... instr) {
        return isNotEmpty(str) && Arrays.stream(instr).anyMatch(str::contains);
    }

    public static String prettyJson(String json) throws Exception {
        return ObjectConverter.toPrettyJson(ObjectConverter.toMap(json));
    }

    public static String quotation(String s) {
        return String.join(s, StringUtils.DOUBLE_QUOTATION, StringUtils.DOUBLE_QUOTATION);
    }

}
