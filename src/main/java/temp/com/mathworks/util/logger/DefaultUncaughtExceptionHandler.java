   package temp.com.mathworks.util.logger;







   public class DefaultUncaughtExceptionHandler
     implements Thread.UncaughtExceptionHandler
   {
     private static final Log LOG = Log.getInstance("com.mathworks.util.logger.DefaultUncaughtExceptionHandler");








     public void uncaughtException(Thread paramThread, Throwable paramThrowable)
     {
       LOG.error(paramThrowable);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\logger\DefaultUncaughtExceptionHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */