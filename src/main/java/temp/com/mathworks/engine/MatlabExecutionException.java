   package temp.com.mathworks.engine;

   public class MatlabExecutionException
     extends EngineExecutionException
   {
     public MatlabExecutionException(String paramString, MatlabException paramMatlabException)
     {
       super(paramString, paramMatlabException);
     }

     public MatlabExecutionException(String paramString) {
       super(paramString);
     }

     public MatlabExecutionException(MatlabException paramMatlabException) {
       super(paramMatlabException);
     }

     public MatlabExecutionException(Throwable paramThrowable) {
       super(paramThrowable);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\MatlabExecutionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */