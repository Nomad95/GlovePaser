   package temp.com.mathworks.mvm;

   import com.mathworks.mvm.eventmgr.DefaultEventMgr;
   import com.mathworks.mvm.eventmgr.EventMgr;
   import com.mathworks.util.ShutdownRuntimeException;
   import java.util.Arrays;
   import org.jetbrains.annotations.Nullable;



















   public class MvmSession
     implements AutoCloseable
   {
     private static volatile EventMgr sEventMgr = null;

     public static class SessionOptions {
       private final PrimaryMode fPrimaryMode;
       private final SecondaryMode fSecondaryMode;
       @Nullable
       private final String[] fSessionArgs;
       private volatile int fHashCode = 0;




       public boolean fDisableLocalMVM = false;




       public static enum PrimaryMode
       {
         UndefinedPrimaryMode(0),
         Matlab(1),
         StandaloneApp(2),
         StandaloneContainer(3),
         StandaloneServer(4),
         Client(5);

         private PrimaryMode(int paramInt) {
           this.fValue = paramInt;
         }

         int getValue() { return this.fValue; }



         private int fValue;
       }


       public static enum SecondaryMode
       {
         UndefinedSecondaryMode(0),
         ParallelWorker(1),
         Mcc(2),
         JavaBuilder(3),
         ParallelWorkerDesktop(4),
         JavaEngine(5),
         WebAppServer(6);

         private int fValue;

         private SecondaryMode(int paramInt) { this.fValue = paramInt; }

         int getValue() {
           return this.fValue;
         }
       }










       public SessionOptions(@Nullable PrimaryMode paramPrimaryMode, @Nullable SecondaryMode paramSecondaryMode, @Nullable String[] paramArrayOfString)
       {
         this.fSessionArgs = (paramArrayOfString == null ? null : (String[])paramArrayOfString.clone());
         this.fPrimaryMode = (paramPrimaryMode == null ? PrimaryMode.StandaloneContainer : paramPrimaryMode);
         this.fSecondaryMode = (paramSecondaryMode == null ? SecondaryMode.JavaBuilder : paramSecondaryMode);
       }





       public SessionOptions(@Nullable String[] paramArrayOfString)
       {
         this(null, null, paramArrayOfString);
       }


       public SessionOptions()
       {
         this(null, null, null);
       }




       public boolean equals(Object paramObject)
       {
         if (!(paramObject instanceof SessionOptions)) return false;
         SessionOptions localSessionOptions = (SessionOptions)paramObject;
         return (localSessionOptions.hashCode() == hashCode()) && (this.fPrimaryMode == localSessionOptions.getPrimaryMode()) && (this.fSecondaryMode == localSessionOptions.getSecondaryMode()) && (Arrays.equals(this.fSessionArgs, localSessionOptions.getSessionArgs()));
       }





       public int hashCode()
       {
         if (this.fHashCode == 0) {
           this.fHashCode = (Arrays.hashCode(this.fSessionArgs) + this.fPrimaryMode.getValue() + this.fSecondaryMode.getValue());
         }
         return this.fHashCode;
       }

       @Nullable
       public String[] getSessionArgs() {
         return this.fSessionArgs == null ? null : (String[])this.fSessionArgs.clone();
       }


       public PrimaryMode getPrimaryMode()
       {
         return this.fPrimaryMode;
       }

       public SecondaryMode getSecondaryMode()
       {
         return this.fSecondaryMode;
       }
     }



     @Nullable
     private final SessionOptions fOptions;


     private static volatile MvmSession sInstance = null;







     private MvmSession(@Nullable SessionOptions paramSessionOptions)
     {
       this.fOptions = paramSessionOptions;
     }





     @Nullable
     public static MvmSession getSession()
       throws ShutdownRuntimeException
     {
       if (!MvmFactory.nativeSessionExists()) { return null;
       }
       if (sInstance != null) { return sInstance;
       }


       return getInstance(null);
     }




     private static synchronized MvmSession getInstance(@Nullable SessionOptions paramSessionOptions)
     {
       if (sInstance == null) {
         sInstance = new MvmSession(paramSessionOptions);
       }
       else {
         assert (paramSessionOptions == null);
       }
       return sInstance;
     }












     public static synchronized MvmSession createSession(@Nullable SessionOptions paramSessionOptions)
       throws ShutdownRuntimeException, IllegalStateException
     {
       return createSession(paramSessionOptions, null);
     }
















     public static synchronized MvmSession createSession(@Nullable SessionOptions paramSessionOptions, @Nullable MvmFactory.JavaLevel paramJavaLevel)
       throws ShutdownRuntimeException, IllegalStateException
     {
       SessionOptions localSessionOptions = paramSessionOptions == null ? new SessionOptions() : paramSessionOptions;




       boolean bool = MvmFactory.nativeStartSession(localSessionOptions.getPrimaryMode().getValue(), localSessionOptions.getSecondaryMode().getValue(), localSessionOptions.fDisableLocalMVM, localSessionOptions.getSessionArgs(), paramJavaLevel == null ? MvmFactory.JavaLevel.Undefined.getValue() : paramJavaLevel.getValue());


       if (!bool)
       {


         throw new IllegalStateException("Session already created in " + ((sInstance == null) || (sInstance.getOptions() == null) ? "native code" : "Java"));
       }


       return getInstance(localSessionOptions);
     }






     public void close()
       throws ShutdownRuntimeException
     {
       boolean bool = MvmFactory.nativeTerminateSession();


       assert (bool);
     }




     @Nullable
     public SessionOptions getOptions()
     {
       return this.fOptions;
     }





     private static EventMgr getDefaultEventMgr()
     {
       if (sEventMgr == null) {
         synchronized (MvmSession.class) {
           if (sEventMgr == null)
             sEventMgr = new DefaultEventMgr();
         }
       }
       return sEventMgr;
     }




     public static EventMgr getEventMgr()
     {
       return getDefaultEventMgr();
     }



     public static void flushEvents()
     {
       getDefaultEventMgr().flush();
     }






     protected void finalize()
       throws Throwable
     {
       try
       {
         close();
       }
       catch (ShutdownRuntimeException localShutdownRuntimeException) {}
       super.finalize();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\MvmSession.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */