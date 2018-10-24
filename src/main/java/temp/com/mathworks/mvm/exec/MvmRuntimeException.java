   package temp.com.mathworks.mvm.exec;



   public class MvmRuntimeException
     extends MvmException
   {
     private final Throwable fJavaCause;



     protected MvmRuntimeException(String paramString1, String paramString2, MatlabStackTraceElement[] paramArrayOfMatlabStackTraceElement, Throwable paramThrowable, String paramString3)
     {
       super(paramString1, paramString2, paramArrayOfMatlabStackTraceElement, paramString3);
       this.fJavaCause = paramThrowable;
     }





     public Throwable getJavaCause()
     {
       return this.fJavaCause;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MvmRuntimeException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */