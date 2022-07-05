package task_2604;

public class MultiplicationTable {
    public void printMT()
    {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i +"x"+ j +"="+ (i*j) + " ");
            }
            System.out.println();
        }
    }
}
