   package temp.com.mathworks.util;

   import java.util.concurrent.ThreadFactory;
   import org.jetbrains.annotations.NotNull;

   public final class NamedDaemonThreadFactory
     implements ThreadFactory
   {
     private final String fName;

     public NamedDaemonThreadFactory(String paramString)
     {
       this.fName = paramString;
     }

     @NotNull
     public Thread newThread(@NotNull Runnable paramRunnable)
     {
       Thread localThread = new Thread(paramRunnable, this.fName);
       localThread.setDaemon(true);
       return localThread;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\NamedDaemonThreadFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */