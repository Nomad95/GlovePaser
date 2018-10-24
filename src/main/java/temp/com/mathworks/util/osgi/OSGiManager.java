   package temp.com.mathworks.util.osgi;

   import com.mathworks.util.Log;
   import java.io.File;
   import java.io.IOException;
   import java.net.MalformedURLException;
   import java.nio.file.Path;
   import java.rmi.server.UID;
   import java.util.ArrayList;
   import java.util.Arrays;
   import java.util.Collection;
   import java.util.Collections;
   import java.util.HashSet;
   import java.util.List;
   import org.apache.commons.io.FileUtils;
   import org.apache.commons.lang.SystemUtils;
   import org.apache.felix.framework.Felix;
   import org.apache.felix.framework.util.StringMap;
   import org.osgi.framework.Bundle;
   import org.osgi.framework.BundleActivator;
   import org.osgi.framework.BundleContext;
   import org.osgi.framework.BundleException;
   import org.osgi.util.tracker.ServiceTracker;


































   public final class OSGiManager
   {
     private static final int BUNDLE_INITIAL_START_LEVEL = Integer.MAX_VALUE;
     public static final String FINAL_BUNDLE_NAME = "com.mathworks.util.final-bundle";
     private final Felix fFelix;
     private final List<BundleProvider> fBundleProviders;
     private final BundleStarter fBundleStarter;
     private HostActivator fHostActivator;
     private Collection<Bundle> fBundles;
     private boolean fStartedBundles;
     private File fCacheDir;
     private Exception fStopException;

     public OSGiManager(String paramString, BundleStarter paramBundleStarter, BundleProvider... paramVarArgs)
     {
       if ((paramString == null) || (paramString.length() == 0))
         throw new IllegalArgumentException("'cacheDirName' cannot be null or empty");
       if ((paramVarArgs == null) || (paramVarArgs.length == 0))
         throw new IllegalArgumentException("'bundleProvider' cannot be null or empty");
       if (paramBundleStarter == null) {
         throw new IllegalArgumentException("'bundleStarter' cannot be null");
       }
       this.fHostActivator = new HostActivator();
       StringMap localStringMap = createDefaultConfiguration(this.fHostActivator);
       setupCache(paramString, localStringMap);

       this.fBundleProviders = Arrays.asList(paramVarArgs);
       this.fBundleStarter = paramBundleStarter;

       this.fFelix = new Felix(localStringMap);
     }




     public Collection<Bundle> getInstalledBundles()
     {
       return Collections.unmodifiableCollection(getBundles());
     }



     public synchronized void start()
     {
       if (!this.fStartedBundles) {
         this.fBundleStarter.startBundles(this.fFelix, getBundles());
         this.fStartedBundles = true;
       }
     }




     public synchronized void stop()
     {
       try
       {
         if (this.fFelix.getState() != 1) {
           OSGiUtils.log("Stopping OSGi.");
           this.fFelix.stop();
           this.fFelix.waitForStop(0L);
           removeCacheDirectory();
         }
       }
       catch (Exception localException) {
         localException.printStackTrace();
         this.fStopException = localException;
       }
     }





     private Exception getStopException()
     {
       return this.fStopException;
     }




     public BundleContext getBundleContext()
     {
       return this.fFelix.getBundleContext();
     }











     public <T> Collection<T> getImplementors(Class<T> paramClass)
     {
       OSGiUtils.log("getImplementors(" + paramClass + ")");
       start();
       waitForFinalBundle();
       ServiceTracker localServiceTracker = new ServiceTracker(this.fHostActivator.getContext(), paramClass.getName(), null);

       localServiceTracker.open();

       Object[] arrayOfObject = (Object[])localServiceTracker.getServices();
       localServiceTracker.close();
       return arrayOfObject == null ? Collections.emptyList() : Arrays.asList(arrayOfObject);
     }








     public Bundle findBundle(String paramString)
     {
       if ((paramString == null) || (paramString.length() == 0))
         throw new IllegalArgumentException("'symbolicName' cannot be null or empty");
       if (this.fFelix.getBundleContext() == null)
         throw new IllegalStateException("Felix has not been started");
       for (Bundle localBundle : this.fFelix.getBundleContext().getBundles()) {
         if (localBundle.getSymbolicName().equals(paramString))
           return localBundle;
       }
       return null;
     }






     public File getCacheDirectory()
     {
       return this.fCacheDir;
     }




     private synchronized Collection<Bundle> getBundles()
     {
       if (this.fBundles == null)
       {
         Runnable local1 = new Runnable()
         {
           public void run() {
             try {
               OSGiUtils.log("Starting OSGi on daemon thread...");
               OSGiManager.this.fFelix.start();
             } catch (Exception localException) {
               localException.printStackTrace();
             }
           }
         };
         Thread localThread = new Thread(local1, "OSGi Starter");
         localThread.setDaemon(true);
         localThread.start();


         try
         {
           localThread.join();
         } catch (InterruptedException localInterruptedException) {
           Log.logException(localInterruptedException);
         }

         HashSet localHashSet = new HashSet();
         for (BundleProvider localBundleProvider : this.fBundleProviders) {
           localHashSet.addAll(localBundleProvider.getBundleJarFiles(this.fFelix));
         }

         OSGiUtils.log("About to install: " + localHashSet);
         this.fBundles = installBundles(localHashSet);
       }
       return this.fBundles;
     }



     private List<Bundle> installBundles(Collection<File> paramCollection)
     {
       ArrayList localArrayList = new ArrayList();
       for (File localFile : paramCollection) {
         try {
           Bundle localBundle = this.fFelix.getBundleContext().installBundle(OSGiUtils.toReferenceUrl(localFile));

           OSGiUtils.log("Installed " + localBundle);
           localArrayList.add(localBundle);
         } catch (Exception localException) {
           Log.logException(localException);
           localException.printStackTrace();
         }
       }
       return localArrayList;
     }







     public void installAndStartBundleAtRuntime(Class paramClass, Path paramPath)
     {
       Bundle localBundle = null;
       try {
         String str = OSGiUtils.toReferenceUrl(paramPath.toFile());
         localBundle = this.fFelix.getBundleContext().installBundle(str);
       } catch (BundleException|MalformedURLException localBundleException) {
         Log.logException(localBundleException);
         return;
       }
       this.fBundleStarter.startBundleAtRuntime(this.fFelix, localBundle);
       waitForRuntimeBundleToStart(localBundle, paramClass);
     }

     private void waitForRuntimeBundleToStart(Bundle paramBundle, Class paramClass) {
       OSGiUtils.log("waitForRuntimeBundleToStart");

       if (findBundle(paramBundle.getSymbolicName()) == null) {
         if (!$assertionsDisabled) throw new AssertionError("OSGi error: The bundle " + paramBundle.toString() + "was not installed");
         return;
       }
       paramBundle.getClass().getName();

       ServiceTracker localServiceTracker = new ServiceTracker(this.fHostActivator.getContext(), paramClass.getName(), null);

       localServiceTracker.open();
       try {
         localServiceTracker.waitForService(0L);
       } catch (InterruptedException localInterruptedException) {
         OSGiUtils.log(localInterruptedException.getMessage());
       }

       localServiceTracker.close();
       OSGiUtils.log("done with waitForRuntimeBundleToStart");
     }




     private void waitForFinalBundle()
     {
       OSGiUtils.log("waitForFinalBundle");



       if (findBundle("com.mathworks.util.final-bundle") == null) {
         if (!$assertionsDisabled) throw new AssertionError("OSGi error: The util.jar bundle was not installed");
         return;
       }



       ServiceTracker localServiceTracker = new ServiceTracker(this.fHostActivator.getContext(), FinalBundleActivator.FinalInterface.class.getName(), null);

       localServiceTracker.open();
       try {
         localServiceTracker.waitForService(0L);
       }
       catch (InterruptedException localInterruptedException) {}


       localServiceTracker.close();
       OSGiUtils.log("Done with waitForFinalBundle");
     }

     private static StringMap createDefaultConfiguration(BundleActivator paramBundleActivator) {
       StringMap localStringMap = new StringMap(false);


       localStringMap.put("org.osgi.framework.system.packages", "org.osgi.framework; version=1.3.0,org.osgi.service.packageadmin; version=1.2.0,org.osgi.service.startlevel; version=1.0.0,org.osgi.service.url; version=1.0.0,org.osgi.util.tracker; version=1.3.2");








       localStringMap.put("org.osgi.framework.bootdelegation", "*");

       localStringMap.put("felix.service.urlhandlers", "false");


       localStringMap.put("felix.log.level", "1");



       localStringMap.put("felix.startlevel.bundle", String.valueOf(Integer.MAX_VALUE));



       localStringMap.put("felix.systembundle.activators", new ArrayList(Collections.singletonList(paramBundleActivator)));


       return localStringMap;
     }



     private void setupCache(String paramString, StringMap paramStringMap)
     {
       setupNewCacheDirectory(paramString, paramStringMap);
       registerShutdownHook();
     }



     private void registerShutdownHook()
     {
       Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
       {

         public void run() { OSGiManager.this.stop(); } }, "OSGiManager ShutdownHook"));




       try
       {
         Class.forName("com.mathworks.jmi.OSGiShutdownHook");
       }
       catch (ClassNotFoundException localClassNotFoundException) {
         OSGiUtils.log("OSGiShutdownHook not on classpath.");
       }
     }

     private void removeCacheDirectory() {
       try {
         OSGiUtils.log("Removing cache directory " + this.fCacheDir);
         FileUtils.deleteDirectory(this.fCacheDir);
       }
       catch (Exception localException)
       {
         OSGiUtils.log("Unable to remove cache directory " + this.fCacheDir + ": " + localException.getMessage());
       }
     }


     private void setupNewCacheDirectory(String paramString, StringMap paramStringMap)
     {
       String[] arrayOfString = { System.getenv("TMPDIR"), System.getenv("TMP"), System.getenv("TEMP"), SystemUtils.JAVA_IO_TMPDIR, System.getenv("HOME"), SystemUtils.USER_HOME };







       try
       {
         boolean bool = false;
         for (String str : arrayOfString) {
           if (str != null) {
             bool = tryMakingDirectory(str, paramString);
             if (bool) {
               break;
             }
           }
         }

         if (!bool) {
           ??? = File.listRoots();
           if ((??? != null) && (???.length > 0)) {
             bool = tryMakingDirectory(???[0].getAbsolutePath(), paramString);
           } else {
             Log.log("OSGi: No file system roots available: " + Arrays.toString((Object[])???));
           }
         }
         if (!bool) {
           throw new IOException("Failed to create " + this.fCacheDir);
         }

         OSGiUtils.log("Created cache directory " + this.fCacheDir);



         paramStringMap.put("org.osgi.framework.storage", this.fCacheDir.getAbsolutePath());
       } catch (IOException localIOException) {
         if (!$assertionsDisabled) throw new AssertionError(localIOException);
       }
     }

     private boolean tryMakingDirectory(String paramString1, String paramString2) {
       File localFile = new File(paramString1, ".felix");


       String str = new UID().toString().replace(':', '_');
       this.fCacheDir = new File(localFile, paramString2 + str);

       boolean bool;
       if (this.fCacheDir.exists()) {
         OSGiUtils.log("Directory " + this.fCacheDir + " unexpectedly already exists");
         bool = true;
       } else {
         bool = this.fCacheDir.mkdirs();
         OSGiUtils.log("Trying to create cache directory " + this.fCacheDir + ": " + bool);
       }
       return bool;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\OSGiManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */