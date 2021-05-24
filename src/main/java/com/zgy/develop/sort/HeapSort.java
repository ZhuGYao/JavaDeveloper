package com.zgy.develop.sort;

/**
 * 堆排序
 * @author zgy
 * @data 2021/5/24 22:37
 */

public class HeapSort {

    public static void sort(int[] arr) {

        // 构建大根堆
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }

        int heapSize = arr.length;
        // 将大根堆头部与尾部进行交换，然后长度减一
        swap(arr, 0, --heapSize);

        // 循环此过程，是为了将大的数字不断放入尾部，然后构建一个有顺序的数组
        while (heapSize > 0) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    public static void heapInsert(int[] arr, int i) {

        while (arr[i] > arr[(i-1)/2]) {
            swap(arr, i, (i-1)/2);
            i = (i-1)/2;
        }
    }

    public static void heapify(int[] arr, int index, int heapSize) {
        int left = index*2+1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) return;
            swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
