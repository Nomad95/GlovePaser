   package temp.com.mathworks.util;

   import com.mathworks.util.osgi.ClasspathOSGiManager;
   import java.util.concurrent.ExecutorService;


   public final class OSGIInitializer
   {
     static
     {
       ExecutorService localExecutorService = ThreadUtils.newSingleDaemonThreadExecutor("Initialize OSGI on MATLAB Start-up");
       localExecutorService.submit(new Runnable()
       {

         public void run()
         {

           ClasspathOSGiManager.getInstance().getImplementors(getClass());
         }
       });
       localExecutorService.shutdown();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\OSGIInitializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */