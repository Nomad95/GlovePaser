   package temp.com.mathworks.util.osgi;

   import java.util.Properties;
   import org.osgi.framework.BundleActivator;
   import org.osgi.framework.BundleContext;
















   public class FinalBundleActivator
     implements BundleActivator
   {
     public void start(BundleContext paramBundleContext)
       throws Exception
     {
       paramBundleContext.registerService(FinalInterface.class.getName(), new DummyService(null), new Properties());
     }

     public void stop(BundleContext paramBundleContext)
       throws Exception
     {}

     private static class DummyService
       implements FinalInterface
     {}

     public static abstract interface FinalInterface {}
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\FinalBundleActivator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */