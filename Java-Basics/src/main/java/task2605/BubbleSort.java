import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] unsorted = {8,10,9,7,12,6,6,10};
        BubbleSort bs = new BubbleSort();
        int[] sorted = bs.bubbleSort1(Arrays.copyOf(unsorted,unsorted.length));
        System.out.println(Arrays.toString(sorted));
    }

    public int[] bubbleSort(int[] arr){
        int len = arr.length;
        int temp = 0;
        boolean isSwap;

        for (int i = 0; i < len-1; i++) {
            isSwap = false;
            for (int j = 0; j < len-1-i; j++) {
                if (arr[j] > arr[j+1]){
                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    isSwap = true;
                }
            }
            if(!isSwap) break;
        }
        return arr;
    }

    public int[] bubbleSort1(int[] arr){
        int len = arr.length;
        Integer temp = 0;
        boolean isSwap;

        for (int i = 0; i < len-1; i++) {
            isSwap = false;
            for (int j = 0; j < len-1-i; j++) {
                if (arr[j] > arr[j+1]){
                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    isSwap = true;
                }
            }
            if(!isSwap) break;
        }
        return arr;
    }

}
