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
 public class CyclicBarrierDemo {

     public static void main(String[] args) {

         //         CyclicBarrier barrier = new CyclicBarrier(3, new BarAction());
         CyclicBarrier barrier = new CyclicBarrier(3, new StatBarAction());
         //         CyclicBarrier barrier = new CyclicBarrier(3);

         System.out.println("中午打球去唠!\n");
         new Player("weirui", barrier).start();
         new Player("huwei", barrier).start();
         new Player("shuchen", barrier).start();
         System.out.println("\n打球结束，好好上班!");
     }
 }

 class Player extends Thread {
     private CyclicBarrier barrier;

     public Player(String name, CyclicBarrier barrier) {
         super(name);
         this.barrier = barrier;
     }

     @Override
     public void run() {
         try {
             System.out.println(this.getName() + "准备出发");
             this.barrier.await();
             System.out.println(this.getName() + "等电梯");
             this.barrier.await();
             System.out.println(this.getName() + "坐车去球场");
             this.barrier.await();
             System.out.println(this.getName() + "换运动装备");
             this.barrier.await();
             System.out.println(this.getName() + "打球中");
             this.barrier.await();
             System.out.println(this.getName() + "打完球，洗漱换装备");
             this.barrier.await();
             System.out.println(this.getName() + "集合，坐车回公司");
             this.barrier.await();
         } catch (InterruptedException e) {
         } catch (BrokenBarrierException e) {
         }
     }
 }

 class BarAction implements Runnable {
     @Override
     public void run() {
         System.out.println("Barrier Reached!");
         System.out.println();
     }
 }

 class StatBarAction implements Runnable {

     private AtomicInteger generation = new AtomicInteger();

     public StatBarAction() {
     }

     @Override
     public void run() {
         System.out.println(this.generation.getAndIncrement() + " generation barrier reached!");
         System.out.println();
     }
 }
