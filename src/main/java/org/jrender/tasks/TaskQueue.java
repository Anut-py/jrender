package org.jrender.tasks;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskQueue {
    private final Queue<Runnable> tasks;

    private final AtomicInteger incompleteTasks;

    public TaskQueue(int numThreads) {
        tasks = new LinkedList<>();
        incompleteTasks = new AtomicInteger();
        for (int i = 0; i < numThreads; i ++) {
            Thread t = new Thread(() -> {
                while (true) {
                    Runnable task;
                    synchronized (tasks) {
                        task = tasks.poll();
                    }
                    if (task != null) {
                        task.run();
                        if (incompleteTasks.decrementAndGet() == 0) {
                            synchronized (this) {
                                notifyAll();
                            }
                        }
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        }
    }

    public void submitTask(Runnable task) {
        incompleteTasks.incrementAndGet();
        synchronized (tasks) {
            tasks.offer(task);
        }
    }

    public void awaitCompletion() throws InterruptedException {
        synchronized (this) {
            while (incompleteTasks.get() != 0) {
                wait();
            }
        }
    }
}
