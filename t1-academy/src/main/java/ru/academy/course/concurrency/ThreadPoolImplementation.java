package ru.academy.course.concurrency;

import java.util.LinkedList;

public class ThreadPoolImplementation {

    private final LinkedList<Runnable> taskQueue;
    private final Thread[] threads;
    private volatile Boolean isTerminated;

    public ThreadPoolImplementation(int capacity) {
        this.taskQueue = new LinkedList<>();
        this.threads = new Executor[capacity];
        this.isTerminated = Boolean.FALSE;

        for (int i = 0; i < capacity; i++) {
            Thread thread = new Executor("Executor-" + i);
            thread.start();
            threads[i] = thread;
        }
    }

    public void execute(Runnable task) {
        if (isTerminated) {
            throw new IllegalArgumentException("Thread pool is terminated");
        }
        synchronized (taskQueue) {
            taskQueue.offer(task);
            taskQueue.notifyAll();
        }
    }

    public void shutdown() {
        this.isTerminated = Boolean.TRUE;
    }

    public void awaitTermination() throws InterruptedException {
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }

    private class Executor extends Thread {

        public Executor(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " STARTED");
            while (!isTerminated) {
                try {
                    Runnable task;
                    synchronized (taskQueue) {
                        task = taskQueue.poll();
                        if (task == null) {
                            taskQueue.wait();
                        }
                    }
                    if (task != null) {
                        System.out.println(this.getName() + " RUNS TASK");
                        task.run();
                        System.out.println(this.getName() + " COMPLETE TASK");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(this.getName() + " CLOSED");
        }
    }
}
