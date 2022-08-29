package task2614.threads;

public class addThread4 extends Thread {
    public int sum;
    private static final int SIZE = 100_000_000;
    private static int[] ARRAY = new int[SIZE];

    @Override
    public void run() {
        for (int i = SIZE * 3 / 4; i < SIZE; i++) {
            sum += ARRAY[i];
        }
    }

    public int getSum(){
        return sum;
    }
}



