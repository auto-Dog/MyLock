class multiProcessDemo implements Runnable {    // 采用runnable创建线程
    private Thread t;
    private String threadName;
    private static final MyLock lock = new MyLock();
    multiProcessDemo( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void run() { // 运行状态
        System.out.println("Running " +  threadName );
        lock.lock();    // 上锁
        try {   // 创建线程需要捕获异常处理
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // 让线程睡眠一会
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }finally{
            lock.unlock();
        }
        System.out.println("Thread " +  threadName + " exiting.");  // 自然结束，没有设置中断或停止方法
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);  // 新建状态
            t.start ();     // 就绪状态
        }
    }


    public static void main(String args[]) {
        multiProcessDemo R1 = new multiProcessDemo( "Thread-1");
        R1.start();

        multiProcessDemo R2 = new multiProcessDemo( "Thread-2");
        R2.start();
    }
}
