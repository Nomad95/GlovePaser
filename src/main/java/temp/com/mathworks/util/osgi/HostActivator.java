   package temp.com.mathworks.util.osgi;

   import org.osgi.framework.BundleActivator;
   import org.osgi.framework.BundleContext;



   public class HostActivator
     implements BundleActivator
   {
     private BundleContext fContext;

     public void start(BundleContext paramBundleContext)
       throws Exception
     {
       this.fContext = paramBundleContext;
     }

     public void stop(BundleContext paramBundleContext) throws Exception {
       this.fContext = null;
     }






     public BundleContext getContext()
     {
       return this.fContext;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\HostActivator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */