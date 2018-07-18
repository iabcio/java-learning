package io.iabc.learning.jdk8;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.iabc.learning.jdk8.function.GenericConuterFunction;
import io.iabc.learning.jdk8.methodRef.GenericMethod;

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

public class LambdaTest5 {
    private final static Logger logger = LoggerFactory.getLogger(LambdaTest5.class);

    static <T> int doCount(GenericConuterFunction<T> function, List<T> values, T value) {
        return function.func(values, value);
    }

    public static void main(String[] args) {
        List<String> programers = new ArrayList<String>() {{
            add("zhangjj");
            add("zhangjj");
            add("huwei");
            add("yangxf");
            add("zhangjj");
            add("yangxf");
            add("shuchen");
            add("zhangjj");
            add("shuchen");
            add("yangxf");
            add("lunzm");
            add("lunzm");
        }};

        ArrayList<Integer> integerlist = new ArrayList<Integer>() {{
            add(1);
            add(3);
            add(3);
            add(2);
            add(7);
            add(6);
            add(5);
            add(5);
            add(6);
            add(5);
            add(8);
            add(8);
            add(9);
            add(4);
        }};

        System.out.println("静态方法引用:");
        System.out.println("countMatch：" + doCount(GenericMethod::<String>countMatch, programers, "shuchen"));
        System.out.println("countMatch：" + doCount(GenericMethod::<String>countMatch, programers, "sei"));

        System.out.println("countMatch：" + doCount(GenericMethod::<Integer>countMatch, integerlist, 5));
        System.out.println("countMatch：" + doCount(GenericMethod::<Integer>countMatch, integerlist, 0));

        System.out.println();
        System.out.println("实例方法引用:");
        GenericMethod<Integer> integerGenericMethod = new GenericMethod<Integer>();
        System.out
            .println("countNotMatch：" + doCount(integerGenericMethod::<String>countNotMatch, programers, "shuchen"));
        System.out.println("countNotMatch：" + doCount(integerGenericMethod::<String>countNotMatch, programers, "sei"));

        System.out.println("countNotMatch：" + doCount(integerGenericMethod::<Integer>countNotMatch, integerlist, 5));
        System.out.println("countNotMatch：" + doCount(integerGenericMethod::<Integer>countNotMatch, integerlist, 0));
    }
}