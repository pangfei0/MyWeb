package juli.service.ParticularElevatorSync;


public class AsciiUtil {

    public static String toString(byte... bytes) {
        return new String(bytes);
    }

    public static int toInt(byte... bytes) {
        return toInt(10, bytes);
    }

    public static int toInt(int radix, byte... bytes) {
        return toInt(toString(bytes), radix);
    }

    public static int toInt(String str, int radix) {
        return Integer.parseInt(str, radix);
    }

    public static double toDouble(byte... bytes) {
        return toDouble(toString(bytes));
    }

    public static double toDouble(String str) {
        return Double.parseDouble(str);
    }

    public static long toLong(byte... bytes) {
        return toLong(32, bytes);
    }

    public static long toLong(int radix, byte... bytes) {
        return toLong(toString(bytes), radix);
    }

    public static long toLong(String str, int radix) {
        return Long.parseLong(str, radix);
    }

    public static float toFloat(byte... bytes) {
        return toFloat(toString(bytes));
    }

    public static float toFloat(String str) {
        return Float.parseFloat(str);
    }
}
