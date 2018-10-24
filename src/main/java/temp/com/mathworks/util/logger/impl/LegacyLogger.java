   package temp.com.mathworks.util.logger.impl;

   import com.mathworks.util.Log;
   import com.mathworks.util.logger.Logger;




   public class LegacyLogger
     implements Logger
   {
     private static final Logger INSTANCE = new LegacyLogger();




     private static final boolean DEBUG = "true".equalsIgnoreCase(System.getProperty("logger.debug"));

     public static Logger getInstance()
     {
       return INSTANCE;
     }

     public boolean isDebugEnabled() {
       return (DEBUG) && (Log.LOGGING);
     }

     public boolean isErrorEnabled() {
       return Log.LOGGING;
     }

     public boolean isInfoEnabled() {
       return Log.LOGGING;
     }

     public boolean isWarnEnabled() {
       return Log.LOGGING;
     }

     public void debug(String paramString) {
       Log.printLn(paramString);
     }

     public void debug(Throwable paramThrowable, String paramString) {
       Log.printLn(paramString);
       Log.logThrowable(paramThrowable);
     }

     public void error(String paramString) {
       Log.printLn(paramString);
     }

     public void error(Throwable paramThrowable, String paramString) {
       Log.printLn(paramString);
       Log.logThrowable(paramThrowable);
     }

     public void info(String paramString) {
       Log.printLn(paramString);
     }

     public void info(Throwable paramThrowable, String paramString) {
       Log.printLn(paramString);
       Log.logThrowable(paramThrowable);
     }

     public void warn(String paramString) {
       Log.printLn(paramString);
     }

     public void warn(Throwable paramThrowable, String paramString) {
       Log.printLn(paramString);
       Log.logThrowable(paramThrowable);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\logger\impl\LegacyLogger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */