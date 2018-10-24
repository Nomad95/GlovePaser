   package temp.com.mathworks.engine;

   import com.mathworks.mvm.exec.MvmException;
   import java.io.PrintStream;
   import java.io.PrintWriter;




   public class MatlabException
     extends Exception
   {
     private MvmException fException;

     public MatlabException(MvmException paramMvmException)
     {
       this.fException = paramMvmException;
     }

     public String getMessage()
     {
       return this.fException.getMessage();
     }

     public String getLocalizedMessage()
     {
       return this.fException.getLocalizedMessage();
     }

     public void printStackTrace()
     {
       this.fException.printStackTrace();
     }

     public void printStackTrace(PrintWriter paramPrintWriter)
     {
       this.fException.printStackTrace(paramPrintWriter);
     }

     public void printStackTrace(PrintStream paramPrintStream)
     {
       this.fException.printStackTrace(paramPrintStream);
     }

     public Throwable getCause()
     {
       return this.fException.getCause();
     }

     public StackTraceElement[] getStackTrace()
     {
       if (this.fException != null) {
         return this.fException.getStackTrace();
       }
       return null;
     }

     public String toString()
     {
       return this.fException.toString();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\MatlabException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */