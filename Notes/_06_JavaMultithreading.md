# Java Multithreading — Complete Reference Guide

---

## Table of Contents
1. [Thread Fundamentals](#1-thread-fundamentals)
2. [Creating Threads — 4 Ways](#2-creating-threads--4-ways)
3. [Thread Lifecycle](#3-thread-lifecycle)
4. [Synchronization](#4-synchronization)
5. [`volatile` Keyword](#5-volatile-keyword)
6. [`wait()`, `notify()`, `notifyAll()`](#6-wait-notify-notifyall)
7. [ExecutorService & Thread Pools](#7-executorservice--thread-pools)
8. [`Callable` and `Future`](#8-callable-and-future)
9. [Concurrency Utilities](#9-concurrency-utilities)
10. [Thread-Safe Collections](#10-thread-safe-collections)
11. [Common Problems — Deadlock, Livelock, Race Condition](#11-common-problems--deadlock-livelock-race-condition)
12. [Multithreading in Automation](#12-multithreading-in-automation)
13. [Interview Q&A](#13-interview-qa)

---

## 1. Thread Fundamentals

A **thread** is the smallest unit of execution within a process. A Java application starts with one thread (`main`). Additional threads can be created to run code concurrently.

```java
// Current thread info
Thread t = Thread.currentThread();
t.getName();     // "main"
t.getId();       // thread ID
t.getPriority(); // 1-10, default 5
t.getState();    // Thread.State enum
t.isDaemon();    // false for user threads

// Thread priorities
Thread.MIN_PRIORITY  = 1
Thread.NORM_PRIORITY = 5   // default
Thread.MAX_PRIORITY  = 10
```

---

## 2. Creating Threads — 4 Ways

### Way 1: Extend `Thread` class

```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}

MyThread t = new MyThread();
t.start();   // calls run() in a new thread
// t.run(); ← this runs in the CURRENT thread — NOT multithreading!
```

### Way 2: Implement `Runnable` interface (preferred)

```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Running: " + Thread.currentThread().getName());
    }
}

Thread t = new Thread(new MyRunnable());
t.start();

// Lambda version (preferred)
Thread t2 = new Thread(() -> System.out.println("Lambda thread"));
t2.start();
```

### Way 3: Implement `Callable<V>` (returns result, throws exception)

```java
Callable<String> task = () -> {
    Thread.sleep(1000);
    return "Result from " + Thread.currentThread().getName();
};

ExecutorService executor = Executors.newSingleThreadExecutor();
Future<String> future = executor.submit(task);
String result = future.get();   // blocks until done
System.out.println(result);
executor.shutdown();
```

### Way 4: Using `ExecutorService` (recommended for production)

```java
ExecutorService pool = Executors.newFixedThreadPool(4);
pool.execute(() -> System.out.println("Task 1"));
pool.execute(() -> System.out.println("Task 2"));
pool.shutdown();  // no new tasks accepted, existing finish
pool.awaitTermination(10, TimeUnit.SECONDS);
```

### `start()` vs `run()`

```java
Thread t = new Thread(() -> System.out.println("thread: " + Thread.currentThread().getName()));
t.run();    // Runs in CURRENT thread — no new thread created. Prints "thread: main"
t.start();  // Creates new thread. Prints "thread: Thread-0"
```

---

## 3. Thread Lifecycle

```
NEW → start() → RUNNABLE → (scheduler picks) → RUNNING
                    ↑                               ↓
              BLOCKED/WAITING ← wait()/sleep()/join()/lock
                                                    ↓
                                             TERMINATED (run() ends)
```

| State | When |
|-------|------|
| `NEW` | Thread created, `start()` not called |
| `RUNNABLE` | `start()` called, ready to run or running |
| `BLOCKED` | Waiting for a monitor lock |
| `WAITING` | Waiting indefinitely (`wait()`, `join()`) |
| `TIMED_WAITING` | Waiting for a time (`sleep()`, `wait(n)`, `join(n)`) |
| `TERMINATED` | `run()` completed or exception thrown |

```java
// Thread sleep
Thread.sleep(2000);  // pause current thread for 2 seconds (throws InterruptedException)

// Thread join — wait for another thread to finish
Thread t1 = new Thread(() -> { /* work */ });
t1.start();
t1.join();  // main thread waits until t1 finishes

// Thread join with timeout
t1.join(5000);  // wait at most 5 seconds

// Interrupt a thread
t1.interrupt();   // sets interrupted flag
// Inside t1's run(), check:
if (Thread.currentThread().isInterrupted()) { return; }
// Or handle InterruptedException from sleep/wait
```

---

## 4. Synchronization

**Race condition**: Two threads read/modify the same data simultaneously → unpredictable results.

```java
// Without sync — race condition
class Counter {
    private int count = 0;
    public void increment() { count++; }  // read-modify-write — NOT atomic!
}

Counter c = new Counter();
// Thread 1 reads count = 5
// Thread 2 reads count = 5
// Thread 1 writes count = 6
// Thread 2 writes count = 6  ← lost update! Should be 7
```

### `synchronized` keyword

**Synchronized method** — locks on `this` (the instance):
```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;  // only one thread at a time
    }

    public synchronized int getCount() {
        return count;
    }
}
```

**Synchronized block** — more granular, locks on a specific object:
```java
class BrowserPool {
    private final Object lock = new Object();
    private List<WebDriver> drivers = new ArrayList<>();

    public void addDriver(WebDriver driver) {
        synchronized (lock) {
            drivers.add(driver);
        }
        // Other code here runs without lock
    }
}
```

**Static synchronized** — locks on the Class object (shared across all instances):
```java
public static synchronized void staticMethod() { ... }
// Equivalent to: synchronized(Counter.class) { ... }
```

### `ReentrantLock` — More Control Than `synchronized`

```java
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();  // ALWAYS unlock in finally!
        }
    }

    public boolean tryIncrement() {
        if (lock.tryLock()) {  // non-blocking — returns false if lock unavailable
            try {
                count++;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }
}
```

| `synchronized` | `ReentrantLock` |
|---------------|----------------|
| Built-in keyword | External class |
| Auto-releases on block exit | Must manually unlock (always use try-finally) |
| No timeout | `tryLock(timeout)` available |
| No fairness | `new ReentrantLock(true)` = fair |
| `wait()`/`notify()` | `Condition.await()`/`signal()` |

---

## 5. `volatile` Keyword

`volatile` ensures that reads/writes to a variable are always done from/to **main memory**, not from thread's local cache.

```java
class StatusMonitor {
    private volatile boolean stopRequested = false;   // visible to all threads

    public void requestStop() {
        stopRequested = true;   // written to main memory immediately
    }

    public void run() {
        while (!stopRequested) {  // reads from main memory — sees the update
            // do work
        }
        System.out.println("Stopped");
    }
}
```

### `volatile` vs `synchronized`

| | `volatile` | `synchronized` |
|--|-----------|---------------|
| Atomicity | ❌ Only for reads/writes of single variable | ✅ Entire block |
| Visibility | ✅ | ✅ |
| Mutual exclusion | ❌ | ✅ |
| Performance | Faster | Slower (blocking) |
| Use case | Simple flag/state | Complex operations, compound actions |

```java
// volatile is NOT sufficient for compound actions
private volatile int count = 0;
count++;  // ← still NOT atomic (3 steps: read, increment, write)
// Use AtomicInteger instead
```

---

## 6. `wait()`, `notify()`, `notifyAll()`

Used for inter-thread communication. Must be called inside a `synchronized` block.

```java
class ProducerConsumer {
    private final Object lock = new Object();
    private String data = null;

    // Producer thread
    public void produce(String item) throws InterruptedException {
        synchronized (lock) {
            while (data != null) {
                lock.wait();   // release lock + wait until notified
            }
            data = item;
            lock.notifyAll();  // wake up waiting consumer
        }
    }

    // Consumer thread
    public String consume() throws InterruptedException {
        synchronized (lock) {
            while (data == null) {
                lock.wait();   // release lock + wait until notified
            }
            String result = data;
            data = null;
            lock.notifyAll();  // wake up waiting producer
            return result;
        }
    }
}
```

| Method | Description |
|--------|-------------|
| `wait()` | Release lock, enter WAITING state |
| `wait(long ms)` | Release lock, enter TIMED_WAITING |
| `notify()` | Wake up ONE waiting thread (arbitrary which one) |
| `notifyAll()` | Wake up ALL waiting threads (prefer over `notify()`) |

> Always use `wait()` in a `while` loop, not `if` — guard against spurious wakeups.

---

## 7. ExecutorService & Thread Pools

Creating threads is expensive. Thread pools reuse threads for multiple tasks.

```java
// Types of executors
ExecutorService fixed    = Executors.newFixedThreadPool(4);       // fixed n threads
ExecutorService single   = Executors.newSingleThreadExecutor();   // 1 thread, FIFO
ExecutorService cached   = Executors.newCachedThreadPool();       // grow as needed, idle timeout 60s
ExecutorService scheduled= Executors.newScheduledThreadPool(2);   // for scheduled tasks

// Submit tasks
fixed.execute(runnable);          // fire and forget
Future<T> f = fixed.submit(callable);  // get result later

// Proper shutdown
fixed.shutdown();                  // no new tasks, existing finish
fixed.awaitTermination(30, TimeUnit.SECONDS);
fixed.shutdownNow();               // interrupt running tasks
```

### `ScheduledExecutorService`

```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

// Run once after delay
scheduler.schedule(() -> System.out.println("once"), 5, TimeUnit.SECONDS);

// Run repeatedly with fixed rate
scheduler.scheduleAtFixedRate(
    () -> System.out.println("heartbeat"),
    0,    // initial delay
    10,   // period
    TimeUnit.SECONDS
);

// Run repeatedly with fixed delay (after each completion)
scheduler.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);
```

---

## 8. `Callable` and `Future`

```java
// Callable — like Runnable but returns a value and can throw
Callable<Integer> sumTask = () -> {
    int sum = 0;
    for (int i = 1; i <= 100; i++) sum += i;
    return sum;
};

ExecutorService executor = Executors.newSingleThreadExecutor();
Future<Integer> future = executor.submit(sumTask);

// Future methods
future.isDone();           // check if complete
future.isCancelled();      // check if cancelled
future.cancel(true);       // cancel (interrupt if running)
int result = future.get(); // BLOCK until result available
int r2 = future.get(5, TimeUnit.SECONDS); // block with timeout

executor.shutdown();
```

### `CompletableFuture` (Java 8+) — Non-Blocking Async

```java
// Run async, get result
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
    Thread.sleep(1000);
    return "Result";
});

// Chain (non-blocking)
cf.thenApply(result -> result.toUpperCase())
  .thenAccept(result -> System.out.println("Final: " + result))
  .thenRun(() -> System.out.println("All done"));

// Combine two futures
CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "World");
cf1.thenCombine(cf2, (s1, s2) -> s1 + " " + s2)
   .thenAccept(System.out::println);  // "Hello World"

// Handle exceptions
cf.exceptionally(ex -> "Default on error: " + ex.getMessage());
cf.handle((result, ex) -> ex != null ? "Error" : result.toUpperCase());

// Wait for all
CompletableFuture.allOf(cf1, cf2).join();  // wait for both

// Wait for first
CompletableFuture.anyOf(cf1, cf2).thenAccept(System.out::println);
```

---

## 9. Concurrency Utilities

### `CountDownLatch` — Wait for N events

```java
// Wait for all 3 services to start before running tests
CountDownLatch latch = new CountDownLatch(3);

executor.execute(() -> { startServiceA(); latch.countDown(); });
executor.execute(() -> { startServiceB(); latch.countDown(); });
executor.execute(() -> { startServiceC(); latch.countDown(); });

latch.await();            // block until count reaches 0
latch.await(10, TimeUnit.SECONDS);  // with timeout
// Continue once all 3 services are up
```

### `CyclicBarrier` — Wait for all threads at a point

```java
// All threads wait at the barrier before proceeding
CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All ready!"));

Runnable task = () -> {
    // do prep work
    barrier.await();   // wait here until all 3 threads arrive
    // all start together from here
};
```

### `Semaphore` — Limit concurrent access

```java
// Max 3 parallel browser sessions
Semaphore semaphore = new Semaphore(3);

Runnable testTask = () -> {
    semaphore.acquire();   // wait for permit
    try {
        runTest();
    } finally {
        semaphore.release();  // always release
    }
};
```

### `AtomicInteger` / `AtomicLong` / `AtomicBoolean`

```java
// Thread-safe counter without synchronized
AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet();     // ++count, returns new value
count.getAndIncrement();     // count++, returns old value
count.addAndGet(5);          // count += 5, returns new value
count.compareAndSet(5, 10);  // if count==5, set to 10 (CAS operation)
count.get();                 // read current value

AtomicBoolean isRunning = new AtomicBoolean(false);
isRunning.compareAndSet(false, true);  // start only if not already running
```

---

## 10. Thread-Safe Collections

| Regular | Thread-Safe Alternative | Notes |
|---------|------------------------|-------|
| `ArrayList` | `CopyOnWriteArrayList` | Copies array on every write — good for read-heavy |
| `HashMap` | `ConcurrentHashMap` | Segment locking — better than Hashtable |
| `HashSet` | `CopyOnWriteArraySet` | Backed by `CopyOnWriteArrayList` |
| `LinkedList` | `ConcurrentLinkedQueue` | Lock-free, FIFO |
| `PriorityQueue` | `PriorityBlockingQueue` | Blocking ops, unbounded |
| — | `BlockingQueue` (interface) | `LinkedBlockingQueue`, `ArrayBlockingQueue` |
| `TreeMap` | `ConcurrentSkipListMap` | Sorted, concurrent |

```java
// ConcurrentHashMap — preferred over synchronized HashMap
ConcurrentHashMap<String, WebDriver> drivers = new ConcurrentHashMap<>();
drivers.put("chrome", new ChromeDriver());
drivers.putIfAbsent("firefox", new FirefoxDriver());
drivers.computeIfAbsent("edge", k -> new EdgeDriver());

// BlockingQueue — producer-consumer
BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
// Producer:
queue.put("task1");    // blocks if queue full
queue.offer("task2");  // non-blocking, returns false if full

// Consumer:
String task = queue.take();    // blocks if queue empty
String t = queue.poll();       // non-blocking, returns null if empty
String t2 = queue.poll(5, TimeUnit.SECONDS);  // with timeout

// CopyOnWriteArrayList — safe iteration without ConcurrentModificationException
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
list.add("chrome");
for (String s : list) {   // iterates over snapshot — safe
    list.add("firefox");  // won't affect current iteration
}
```

---

## 11. Common Problems — Deadlock, Livelock, Race Condition

### Deadlock

Two threads each hold a lock the other needs — both wait forever.

```java
// Deadlock scenario
Object lockA = new Object();
Object lockB = new Object();

Thread t1 = new Thread(() -> {
    synchronized (lockA) {
        Thread.sleep(100);
        synchronized (lockB) { /* work */ }   // waits for lockB
    }
});

Thread t2 = new Thread(() -> {
    synchronized (lockB) {
        Thread.sleep(100);
        synchronized (lockA) { /* work */ }   // waits for lockA
    }
});
// t1 holds lockA, wants lockB
// t2 holds lockB, wants lockA → DEADLOCK

// Prevention: always acquire locks in the same order
// Or: use tryLock() with timeout
```

### Livelock

Threads are active but keep reacting to each other without making progress.

```java
// Livelock: two threads keep backing off
// Thread A: "after you" → Thread B: "no, after you" → loops forever
```

### Race Condition

```java
// Classic race: check-then-act without atomicity
if (!map.containsKey(key)) {        // Thread A checks — not present
    // Thread B inserts key here
    map.put(key, value);             // Thread A inserts duplicate!
}

// Fix: use putIfAbsent()
map.putIfAbsent(key, value);         // atomic in ConcurrentHashMap
// Or synchronize the block
```

### Starvation

A thread is perpetually denied access to a resource because other threads always get priority.

---

## 12. Multithreading in Automation

### Parallel Test Execution with ThreadLocal

```java
// Each thread (test method) gets its OWN WebDriver instance
public class BrowserDriverFactory {
    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public WebDriver createDriver() {
        WebDriver driver = new ChromeDriver();
        driverThreadLocal.set(driver);   // set for current thread
        return driver;
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();  // get for current thread
    }

    public void removeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();  // prevent memory leak
        }
    }
}

// BaseTest — driver is effectively ThreadLocal when parallel=methods
public class BaseTest {
    public WebDriver driver;  // each test method creates its own in @BeforeMethod

    @BeforeMethod
    public void setUp() {
        driver = factory.createDriver();  // new driver per test method
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
```

### TestNG parallel in `testng.xml`

```xml
<suite name="Parallel Suite" parallel="methods" thread-count="4">
    <test name="All Tests">
        <classes>
            <class name="com.tests.LoginTest"/>
            <class name="com.tests.CartTest"/>
        </classes>
    </test>
</suite>
```

---

## 13. Interview Q&A

**Q: What is the difference between `Thread` and `Runnable`?**
> Extending `Thread` ties your class to the thread hierarchy — it can't extend any other class. Implementing `Runnable` separates the task from the threading mechanism (favours composition). `Runnable` can be passed to any `Executor`, used with lambda, or shared across threads.

**Q: What is the difference between `sleep()` and `wait()`?**
> `sleep()`: Makes current thread pause for a set time. Does NOT release any lock. Static method on `Thread`.
> `wait()`: Makes current thread wait and **releases the lock**. Must be called inside `synchronized`. Woken by `notify()`/`notifyAll()` or timeout.

**Q: What is a daemon thread?**
> A daemon thread runs in the background and dies automatically when all non-daemon (user) threads finish. JVM doesn't wait for daemon threads to complete. Used for garbage collection, background logging.
```java
Thread t = new Thread(task);
t.setDaemon(true);   // must be set before start()
t.start();
```

**Q: What is `ThreadLocal`?**
> `ThreadLocal` provides **thread-scoped variables** — each thread has its own copy. Used for driver management in parallel Selenium tests: each thread gets its own `WebDriver` without sharing.

**Q: What is the difference between `ConcurrentHashMap` and `Collections.synchronizedMap()`?**
> `Collections.synchronizedMap()` wraps a HashMap with a single lock — the entire map is locked for every operation (including reads in Java 7).
> `ConcurrentHashMap` uses **segment-level locking** (Java 7) / CAS operations (Java 8+) — allows multiple readers and concurrent writes to different segments. Far better throughput for read-heavy scenarios.

**Q: What happens when you call `start()` twice on the same Thread object?**
> `IllegalThreadStateException` — a thread can only be started once.

**Q: What is a `Future`?**
> A `Future<T>` represents the result of an asynchronous computation. You can check if it's done (`isDone()`), cancel it (`cancel()`), or block waiting for the result (`get()`). `CompletableFuture` extends this with non-blocking chaining.

**Q: What is the happens-before relationship?**
> A memory visibility guarantee: if action A "happens-before" action B, all writes A made are visible to B. Examples: writing to a `volatile` happens-before any subsequent read of that variable. `synchronized` block exit happens-before entering the same monitor.

**Q: What is `AtomicInteger` and why use it?**
> `AtomicInteger` is a wrapper around `int` that provides **lock-free** atomic operations using CAS (Compare-And-Swap) hardware instructions. It's faster than `synchronized` for simple counters. `incrementAndGet()` is atomic — read + increment + write as one operation.

**Q: How would you print even and odd numbers alternately from two threads?**
```java
Object lock = new Object();
int[] num = {1};

Thread odd = new Thread(() -> {
    while (num[0] <= 9) {
        synchronized (lock) {
            if (num[0] % 2 == 1) {
                System.out.println("ODD: " + num[0]++);
                lock.notify();
            } else {
                lock.wait();
            }
        }
    }
});

Thread even = new Thread(() -> {
    while (num[0] <= 10) {
        synchronized (lock) {
            if (num[0] % 2 == 0) {
                System.out.println("EVEN: " + num[0]++);
                lock.notify();
            } else {
                lock.wait();
            }
        }
    }
});
odd.start(); even.start();
```
