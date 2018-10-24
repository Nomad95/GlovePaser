   package temp.com.mathworks.mvm.exec;

   import java.util.concurrent.CancellationException;
   import java.util.concurrent.Future;
   import java.util.concurrent.TimeUnit;
   import java.util.concurrent.TimeoutException;
   import javax.swing.SwingUtilities;
   import org.jetbrains.annotations.Nullable;

























   public class FutureResult<V>
     implements Future<V>
   {
     private final NativeFutureResult<V> fNativeFutureResult;
     private final int fNlhs;

     private static enum CancelState
     {
       NONE,  SUBMITTED,  FAILED;

       private CancelState() {} }
     private volatile CancelState fCancelState = CancelState.NONE;


     private static final double DEFAULT_INTERVAL_SECS = 1.0D;


     FutureResult(NativeFutureResult<V> paramNativeFutureResult)
     {
       this(paramNativeFutureResult, 0);
     }





     FutureResult(NativeFutureResult<V> paramNativeFutureResult, int paramInt)
     {
       this.fNativeFutureResult = paramNativeFutureResult;
       this.fNlhs = paramInt;
     }







     public int getNlhs()
     {
       return this.fNlhs;
     }
















































     public synchronized boolean cancel(boolean paramBoolean)
     {
       if (this.fCancelState != CancelState.NONE) { return false;
       }


       boolean bool = this.fNativeFutureResult.cancel(paramBoolean);

       if (bool) this.fCancelState = CancelState.SUBMITTED; else
         this.fCancelState = CancelState.FAILED;
       return bool;
     }





















     public void waitForCompletion()
       throws InterruptedException, IllegalStateException
     {
       waitInternal(0L, null, false);
     }











     public boolean waitForCompletion(long paramLong, TimeUnit paramTimeUnit)
       throws InterruptedException, IllegalStateException
     {
       return waitInternal(paramLong, paramTimeUnit, false);
     }




     public synchronized boolean isCancelled()
     {
       if ((this.fCancelState == CancelState.NONE) && (this.fNativeFutureResult.isCancelled()))
       {
         this.fCancelState = CancelState.SUBMITTED;
       }
       return this.fCancelState == CancelState.SUBMITTED;
     }






     public boolean isDone()
     {
       return (this.fCancelState != CancelState.NONE) || (this.fNativeFutureResult.isDone());
     }













     public void runWhenDone(@Nullable Runnable paramRunnable)
     {
       this.fNativeFutureResult.runWhenDone(paramRunnable);
     }









































     public V get()
       throws InterruptedException, MvmExecutionException, MvmCancellationException, CancellationException, IllegalStateException
     {
       waitInternal(0L, null, true);
       return (V)getInternal();
     }















     public V get(long paramLong, TimeUnit paramTimeUnit)
       throws InterruptedException, MvmExecutionException, MvmCancellationException, CancellationException, IllegalStateException, TimeoutException
     {
       boolean bool = waitInternal(paramLong, paramTimeUnit, true);
       if (!bool) throw new TimeoutException();
       return (V)getInternal();
     }





















     private boolean waitInternal(long paramLong, @Nullable TimeUnit paramTimeUnit, boolean paramBoolean)
       throws IllegalStateException, InterruptedException, CancellationException
     {
       if (paramLong < 0L) { throw new IllegalArgumentException("Timeout cannot be negative");
       }



       if ((!isDone()) && (SwingUtilities.isEventDispatchThread()))
         throw new IllegalStateException("Blocking in Event Dispatch Thread not allowed");
       long l1 = paramTimeUnit == null ? 0L : System.currentTimeMillis();
       long l2 = paramTimeUnit == null ? 0L : l1 + TimeUnit.MILLISECONDS.convert(paramLong, paramTimeUnit);
       double d1 = 1.0D;




       int i = 0;
       do {
         boolean bool1 = isCancelled();



         if (paramTimeUnit != null)
         {
           long l3 = l2 - l1;

           double d2 = l3 / 1000.0D;
           if (d2 < d1) d1 = d2;
           if (i != 0)
           {







             Thread.sleep(d1);
           }
         }
         if (i == 0)
         {
           try {
             boolean bool2 = this.fNativeFutureResult.waitForCompletion(d1);


             if (bool2) return true;
           }
           catch (IllegalStateException localIllegalStateException) {
             if (paramTimeUnit == null) {
               throw new IllegalStateException("Call to get() or waitForCompletion() in MATLAB thread would block forever");
             }

             i = 1;
             continue;
           }


           if (Thread.interrupted()) {
             throw new InterruptedException();
           }
         }










         if ((paramBoolean) && (bool1)) { throw new CancellationException();
         }
         if (paramTimeUnit != null) l1 = System.currentTimeMillis();
       } while ((paramTimeUnit == null) || (l1 < l2));

       return false;
     }






     protected V getInternal()
       throws InterruptedException, MvmExecutionException, CancellationException
     {
       Object localObject1;





       try
       {
         localObject1 = this.fNativeFutureResult.get();
       }
       catch (MvmExecutionException localMvmExecutionException) {
         if ((localMvmExecutionException.getMvmCause() instanceof MvmInterruptedException))
         {
           throw new MvmCancellationException((MvmInterruptedException)localMvmExecutionException.getMvmCause());
         }



         if (isCancelled()) throw new CancellationException();
         throw localMvmExecutionException;
       }


       if (isCancelled()) { throw new CancellationException();
       }

       Object localObject2 = localObject1;
       return (V)localObject2;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\FutureResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */