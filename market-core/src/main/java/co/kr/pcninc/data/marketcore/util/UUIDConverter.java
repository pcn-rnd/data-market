package co.kr.pcninc.data.marketcore.util;
// 출처 : https://javacan.tistory.com/entry/use-64radix-for-generating-short-sessionid

public class UUIDConverter {
    public static String toUnsignedString(long i, int shift) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        long number = i;

        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= shift;
        } while (number != 0);

        return new String(buf, charPos, (64 - charPos));
    }

    final static char[] digits = {

            '0', '1', '2', '3', '4', '5', '6', '7',

            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',

            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',

            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',

            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',

            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',

            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',

            'U', 'V', 'W', 'X', 'Y', 'Z', '_', '*' // '.', '-'

    };
}
