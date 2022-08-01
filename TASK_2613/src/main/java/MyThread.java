public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread started");
        System.out.print(Thread.currentThread().getName());
        System.out.println(" is my very first thread. I love it.");
        System.out.println("Thread is ending....");
    }
}
