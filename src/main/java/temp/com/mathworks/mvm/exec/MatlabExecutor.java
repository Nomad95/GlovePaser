   package temp.com.mathworks.mvm.exec;

   import com.mathworks.mvm.MVM;
   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.mvm.MvmTerminatedException;
   import com.mathworks.mvm.MvmWrapper;
   import com.mathworks.util.ShutdownRuntimeException;
   import java.util.List;
   import java.util.concurrent.AbstractExecutorService;
   import java.util.concurrent.Callable;
   import java.util.concurrent.RejectedExecutionException;
   import java.util.concurrent.RunnableFuture;
   import java.util.concurrent.TimeUnit;
   import org.jetbrains.annotations.NotNull;






















   public class MatlabExecutor
     extends AbstractExecutorService
   {
     private final MVM fMvm;

     public MatlabExecutor(MvmWrapper paramMvmWrapper)
     {
       this.fMvm = paramMvmWrapper.get();
     }























     public <V> FutureResult<V> submit(MatlabRequest<V> paramMatlabRequest)
       throws RejectedExecutionException, ShutdownRuntimeException
     {
       NativeFutureResult localNativeFutureResult = nativeSubmitIIP(this.fMvm.getHandle(), paramMatlabRequest.createIIP());
       if (localNativeFutureResult == null) {
         if (this.fMvm.isTerminated())
         {
           MvmTerminatedException localMvmTerminatedException = new MvmTerminatedException();
           throw new RejectedExecutionException(localMvmTerminatedException.getMessage(), localMvmTerminatedException);
         }
         throw new RejectedExecutionException();
       }

       return paramMatlabRequest.createFutureResult(localNativeFutureResult);
     }




     public <V> FutureFevalResult<V> submit(MatlabFevalRequest<V> paramMatlabFevalRequest)
       throws RejectedExecutionException
     {
       return (FutureFevalResult)submit(paramMatlabFevalRequest);
     }




     public FutureEvalResult submit(MatlabEvalRequest paramMatlabEvalRequest)
       throws RejectedExecutionException
     {
       return (FutureEvalResult)submit(paramMatlabEvalRequest);
     }






     private native <V> NativeFutureResult<V> nativeSubmitIIP(long paramLong, MatlabIIP paramMatlabIIP);






     public void execute(@NotNull Runnable paramRunnable)
     {
       throw new RejectedExecutionException();
     }

     public List<Runnable> shutdownNow()
     {
       return null;
     }


     public boolean awaitTermination(long paramLong, @NotNull TimeUnit paramTimeUnit)
     {
       return true;
     }



     public void shutdown() {}


     public boolean isTerminated()
     {
       return false;
     }



     public boolean isShutdown()
     {
       return false;
     }





     protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable)
     {
       throw new RejectedExecutionException();
     }





     protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
     {
       throw new RejectedExecutionException();
     }

     static {}
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MatlabExecutor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */