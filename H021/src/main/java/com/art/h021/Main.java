package com.art.h021;

import java.lang.management.ManagementFactory;

/**
 * VM options -Xmx512m -Xms512m
 * <p>
 * Runtime runtime = Runtime.getRuntime();
 * long mem = runtime.totalMemory() - runtime.freeMemory();
 * <p>
 * System.gc()
 * <p>
 * jconsole, connect to pid
 */
@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) throws InterruptedException {
       System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        MemoryMeter memoryMeter = new MemoryMeter();
        memoryMeter.prepare();

        memoryMeter.mesure(Object::new, "Object");
        memoryMeter.clean();

        memoryMeter.mesure(String::new, "String with pool");
        memoryMeter.clean();

        memoryMeter.mesure(() -> new String(new char[0]), "String");
        memoryMeter.clean();

        memoryMeter.mesure(() -> new MemoryMeter(10), "Benchmark(10)");
        memoryMeter.clean();

        memoryMeter.mesure(() -> new MyClass(), "MyClass");
        memoryMeter.clean();
    }
}
