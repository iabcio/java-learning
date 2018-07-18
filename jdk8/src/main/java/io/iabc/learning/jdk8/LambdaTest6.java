package io.iabc.learning.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.iabc.learning.jdk8.function.GenericConuterFunction;

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

public class LambdaTest6 {
    private final static Logger logger = LoggerFactory.getLogger(LambdaTest6.class);

    static <T> int doCount(GenericConuterFunction<T> function, List<T> values, T value) {
        return function.func(values, value);
    }

    public static void main(String[] args) {
        List<String> programers = new ArrayList<String>() {{
            add("zhangjj");
            add("huwei");
            add("yangxf");
            add("zhangjj");
            add("yangxf");
            add("shuchen");
            add("zhangjj");
            add("yangxf");
            add("lunzm");
            add("lunzm");
        }};
        //        List<String> list = programers.stream().filter(n -> n.startsWith("z")).collect(Collectors.toList());
        //        list.forEach(n -> System.out.println(n));
        System.out.println("start with z:");
        programers.stream().filter(n -> n.startsWith("z")).forEach(n -> System.out.println(n));
        System.out.println();
        System.out.println("all:");
        programers.stream().filter(n -> true).forEach(n -> System.out.println(n));
        System.out.println();
        System.out.println("none:");
        programers.stream().filter(n -> false).forEach(n -> System.out.println(n));
        System.out.println();
        System.out.println("length 5:");
        programers.stream().filter(n -> n.length() == 5).forEach(n -> System.out.println(n));
        System.out.println();
        System.out.println("length 5 and contain z:");
        programers.stream().filter(n -> n.length() == 5 && n.contains("z")).forEach(n -> System.out.println(n));
        System.out.println();
        System.out.println("length 5 and contain z:");
        Predicate<String> length5 = n -> n.length() == 5;
        Predicate<String> containz = (n) -> n.contains("z");
        programers.stream().filter(length5.and(containz)).forEach(n -> System.out.println(n));
        System.out.println();
        System.out.println("toUpperCase:");
        programers.stream().map(programer -> programer.toUpperCase()).forEach(n -> System.out.println(n));
        System.out.println();
        System.out.println("Distinct:");
        Set<String> names = new ConcurrentSkipListSet<>();
        programers.stream().filter(programer -> {
            boolean flag = !names.contains(programer);
            names.add(programer);
            return flag;
        }).forEach(n -> System.out.println(n));

        //获取数字的个数、最小值、最大值、总和以及平均值
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
        primes.stream().mapToDouble(x -> x + .12 * x).forEach(n -> System.out.println(n));
        double total = primes.stream().mapToDouble(x -> x + .12 * x).reduce((sum, x) -> sum + x).getAsDouble();
        System.out.println("total:" + total);
    }

}