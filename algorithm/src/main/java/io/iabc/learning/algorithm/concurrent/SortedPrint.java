package io.iabc.learning.algorithm.concurrent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * (JDK1.8)线程A打印a，线程B打印l，线程C打印i，线程D打印序号，四个线程交替打印，各打印102次，ali1ali2ali3……ali102
 *
 * @author shuchen
 * @created 2021-12-19 8:47 PM
 * @description:
 */
public class SortedPrint {

    public static void main(String[] args) {
        List<PrintTask> printTasks = new ArrayList<PrintTask>() {{
            add(new PrintTask("A", "a", 0));
            add(new PrintTask("B", "l", 1));
            add(new PrintTask("C", "i", 2));
            add(new PrintTask("D", "", 3));
        }};
        new Solution().solve(printTasks, 102);
    }

    static class PrintTask implements Serializable {
        private String name;
        private String content;
        private int seqNum;

        public PrintTask(String name, String content, int seqNum) {
            this.name = name;
            this.content = content;
            this.seqNum = seqNum;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public int getSeqNum() {
            return seqNum;
        }
    }

    static class Solution {
        public void solve(List<PrintTask> tasks, int totalPhase) {

            Lock lock = new ReentrantLock();
            ArrayList<Condition> conditions = new ArrayList<Condition>();
            for (PrintTask task : tasks) {
                conditions.add(lock.newCondition());
            }

            LongAdder globalCounter = new LongAdder();
            int taskSize = tasks.size();
            for (int i = 0; i < taskSize - 1; i++) {
                new ContentThread(tasks.get(i).getName(), tasks.get(i).getContent(), tasks.get(i).getSeqNum(),
                        taskSize, totalPhase, lock, conditions.get(i), conditions.get((i + 1) % taskSize))
                        .setGlobalCounter(globalCounter).start();
            }

            new CountThread(tasks.get(taskSize - 1).getName(), tasks.get(taskSize - 1).getContent(), tasks.get(taskSize - 1).getSeqNum(),
                    taskSize, totalPhase, lock, conditions.get(taskSize - 1), conditions.get(0))
                    .setGlobalCounter(globalCounter).start();

        }

    }

    static class ContentThread extends Thread implements Printer {
        protected final int totalThread;
        protected final int seqNum;
        protected final int totalPhase;
        protected final String content;

        private final Lock lock;
        private final Condition currentCondition;
        private final Condition nextCondition;
        private LongAdder globalCounter;

        public LongAdder getGlobalCounter() {
            return globalCounter;
        }

        public ContentThread setGlobalCounter(LongAdder globalCounter) {
            this.globalCounter = globalCounter;
            return this;
        }

        public ContentThread(String name, String content, int seqNum, int totalThread, int totalPhase, Lock lock, Condition current, Condition next) {
            super(name);
            this.content = content;
            this.seqNum = seqNum;
            this.totalThread = totalThread;
            this.totalPhase = totalPhase;
            this.lock = lock;
            this.currentCondition = current;
            this.nextCondition = next;
        }

        @Override
        public String getPrintContent() {
            return this.content;
        }

        @Override
        public void doPrintContent() {
            System.out.print(this.getPrintContent());
        }

        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = 0; i < this.totalPhase; i++) {
                    while (this.globalCounter.intValue() % this.totalThread != this.seqNum) {
                        this.currentCondition.await();
                    }
                    this.doPrintContent();
                    this.globalCounter.increment();
                    this.nextCondition.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class CountThread extends ContentThread {

        public CountThread(String name, String content, int seqNum, int totalThread, int totalPhase, Lock lock, Condition current, Condition next) {
            super(name, content, seqNum, totalThread, totalPhase, lock, current, next);
        }

        @Override
        public String getPrintContent() {
            return String.valueOf(super.getGlobalCounter().intValue() / super.totalThread + 1);
        }
    }

    interface Printer {

        /**
         * 获取打印内容
         *
         * @return
         */
        String getPrintContent();

        /**
         * 执行内容打印
         */
        void doPrintContent();
    }
}

