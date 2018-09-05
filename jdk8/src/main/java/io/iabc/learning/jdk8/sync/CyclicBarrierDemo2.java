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

 import java.util.Arrays;
 import java.util.concurrent.BrokenBarrierException;
 import java.util.concurrent.CyclicBarrier;
 import java.util.concurrent.atomic.AtomicInteger;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-05 01:05
  */
 public class CyclicBarrierDemo2 {

     public static void main(String[] args) {
         final String[] members = new String[] { "xiaofei", "huwei", "zhijun", "jiajian", "zeming", "dapeng", "shuchen",
             "haokun", "yunkai", "wangbin", "yinwei" };

         CyclicBarrier barrier = new CyclicBarrier(members.length, new SignBarAction());
         System.out.println("周五晚上团建去唠!\n");
         Arrays.asList(members).parallelStream().forEach(member -> {
             new TeamMember(member, barrier).start();
         });

         try {
             Thread.sleep(10000);
             System.out.println("\n团建结束，周末好好休息!");
         } catch (InterruptedException e) {
         }
     }
 }

 class TeamMember extends Thread implements TeamBuildingActivity {
     private CyclicBarrier barrier;

     public TeamMember(String name, CyclicBarrier barrier) {
         super(name);
         this.barrier = barrier;
     }

     @Override
     public String who() {
         return this.getName();
     }

     @Override
     public void run() {
         try {
             this.prepare();
             this.barrier.await();
             this.depart();
             this.barrier.await();
             this.eating();
             this.barrier.await();
             this.playBilliards();
             this.barrier.await();
             this.goKTV();
             this.barrier.await();
             this.goHome();
             this.barrier.await();
         } catch (InterruptedException e) {
         } catch (BrokenBarrierException e) {
         }
     }
 }

 interface TeamBuildingActivity {

     String who();

     default void prepare() {
         try {
             System.out.println(this.who() + "准备出发");
             Thread.sleep(1000);
         } catch (InterruptedException e) {
         }
     }

     default void depart() {
         try {
             System.out.println(this.who() + "打车去餐馆");
             Thread.sleep(1000);
         } catch (InterruptedException e) {
         }
     }

     default void eating() {
         try {
             System.out.println(this.who() + "吃饭、聊天、喝酒、...");
             Thread.sleep(1000);
         } catch (InterruptedException e) {
         }
     }

     default void playBilliards() {
         try {
             System.out.println(this.who() + "打桌球唠");
             Thread.sleep(1000);
         } catch (InterruptedException e) {
         }
     }

     default void goKTV() {
         try {
             System.out.println(this.who() + "去KTV飙歌");
             Thread.sleep(1000);
         } catch (InterruptedException e) {
         }
     }

     default void goHome() {
         try {
             System.out.println(this.who() + "各回各家，各找各妈");
             Thread.sleep(1000);
         } catch (InterruptedException e) {
         }
     }
 }

 class SignBarAction implements Runnable {

     private AtomicInteger generation = new AtomicInteger();

     public SignBarAction() {
     }

     @Override
     public void run() {
         System.out.println(this.generation.getAndIncrement() + " generation barrier reached!");
         System.out.println();
     }
 }

 