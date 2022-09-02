package chapter2;

public class testAllocation3 {
    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("Unused")
    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];

        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];

        System.out.println("good");

    }
}
