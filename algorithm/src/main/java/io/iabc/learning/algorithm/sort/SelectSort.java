/*
 * Copyright 2017-2018 Iabc Co.Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.iabc.learning.algorithm.sort;

/**
 * Project: java-learning
 * TODO:
 *
 * @author <a href="mailto:h@iabc.io">shuchen</a>
 * @version V1.0.0
 * @since 2020-09-02 09:42
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] a = new int[] { 1, 5, 3, 7, 8, 4, 8, 0, 7, 3, 6, 5 };
        print(a);
        selectSort(a);
        print(a);
    }

    public static void selectSort(int[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            int swapped = 0;
            int pos = i;
            for (int j = i + 1; j < len; j++) {
                if (array[j] < array[pos]) {
                    pos = j;
                    swapped++;
                }
            }
            if (swapped > 0) {
                swap(array, i, pos);
            }
        }
    }

    public static void selectSort2(int[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (array[j] < array[i]) {
                    swap(array, i, j);
                }
            }

        }
    }

    private static void swap(int[] array, int x, int y) {
        array[x] ^= array[y];
        array[y] ^= array[x];
        array[x] ^= array[y];
    }

    private static void swap2(int[] array, int x, int y) {
        int tmp = array[x];
        array[x] = array[y];
        array[y] = tmp;
    }

    private static void print(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
