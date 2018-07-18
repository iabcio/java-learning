package io.iabc.learning.jdk8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.iabc.learning.jdk8.function.SimpleStringFunction;
import io.iabc.learning.jdk8.methodRef.SimpleMethod;

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

public class LambdaTest4 {
    private final static Logger logger = LoggerFactory.getLogger(LambdaTest4.class);

    static String doAnyThing(SimpleStringFunction function, String content) {
        return function.func(content);
    }

    public static void main(String[] args) {

        System.out.println("静态方法引用:");
        System.out.println("toUpperCase：" + doAnyThing(SimpleMethod::uppercase, "Ohaha"));
        System.out.println("toUpperCase：" + doAnyThing(SimpleMethod::uppercase, "Hello World!"));

        System.out.println();
        System.out.println("实例方法引用:");
        SimpleMethod simpleMethod = new SimpleMethod();
        System.out.println("toLowerCase：" + doAnyThing(simpleMethod::lowercase, "Ohaha"));
        System.out.println("toLowerCase：" + doAnyThing(simpleMethod::lowercase, "Hello World!"));
    }
}