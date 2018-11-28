package concurrent.interruput;

import java.util.concurrent.TimeUnit;

/**
 * @author elto
 * @Description:
 * JAVA线程中断原理DEMO
 * ------------------
 * 中断的核心原理是，当调用线程的interrupt()方法时，会将线程的中断标志位（jvm维护）置为true, 需要各种可能阻塞的
 * 调用自己实现（在阻塞期间）主动去轮询该标志位，并抛出InterruptException向外传递中断信号, 上层调用再视情况处理该异常，
 * 直至线程顶层的的run()方法
 * 所以，JAVA的线程中断机制是"协商式"的，而不是"抢占式"的
 * 所以，中断响应的 时效性 取决于 run()方法中所有调用函数中检测中断标志位的频率
 * @Create 2018/11/28 9:30 AM
 */
public class InterruptDemo {
    public static void main(String[] args) {
        /**
         * 一个不会检测中断标志位的线程
         */
        Thread t = new MyThread(InvokMethod.NON_INTERRUPT);
        t.start();
        t.interrupt();
        System.out.println("已调用线程的interrupt方法");

        /**
         * 一个会检车中断标志位的线程
         */
        t = new MyThread(InvokMethod.INTERRUPT_BY_SLEEP);
        t.start();
        t.interrupt();
        System.out.println("已调用线程的interrupt方法");
    }

    static class MyThread extends Thread {
        InvokMethod invokMethod;
        public MyThread(InvokMethod invokMethod) {
            this.invokMethod = invokMethod;
        }

        @Override
        public void run() {
            try {
                int num = invoke(invokMethod, 2, 0);
                System.out.println("长时间任务运行结束， num=" + num);
                System.out.println("线程的中断状态:" + Thread.interrupted());
            } catch (InterruptedException e) {
                System.out.println("调用方法抛出了中断异常");
                e.printStackTrace();
            }
        }

        private int invoke(InvokMethod invokMethod, int count, int initNum)
            throws InterruptedException
        {
            int res = 0;
            switch (invokMethod){
                case INTERRUPT_BY_SLEEP:
                    res = longTimeRunningNonInterruptBySleep(count, initNum);
                    break;
                case NON_INTERRUPT:
                    res = longTimeRunningNonInterrupt(count, initNum);
                    break;
                default:break;
            }

            return res;
        }

        private static int longTimeRunningNonInterrupt  (int count, int initNum)
            throws InterruptedException {

            /**
             * 循环中没有任何位置检查中断标志位，所以永远都不会抛出InterruptedException异常
             * 这种调用一旦陷入阻塞（比如死循环），将永远都不能被中断
             */
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < Integer.MAX_VALUE; j++) {
                    initNum ++;
                }
            }

            return initNum;
        }

        private static int longTimeRunningNonInterruptBySleep  (int count, int initNum)
            throws InterruptedException {
            for (int i = 0; i < count; i++) {
                /**
                 * sleep()调用会轮询检查中断标志位，一旦检查到中断抛出异常
                 */
                TimeUnit.SECONDS.sleep(5);
            }

            return initNum;
        }

        private static int longTimeRunningNonInterruptManual  (int count, int initNum)
            throws InterruptedException {
            for (int i = 0; i < count; i++) {
                /**
                 * sleep()调用会轮询检查中断标志位，一旦检查到中断抛出异常
                 */
                TimeUnit.SECONDS.sleep(5);
            }

            return initNum;
        }
    }


    enum InvokMethod {
        /**
         * 不会检测中断标志位的调用
         */
        NON_INTERRUPT,
        /**
         * 会检查中断标志位的调用
         */
        INTERRUPT_BY_SLEEP
    }
}



