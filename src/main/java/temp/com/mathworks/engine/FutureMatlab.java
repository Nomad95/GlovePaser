   package temp.com.mathworks.engine;

   import java.util.concurrent.ExecutionException;
   import java.util.concurrent.Future;
   import java.util.concurrent.TimeUnit;
   import java.util.concurrent.TimeoutException;






   public class FutureMatlab
     implements Future
   {
     private Future<MatlabEngine> fResult;
     private static final String sMatlabNotStarted = "Unable to launch MATLAB or connect a shared MATLAB session.";

     public FutureMatlab(Future paramFuture)
     {
       this.fResult = paramFuture;
     }

     public boolean isDone() {
       return this.fResult.isDone();
     }

     public boolean isCancelled() {
       return this.fResult.isCancelled();
     }

     public boolean cancel(boolean paramBoolean) {
       Thread localThread = new Thread(new Runnable() {
         public void run() {
           try {
             ((MatlabEngine)FutureMatlab.this.fResult.get()).close(); } catch (InterruptedException|ExecutionException localInterruptedException) {} } }, "closeMatlabRunnableThread");





       localThread.setDaemon(true);
       localThread.start();
       return true;
     }

     public MatlabEngine get() throws EngineException, InterruptedException {
       try {
         return (MatlabEngine)this.fResult.get();

       }
       catch (ExecutionException|IllegalStateException localExecutionException)
       {
         throw new EngineException("Unable to launch MATLAB or connect a shared MATLAB session.");
       }
     }

     public MatlabEngine get(long paramLong, TimeUnit paramTimeUnit) throws EngineException, InterruptedException, TimeoutException
     {
       try {
         return (MatlabEngine)this.fResult.get(paramLong, paramTimeUnit);

       }
       catch (ExecutionException|IllegalStateException localExecutionException)
       {
         throw new EngineException("Unable to launch MATLAB or connect a shared MATLAB session.");
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\FutureMatlab.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */