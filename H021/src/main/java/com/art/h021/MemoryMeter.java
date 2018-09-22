package com.art.h021;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class MemoryMeter {
    private Object[] array;
    private int size;

    public MemoryMeter() {
        size = 10 * 1000 * 1000;
    }

    public MemoryMeter(int size) {
        this.size = size;
        array = new Object[size];
    }

    public void prepare() throws InterruptedException {
        long memory = getMemoryChanges(() -> {
            array = new Object[size];
        });

        System.out.println("Reference size: " + memory / size);
    }

    public void mesure(Supplier<Object> supplier, String name) throws InterruptedException {
        long memory = getMemoryChanges(() -> {
            int i = 0;
            while (i < size) {
                array[i] = supplier.get();
                i++;
            }
        });

        System.out.println(name + " size: " + Math.round((double) memory /size));
    }

    public void clean() throws InterruptedException {
        array = new Object[size];
        System.gc();
        TimeUnit.SECONDS.sleep(1);
    }

    private long getMemoryChanges(Runnable runnable) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        long start = runtime.totalMemory() - runtime.freeMemory();
        runnable.run();
        System.gc();
        long end = runtime.totalMemory() - runtime.freeMemory();
        return end - start;
    }
}
