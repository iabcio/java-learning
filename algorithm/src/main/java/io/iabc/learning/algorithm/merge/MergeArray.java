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

package io.iabc.learning.algorithm.merge;

/**
 * Project: java-learning
 *
 * (JDK1.8)给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
 * 说明:
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
 * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
 * 输入:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 * <p>
 * 输出: [1,2,2,3,5,6]
 *
 * @author <a href="mailto:h@iabc.io">shuchen</a>
 * @version V1.0.0
 * @since 2020-09-14 10:09
 */
public class MergeArray {
    public static void main(String[] args) {
        int m = 3;
        int n = 3;
        int[] nums1 = new int[]{1, 2, 3, 0, 0, 0};
        int[] nums2 = new int[]{2, 5, 6};

        //        merge0(m, nums1, n, nums2);
        merge(m, nums1, n, nums2);

        print(nums1);
    }

    private static void print(int[] array) {
        for (int x : array) {
            System.out.println(x);
        }
    }

    private static void merge0(int m, int[] nums1, int n, int[] nums2) {
        int j = 0;
        for (int i = 0; i < n; i++) {
            j = m + i - 1;
            while (j >= 0 && nums1[j] > nums2[i]) {
                nums1[j + 1] = nums1[j];
                j--;
            }
            nums1[j + 1] = nums2[i];
        }

    }

    private static void merge(int m, int[] nums1, int n, int[] nums2) {
        int pos = m + n;
        m--;
        n--;
        while (pos > 0) {
            if (m >= 0 && n >= 0) {
                if (nums1[m] > nums2[n]) {
                    nums1[--pos] = nums1[m--];
                } else {
                    nums1[--pos] = nums2[n--];
                }
            } else {
                if (m > 0) {
                    for (int i = m; i >= 0; i--) {
                        nums1[--pos] = nums1[m--];
                    }
                } else {
                    for (int i = n; i >= 0; i--) {
                        nums1[--pos] = nums2[n--];
                    }
                }
            }

        }

    }

    private static void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

}
