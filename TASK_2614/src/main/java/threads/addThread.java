package threads;

public class addThread extends Thread {
    public int sum;
    private static final int SIZE = 100_000_000;
    private static final int[] ARRAY = new int[SIZE];

    @Override
    public void run() {
        for (int i = 0; i < SIZE ; i++) {
            sum += ARRAY[i];
        }
    }

    public int getSum(){
        return sum;
    }
}



