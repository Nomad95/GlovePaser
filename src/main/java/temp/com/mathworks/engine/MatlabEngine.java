   package temp.com.mathworks.engine;

   import com.mathworks.mvm.MVM;
   import com.mathworks.mvm.MvmFactory;
   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.mvm.MvmSession;
   import com.mathworks.mvm.MvmSession.SessionOptions;
   import com.mathworks.mvm.MvmSession.SessionOptions.PrimaryMode;
   import com.mathworks.mvm.MvmSession.SessionOptions.SecondaryMode;
   import com.mathworks.mvm.MvmTerminatedException;
   import com.mathworks.mvm.exec.FutureEvalResult;
   import com.mathworks.mvm.exec.FutureFevalResult;
   import com.mathworks.mvm.exec.MatlabEvalRequest;
   import com.mathworks.mvm.exec.MatlabExecutor;
   import com.mathworks.mvm.exec.MatlabFevalRequest;
   import java.io.IOException;
   import java.io.PrintWriter;
   import java.io.Writer;
   import java.util.concurrent.Callable;
   import java.util.concurrent.CancellationException;
   import java.util.concurrent.ExecutionException;
   import java.util.concurrent.ExecutorService;
   import java.util.concurrent.Executors;
   import java.util.concurrent.Future;
   import java.util.concurrent.RejectedExecutionException;
   import java.util.concurrent.ThreadFactory;
   import java.util.concurrent.atomic.AtomicBoolean;









   public class MatlabEngine
   {
     public static final Writer NULL_WRITER = new Writer()
     {
       public void write(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2) {}


       public void flush() {}


       public void close() {}
     };
     private String[] eOptions;
     private static MvmSession session;
     private MvmFactory mFactory;
     private MVM mvm;
     private AtomicBoolean fDisconnected = new AtomicBoolean();
     private final Object diconnectLock = new Object();
     private static final Object connectLock = new Object();
     private static final String sEngineNotSupportedInMatlab = "Initializing MATLAB Engine API from MATLAB is not supported.";
     private static final String sDisconnectedErrorMessage = "MATLAB session is already disconnected.";
     private static final String sNlhsLessThanZero = "Number of output must be greater then or equal to 0.";
     private static final String sMatlabCommandNotEmpty = "MATLAB command must be a non-empty String.";
     private static final String sMatlabNameNotValid = "Name of the shared MATLAB session must be a valid MATLAB variable name.";
     private static final String sMatlabVarNameNotValid = "Invalid MATLAB variable name.";
     private static final String sMatlabNotStarted = "Unable to launch MATLAB.";
     private static final String sMatlabNotAvailable = "MATLAB session is not available.";
     private static final String sFindSharedMatlabError = "Unable to find shared MATLAB sessions.";
     private static final String sMatlabTerminateError = "Unable to terminate MATLAB session.";
     private static DaemonThreadFactory dtFactory;

     static {
       MvmImpl.setJavaEngineMode();

       MvmSession.SessionOptions localSessionOptions = new MvmSession.SessionOptions(MvmSession.SessionOptions.PrimaryMode.Client, MvmSession.SessionOptions.SecondaryMode.UndefinedSecondaryMode, null);
       try {
         session = MvmSession.createSession(localSessionOptions);
       } catch (Exception localException) {
         throw new IllegalStateException("Initializing MATLAB Engine API from MATLAB is not supported.");
       }
     }







     private static void verifyNlhs(int paramInt)
     {
       if (paramInt < 0) {
         throw new IllegalArgumentException("Number of output must be greater then or equal to 0.");
       }
     }





     private static void verifyMatlabFunc(String paramString)
     {
       if ((paramString == null) || (paramString.equals(""))) {
         throw new IllegalArgumentException("MATLAB command must be a non-empty String.");
       }
     }





     private static void verifyMatlabSessionName(String paramString)
     {
       if ((paramString == null) || (paramString.equals("")) || (!MvmFactory.nativeVerifyIdentity(paramString))) {
         throw new IllegalArgumentException("Name of the shared MATLAB session must be a valid MATLAB variable name.");
       }
     }





     private static void verifyMatlabVar(String paramString)
     {
       if ((paramString == null) || (paramString.equals(""))) {
         throw new IllegalArgumentException("Invalid MATLAB variable name.");
       }
     }








     private MatlabEngine(String[] paramArrayOfString)
     {
       this.eOptions = paramArrayOfString;
     }



     public static MatlabEngine startMatlab()
       throws EngineException, InterruptedException, IllegalArgumentException, IllegalStateException
     {
       MatlabEngine localMatlabEngine = new MatlabEngine();
       localMatlabEngine.open();
       return localMatlabEngine;
     }




     public static MatlabEngine startMatlab(String[] paramArrayOfString)
       throws EngineException, InterruptedException, IllegalArgumentException, IllegalStateException
     {
       MatlabEngine localMatlabEngine = new MatlabEngine(paramArrayOfString);
       localMatlabEngine.open();
       return localMatlabEngine;
     }

     private static class DaemonThreadFactory implements ThreadFactory
     {
       public Thread newThread(Runnable paramRunnable)
       {
         Thread localThread = new Thread(paramRunnable);
         localThread.setDaemon(true);
         return localThread;
       }
     }


     private static synchronized DaemonThreadFactory getDaemonThreadFactory()
     {
       if (dtFactory == null) {
         dtFactory = new DaemonThreadFactory(null);
       }
       return dtFactory;
     }

     private static class StartMatlabCallable implements Callable<MatlabEngine>
     {
       private String[] eOptions;

       public StartMatlabCallable() {}

       public StartMatlabCallable(String[] paramArrayOfString)
       {
         this.eOptions = paramArrayOfString;
       }

       public MatlabEngine call() throws Exception
       {
         return MatlabEngine.startMatlab(this.eOptions);
       }
     }



     public static Future<MatlabEngine> startMatlabAsync()
     {
       ExecutorService localExecutorService = Executors.newSingleThreadExecutor(getDaemonThreadFactory());
       Future localFuture = localExecutorService.submit(new StartMatlabCallable());
       return new FutureMatlab(localFuture);
     }




     public static Future<MatlabEngine> startMatlabAsync(String[] paramArrayOfString)
     {
       ExecutorService localExecutorService = Executors.newSingleThreadExecutor(getDaemonThreadFactory());
       Future localFuture = localExecutorService.submit(new StartMatlabCallable(paramArrayOfString));
       return new FutureMatlab(localFuture);
     }

     private static class ConnectMatlabCallable implements Callable<MatlabEngine>
     {
       private String name;

       public ConnectMatlabCallable(String paramString) {
         this.name = paramString;
       }

       public MatlabEngine call() throws Exception
       {
         if (this.name == null) {
           return MatlabEngine.connectMatlab();
         }
         return MatlabEngine.connectMatlab(this.name);
       }
     }




     public static MatlabEngine connectMatlab()
       throws EngineException, InterruptedException
     {
       MatlabEngine localMatlabEngine = null;


       synchronized (connectLock) {
         String[] arrayOfString = findMatlab();
         if (arrayOfString.length > 0) {
           localMatlabEngine = connectMatlab(arrayOfString[0]);
         } else {
           localMatlabEngine = startMatlab(new String[] { "-r", "matlab.engine.shareEngine" });
         }
       }
       return localMatlabEngine;
     }





     public static MatlabEngine connectMatlab(String paramString)
       throws EngineException, InterruptedException
     {
       verifyMatlabSessionName(paramString);
       MatlabEngine localMatlabEngine = new MatlabEngine();
       try {
         localMatlabEngine.mFactory = MvmFactory.connectMatlab(paramString);
         localMatlabEngine.mvm = localMatlabEngine.mFactory.createMVM();
       } catch (IllegalStateException|IOException localIllegalStateException) {
         throw new EngineException("Unable to connect to MATLAB session \"" + paramString + "\".");
       }
       return localMatlabEngine;
     }



     public static Future<MatlabEngine> connectMatlabAsync()
     {
       ExecutorService localExecutorService = Executors.newSingleThreadExecutor(getDaemonThreadFactory());
       Future localFuture = localExecutorService.submit(new ConnectMatlabCallable(null));
       return new FutureMatlab(localFuture);
     }




     public static Future<MatlabEngine> connectMatlabAsync(String paramString)
     {
       ExecutorService localExecutorService = Executors.newSingleThreadExecutor(getDaemonThreadFactory());
       Future localFuture = localExecutorService.submit(new ConnectMatlabCallable(paramString));
       return new FutureMatlab(localFuture);
     }

     private static class FindMatlabCallable
       implements Callable<String[]>
     {
       public String[] call()
         throws Exception
       {
         return MatlabEngine.findMatlab();
       }
     }



     public static String[] findMatlab()
       throws EngineException
     {
       String[] arrayOfString;


       try
       {
         arrayOfString = MvmFactory.findMatlab();
       } catch (IllegalStateException localIllegalStateException) {
         throw new EngineException("Unable to find shared MATLAB sessions.");
       }
       return arrayOfString;
     }




     public static Future<String[]> findMatlabAsync()
     {
       ExecutorService localExecutorService = Executors.newSingleThreadExecutor(getDaemonThreadFactory());
       Future localFuture = localExecutorService.submit(new FindMatlabCallable(null));
       return localFuture;
     }



     private void open()
       throws EngineException, InterruptedException, IllegalArgumentException
     {
       try
       {
         this.mFactory = MvmFactory.createEngineFactory(this.eOptions);
         this.mvm = this.mFactory.createMVM();
       } catch (IllegalStateException|IOException|SecurityException localIllegalStateException) {
         throw new EngineException("Unable to launch MATLAB.");
       }
     }






     public void eval(String paramString)
       throws InterruptedException, MatlabExecutionException, MatlabSyntaxException, CancellationException, EngineException, ExecutionException
     {
       Future localFuture = evalAsync(paramString);
       Object localObject = null;
       localFuture.get();
     }






     public Future<Void> evalAsync(String paramString)
     {
       return evalAsync(paramString, new PrintWriter(System.out), new PrintWriter(System.err));
     }








     public void eval(String paramString, Writer paramWriter1, Writer paramWriter2)
       throws MatlabExecutionException, MatlabSyntaxException, EngineException, InterruptedException, ExecutionException
     {
       Future localFuture = evalAsync(paramString, paramWriter1, paramWriter2);
       localFuture.get();
     }







     public Future<Void> evalAsync(String paramString, Writer paramWriter1, Writer paramWriter2)
     {
       verifyMatlabFunc(paramString);
       FutureResult localFutureResult = null;
       try {
         MatlabExecutor localMatlabExecutor = this.mvm.getExecutor();
         MatlabEvalRequest localMatlabEvalRequest = new MatlabEvalRequest(paramString, paramWriter1, paramWriter2);
         FutureEvalResult localFutureEvalResult = localMatlabExecutor.submit(localMatlabEvalRequest);
         localFutureResult = new FutureResult(localFutureEvalResult);
       } catch (MvmTerminatedException|RejectedExecutionException localMvmTerminatedException) {
         throw new IllegalStateException("MATLAB session is not available.");
       }
       return localFutureResult;
     }








     public <T> T feval(String paramString, Object... paramVarArgs)
       throws RejectedExecutionException, EngineException, InterruptedException, ExecutionException
     {
       Future localFuture = fevalAsync(paramString, paramVarArgs);
       Object localObject = localFuture.get();
       return (T)localObject;
     }







     public <T> Future<T> fevalAsync(String paramString, Object... paramVarArgs)
     {
       Future localFuture = fevalAsync(1, paramString, new PrintWriter(System.out), new PrintWriter(System.err), paramVarArgs);
       return localFuture;
     }










     public <T> T feval(String paramString, Writer paramWriter1, Writer paramWriter2, Object... paramVarArgs)
       throws RejectedExecutionException, EngineException, InterruptedException, ExecutionException
     {
       Future localFuture = fevalAsync(paramString, paramWriter1, paramWriter2, paramVarArgs);
       Object localObject = localFuture.get();
       return (T)localObject;
     }








     public <T> Future<T> fevalAsync(String paramString, Writer paramWriter1, Writer paramWriter2, Object... paramVarArgs)
       throws RejectedExecutionException
     {
       Future localFuture = fevalAsync(1, paramString, paramWriter1, paramWriter2, paramVarArgs);
       return localFuture;
     }






     public <T> T feval(int paramInt, String paramString, Object... paramVarArgs)
       throws MatlabExecutionException, MatlabSyntaxException, EngineException, InterruptedException, ExecutionException
     {
       Future localFuture = fevalAsync(paramInt, paramString, paramVarArgs);
       Object localObject = localFuture.get();
       return (T)localObject;
     }






     public <T> Future<T> fevalAsync(int paramInt, String paramString, Object... paramVarArgs)
       throws RejectedExecutionException
     {
       Future localFuture = fevalAsync(paramInt, paramString, new PrintWriter(System.out), new PrintWriter(System.err), paramVarArgs);
       return localFuture;
     }








     public <T> T feval(int paramInt, String paramString, Writer paramWriter1, Writer paramWriter2, Object... paramVarArgs)
       throws RejectedExecutionException, EngineException, InterruptedException, ExecutionException
     {
       Future localFuture = fevalAsync(paramString, new Object[] { Integer.valueOf(paramInt), paramWriter1, paramWriter2, paramVarArgs });
       Object localObject = localFuture.get();
       return (T)localObject;
     }








     public <T> Future<T> fevalAsync(int paramInt, String paramString, Writer paramWriter1, Writer paramWriter2, Object... paramVarArgs)
       throws RejectedExecutionException
     {
       verifyNlhs(paramInt);
       verifyMatlabFunc(paramString);
       FutureFevalResult localFutureFevalResult = null;
       try {
         MatlabExecutor localMatlabExecutor = this.mvm.getExecutor();
         MatlabFevalRequest localMatlabFevalRequest = new MatlabFevalRequest(paramString, Integer.valueOf(paramInt), paramWriter1, paramWriter2, paramVarArgs);
         localFutureFevalResult = localMatlabExecutor.submit(localMatlabFevalRequest);
       } catch (MvmTerminatedException|RejectedExecutionException localMvmTerminatedException) {
         throw new IllegalStateException("MATLAB session is not available.");
       }
       FutureResult localFutureResult = new FutureResult(localFutureFevalResult);
       return localFutureResult;
     }








     public <T> void putVariable(String paramString, T paramT)
       throws InterruptedException, EngineException, CancellationException, IllegalStateException, ExecutionException
     {
       Future localFuture = putVariableAsync(paramString, paramT);
       localFuture.get();
     }








     public <T> Future<T> putVariableAsync(String paramString, T paramT)
       throws InterruptedException, EngineException, IllegalStateException
     {
       verifyMatlabVar(paramString);
       FutureResult localFutureResult = null;
       try {
         MatlabExecutor localMatlabExecutor = this.mvm.getExecutor();
         MatlabFevalRequest localMatlabFevalRequest = new MatlabFevalRequest("assignin", Integer.valueOf(0), (Writer)null, (Writer)null, new Object[] { "base".toCharArray(), paramString.toCharArray(), paramT });

         FutureFevalResult localFutureFevalResult = localMatlabExecutor.submit(localMatlabFevalRequest);
         localFutureResult = new FutureResult(localFutureFevalResult);
       } catch (MvmTerminatedException|RejectedExecutionException localMvmTerminatedException) {
         throw new IllegalStateException("MATLAB session is not available.");
       }
       return localFutureResult;
     }






     public <T> T getVariable(String paramString)
       throws InterruptedException, CancellationException, IllegalStateException, EngineException, ExecutionException
     {
       Future localFuture = getVariableAsync(paramString);

       Object localObject = localFuture.get();
       return (T)localObject;
     }




     public <T> Future<T> getVariableAsync(String paramString)
       throws InterruptedException, CancellationException, IllegalStateException, EngineException, ExecutionException
     {
       verifyMatlabVar(paramString);
       FutureResult localFutureResult = null;
       try {
         MatlabExecutor localMatlabExecutor = this.mvm.getExecutor();
         MatlabFevalRequest localMatlabFevalRequest = new MatlabFevalRequest("matlab.internal.engine.getVariable", new Object[] { paramString.toCharArray() });
         FutureFevalResult localFutureFevalResult = localMatlabExecutor.submit(localMatlabFevalRequest);
         localFutureResult = new FutureResult(localFutureFevalResult);
       } catch (MvmTerminatedException|RejectedExecutionException localMvmTerminatedException) {
         throw new IllegalStateException("MATLAB session is not available.");
       }
       return localFutureResult;
     }

     private static class DisconnectMatlabCallable implements Callable
     {
       private MatlabEngine engine;

       public DisconnectMatlabCallable(MatlabEngine paramMatlabEngine) {
         this.engine = paramMatlabEngine;
       }

       public Void call() throws Exception
       {
         synchronized (this.engine.diconnectLock) {
           if (!this.engine.fDisconnected.get()) {
             this.engine.close();
           }
           return null;
         }
       }
     }


     public void disconnect()
       throws EngineException
     {
       close();
     }





     public Future<Void> disconnectAsync()
     {
       synchronized (this.diconnectLock) {
         if (this.fDisconnected.get()) {
           throw new IllegalStateException("MATLAB session is already disconnected.");
         }
         ExecutorService localExecutorService = Executors.newSingleThreadExecutor(getDaemonThreadFactory());
         Future localFuture = localExecutorService.submit(new DisconnectMatlabCallable(this));
         return localFuture;
       }
     }




     public void quit()
       throws InterruptedException, EngineException
     {
       Future localFuture = quitAsync();
       try
       {
         localFuture.get();
       }
       catch (Exception localException) {}

       try
       {
         this.mFactory.terminate();
       } catch (IllegalStateException localIllegalStateException) {
         throw new EngineException("Unable to terminate MATLAB session.");
       }
     }


     public Future<Void> quitAsync()
       throws InterruptedException
     {
       FutureResult localFutureResult;
       try
       {
         MatlabExecutor localMatlabExecutor = this.mvm.getExecutor();
         MatlabEvalRequest localMatlabEvalRequest = new MatlabEvalRequest("exit");
         FutureEvalResult localFutureEvalResult = localMatlabExecutor.submit(localMatlabEvalRequest);
         localFutureResult = new FutureResult(localFutureEvalResult);
       }
       catch (MvmTerminatedException|RejectedExecutionException localMvmTerminatedException) {
         throw new IllegalStateException("MATLAB session is not available.");
       }
       return localFutureResult;
     }




     public void close()
       throws EngineException
     {
       synchronized (this.diconnectLock) {
         if (this.fDisconnected.get()) {
           throw new IllegalStateException("MATLAB session is already disconnected.");
         }
         try
         {
           this.mFactory.terminate();
           this.fDisconnected.set(true);
         }
         catch (MvmTerminatedException localMvmTerminatedException) {
           throw new IllegalStateException("MATLAB session is not available.");
         } catch (IllegalStateException localIllegalStateException) {
           throw new EngineException("Unable to terminate MATLAB session.");
         }
       }
     }

     protected void finalize()
     {
       try {
         close();
       }
       catch (Exception localException) {}
     }

     private MatlabEngine() {}
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\MatlabEngine.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */