import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class MyLock{
    // 内部类实现AQS的同步器
    public MyLock(){};
    // AQS默认是非公平锁，故这里只需要控制state
    private static class MyLockSync extends AbstractQueuedSynchronizer {

        // 判断当前锁是否被占用
        @Override
        protected boolean isHeldExclusively() {
            // 锁被占用时返回 true，否则返回 false
            return getState() != 0;
        }

        // 获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            // 独占锁。只允许一个线程获取锁，并且如果当前线程已经持有锁（可重入），可以再次获取
            // 第一个线程进来，可以获取锁
            // 第二个线程进来，无法获取锁，返回false
            Thread thread = Thread.currentThread();
            // 判断是否为第一个线程进来
            int state = getState();
            if (state == 0) {
                if (compareAndSetState(0, arg)) {// 如果当前状态值等于预期值，则以原子方式将同步状态设置为给定的更新值
                    // 设置当前线程
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            } else if(getExclusiveOwnerThread() == thread) { // 允许重入锁,当前线程和当前保存的线程是同一个线程
                setState(state + 1);
                return true;
            }
            return false;
        }

        // 释放锁
        @Override
        public boolean tryRelease(int arg) {
            // 只有当前线程持有锁时才能释放
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                throw new IllegalMonitorStateException();
            }

            // 重入锁情况下，每次释放会减去 arg，直到锁释放
            int state = getState() - arg;
            boolean freeFlag = false;
            if (state == 0) {
                freeFlag = true;
                setExclusiveOwnerThread(null);
            }
            setState(state);
            return freeFlag;
        }


        protected Condition newCondition() {
            return new ConditionObject();
        }
    }

    // 使用 AQS 实现同步器
    private final MyLockSync sync = new MyLockSync();

    // 锁的获取
    public void lock() {
        sync.acquire(1);
    }

    // 锁的释放
    public void unlock() {
        sync.release(1);
    }

    // 锁的可重入特性检查
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }
}
