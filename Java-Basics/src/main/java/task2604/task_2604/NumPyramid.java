package task2604.task_2604;

public class NumPyramid {
    public void generatePyramid()
    {
        int NUM = 5;
        for(int i = 0; i < NUM; i++) {
            for (int j = 0; j < NUM-i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < i*2+1; j++) {
                System.out.print(j+1);
            }
            System.out.println();
        }
    }
}
