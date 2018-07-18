package io.iabc.learning.jdk8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.iabc.learning.jdk8.function.GenericFilterFunction;
import io.iabc.learning.jdk8.function.SimpleFunctionSingleAbstractFunction;
import io.iabc.learning.jdk8.function.SimpleFunctionWithParamInteger;
import io.iabc.learning.jdk8.function.SimpleFunctionWithParamString;
import io.iabc.learning.jdk8.function.SimpleFunctionWithReturnDouble;
import io.iabc.learning.jdk8.function.SimpleFunctionWithReturnInteger;
import io.iabc.learning.jdk8.function.SimpleFunctionWithVoid;

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

public class LambdaTest1 {
    private final static Logger logger = LoggerFactory.getLogger(LambdaTest1.class);

    public static void main(String[] args) {

        SimpleFunctionWithVoid function = () -> System.out.println("hello lambda!");

        function.test();

        SimpleFunctionWithVoid function0 = () -> {
            System.out.println("do one thing!");
            System.out.println("do more things!");
        };

        function0.test();

        SimpleFunctionWithReturnDouble function1 = () -> 5.0;
        //        bad return type
        //        SimpleFunctionWithReturnDouble function11 = () -> "5.0";

        System.out.println("SimpleFunctionWithReturnDouble:" + function1.test());

        SimpleFunctionWithReturnInteger function2 = () -> 5;

        System.out.println("SimpleFunctionWithReturnInteger:" + function2.test());

        SimpleFunctionSingleAbstractFunction singleAbstractFunction = () -> System.out
            .println("only single abstract method allowed");

        singleAbstractFunction.test2();

        SimpleFunctionWithParamString function3 = content -> System.out.println("hello " + content);

        function3.hello("wedoctor");
        function3.hello("summer");

        SimpleFunctionWithParamInteger function4 = num -> num % 2 == 0;
        System.out.println("isEven:" + function4.isEven(0));
        System.out.println("isEven:" + function4.isEven(1));
        System.out.println("isEven:" + function4.isEven(2));
        System.out.println("isEven:" + function4.isEven(3));
        System.out.println("isEven:" + function4.isEven(4));

        GenericFilterFunction<Integer> function5 = num -> num % 2 == 0;
        System.out.println("isEven:" + function5.test(0));
        System.out.println("isEven:" + function5.test(1));
        System.out.println("isEven:" + function5.test(2));
        System.out.println("isEven:" + function5.test(3));
        System.out.println("isEven:" + function5.test(4));

        GenericFilterFunction<String> function6 = content -> content.startsWith("w") && content.endsWith("z");
        System.out.println("isMatch:" + function6.test("winxz"));
        System.out.println("isMatch:" + function6.test("winx"));
        System.out.println("isMatch:" + function6.test("xinxz"));
        System.out.println("isMatch:" + function6.test("wwinxz"));
        System.out.println("isMatch:" + function6.test("winxzy"));
        
    }

}