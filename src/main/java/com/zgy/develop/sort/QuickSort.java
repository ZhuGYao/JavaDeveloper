package com.zgy.develop.sort;

/**
 * 快速排序
 */
public class QuickSort {

    public static void sort(int[] arr) {
        start(arr, 0, arr.length - 1);
    }

    public static void start(int[] arr, int l, int r) {

        if (l >= r) {
            return;
        }

        int[] index = partition(arr, l, r);
        start(arr, l, index[0]);
        start(arr, index[1], r);
    }

    public static int[] partition(int[] arr, int l, int r) {
        int less = l - 1;
        int more = r + 1;
        int num = arr[r];

        while (l < more) {
            if (arr[l] > num) {
                swap(arr, l, --more);
            } else if (arr[l] < num) {
                swap(arr, l++, ++less);
            } else {
                l++;
            }
        }

        return new int[]{less, more};
    }


    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
