import java.util.Arrays;
import java.util.List;

public class InsertionSort {
    public static void main(String[] args) {
        int[] unsorted = {8,10,9,7,12,6,6,10};
        InsertionSort inst = new InsertionSort();
        int[] sorted = inst.sort(Arrays.copyOf(unsorted,unsorted.length));
        int[] sorted1 = inst.sort1(Arrays.copyOf(unsorted,unsorted.length));
        int[] sorted2 = inst.sort2(Arrays.copyOf(unsorted,unsorted.length));
        System.out.println(Arrays.toString(sorted));
        System.out.println(Arrays.toString(sorted1));
        System.out.println(Arrays.toString(sorted2));
    }

    /**
     * 基础的插入排序，使用index作为实现基础
     * @param arr unsorted array
     * @return sorted array
     */
    public int[] sort(int[] arr) {
        int len = arr.length;
        for (int i = 1; i < len; ++i) {
            System.out.println("sorted1 "+i);
            int pivot = arr[i];
            int j = i - 1;
            while(j>=0 && arr[j] > pivot){
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = pivot;
        }
        System.out.println();
        return arr;
    }

    /**
     * 改动了i++的位置，其他和sort一样
     * @param arr unsorted array
     * @return sorted array
     */
    public int[] sort1(int[] arr) {
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            System.out.println("sorted2 " + i);
            int pivot = arr[i];
            int j = i - 1;
            while(j>=0 && arr[j] > pivot){
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = pivot;
        }
        return arr;
    }

    /**
     * 改动了判定条件，把>改成了>=
     * @param arr unsorted array
     * @return sorted array
     */
    public int[] sort2(int[] arr) {
        int len = arr.length;
        for (int i = 1; i < len; ++i) {
            int pivot = arr[i];
            int j = i - 1;
            while(j>=0 && arr[j] >= pivot){
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = pivot;
        }
        return arr;
    }
}
