   package temp.com.mathworks.util.logger.impl;

   import com.mathworks.util.logger.Logger;
   import com.mathworks.util.logger.LoggerFactory;






   public class LegacyLoggerFactory
     implements LoggerFactory
   {
     public Logger createLogger(String paramString)
     {
       return LegacyLogger.getInstance();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\logger\impl\LegacyLoggerFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */