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

package io.iabc.learning.jdk8;

/**
 * Project: java-learning
 * TODO:
 *
 * @author <a href="mailto:h@iabc.io">shuchen</a>
 * @version V1.0.0
 * @since 2020-09-13 15:14
 */
public class Test {

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            if (checked(i)) {
                System.out.println(i);
            }
        }

    }

    private static boolean checked(int num) {

        int i = 2;
        int sum = 1;  // 1一定是因子之一
        while (true && i < num) {
            if (num % i == 0) {
                sum += i;
            }
            i++;
        }

        return sum == num;
    }
}
