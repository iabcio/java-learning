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

package io.iabc.learning.algorithm.bit;

import java.math.BigInteger;

/**
 * Project: java-learning
 * TODO:
 *
 * @author <a href="mailto:h@iabc.io">shuchen</a>
 * @version V1.0.0
 * @since 2020-09-01 11:30
 */
public class CountBitOne {
    static final int NTH = 4;
    static final int MAX = BigInteger.valueOf(2).pow(NTH).intValue() - 1;
    static final int[] DATUMS = buildDatums(NTH);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(countBitOfOneByDatums(i));
        }
    }

    /**
     * 使用预算基数批量迭代计算
     *
     * @param num
     * @return
     */
    private static int countBitOfOneByDatums(int num) {
        int sum = 0;
        while (num > 0) {
            sum += DATUMS[num & MAX];
            num >>= NTH;
        }
        return sum;
    }

    /**
     * 使用位运算迭代计算
     *
     * @param num
     * @return
     */
    private static int countBitOfOne(int num) {
        int count = 0;
        while (num > 0) {
            count += num & 1;
            num >>= 1;
        }
        return count;
    }

    /**
     * 构建2的N次方的计数基数数组
     *
     * @param nth
     * @return
     */
    private static int[] buildDatums(int nth) {
        int total = BigInteger.valueOf(2).pow(nth).intValue();
        int[] result = new int[total];
        for (int i = 0; i < total; i++) {
            result[i] = countBitOfOne(i);
        }

        return result;
    }
}
