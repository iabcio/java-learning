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
 import java.util.Arrays;
 import java.util.List;
 import java.util.concurrent.Phaser;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-05 09:06
  */
 public class PhaserDemo2 {

     public static void main(String[] args) {
         final String[] members = new String[] { "xiaofei", "huwei", "zhijun", "jiajian", "zeming", "dapeng", "shuchen",
             "haokun", "yunkai", "wangbin", "yinwei" };

         final List<String> notPalyBilliards = new ArrayList<String>() {{
             add("dapeng");
             add("wangbin");
         }};
         final List<String> notGoKTV = new ArrayList<String>() {{
             add("jiajian");
         }};

         Phaser phaser = new HumanizedPhaser(members.length);
         System.out.println("周五晚上团建去唠!\n");
         Arrays.asList(members).parallelStream().forEach(member -> {
             HumanizedTeamMember teamMember = new HumanizedTeamMember(member, phaser);
             if (notPalyBilliards.contains(member)) {
                 teamMember.setNotPlayBilliardsSelected(true);
             }
             if (notGoKTV.contains(member)) {
                 teamMember.setNotGoKTVSelected(true);
             }

             teamMember.start();
         });

         try {
             Thread.sleep(10000);
             System.out.println("\n团建结束，周末好好休息!");
         } catch (InterruptedException e) {
         }
     }

 }

 class HumanizedTeamMember extends Thread implements HumanizedTeamBuildingActivity, Runnable {
     private Phaser phaser;
     private boolean notPlayBilliardsSelected;
     private boolean notGoKTVSelected;
     private String name;

     public HumanizedTeamMember(String name, Phaser phaser) {
         this.name = name;
         this.phaser = phaser;
     }

     public HumanizedTeamMember setNotPlayBilliardsSelected(boolean notPlayBilliardsSelected) {
         this.notPlayBilliardsSelected = notPlayBilliardsSelected;
         return this;
     }

     public HumanizedTeamMember setNotGoKTVSelected(boolean notGoKTVSelected) {
         this.notGoKTVSelected = notGoKTVSelected;
         return this;
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
         return this.name;
     }

     @Override
     public void run() {
         this.prepare();
         this.phaser.arriveAndAwaitAdvance();
         this.depart();
         this.phaser.arriveAndAwaitAdvance();
         this.eating();
         this.phaser.arriveAndAwaitAdvance();
         if (isPlayBilliardsSelected()) {
             this.playBilliards();
             this.phaser.arriveAndAwaitAdvance();
         } else {
             System.out.println(this.who() + "有事先走了，大伙玩好");
             this.phaser.arriveAndDeregister();
             return;
         }
         if (isGoKTVSelected()) {
             this.goKTV();
             this.phaser.arriveAndAwaitAdvance();
         } else {
             System.out.println(this.who() + "家太远先走了，大伙玩好");
             this.phaser.arriveAndDeregister();
             return;
         }
         this.goHome();
         this.phaser.arriveAndAwaitAdvance();
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
         return super.onAdvance(phase, registeredParties);
     }
 }
