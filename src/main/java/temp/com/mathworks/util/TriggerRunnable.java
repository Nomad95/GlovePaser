   package temp.com.mathworks.util;

   import java.util.concurrent.CountDownLatch;
   import java.util.concurrent.TimeUnit;







   public abstract class TriggerRunnable
     implements Runnable
   {
     private final CountDownLatch fLatch;

     public TriggerRunnable()
     {
       this.fLatch = new CountDownLatch(1);
     }






     public static TriggerRunnable create(Runnable paramRunnable)
     {
       new TriggerRunnable()
       {
         public void run()
         {
           try
           {
             this.val$argCode.run();
           }
           finally
           {
             trigger();
           }
         }
       };
     }


     protected void trigger()
     {
       this.fLatch.countDown();
     }



     public void await()
       throws InterruptedException
     {
       this.fLatch.await();
     }







     public boolean await(long paramLong, TimeUnit paramTimeUnit)
       throws InterruptedException
     {
       return this.fLatch.await(paramLong, paramTimeUnit);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\TriggerRunnable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */