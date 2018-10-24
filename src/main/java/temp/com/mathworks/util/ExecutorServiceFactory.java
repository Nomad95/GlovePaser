   package temp.com.mathworks.util;

   import java.util.concurrent.Executors;
   import java.util.concurrent.ScheduledExecutorService;
   import java.util.concurrent.TimeUnit;







   public final class ExecutorServiceFactory
   {
     public static ScheduledExecutorService createScheduledExecutorService(String paramString, Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
     {
       NamedDaemonThreadFactory localNamedDaemonThreadFactory = new NamedDaemonThreadFactory(paramString);

       ScheduledExecutorService localScheduledExecutorService = Executors.newScheduledThreadPool(1, localNamedDaemonThreadFactory);

       localScheduledExecutorService.scheduleWithFixedDelay(paramRunnable, paramLong, paramLong, paramTimeUnit);
       return localScheduledExecutorService;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ExecutorServiceFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */