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

package io.iabc.learning.algorithm.stack;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Project: java-learning
 * TODO:
 *
 * @author <a href="mailto:h@iabc.io">shuchen</a>
 * @version V1.0.0
 * @since 2020-09-01 13:34
 */
public class Hanoi {

    public static void main(String[] args) {
        //        hanoi(10, 1, 2, 3);
        AtomicInteger counter = new AtomicInteger();
        hanoi(10, 1, 2, 3, counter);
        System.out.println(counter.intValue());
    }

    public static void hanoi(int top, int from, int mid, int to) {

        if (top == 1) {
            System.out.println("from:" + from + " to:" + to);
        } else {
            hanoi(top - 1, from, to, mid);
            System.out.println("from:" + from + " to:" + to);
            hanoi(top - 1, mid, from, to);
        }
    }

    public static void hanoi(int top, int from, int mid, int to, AtomicInteger counter) {

        if (top == 1) {
            System.out.println("Step:" + counter.incrementAndGet() + " from:" + from + " to: " + to);
        } else {
            hanoi(top - 1, from, to, mid, counter);
            System.out.println("Step:" + counter.incrementAndGet() + " from:" + from + " to:" + to);
            hanoi(top - 1, mid, from, to, counter);
        }
    }

}
