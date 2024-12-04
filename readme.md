基于 AQS 实现一个自定义锁
---
_需求背景：请实现一个名为 MyLock 的锁，要求其行为类似于 ReentrantLock。使用 Java 的 AbstractQueuedSynchronizer (AQS) 作为核心机制实现锁的获取与释放。这个锁应该支持以下功能：_

1. 独占锁：实现一个独占锁，只允许一个线程获取锁，其他线程需要阻塞等待，直到锁被释放。

2. 可重入性：支持可重入，即同一个线程在持有锁的情况下可以再次获取锁，而不会发生死锁。

3. 锁的获取与释放：实现标准的 lock() 和 unlock() 方法，使用 AQS 实现同步状态的管理。

4. 阻塞与公平性：锁应该是非公平锁，多个线程争抢锁时，不能保证按照请求顺序获得锁