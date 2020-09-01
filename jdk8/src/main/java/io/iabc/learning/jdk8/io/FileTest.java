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

package io.iabc.learning.jdk8.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project: java-learning
 * TODO:
 *
 * @author <a href="mailto:h@iabc.io">shuchen</a>
 * @version V1.0.0
 * @since 2020-09-01 12:30
 */
public class FileTest {
    public static final Logger logger = LoggerFactory.getLogger(FileTest.class);

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(
            new FileReader("/Users/shuchen/data/cabin/cache/test-cache.txt"))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("quit")) {
                    break;
                }
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

}
