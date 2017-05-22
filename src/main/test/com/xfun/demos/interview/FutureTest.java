package com.xfun.demos.interview;

import java.util.concurrent.*;

import static sun.swing.SwingUtilities2.submit;

/**
 * Created by xfun on 5/17/17.
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        futureTest();
        futureTaskTest();
    }

    public static void futureTest() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable callable = new Callable() {
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return 110;
            }
        };

        Future<Integer> future = executorService.submit(callable);
        executorService.shutdown();

        try {
            System.out.println(future.get(1,TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            if(e instanceof TimeoutException){
                System.out.println("TimeoutException");
                if(!future.isCancelled()) future.cancel(true);
            }
        }

    }

    public static void futureTaskTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable callable = new Callable() {
            public Integer call() throws Exception {
                return 119;
            }
        };
        FutureTask futureTask = new FutureTask(callable);
        executorService.submit(futureTask);
        executorService.shutdown();

        System.out.println(futureTask.get());

    }

}
