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

  ============================================================================

  1. Difference Between Multithreading and Multitasking
Feature	Multithreading	Multitasking
Definition	Running multiple threads within a single process.	Running multiple processes simultaneously.
Granularity	Threads (smallest unit of execution within a program).	Processes (independent programs).
Memory Usage	Shares memory (lightweight).	Each process has separate memory (heavy).
Execution	Multiple threads of the same program run in parallel.	Multiple applications run in parallel.
Example	A web browser using multiple threads for loading pages, playing videos, and downloading files.	Running Chrome, Spotify, and MS Word at the same time.
2. Explanation of Methods in Multithreading
1. yield() Method
Purpose: It pauses the currently executing thread and allows other threads of the same priority to execute. However, the same thread may continue if no other thread is ready to run.
Example:
java
Copy
Edit
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " is running");
            Thread.yield(); // Giving other threads a chance
        }
    }
}

public class YieldExample {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        t2.start();
    }
}
Output (may vary):
arduino
Copy
Edit
Thread-0 is running
Thread-1 is running
Thread-0 is running
Thread-1 is running
Use Case: When a thread should give other threads a chance to execute first.
2. interrupt() Method
Purpose: It is used to stop a thread that is sleeping or waiting.
Example:
java
Copy
Edit
class MyThread extends Thread {
    public void run() {
        try {
            System.out.println("Thread is sleeping...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted!");
        }
    }
}

public class InterruptExample {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start();
        t1.interrupt(); // Interrupting the sleeping thread
    }
}
Output:
mathematica
Copy
Edit
Thread is sleeping...
Thread interrupted!
Use Case: When we want to stop a thread that is sleeping or waiting.
3. join() Method
Purpose: It makes one thread wait for another thread to complete execution before continuing.
Example:
java
Copy
Edit
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class JoinExample {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        try {
            t1.join(); // Main thread will wait for t1 to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
Output:
mathematica
Copy
Edit
Thread-0 running...
Thread-0 running...
Thread-0 running...
Thread-0 running...
Thread-0 running...
Thread-1 running...
Thread-1 running...
Thread-1 running...
Thread-1 running...
Thread-1 running...
Use Case: When we need one thread to finish execution before another starts.
4. sleep() Method
Purpose: It pauses the current thread for a specific period.
Example:
java
Copy
Edit
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " running...");
            try {
                Thread.sleep(2000); // Sleep for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class SleepExample {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start();
    }
}
Output:
arduino
Copy
Edit
Thread-0 running...
(2 seconds pause)
Thread-0 running...
(2 seconds pause)
Thread-0 running...
Use Case: When we need to pause a thread for a certain time.
Summary Table
Method	Purpose	Example Scenario
yield()	Gives other threads a chance to execute first.	When a low-priority task should wait for others.
interrupt()	Stops a sleeping/waiting thread.	Stopping a thread waiting for user input.
join()	Waits for another thread to complete before continuing.	Waiting for a background task to finish before proceeding.
sleep()	Pauses the current thread for a given time.	Adding a delay in execution (e.g., retrying a failed network request).
Would you like more examples or a detailed explanation of any topic? 🚀



  
