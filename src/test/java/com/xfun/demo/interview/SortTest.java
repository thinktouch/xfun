package com.xfun.demo.interview;

import java.util.Arrays;

/**
 * Created by xfun on 5/23/17.
 */
public class SortTest {

    public static void main(String[] args){
        int[] nums = {10, 11, 13, 44, 51, 61, 71, 38, 19, 34, 67, 33};
        System.out.println("sort before: " + Arrays.toString(nums));
        insertSort(nums);
        System.out.println("sort after: " + Arrays.toString(nums));
    }

    public static void binarySearch(){
        int[] nums = {0, 1, 3, 4, 5, 6, 7, 8, 9, 34, 67, 333};
        System.out.println(Arrays.binarySearch(nums, 3));
    }

    //交换
    private static void swap(int[] a, int i, int j) {
        a[i] ^= a[j];
        a[j] ^= a[i];
        a[i] ^= a[j];
    }

    //插入排序
    public static void insertSort(int[] a) {//a0为监视哨位置,不作为数据存储
        int len = a.length;
        for (int i = 2; i < len; i++) {
            if (a[i - 1] > a[i]) {
                a[0] = a[i];
                a[i] = a[i - 1];
                int j = 0;
                for (j = i - 2; a[j] > a[0]; j--) {
                    a[j + 1] = a[j];
                }
                a[j + 1] = a[0];
            }
        }
    }

    //选择排序
    public static void selectSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int j = selectMinKey(a, i); //从i开始a.length中找到最小的位置
            if (i != j) {
                swap(a, i, j);
            }
        }
    }

    // 查找从i开始到a.length中最小的位置
    private static int selectMinKey(int[] a, int i) {
        int key = i;
        for (int j = i + 1; j < a.length; j++) {
            if (a[j] < a[key]) {
                key = j;
            }
        }
        return key;
    }

    //冒泡排序
    public static void bubbleSort(int[] a) {
        int len = a.length;
        for (int i = 1; i < len - 1; i++) {
            for (int j = i; j < len - i; j++) {
                if (a[j + 1] < a[j]) {
                    swap(a, j + 1, j);
                }
            }
        }
    }

    //快速排序
    public static void quickSort(int[] a, int low, int high) {
        //递归快速排序
        int pivotLoc = 0;//中心点
        if (low < high) {
            pivotLoc = partitionLoc(a, low, high);
            quickSort(a, low, pivotLoc-1);
            quickSort(a, pivotLoc+1, high);
        }
    }

    //获取到a的下标 low ~ high 中, a[low]的应该放的位置, 即左边的数 < a[low] 右边的数 > a[low]
    private static int partitionLoc(int[] a, int low, int high) {
        a[0] = a[low];
        while (low < high) {
            while (low < high && a[high] >= a[0]) {
                high--;
            }
            a[low] = a[high];
            while (low < high && a[low] <= a[0]) {
                low++;
            }
            a[high] = a[low];
        }
        a[low] = a[0];
        return low;
    }
}
