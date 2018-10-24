   package temp.com.mathworks.util;

   import java.util.concurrent.ThreadFactory;



   public class DaemonThreadFactory
     implements ThreadFactory
   {
     private final String fThreadName;

     public DaemonThreadFactory(String paramString)
     {
       this.fThreadName = paramString;
     }

     public Thread newThread(Runnable paramRunnable) {
       Thread localThread = new Thread(paramRunnable, this.fThreadName);
       localThread.setDaemon(true);
       return localThread;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\DaemonThreadFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */