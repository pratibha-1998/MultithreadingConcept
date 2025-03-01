Complete Notes on Threads and Multithreading in Java

1. Introduction to Threads and Multithreading

Thread: A lightweight sub-process, the smallest unit of execution in Java.

Multithreading: Running multiple threads simultaneously within a process.

Benefits:

Efficient CPU utilization

Faster execution of independent tasks

Better user experience (e.g., UI responsiveness)

Single-threaded vs Multi-threaded Applications:

Single-threaded: Only one execution flow.

Multi-threaded: Multiple execution flows
  =============================================
  2. Creating Threads in Java

Method 1: Extending Thread Class

class MyThread extends Thread {
    public void run() {
        System.out.println("Thread is running...");
    }
    public static void main(String args[]) {
        MyThread t1 = new MyThread();
        t1.start();
    }
}

Cons: Cannot extend another class as Java doesn’t support multiple inheritance.
  =================================================
Method 2: Implementing Runnable Interface

class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Thread is running...");
    }
    public static void main(String args[]) {
        Thread t1 = new Thread(new MyRunnable());
        t1.start();
    }
}

Pros: More flexible; allows extending another class.

Method 3: Using Lambda Expression (Java 8+)

public class LambdaThread {
    public static void main(String[] args) {
        Thread t = new Thread(() -> System.out.println("Thread is running..."));
        t.start();
    }
}

Pros: Concise and readable.

3. Thread Lifecycle

New – Thread is created but not started.

Runnable – Thread is ready to run.

Running – Thread is executing.

Blocked – Waiting for a resource.

Waiting – Waiting indefinitely for another thread.

Timed Waiting – Waiting for a specific time.

Terminated – Thread has completed execution.

  ======================================================

  4. Thread Methods and Their Differences

Method

Description

Example

start()

Starts a new thread

t.start();

run()

Runs in the same thread if called directly

t.run();

sleep(ms)

Pauses thread for given time

Thread.sleep(1000);

join()

Waits for a thread to finish

t1.join();

yield()

Hints scheduler to allow other threads

Thread.yield();

setDaemon(true)

Marks a thread as daemon

t.setDaemon(true);

wait()

Thread waits for a signal

obj.wait();

notify()

Wakes up a waiting thread

obj.notify();

notifyAll()

Wakes up all waiting threads

obj.notifyAll();

5. Thread Synchronization

Race Condition: Multiple threads accessing shared resources inconsistently.

Synchronized Block: Ensures only one thread can access critical code.

class SharedResource {
    synchronized void printNumbers(int n) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(n * i);
            try { Thread.sleep(500); } catch (InterruptedException e) { }
        }
    }
}

Using ReentrantLock:

ReentrantLock lock = new ReentrantLock();
lock.lock();
try { /* critical section */ }
finally { lock.unlock(); }

========================================================

  4. Thread Methods and Their Differences

Method

Description

Example

start()

Starts a new thread

t.start();

run()

Runs in the same thread if called directly

t.run();

sleep(ms)

Pauses thread for given time

Thread.sleep(1000);

join()

Waits for a thread to finish

t1.join();

yield()

Hints scheduler to allow other threads

Thread.yield();

setDaemon(true)

Marks a thread as daemon

t.setDaemon(true);

wait()

Thread waits for a signal

obj.wait();

notify()

Wakes up a waiting thread

obj.notify();

notifyAll()

Wakes up all waiting threads

obj.notifyAll();

5. Thread Synchronization

Race Condition: Multiple threads accessing shared resources inconsistently.

Synchronized Block: Ensures only one thread can access critical code.

class SharedResource {
    synchronized void printNumbers(int n) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(n * i);
            try { Thread.sleep(500); } catch (InterruptedException e) { }
        }
    }
}

Using ReentrantLock:

ReentrantLock lock = new ReentrantLock();
lock.lock();
try { /* critical section */ }
finally { lock.unlock(); }
=============================================================
  6. Inter-Thread Communication

Methods: wait(), notify(), notifyAll()

Example:

class Shared {
    synchronized void print() {
        try {
            System.out.println("Waiting...");
            wait();
        } catch (InterruptedException e) {}
        System.out.println("Notified!");
    }
    synchronized void notifyThread() {
        System.out.println("Notifying...");
        notify();
    }
}

7. Deadlocks and How to Avoid Them

Deadlock: When two threads wait indefinitely for each other’s locks.

Avoidance:

Acquire locks in a fixed order.

Use tryLock() in ReentrantLock.

8. Java Concurrency Framework

ExecutorService (Thread Pool Management)

ExecutorService executor = Executors.newFixedThreadPool(2);
Future<Integer> future = executor.submit(() -> 10 + 20);
System.out.println(future.get());
executor.shutdown();

ForkJoinPool (Parallel Execution)

ThreadLocal (Per-thread Variables)

ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);
System.out.println(threadLocal.get());

CompletableFuture (Asynchronous Processing)

CompletableFuture.supplyAsync(() -> "Hello").thenAccept(System.out::println);

9. Real-Life Example

Banking System (Using Synchronization)

class BankAccount {
    private int balance = 1000;
    synchronized void withdraw(int amount) {
        if (balance < amount) {
            System.out.println("Insufficient balance. Waiting for deposit...");
            try { wait(); } catch (InterruptedException e) { }
        }
        balance -= amount;
        System.out.println("Withdrawal successful. Remaining balance: " + balance);
    }
    synchronized void deposit(int amount) {
        balance += amount;
        System.out.println("Deposit successful. New balance: " + balance);
        notify();
    }
}

public class BankSystem {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        new Thread(() -> account.withdraw(1200)).start();
        new Thread(() -> account.deposit(500)).start();
    }
}

10. Best Practices

Minimize shared resource usage.

Use thread-safe collections (ConcurrentHashMap, CopyOnWriteArrayList).

Prefer thread pools over manually creating threads.

Handle exceptions properly in threads.

Profile and debug multithreading issues using monitoring tools.



  
