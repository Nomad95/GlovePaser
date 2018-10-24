   package temp.com.mathworks.util.logger;

   import com.mathworks.util.logger.impl.LegacyLoggerFactory;








   public class Log
   {
     private static boolean sThrowExceptionsOnErrors = false;

     private static volatile LoggerFactory sLoggerFactory = new LegacyLoggerFactory();


     private Logger fLogger;


     private final String fName;


     public Log(String paramString)
     {
       this.fName = paramString;
     }





     public Log(Class paramClass)
     {
       this(paramClass.getName());
     }

     private synchronized Logger getLogger() {
       if (this.fLogger == null) {
         this.fLogger = sLoggerFactory.createLogger(this.fName);
       }

       return this.fLogger;
     }



     public boolean isDebugEnabled()
     {
       return getLogger().isDebugEnabled();
     }






     public void debug(String paramString, Object... paramVarArgs)
     {
       Logger localLogger = getLogger();
       if (localLogger.isDebugEnabled()) {
         localLogger.debug(format(paramString, paramVarArgs));
       }
     }





     public void debug(Throwable paramThrowable)
     {
       Logger localLogger = getLogger();
       if ((localLogger.isDebugEnabled()) &&
         (paramThrowable != null)) { localLogger.debug(paramThrowable, "");
       }
     }







     public void debug(Throwable paramThrowable, String paramString, Object... paramVarArgs)
     {
       Logger localLogger = getLogger();
       if (localLogger.isDebugEnabled()) {
         localLogger.debug(format(paramString, paramVarArgs));
         if (paramThrowable != null) { localLogger.debug(paramThrowable, "");
         }
       }
     }







     public void error(String paramString, Object... paramVarArgs)
     {
       if (getLogger().isErrorEnabled()) {
         String str = format(paramString, paramVarArgs);
         error((Throwable)null, str, new Object[0]);
       }
     }







     public void error(Throwable paramThrowable)
     {
       error(paramThrowable, "", new Object[0]);
     }









     public void error(Throwable paramThrowable, String paramString, Object... paramVarArgs)
     {
       Logger localLogger = getLogger();
       if (localLogger.isErrorEnabled()) {
         String str = format(paramString, paramVarArgs);

         if (paramThrowable == null) localLogger.error(str); else {
           localLogger.error(paramThrowable, str);
         }
         if (isThrowExceptionsOnErrors()) {
           throw new RuntimeException(str, paramThrowable);
         }
       }
     }






     public void info(String paramString, Object... paramVarArgs)
     {
       Logger localLogger = getLogger();
       if (localLogger.isInfoEnabled()) {
         localLogger.info(format(paramString, paramVarArgs));
       }
     }







     public void info(Throwable paramThrowable, String paramString, Object... paramVarArgs)
     {
       Logger localLogger = getLogger();
       if (localLogger.isInfoEnabled()) {
         localLogger.info(paramThrowable, format(paramString, paramVarArgs));
       }
     }





     public void info(Throwable paramThrowable)
     {
       Logger localLogger = getLogger();
       if ((localLogger.isInfoEnabled()) &&
         (paramThrowable != null)) { localLogger.info(paramThrowable, "");
       }
     }






     public void warn(String paramString, Object... paramVarArgs)
     {
       Logger localLogger = getLogger();
       if (localLogger.isWarnEnabled()) {
         localLogger.warn(format(paramString, paramVarArgs));
       }
     }







     public void warn(Throwable paramThrowable, String paramString, Object... paramVarArgs)
     {
       Logger localLogger = getLogger();
       if (localLogger.isWarnEnabled()) {
         localLogger.warn(paramThrowable, format(paramString, paramVarArgs));
       }
     }





     public void warn(Throwable paramThrowable)
     {
       Logger localLogger = getLogger();
       if ((localLogger.isWarnEnabled()) &&
         (paramThrowable != null)) localLogger.warn(paramThrowable, "");
     }

     private String format(String paramString, Object... paramVarArgs)
     {
       if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
         return paramString;
       }
       return String.format(paramString, paramVarArgs);
     }



     public static boolean isThrowExceptionsOnErrors()
     {
       return sThrowExceptionsOnErrors;
     }



     public static void setThrowExceptionsOnErrors(boolean paramBoolean)
     {
       sThrowExceptionsOnErrors = paramBoolean;
     }




     public static void setLoggerFactory(LoggerFactory paramLoggerFactory)
     {
       sLoggerFactory = paramLoggerFactory;
     }






     public static Log getInstance(String paramString)
     {
       return new Log(paramString);
     }






     public static Log getInstance(Class paramClass)
     {
       return new Log(paramClass);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\logger\Log.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */