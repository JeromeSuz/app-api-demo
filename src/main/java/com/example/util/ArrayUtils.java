package com.example.util;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 数组操作工具类
 *
 * @author jerome
 * @date 2017/2/27 10:25
 */
public class ArrayUtils {
    /**
     * <p>Checks if an array of Objects is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive longs is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive ints is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive shorts is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final short[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive chars is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final char[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive bytes is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final byte[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive doubles is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final double[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive floats is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final float[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if an array of primitive booleans is empty or {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final boolean[] array) {
        return array == null || array.length == 0;
    }

    // ----------------------------------------------------------------------

    /**
     * <p>Checks if an array of Objects is not empty or not {@code null}.</p>
     *
     * @param <T>   the component type of the array
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static <T> boolean isNotEmpty(final T[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive longs is not empty or not {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final long[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive ints is not empty or not {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final int[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive shorts is not empty or not {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final short[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive chars is not empty or not {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final char[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive bytes is not empty or not {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final byte[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive doubles is not empty or not {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final double[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive floats is not empty or not {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final float[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * <p>Checks if an array of primitive booleans is not empty or not {@code null}.</p>
     *
     * @param array the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final boolean[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 对数组进行排序，(重写toString()进行排序区分)
     */
    public static void arraySort(Object[] arr) {
        // Arrays.sort
        Arrays.sort(arr, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
    }

    /**
     * 交换数组中两元素
     *
     * @param ints 需要进行交换操作的数组
     * @param x    数组中的位置1
     * @param y    数组中的位置2
     * @return 交换后的数组
     */
    public static int[] swap(int[] ints, int x, int y) {
        int temp = ints[x];
        ints[x] = ints[y];
        ints[y] = temp;
        return ints;
    }

    /**
     * 2个数组合并，形成一个新的数组
     *
     * @param array1
     * @param array2
     * @return
     */
    public static int[] merge(int[] array1, int[] array2) {
        int[] dest = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, dest, 0, array1.length);
        System.arraycopy(array2, 0, dest, array1.length, array2.length);
        return dest;
    }
}
