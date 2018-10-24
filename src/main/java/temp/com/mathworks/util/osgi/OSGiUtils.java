   package temp.com.mathworks.util.osgi;

   import java.io.File;
   import java.io.PrintStream;
   import java.net.MalformedURLException;
   import java.net.URL;
   import org.osgi.framework.BundleContext;
   import org.osgi.framework.ServiceReference;
   import org.osgi.service.startlevel.StartLevel;












   public class OSGiUtils
   {
     private static final String LOG_PREFIX = "[OSGi] ";
     private static boolean sDebugging = false;







     public static void setDebugging(boolean paramBoolean)
     {
       sDebugging = paramBoolean;
       log("Enabled debugging messages.");
     }






     public static void log(String paramString)
     {
       if (sDebugging) {
         System.out.println("[OSGi] " + Thread.currentThread().getId() + " " + paramString);
       }
     }









     public static String toReferenceUrl(File paramFile)
       throws MalformedURLException
     {
       if (paramFile == null) {
         throw new IllegalArgumentException("'file' cannot be null");
       }

       return "reference:" + paramFile.toURL().toString();
     }







     public static StartLevel getStartLevelService(BundleContext paramBundleContext)
     {
       ServiceReference localServiceReference = paramBundleContext.getServiceReference(StartLevel.class.getName());

       assert (localServiceReference != null) : "Failed to find StartLevel service";
       return (StartLevel)paramBundleContext.getService(localServiceReference);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\OSGiUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */