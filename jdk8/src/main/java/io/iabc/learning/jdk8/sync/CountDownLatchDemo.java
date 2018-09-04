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

 package io.iabc.learning.jdk8.sync;

 import java.util.Date;
 import java.util.concurrent.CountDownLatch;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-04 21:58
  */
 public class CountDownLatchDemo {

     public static void main(String[] args) {
         CountDownLatch latch = new CountDownLatch(12);

         System.out.println("begin");
         new SimpleTask("test1", latch).start();
         new SimpleTask("test2", latch).start();
         new SimpleTask("test3", latch).start();
         new SimpleTask("test4", latch).start();
         new SimpleTask("test5", latch).start();
         new SimpleTask("test6", latch).start();
         new SimpleTask("test7", latch).start();
         new SimpleTask("test8", latch).start();
         new SimpleTask("test9", latch).start();
         new SimpleTask("test10", latch).start();
         new SimpleTask("test11", latch).start();
         new SimpleTask("test12", latch).start();

         try {
             latch.await();
         } catch (InterruptedException e) {

         }
         System.out.println("end");
     }

 }

 class SimpleTask extends Thread {

     private CountDownLatch latch;

     public SimpleTask(String name, CountDownLatch latch) {
         super(name);
         this.latch = latch;
     }

     @Override
     public void run() {
         try {
             Thread.sleep(10);
             System.out.println(this.getName() + ":" + new Date());
             this.latch.countDown();
         } catch (InterruptedException e) {
         }
     }
 }
