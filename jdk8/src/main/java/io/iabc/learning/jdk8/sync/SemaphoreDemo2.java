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

 import java.util.ArrayList;
 import java.util.List;
 import java.util.concurrent.Semaphore;
 import java.util.concurrent.atomic.AtomicInteger;
 import java.util.concurrent.atomic.LongAdder;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-04 21:58
  */
 public class SemaphoreDemo2 {

     public static void main(String[] args) {

         new Cook("huwei", Cafeteria.semaphore).start();
         new Eater("jiajian", Cafeteria.semaphore).start();
     }

     /**
      * 盘子
      */
     private static class Cafeteria {
         private final static List<String> foodList = new ArrayList<String>() {
             {
                 add("三文鱼");
                 add("牛排");
                 add("生蚝");
                 add("鱿鱼");
                 add("海星");
                 add("甜点");
             }
         };
         static String plate;
         final static Semaphore semaphore = new Semaphore(1, true);
     }

     private static class Cook extends Thread {

         private Semaphore semaphore;
         private AtomicInteger seq = new AtomicInteger();

         public Cook(String name, Semaphore semaphore) {
             super("cook-" + name);
             this.semaphore = semaphore;
         }

         @Override
         public void run() {
             super.run();

             do {

                 try {
                     this.semaphore.acquireUninterruptibly();
                     if (Cafeteria.plate == null) {
                         Cafeteria.plate = Cafeteria.foodList.get(seq.getAndIncrement());
                         System.out.println(super.getName() + " cook " + Cafeteria.plate);
                     }
                     try {
                         Thread.sleep(10);
                     } catch (InterruptedException e) {
                     }
                 } finally {
                     this.semaphore.release();
                 }
             } while (seq.intValue() < Cafeteria.foodList.size());

         }
     }

     private static class Eater extends Thread {

         private Semaphore semaphore;

         public Eater(String name, Semaphore semaphore) {
             super("eater-" + name);
             this.semaphore = semaphore;
         }

         @Override
         public void run() {
             super.run();
             LongAdder count = new LongAdder();
             do {
                 this.semaphore.acquireUninterruptibly();
                 if (Cafeteria.plate != null) {
                     System.out.println(super.getName() + " is eating " + Cafeteria.plate);
                     Cafeteria.plate = null;
                     count.increment();
                 }
                 this.semaphore.release();

                 try {
                     Thread.sleep(10);
                 } catch (InterruptedException e) {
                 }

             } while (count.intValue() < Cafeteria.foodList.size());

         }
     }

 }
