   package temp.com.mathworks.engine;

   import com.mathworks.mvm.exec.MatlabConversionError;
   import com.mathworks.mvm.exec.MvmCompileException;
   import com.mathworks.mvm.exec.MvmException;
   import com.mathworks.mvm.exec.MvmExecutionException;
   import com.mathworks.mvm.exec.MvmRuntimeException;
   import java.util.concurrent.CancellationException;
   import java.util.concurrent.ExecutionException;
   import java.util.concurrent.Future;
   import java.util.concurrent.TimeUnit;
   import java.util.concurrent.TimeoutException;








   public class FutureResult<T>
     implements Future
   {
     private Future<T> fResult;
     private static final String sMatlabDisconnected = "MATLAB session is already disconnected.";
     private static final String sMatlabNotAvailable = "MATLAB session is not available.";

     protected FutureResult(Future<T> paramFuture)
     {
       this.fResult = paramFuture;
     }

     protected Future<T> getFutureResult() {
       return this.fResult;
     }

     protected void putFutureFevalResult(Future<T> paramFuture) {
       this.fResult = paramFuture;
     }

     public T get() throws InterruptedException, EngineException, IllegalStateException, MatlabExecutionException, MatlabSyntaxException {
       Object localObject = null;
       try {
         localObject = this.fResult.get();
       } catch (CancellationException localCancellationException) {
         throw new CancellationException(localCancellationException.getLocalizedMessage());
       } catch (MvmExecutionException localMvmExecutionException) {
         MvmException localMvmException = localMvmExecutionException.getMvmCause();
         Throwable localThrowable = localMvmExecutionException.getCause();

         if ((localThrowable instanceof MatlabConversionError)) {
           throw new UnsupportedTypeException(localThrowable.getLocalizedMessage());
         }

         if (((localMvmException instanceof MvmRuntimeException)) && (localMvmException.getLocalizedMessage().contains("Data Conversion Error")))
         {
           throw new UnsupportedTypeException(localMvmException.getLocalizedMessage()); }
         if ((localMvmException instanceof MvmCompileException)) {
           throw new MatlabSyntaxException(localMvmExecutionException.getMessage(), new MatlabException(localMvmException));
         }
         if ((localMvmException != null) && ((localMvmException.getMessage().contains("stopped.")) || (localMvmException.getMessage().contains("has been ended"))))
           throw new IllegalStateException("MATLAB session is already disconnected.");
         if (localMvmException != null) {
           throw new MatlabExecutionException(localMvmExecutionException.getMessage(), new MatlabException(localMvmException));
         }
         throw new IllegalStateException("MATLAB session is not available.");
       }
       catch (ExecutionException localExecutionException) {
         throw new IllegalStateException("MATLAB session is not available.");
       }
       return (T)localObject;
     }

     public T get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, TimeoutException, ExecutionException, EngineException, IllegalStateException {
       Object localObject = null;
       try {
         localObject = this.fResult.get(paramLong, paramTimeUnit);
       } catch (CancellationException localCancellationException) {
         throw new CancellationException(localCancellationException.getLocalizedMessage());
       } catch (MvmExecutionException localMvmExecutionException) {
         MvmException localMvmException = localMvmExecutionException.getMvmCause();
         Throwable localThrowable = localMvmExecutionException.getCause();

         if ((localThrowable instanceof MatlabConversionError)) {
           throw new UnsupportedTypeException(localThrowable.getLocalizedMessage());
         }

         if (((localMvmException instanceof MvmRuntimeException)) && (localMvmException.getLocalizedMessage().contains("Data Conversion Error")))
           throw new UnsupportedTypeException(localMvmException.getLocalizedMessage());
         if ((localMvmException instanceof MvmCompileException))
           throw new MatlabSyntaxException(localMvmExecutionException.getMessage(), new MatlabException(localMvmException));
         if ((localMvmException != null) && ((localMvmException.getMessage().contains("stopped.")) || (localMvmException.getMessage().contains("The pipe has been ended"))))
           throw new IllegalStateException("MATLAB session is already disconnected.");
         if (localMvmException != null) {
           throw new MatlabExecutionException(localMvmExecutionException.getMessage(), new MatlabException(localMvmException));
         }
         throw new IllegalStateException("MATLAB session is not available.");
       }
       catch (ExecutionException localExecutionException) {
         throw new IllegalStateException("MATLAB session is not available.");
       }
       return (T)localObject;
     }

     public boolean cancel(boolean paramBoolean) {
       return this.fResult.cancel(paramBoolean);
     }

     public boolean isCancelled()
     {
       return this.fResult.isCancelled();
     }

     public boolean isDone()
     {
       return this.fResult.isDone();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\FutureResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */