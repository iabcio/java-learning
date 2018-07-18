package io.iabc.learning.jdk8;

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
        // 传统写法
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        }).run();

        // Lambda写法
        new Thread(() -> System.out.println("Hello World!"));
    }
}