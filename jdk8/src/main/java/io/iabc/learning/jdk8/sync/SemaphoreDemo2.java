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
         new Cook("huwei").start();
         new Eater("jiajian").start();
     }

     /**
      * 自助餐厅
      */
     private static class Cafeteria {
         public final static List<String> foodList = new ArrayList<String>() {
             {
                 add("三文鱼");
                 add("牛排");
                 add("生蚝");
                 add("鱿鱼");
                 add("海星");
                 add("甜点");
             }
         };
         private static String plate;
         private final static Semaphore consumerSemaphore = new Semaphore(0);
         private final static Semaphore producerSemaphore = new Semaphore(1);

         public static void put(String food, String who) {
             producerSemaphore.acquireUninterruptibly();
             Cafeteria.plate = food;
             System.out.println(who + " put food:" + food);
             consumerSemaphore.release();
         }

         public static String get(String who) {
             consumerSemaphore.acquireUninterruptibly();
             System.out.println(who + " get food:" + Cafeteria.plate);
             producerSemaphore.release();
             return Cafeteria.plate;
         }
     }

     private static class Cook extends Thread {

         public Cook(String name) {
             super("cook-" + name);
         }

         @Override
         public void run() {
             super.run();

             Cafeteria.foodList.stream().forEachOrdered(food -> {
                 Cafeteria.put(food, this.getName());
             });
         }
     }

     private static class Eater extends Thread {

         public Eater(String name) {
             super("eater-" + name);
         }

         @Override
         public void run() {
             AtomicInteger eated = new AtomicInteger();
             do {
                 String food = Cafeteria.get(this.getName());

             } while (eated.get() < Cafeteria.foodList.size());
         }
     }

 }
