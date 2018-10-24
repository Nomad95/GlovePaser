   package temp.com.mathworks.engine;

   import java.io.PrintStream;
   import java.io.PrintWriter;
   import java.util.concurrent.ExecutionException;











   public class EngineExecutionException
     extends ExecutionException
   {
     private MatlabException mCause;
     private StackTraceElement[] stackTrace;

     protected EngineExecutionException(Throwable paramThrowable)
     {
       super(paramThrowable);
     }




     protected EngineExecutionException(String paramString)
     {
       super(paramString);
     }





     EngineExecutionException(String paramString, MatlabException paramMatlabException)
     {
       super(paramString);
       if (paramMatlabException != null) {
         this.mCause = paramMatlabException;
         this.stackTrace = this.mCause.getStackTrace();
       }
     }




     EngineExecutionException(MatlabException paramMatlabException)
     {
       if (paramMatlabException != null) {
         this.mCause = paramMatlabException;
         this.stackTrace = this.mCause.getStackTrace();
       }
     }





     public StackTraceElement[] getStackTrace()
     {
       return this.stackTrace;
     }




     public void printStackTrace()
     {
       printStackTrace(System.err);
     }





     public void printStackTrace(PrintStream paramPrintStream)
     {
       synchronized (paramPrintStream) {
         super.printStackTrace(paramPrintStream);
         this.mCause.printStackTrace(paramPrintStream);
       }
     }






     public void printStackTrace(PrintWriter paramPrintWriter)
     {
       synchronized (paramPrintWriter) {
         super.printStackTrace(paramPrintWriter);
         this.mCause.printStackTrace(paramPrintWriter);
       }
     }





     public Throwable getCause()
     {
       if (this.mCause != null) {
         return this.mCause;
       }
       return super.getCause();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\EngineExecutionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */