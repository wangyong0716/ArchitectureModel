package com.archi.architecture.math;

import android.util.Log;

import java.util.Arrays;

public class SortUtil {
    private static final String TAG = "SortUtil";

    public static void test() {
        int[] a = new int[]{2, 5, 7, 3, 0, 12};
        Log.i(TAG, "quickFindKth = " + quickFindKth(a, 0, a.length-1, 2));

        Log.i(TAG, "before quickSort a= " + getStringFromArray(a));
        quickSort(a, 0, a.length - 1);
        Log.i(TAG, "after quickSort a= " + getStringFromArray(a));
    }

    /**
     * 快排-降序
     *
     * @param a
     * @param left
     * @param right
     */
    private static void quickSort(int[] a, int left, int right) {
        Log.i(TAG, "in quickSort a= " + getStringFromArray(a) + ", left = " + left + ", right = " + right);
        if (left >= right) {
            return;
        }
        int base = a[left];
        int i = left;
        int j = right;

        while (i < j) {
            while (a[j] <= base && i < j) {
                j--;
            }
            while (a[i] >= base && i < j) {
                i++;
            }
            if (i < j) {
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
        }
        a[left] = a[i];
        a[i] = base;
        quickSort(a, left, i - 1);
        quickSort(a, i + 1, right);
    }

    private static int quickFindKth(int[] a, int left, int right, int k) {
        if (left >= right) {
            return -1;
        }
        int base = a[left];
        int i = left;
        int j = right;

        while (i < j) {
            while (a[j] <= base && i < j) {
                j--;
            }
            while (a[i] >= base && i < j) {
                i++;
            }
            if (i < j) {
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
        }
        a[left] = a[i];
        a[i] = base;

        if (i == k) {
            return a[i];
        } else if (i>k){
            return quickFindKth(a, 0, i-1, k);
        } else {
            return quickFindKth(a, i+1, right, k);
        }
    }

    private static String getStringFromArray(int[] a) {
        String result = "";
        for (int i = 0; i < a.length; i++) {
            result += a[i];
            if (i != a.length - 1) {
                result += ", ";
            }
        }
        return result;
    }
}
