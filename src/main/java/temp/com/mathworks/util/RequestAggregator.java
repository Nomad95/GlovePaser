   package temp.com.mathworks.util;

   import java.util.Iterator;
   import java.util.LinkedList;
   import java.util.List;
   import java.util.Vector;
   import java.util.concurrent.CountDownLatch;
   import java.util.concurrent.ScheduledExecutorService;
   import java.util.concurrent.TimeUnit;




















   public class RequestAggregator<T extends Runnable>
   {
     private final Combiner<T, ? extends Runnable> fCombiner;
     private final boolean fAdaptive;
     private final Object fRequestLock;
     private final Object fExecuteLock;
     private final long fOriginalPeriodMilliseconds;
     private final Integer fSuspendRate;
     private long fPeriodMilliseconds;
     private long fLastExecuteTime;
     private int fLastRequestCount;
     private String fName;
     private List<T> fPendingRequests;
     private List<Runnable> fPendingIdleRequests;
     private ScheduledExecutorService fTimer;
     public static final int DEFAULT_PERIOD_MS = 100;

     public RequestAggregator()
     {
       this(new DefaultCombiner(null));
     }




     public RequestAggregator(Combiner<T, ? extends Runnable> paramCombiner)
     {
       this(paramCombiner, 100);
     }

     public RequestAggregator(int paramInt) {
       this(new DefaultCombiner(null), paramInt);
     }





     public RequestAggregator(Combiner<T, ? extends Runnable> paramCombiner, int paramInt)
     {
       this(paramCombiner, paramInt, false);
     }









     public RequestAggregator(Combiner<T, ? extends Runnable> paramCombiner, int paramInt, boolean paramBoolean)
     {
       this(paramCombiner, paramInt, paramBoolean, null);
     }













     public RequestAggregator(Combiner<T, ? extends Runnable> paramCombiner, int paramInt, boolean paramBoolean, Integer paramInteger)
     {
       this.fSuspendRate = paramInteger;
       this.fCombiner = (paramCombiner == null ? new DefaultCombiner(null) : paramCombiner);
       this.fPeriodMilliseconds = paramInt;
       this.fAdaptive = paramBoolean;
       this.fRequestLock = new Object();
       this.fExecuteLock = new Object();
       this.fPendingRequests = new LinkedList();
       this.fPendingIdleRequests = new Vector();
       this.fOriginalPeriodMilliseconds = paramInt;

       if (this.fAdaptive) {
         this.fPeriodMilliseconds = 0L;
       }
     }



     public void setName(String paramString)
     {
       this.fName = paramString;
     }



     public String getName()
     {
       return this.fName;
     }

     public void waitForPendingRequests() {
       final CountDownLatch localCountDownLatch = new CountDownLatch(1);
       runWhenIdle(new Runnable() {
         public void run() {
           localCountDownLatch.countDown();
         }
       });
       try
       {
         localCountDownLatch.await();
       } catch (InterruptedException localInterruptedException) {
         throw new IllegalStateException(localInterruptedException);
       }
     }



     public void runWhenIdle(Runnable paramRunnable)
     {
       synchronized (this.fRequestLock) {
         this.fPendingIdleRequests.add(paramRunnable);



         request(null);
       }
     }

     private void recordExecutionAndAdapt() {
       if (this.fAdaptive) {
         long l = System.currentTimeMillis();
         int i = l - this.fLastExecuteTime < Math.max(50L, this.fPeriodMilliseconds) * 1.5D ? 1 : 0;
         if (i != 0) {
           this.fPeriodMilliseconds = ((int)Math.max(10.0D, this.fPeriodMilliseconds * 1.5D));
           this.fPeriodMilliseconds = Math.min(this.fPeriodMilliseconds, this.fOriginalPeriodMilliseconds * 8L);
         }
         else {
           this.fPeriodMilliseconds = 0L;
         }
         this.fLastExecuteTime = l;
       }
     }

     public void cancelPendingRequests() {
       synchronized (this.fRequestLock) {
         this.fPendingRequests.clear();
       }
     }




     public void request(T paramT)
     {
       synchronized (this.fRequestLock) {
         if (paramT != null) {
           this.fPendingRequests.add(paramT);
         }
         if (this.fTimer == null) {
           String str;
           if (this.fName == null) {
             str = "Unnamed RequestAggregator Timer";
           } else {
             str = this.fName;
           }

           this.fTimer = ExecutorServiceFactory.createScheduledExecutorService(str, new Runnable()
           {
             public void run()
             {
               synchronized (RequestAggregator.this.fExecuteLock) {
                 long l1 = RequestAggregator.this.fLastExecuteTime;
                 RequestAggregator.this.recordExecutionAndAdapt();


                 if (RequestAggregator.this.fSuspendRate != null) {
                   synchronized (RequestAggregator.this.fRequestLock) {
                     long l2 = System.currentTimeMillis() - l1;

                     long l3 = RequestAggregator.this.fPendingRequests.size() - RequestAggregator.this.fLastRequestCount;


                     RequestAggregator.this.fLastRequestCount = RequestAggregator.this.fPendingRequests.size();
                     if ((l1 > 0L) && (l2 > 0L) && (l3 > 0L) && (l3 * 1000L / l2 >= RequestAggregator.this.fSuspendRate.intValue()))
                     {

                       return;
                     }
                   }
                 }




                 synchronized (RequestAggregator.this.fRequestLock) {
                   ??? = new Vector(RequestAggregator.this.fPendingRequests);
                   RequestAggregator.this.fPendingRequests.clear();
                   if (RequestAggregator.this.fPendingRequests.isEmpty()) {
                     RequestAggregator.this.fTimer.shutdown();
                     RequestAggregator.this.fTimer = null;
                   }
                 }



                 if (!((List)???).isEmpty()) {
                   ??? = (Runnable)RequestAggregator.this.fCombiner.combine((List)???);

                   ((Runnable)???).run();
                 }



                 synchronized (RequestAggregator.this.fRequestLock) {
                   if (RequestAggregator.this.fTimer == null) {
                     ??? = new Vector(RequestAggregator.this.fPendingIdleRequests);

                     RequestAggregator.this.fPendingIdleRequests.clear();
                   } else {
                     ??? = new Vector();
                   }
                 }
                 for (??? = ((List)???).iterator(); ((Iterator)???).hasNext();) { Runnable localRunnable = (Runnable)((Iterator)???).next();
                   localRunnable.run(); } } } }, Math.max(1L, this.fPeriodMilliseconds), TimeUnit.MILLISECONDS);
         }
       }
     }

     private static class DefaultCombiner<T extends Runnable>
       implements Combiner<T, Runnable>
     {
       public Runnable combine(final List<T> paramList)
       {
         new Runnable() {
           public void run() {
             long l1 = System.currentTimeMillis();
             for (Runnable localRunnable : paramList) {
               long l2 = System.currentTimeMillis() - l1;
               if (l2 - l1 > 150L) {
                 l1 = l2;
                 Thread.yield();
               }
               localRunnable.run();
             }
           }
         };
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\RequestAggregator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */