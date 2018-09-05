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
 import java.util.concurrent.Phaser;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-05 08:54
  */
 public class PhaserDemo {

     public static void main(String[] args) {
         final String[] members = new String[] { "xiaofei", "huwei", "zhijun", "jiajian", "zeming", "dapeng", "shuchen",
             "haokun", "yunkai", "wangbin", "yinwei" };

         Phaser phaser = new Phaser(members.length);
         System.out.println("周五晚上团建去唠!\n");
         Arrays.asList(members).parallelStream().forEach(member -> {
             new NewTeamMember(member, phaser).start();
         });

         try {
             Thread.sleep(10000);
             System.out.println("\n团建结束，周末好好休息!");
         } catch (InterruptedException e) {
         }
     }

 }

 class NewTeamMember extends Thread implements TeamBuildingActivity {
     private Phaser phaser;

     public NewTeamMember(String name, Phaser phaser) {
         super(name);
         this.phaser = phaser;
     }

     @Override
     public String who() {
         return this.getName();
     }

     @Override
     public void run() {
         this.prepare();
         this.phaser.arriveAndAwaitAdvance();
         this.depart();
         this.phaser.arriveAndAwaitAdvance();
         this.eating();
         this.phaser.arriveAndAwaitAdvance();
         this.playBilliards();
         this.phaser.arriveAndAwaitAdvance();
         this.goKTV();
         this.phaser.arriveAndAwaitAdvance();
         this.goHome();
         this.phaser.arriveAndAwaitAdvance();
     }
 }
