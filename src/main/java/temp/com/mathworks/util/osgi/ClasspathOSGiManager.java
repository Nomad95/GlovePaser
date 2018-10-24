   package temp.com.mathworks.util.osgi;

   import java.nio.file.Path;
   import java.util.ArrayList;
   import java.util.Collection;
   import java.util.List;
   import org.osgi.framework.Bundle;













   public class ClasspathOSGiManager
   {
     private static final String CACHE_DIR_BASENAME = "osgi-cache";
     private static ClasspathOSGiManager sInstance;
     private OSGiManager fManager;
     private List<Runnable> fStoppingListeners = new ArrayList();





     public static synchronized ClasspathOSGiManager getInstance()
     {
       if (sInstance == null)
         sInstance = new ClasspathOSGiManager();
       return sInstance;
     }

     private ClasspathOSGiManager() {
       this.fManager = new OSGiManager("osgi-cache", new StartLevelBundleStarter(), new BundleProvider[] { new ClasspathBundleProvider() });
     }





     public <T> Collection<T> getImplementors(Class<T> paramClass)
     {
       return this.fManager.getImplementors(paramClass);
     }



     public Collection<Bundle> getInstalledBundles()
     {
       return this.fManager.getInstalledBundles();
     }



     public void stop()
     {
       for (Runnable localRunnable : this.fStoppingListeners) {
         localRunnable.run();
       }
       this.fManager.stop();
     }



     public void addStoppingListener(Runnable paramRunnable)
     {
       this.fStoppingListeners.add(paramRunnable);
     }




     public void installAndStartBundle(Class paramClass, Path paramPath)
     {
       this.fManager.installAndStartBundleAtRuntime(paramClass, paramPath);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\ClasspathOSGiManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */