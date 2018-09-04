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
 import java.util.concurrent.Callable;
 import java.util.concurrent.CountDownLatch;
 import java.util.concurrent.ExecutionException;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Future;
 import java.util.concurrent.LinkedBlockingDeque;
 import java.util.concurrent.ThreadPoolExecutor;
 import java.util.concurrent.TimeUnit;

 /**
  * Project: java-learning
  * TODO:
  *
  * @author <a href="mailto:h@iabc.io">shuchen</a>
  * @version V1.0
  * @since 2018-09-04 21:58
  */
 public class CountDownLatchDemo2 {

     private static ExecutorService pool = new ThreadPoolExecutor(4, 4, 0, TimeUnit.MINUTES,
         new LinkedBlockingDeque<Runnable>(1024));

     public static void main(String[] args) {
         final List<String> members = new ArrayList<String>() {{
             add("huwei");
             add("jiajian");
             add("zeming");
             add("xiaofei");
             add("shuchen");
         }};

         final CountDownLatch countDownLatch = new CountDownLatch(members.size());
         List<Future<String>> futureList = new ArrayList<Future<String>>();

         System.out.println("大伙更新一下本周任务进度:");
         members.parallelStream().forEach(member -> {
             Callable<String> c = new Callable<String>() {
                 @Override
                 public String call() throws Exception {
                     String stat = null;
                     try {
                         stat = member + "任务进度更新完毕";
                     } finally {
                         // 每完成一个计数器减1
                         countDownLatch.countDown();
                     }
                     return stat;
                 }
             };
             Future<String> f = pool.submit(c);
             futureList.add(f);
         });

         try {
             System.out.println("等待组员更新本周任务进度中...");
             countDownLatch.await();
             System.out.println("组员更新本周任务进度完毕");
         } catch (InterruptedException e) {
             System.out.println("countDownLatch exception with reason:" + e.getLocalizedMessage());
         }

         List<String> stats = new ArrayList<>();
         // 获取所有并发任务的运行结果
         for (Future<String> f : futureList) {
             try {
                 String stat = f.get();
                 if (null != stat) {
                     stats.add(stat);
                 }
             } catch (InterruptedException e) {
                 System.out.println("interrupted exception with reason:" + e.getLocalizedMessage());
             } catch (ExecutionException e) {
                 System.out.println("execute exception with reason:" + e.getLocalizedMessage());
             }
         }

         System.out.println("本周任务进度统计结果:");
         stats.stream().forEach(System.out::println);

     }

 }
