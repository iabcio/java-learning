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

 import static io.iabc.learning.jdk8.sync.PhaserDemo3.CAO;

 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import java.util.concurrent.CountDownLatch;
 import java.util.concurrent.Phaser;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-05 13:00
  */
 public class PhaserDemo3 {

     final static String CAO = "huwei";
     private final static String[] members = new String[] { "xiaofei", "huwei", "zhijun", "jiajian", "zeming", "dapeng",
         "shuchen", "haokun", "yunkai", "wangbin", "yinwei" };

     private final static List<String> notEating = new ArrayList<String>() {{
         add("xiaofei");
     }};

     private final static List<String> notPalyBilliards = new ArrayList<String>() {{
         add("dapeng");
         add("wangbin");
     }};
     private final static List<String> notGoKTV = new ArrayList<String>() {{
         add("jiajian");
     }};

     public static void main(String[] args) {
         System.out.println("周五晚上团建去唠!\n");
         tb(new HumanizedPhaser(members.length - notEating.size()), new CountDownLatch(1));
         try {
             Thread.sleep(10000);
             System.out.println("\n团建结束，周末好好休息!");
         } catch (InterruptedException e) {
         }
     }

     private static void tb(Phaser phaser, CountDownLatch countDownLatch) {

         Arrays.asList(members).parallelStream().filter(member -> !notEating.contains(member)).forEach(member -> {
             FreeStyleTeamMember teamMember = new FreeStyleTeamMember(member, phaser, countDownLatch);
             if (notPalyBilliards.contains(member)) {
                 teamMember.setNotPlayBilliardsSelected(true);
             }
             if (notGoKTV.contains(member)) {
                 teamMember.setNotGoKTVSelected(true);
             }
             teamMember.start();
         });

         notEating.parallelStream().forEach(member -> {
             FlyManTeamMember teamMember = new FlyManTeamMember(member, phaser, countDownLatch);
             teamMember.setNotEatingSelected(true);
             if (notPalyBilliards.contains(member)) {
                 teamMember.setNotPlayBilliardsSelected(true);
             }
             if (notGoKTV.contains(member)) {
                 teamMember.setNotGoKTVSelected(true);
             }
             teamMember.start();
         });
     }

 }

 class HumanizedPhaser extends Phaser {

     public HumanizedPhaser(int parties) {
         super(parties);
     }

     @Override
     protected boolean onAdvance(int phase, int registeredParties) {
         System.out.println("phase:" + phase + " registeredParties:" + registeredParties);
         System.out.println();
         switch (phase) {
         case 0:
             break;
         case 1:
             break;
         case 2:
             System.out.println("phase:" + phase + " registeredParties:" + this.getRegisteredParties() + " 杨老板加完班要来了");
             break;
         case 3:
             break;
         case 4:
             break;
         case 5:
             break;
         default:
             break;
         }
         return super.onAdvance(phase, registeredParties);
     }
 }

 class FlyManTeamMember extends FreeStyleTeamMember {
     public FlyManTeamMember(String name, Phaser phaser, CountDownLatch countDownLatch) {
         super(name, phaser, countDownLatch);
     }

     @Override
     public void run() {

         try {
             super.countDownLatch.await();
         } catch (InterruptedException e) {
         }

         this.playBilliards();
         this.phaser.arriveAndAwaitAdvance();

         this.goKTV();
         this.phaser.arriveAndAwaitAdvance();

         this.goHome();
         this.phaser.arriveAndAwaitAdvance();
     }
 }

 class FreeStyleTeamMember extends Thread implements HumanizedTeamBuildingActivity {

     protected Phaser phaser;
     protected CountDownLatch countDownLatch;
     private boolean notEatingSelected;
     private boolean notPlayBilliardsSelected;
     private boolean notGoKTVSelected;

     public FreeStyleTeamMember(String name, Phaser phaser, CountDownLatch countDownLatch) {
         super(name);
         this.phaser = phaser;
         this.countDownLatch = countDownLatch;
     }

     public FreeStyleTeamMember setNotPlayBilliardsSelected(boolean notPlayBilliardsSelected) {
         this.notPlayBilliardsSelected = notPlayBilliardsSelected;
         return this;
     }

     public FreeStyleTeamMember setNotGoKTVSelected(boolean notGoKTVSelected) {
         this.notGoKTVSelected = notGoKTVSelected;
         return this;
     }

     public FreeStyleTeamMember setNotEatingSelected(boolean notEatingSelected) {
         this.notEatingSelected = notEatingSelected;
         return this;
     }

     @Override
     public boolean isEatingSelected() {
         return !notEatingSelected;
     }

     @Override
     public boolean isPlayBilliardsSelected() {
         return !notPlayBilliardsSelected;
     }

     @Override
     public boolean isGoKTVSelected() {
         return !notGoKTVSelected;
     }

     @Override
     public String who() {
         return this.getName();
     }

     @Override
     public void run() {

         if (isEatingSelected()) {
             this.prepare();
             this.phaser.arriveAndAwaitAdvance();

             this.depart();
             this.phaser.arriveAndAwaitAdvance();

             this.eating();
             this.phaser.arriveAndAwaitAdvance();
             if (this.getName().equals(CAO)) {
                 this.phaser.register();
                 this.countDownLatch.countDown();
             }
         }

         if (!isPlayBilliardsSelected()) {
             this.phaser.arriveAndDeregister();
             System.out.println(this.who() + "有事先走了，大伙玩好");
             return;
         } else {
             this.playBilliards();
             this.phaser.arriveAndAwaitAdvance();
         }

         if (!isGoKTVSelected()) {
             this.phaser.arriveAndDeregister();
             System.out.println(this.who() + "家太远先走了，大伙玩好");
             return;
         } else {
             this.goKTV();
             this.phaser.arriveAndAwaitAdvance();
         }

         this.goHome();
         this.phaser.arriveAndAwaitAdvance();
     }
 }

