import jdk.jfr.StackTrace;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleMath {
    public static void main(String[] args) {
        SimpleMath sm = new SimpleMath();
        sm.divide();
        sm.divide2();
//        sm.tryFinal();
    }

    public void tryFinal2(){
        try (Scanner scanner = new Scanner(new FileInputStream("a"), StandardCharsets.UTF_8)) {
            System.out.println("Something happens here.");
        } catch(Exception e) {
            System.out.println("Exception handling here.");
        }
    }

    public void tryFinal(){
        ReentrantLock lock = new ReentrantLock();
        try {
            System.out.println("Something needs to be locked");
        } finally {
            lock.unlock();
        }
    }

    public void divide2(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter numerator: ");
        int nm = scanner.nextInt();
        System.out.print("Enter denominator: ");
        int dm = scanner.nextInt();

        try {
            System.out.println(MessageFormat.format("{0}/{1} = {2}", nm, dm, nm / dm));
        } catch (ArithmeticException e) {
            System.out.println("Exception happened");
            System.out.println(e.getMessage());
            throw e;
        } catch (Exception e){
            System.out.println("General Exception happened");
            System.out.println(e.getMessage());
            throw e;
        }
        finally {
            System.out.println("This is a finally block");
        }
        System.out.println("Thanks");
    }

    public void divide(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter numerator: ");
        int nm = scanner.nextInt();
        System.out.print("Enter denominator: ");
        if(scanner.hasNextInt()){
            int dm = scanner.nextInt();
            if (dm==0){
                System.out.println("Denominator cannot be 0, safely exit...");
                System.exit(1);
            } else {
                System.out.println(MessageFormat.format(
                        "{0}/{1} = {2}",
                        nm, dm, nm / dm));
                System.out.println("Thanks");
            }
        } else {
            System.out.println("Denominator is not an integer, safely exit...");
            System.exit(1);
        }
    }
}
