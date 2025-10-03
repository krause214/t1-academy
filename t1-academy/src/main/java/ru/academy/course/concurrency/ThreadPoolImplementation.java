package ru.academy.course.concurrency;

import java.util.LinkedList;

public class ThreadPoolImplementation {

    private final LinkedList<Runnable> taskQueue;
    private final Object monitor = new Object();
    private final Object terminationMonitor = new Object();
    private Thread[] threadPool;
    private Boolean isTerminated;
    private int terminationCounter;

    public ThreadPoolImplementation(int capacity) {
        this.taskQueue = new LinkedList<>();
        this.threadPool = new Executor[capacity];
        this.isTerminated = Boolean.FALSE;
        this.terminationCounter = 4;

        for (int i = 0; i < capacity; i++) {
            Thread thread = new Executor("Executor-" + i);
            thread.start();
            threadPool[i] = thread;
        }
    }

    public void execute(Runnable task) {
        if (isTerminated) {
            throw new IllegalArgumentException("Thread pool is terminated");
        }
        synchronized (monitor) {
            taskQueue.offer(task);
            monitor.notifyAll();
        }
    }

    public void shutdown() {
        this.isTerminated = Boolean.TRUE;
    }

    public void awaitTermination() throws InterruptedException {
        Thread awaitThread = new Thread(
                () -> {
                    synchronized (terminationMonitor) {
                        while (this.terminationCounter > 0) {
                            try {
                                terminationMonitor.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    System.out.println("AWAITING ENDS");
                }
        );

        awaitThread.start();
        awaitThread.join();
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
                    }
                    if (task != null) {
                        System.out.println(this.getName() + " RUNS TASK");
                        task.run();
                        System.out.println(this.getName() + " COMPLETE TASK");
                    } else {
                        synchronized (monitor) {
                            monitor.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            synchronized (terminationMonitor) {
                terminationCounter--;
                terminationMonitor.notify();
            }
            System.out.println(this.getName() + " CLOSED");
        }
    }
}
