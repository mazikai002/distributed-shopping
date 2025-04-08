package pers.yhf.seckill.component.limiter.counter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 使用Semaphore信号量来控制并发执行的次数，
 * 如果超过域值信号量，则进入阻塞队列中排队等待获取信号量进行执行。
 * 如果阻塞队列中排队的请求过多超出系统处理能力,则可以在拒绝请求。
 * 
 *  相对Atomic优点：如果是瞬时的高并发，可以使请求在阻塞队列中排队，
 *    而不是马上拒绝请求，从而达到一个流量削峰的目的。
 * 
 * @author Yin
 */
public class CountRateLimiting {

    private static Semaphore semphore = new Semaphore(50);  
    
    public static void execute(int count) {  
        if(semphore.getQueueLength()>count){  
            System.out.println("当前等待排队的任务数大于"+count+"，请稍候再试...");  
        }  
        try {  
            semphore.acquire();  
            // 处理核心逻辑  
            TimeUnit.SECONDS.sleep(1);  
            System.out.println("--" + System.currentTimeMillis() / 1000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } finally {  
            semphore.release();  
        }  
    }  
}