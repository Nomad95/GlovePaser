   package temp.com.mathworks.util;




   public class ShutdownRuntimeException
     extends RuntimeException
   {
     public ShutdownRuntimeException() {}



     public ShutdownRuntimeException(String paramString)
     {
       super(paramString);
     }

     public ShutdownRuntimeException(String paramString, Throwable paramThrowable) {
       super(paramString, paramThrowable);
     }

     public ShutdownRuntimeException(Throwable paramThrowable)
     {
       super(paramThrowable);
     }


     public ShutdownRuntimeException(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2)
     {
       super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
     }

     public String getLocalizedMessage()
     {
       if (getMessage() == null) return "MATLAB is shutting down";
       return super.getLocalizedMessage();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ShutdownRuntimeException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */