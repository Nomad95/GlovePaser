   package temp.com.mathworks.mvm.exec;

   import java.util.concurrent.atomic.AtomicBoolean;
   import javax.swing.SwingUtilities;











































   public abstract class MvmSwingWorker<V>
   {
     private final FutureResult<V> fResult;
     private AtomicBoolean fWorkerQueued = new AtomicBoolean();












     public MvmSwingWorker(FutureResult<V> paramFutureResult)
     {
       this.fResult = paramFutureResult;
     }











     protected abstract void done();











     protected V get()
       throws MvmExecutionException, IllegalStateException
     {
       if (!this.fResult.isDone())
       {


         throw new IllegalStateException("MvmSwingWorker's get() called before command completed");
       }
       try {
         return (V)this.fResult.get();

       }
       catch (InterruptedException localInterruptedException)
       {

         throw new IllegalStateException("MvmSwingWorker's get() was interrupted");
       }
     }























     public void runWhenReady()
     {
       if (this.fWorkerQueued.compareAndSet(false, true)) {
         this.fResult.runWhenDone(new Runnable()
         {
           public void run() {
             SwingUtilities.invokeLater(new Runnable()
             {
               public void run() {
                 MvmSwingWorker.this.done();
               }
             });
           }
         });
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MvmSwingWorker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */