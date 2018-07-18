package io.iabc.learning.jdk8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.iabc.learning.jdk8.function.HigerOrderFunction;
import io.iabc.learning.jdk8.function.StringProcessFunction;

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

public class LambdaTest2 {
    private final static Logger logger = LoggerFactory.getLogger(LambdaTest2.class);

    public static void main(String[] args) {

        HigerOrderFunction higerOrderFunction = (func, content) -> func.process(content);

        StringProcessFunction function = content -> content.toUpperCase();
        System.out.println("toUpperCase：" + higerOrderFunction.doSomeThing(function, "Ohaha"));
        System.out.println("toUpperCase：" + higerOrderFunction.doSomeThing(function, "Hello World!"));

        StringProcessFunction function2 = content -> content.toLowerCase();
        System.out.println("toLowerCase：" + higerOrderFunction.doSomeThing(function2, "Ohaha"));
        System.out.println("toLowerCase：" + higerOrderFunction.doSomeThing(function2, "Hello World"));

        StringProcessFunction function3 = content -> {
            StringBuilder sb = new StringBuilder();
            for (int i = content.length() - 1; i >= 0; i--) {
                sb.append(content.charAt(i));
            }
            return sb.toString();
        };
        System.out.println("toReverse：" + higerOrderFunction.doSomeThing(function3, "Ohaha"));
        System.out.println("toReverse：" + higerOrderFunction.doSomeThing(function3, "Hello World"));
    }
}