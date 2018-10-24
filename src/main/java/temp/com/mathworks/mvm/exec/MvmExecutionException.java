   package temp.com.mathworks.mvm.exec;

   import java.io.PrintStream;
   import java.io.PrintWriter;
   import java.util.concurrent.ExecutionException;























   public class MvmExecutionException
     extends ExecutionException
   {
     private final MvmException fMvmCause;

     private MvmExecutionException(String paramString, Throwable paramThrowable, MvmException paramMvmException)
     {
       super(paramString, paramThrowable);
       this.fMvmCause = paramMvmException;
     }





     private MvmExecutionException(String paramString)
     {
       super(paramString);
       this.fMvmCause = null;
     }







     public MvmException getMvmCause()
     {
       return this.fMvmCause;
     }



















     public Throwable getCause()
     {
       return super.getCause();
     }



     public void printStackTrace()
     {
       printStackTrace(System.err);
     }


     public void printStackTrace(PrintStream paramPrintStream)
     {
       super.printStackTrace(paramPrintStream);
       MvmException localMvmException = getMvmCause();
       if (localMvmException != null) {
         paramPrintStream.print("Caused by: ");
         localMvmException.printStackTrace(paramPrintStream);
       }
     }


     public void printStackTrace(PrintWriter paramPrintWriter)
     {
       super.printStackTrace(paramPrintWriter);
       MvmException localMvmException = getMvmCause();
       if (localMvmException != null) {
         paramPrintWriter.print("Caused by: ");
         localMvmException.printStackTrace(paramPrintWriter);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MvmExecutionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */