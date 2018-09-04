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

 import java.util.concurrent.Semaphore;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-04 21:58
  */
 public class SemaphoreDemo {

     public static void main(String[] args) {
         Semaphore semaphore = new Semaphore(2);
         
         new Player("shuchen", semaphore).start();
         new Player("jiajian", semaphore).start();
         new Player("xiaofei", semaphore).start();
         new Player("huwei", semaphore).start();
         new Player("zeming", semaphore).start();
         new Player("wangbin", semaphore).start();
         new Player("dapeng", semaphore).start();
         new Player("zhijun", semaphore).start();
         new Player("xiaobai", semaphore).start();
     }

     private static class Player extends Thread {

         private Semaphore semaphore;

         public Player(String name, Semaphore semaphore) {
             super(name);
             this.semaphore = semaphore;
         }

         @Override
         public void run() {
             super.run();

             try {
                 this.semaphore.acquireUninterruptibly();
                 System.out.println(super.getName() + " is play badminton");

                 Thread.sleep(100);
                 System.out.println(super.getName() + " is play badminton finished ");
             } catch (InterruptedException e) {

             } finally {
                 this.semaphore.release();
             }
         }
     }

 }
