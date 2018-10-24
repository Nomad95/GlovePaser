   package temp.com.mathworks.util;

   import java.util.concurrent.ExecutorService;
   import java.util.concurrent.Executors;











   public class ThreadUtils
   {
     public static ExecutorService newSingleDaemonThreadExecutor(String paramString)
     {
       return Executors.newSingleThreadExecutor(new DaemonThreadFactory(paramString));
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ThreadUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */