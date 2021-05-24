package com.zgy.develop.sort;

/**
 * 归并排序
 */
public class MergeSort {

    public static void sort(int[] arr) {
        start(arr, 0, arr.length - 1);
    }

    public static void start(int[] arr, int l, int r) {

        if (l >= r) {
            return;
        }

        int mid = (r + l) / 2;
        start(arr, l, mid);
        start(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    public static void merge(int[] arr, int l, int mid, int r) {

        int[] temp = new int[r - l + 1];
        int i1 = l;
        int i2 = mid + 1;
        int i = 0;

        while (i1 <= mid && i2 <= r) {
            temp[i++] = arr[i1] < arr[i2] ? arr[i1++] : arr[i2++];
        }

        while (i1 <= mid) {
            temp[i++] = arr[i1++];
        }

        while (i2 <= r) {
            temp[i++] = arr[i2++];
        }

        for (int j = 0; j < temp.length; j++) {
            arr[l + j] = temp[j];
        }
    }
}
