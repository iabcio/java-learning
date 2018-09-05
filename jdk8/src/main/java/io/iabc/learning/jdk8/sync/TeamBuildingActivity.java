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

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-05 08:58
  */
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
