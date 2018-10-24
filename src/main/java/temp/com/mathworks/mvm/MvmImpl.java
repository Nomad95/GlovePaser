   package temp.com.mathworks.mvm;

   import com.mathworks.mvm.eventmgr.DefaultEventMgr;
   import com.mathworks.mvm.eventmgr.EventMgr;
   import com.mathworks.mvm.exec.FutureEvalResult;
   import com.mathworks.mvm.exec.FutureFevalResult;
   import com.mathworks.mvm.exec.MatlabEvalRequest;
   import com.mathworks.mvm.exec.MatlabExecutor;
   import com.mathworks.mvm.exec.MatlabFevalRequest;
   import com.mathworks.util.Log;
   import java.io.Writer;
   import java.util.concurrent.atomic.AtomicBoolean;
   import javax.swing.JDialog;
   import javax.swing.JOptionPane;
   import javax.swing.SwingUtilities;
   import org.jetbrains.annotations.Nullable;
























   public class MvmImpl
     implements MVM
   {
     private volatile EventMgr fEventMgr;
     private volatile MatlabExecutor fExecutor;
     private final long fHandle;
     private static final AtomicBoolean LIB_LOADED = new AtomicBoolean(false);





     private static volatile boolean sSpecialJUnitTest = false;

     public long getHandle()
     {
       return this.fHandle;
     }



     MvmImpl(long paramLong)
     {
       loadLibrary();
       this.fHandle = paramLong;
     }






     private static void noLoadLibrary()
     {
       LIB_LOADED.set(true);
     }




     public static void setSpecialJUnitTest()
     {
       synchronized (MvmImpl.class)
       {
         assert (!LIB_LOADED.get());
         sSpecialJUnitTest = true;
       }
     }







     public static void loadLibrary()
     {
       if (LIB_LOADED.get())
       {
         assert (!sSpecialJUnitTest);
         return;
       }

       synchronized (MvmImpl.class) {
         if (LIB_LOADED.get()) return;
         try {
           System.loadLibrary("nativemvm");
           sSpecialJUnitTest = false;
         } catch (Throwable localThrowable) {
           SwingUtilities.invokeLater(new Runnable()
           {
             public void run()
             {
               JOptionPane local1 = new JOptionPane("Failed to load nativemvm library: " + this.val$e.getMessage(), 0, -1)
               {

                 public int getMaxCharactersPerLineCount()
                 {
                   return 100;
                 }
               };
               local1.createDialog("ERROR").setVisible(true);
             }
           });
           Log.logThrowable(localThrowable);





           if ((localThrowable instanceof Error)) throw ((Error)localThrowable);
           if ((localThrowable instanceof RuntimeException)) throw ((RuntimeException)localThrowable);
         } finally {
           LIB_LOADED.set(true);
         }
       }
     }



     public <V> FutureFevalResult<V> feval(String paramString, Object... paramVarArgs)
     {
       return getExecutor().submit(new MatlabFevalRequest(paramString, paramVarArgs));
     }

     public FutureEvalResult eval(String paramString, @Nullable Writer paramWriter1, @Nullable Writer paramWriter2)
     {
       return getExecutor().submit(new MatlabEvalRequest(paramString, paramWriter1, paramWriter2));
     }

     public FutureEvalResult eval(String paramString)
     {
       return getExecutor().submit(new MatlabEvalRequest(paramString, MatlabEvalRequest.NULL_WRITER, MatlabEvalRequest.NULL_WRITER));
     }



     public EventMgr getEventMgr()
     {
       if (this.fEventMgr == null) {
         synchronized (this) {
           if (this.fEventMgr == null)
             this.fEventMgr = new DefaultEventMgr(new MvmWrapper(this));
         }
       }
       return this.fEventMgr;
     }



     public MatlabExecutor getExecutor()
     {
       if (this.fExecutor == null) {
         synchronized (this) {
           if (this.fExecutor == null)
             this.fExecutor = new MatlabExecutor(new MvmWrapper(this));
         }
       }
       return this.fExecutor;
     }

     public boolean breakInDebugger()
     {
       return MvmFactory.nativeBreakInDebugger(this.fHandle);
     }

     public boolean isTerminated()
     {
       return MvmFactory.nativeIsMvmTerminated(this.fHandle);
     }

     public void terminate()
       throws IllegalStateException
     {
       if (!MvmFactory.nativeTerminateMVM(this.fHandle)) {
         throw new IllegalStateException("Cannot terminate current MVM or desktop MATLAB");
       }
     }

     public static void setJavaEngineMode()
     {
       loadLibrary();
       MvmFactory.nativeSetJavaEngine(true);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\MvmImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */