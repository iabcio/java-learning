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
 import java.util.concurrent.Exchanger;
 import java.util.concurrent.atomic.AtomicInteger;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-05 00:46
  */
 public class ExchangerDemo {

     public static void main(String[] args) {
         Exchanger<String> exchanger = new Exchanger<>();

         new Cook("huwei", exchanger).start();
         new Eater("jiajian", exchanger).start();
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
                 add("海星");
                 add("鱿鱼");
                 add("甜点");
             }
         };

         public final static List<String> ticketList = new ArrayList<String>() {
             {
                 add("三文鱼券");
                 add("牛排券");
                 add("生蚝券");
                 add("海星券");
                 add("鱿鱼券");
                 add("甜点券");
             }
         };
     }

     private static class Cook extends Thread {

         Exchanger<String> exchanger;

         public Cook(String name, Exchanger<String> exchanger) {
             super("cook-" + name);
             this.exchanger = exchanger;
         }

         @Override
         public void run() {
             Cafeteria.foodList.stream().forEach(food -> {
                 try {
                     final String ticket = exchanger.exchange(food);
                     System.out.println(this.getName() + " get ticket:" + ticket);
                 } catch (InterruptedException e) {
                 }
             });
         }
     }

     private static class Eater extends Thread {

         Exchanger<String> exchanger;

         public Eater(String name, Exchanger<String> exchanger) {
             super("eater-" + name);
             this.exchanger = exchanger;
         }

         @Override
         public void run() {
             AtomicInteger eated = new AtomicInteger();

             Cafeteria.ticketList.stream().forEach(ticket -> {
                 try {
                     final String food = exchanger.exchange(ticket);
                     System.out.println(this.getName() + " get food:" + food);
                 } catch (InterruptedException e) {
                 }
             });
         }
     }

 }
