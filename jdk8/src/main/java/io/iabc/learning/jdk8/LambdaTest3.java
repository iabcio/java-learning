package io.iabc.learning.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class LambdaTest3 {
    private final static Logger logger = LoggerFactory.getLogger(LambdaTest3.class);

    public static void main(String[] args) {

        runnableTest();
        integerCompareTest();
        stringCompareTest();
        forTest();

    }

    private static void runnableTest() {
        // 使用匿名内部类  
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world !");
            }
        }).start();

        // 使用lambda表达式
        new Thread(() -> System.out.println("Hello world !")).start();

        // 使用匿名内部类  
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world !");
            }
        };

        // 使用 lambda表达式  
        Runnable runnable2 = () -> System.out.println("Hello world !");

        // 直接调用run 方法(没开新线程哦!)  
        runnable1.run();
        runnable2.run();
    }

    private static void integerCompareTest() {
        System.out.println();
        ArrayList<Integer> integerlist = new ArrayList<Integer>() {{
            add(1);
            add(3);
            add(2);
            add(7);
            add(6);
            add(8);
            add(5);
            add(9);
            add(4);
        }};

        integerlist.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("匿名内部类排序输出:" + integerlist);

        integerlist.sort((o1, o2) -> o1.compareTo(02));
        System.out.println("lambda表达式排序输出:" + integerlist);

        integerlist.sort(Integer::compareTo);
        System.out.println("lambda方法引用排序输出:" + integerlist);
    }

    private static void stringCompareTest() {

        String[] programers = { "zhangjj", "huwei", "yangxf", "lunzm", "shuchen" };

        // 使用匿名内部类根据 name 排序  
        Arrays.sort(programers, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return (s1.compareTo(s2));
            }
        });

        // 使用 lambda 表达式排序 programers  
        Comparator<String> sortByName = (String s1, String s2) -> (s1.compareTo(s2));
        Arrays.sort(programers, sortByName);
        System.out.println();
        System.out.println("匿名内部类排序输出:");
        for (String programer : programers) {
            System.out.print(programer + "; ");
        }

        // 也可以采用如下形式:  
        Arrays.sort(programers, (String s1, String s2) -> (s1.compareTo(s2)));
        System.out.println();
        System.out.println("lambda表达式排序输出:");
        for (String programer : programers) {
            System.out.print(programer + "; ");
        }

        // 还可以
        Arrays.sort(programers, String::compareTo);
        System.out.println();
        System.out.println("lambda方法引用排序输出:");
        for (String programer : programers) {
            System.out.print(programer + "; ");
        }
    }

    private static void forTest() {
        List<String> programers = new ArrayList<String>() {{
            add("zhangjj");
            add("huwei");
            add("yangxf");
            add("lunzm");
            add("shuchen");
        }};

        // 以前的循环方式  
        System.out.println();
        System.out.println();
        System.out.println("旧方式:");
        for (String programer : programers) {
            System.out.println(programer);
        }

        // 使用 lambda 表达式以及函数操作(functional operation)  
        System.out.println();
        System.out.println("lambda表达式:");
        programers.forEach((programer) -> System.out.println(programer));

        // 在 Java 8 中使用双冒号操作符(double colon operator)  
        System.out.println();
        System.out.println("lambda方法引用:");
        programers.forEach(System.out::println);

    }

}