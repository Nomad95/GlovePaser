   package temp.com.mathworks.util.osgi;

   import com.mathworks.util.Log;
   import java.util.Collection;
   import java.util.Dictionary;
   import org.osgi.framework.Bundle;
   import org.osgi.framework.BundleContext;
   import org.osgi.framework.BundleException;
   import org.osgi.service.startlevel.StartLevel;



















   public class StartLevelBundleStarter
     implements BundleStarter
   {
     private static final String BUNDLE_START_LEVEL_ATTRIBUTE = "Bundle-Start-Level";

     public void startBundles(Bundle paramBundle, Collection<Bundle> paramCollection)
     {
       int i = adjustBundleStartLevels(paramBundle.getBundleContext(), paramCollection);



       for (Bundle localBundle : paramCollection) {
         try {
           OSGiUtils.log("Starting " + localBundle);
           localBundle.start();
         } catch (BundleException localBundleException) {
           Log.logException(localBundleException);
         }
       }





       OSGiUtils.log("Setting system start level to " + i);
       OSGiUtils.getStartLevelService(paramBundle.getBundleContext()).setStartLevel(i);
     }


     public void startBundleAtRuntime(Bundle paramBundle1, Bundle paramBundle2)
     {
       OSGiUtils.getStartLevelService(paramBundle1.getBundleContext()).setBundleStartLevel(paramBundle2, OSGiUtils.getStartLevelService(paramBundle1.getBundleContext()).getStartLevel());
       try {
         OSGiUtils.log("Starting " + paramBundle2);
         paramBundle2.start();
       } catch (BundleException localBundleException) {
         Log.logException(localBundleException);
       }
     }






     private static int adjustBundleStartLevels(BundleContext paramBundleContext, Collection<Bundle> paramCollection)
     {
       assert (OSGiUtils.getStartLevelService(paramBundleContext).getStartLevel() == 1) : "The system start level is not defaulted to 1; we assume that this is the case";

       int i = 1;
       for (Bundle localBundle : paramCollection) {
         int j = adjustStartLevel(paramBundleContext, localBundle);
         i = Math.max(i, j);
       }

       return i;
     }












     private static int adjustStartLevel(BundleContext paramBundleContext, Bundle paramBundle)
     {
       String str = (String)paramBundle.getHeaders().get("Bundle-Start-Level");
       int i;
       if (str != null) {
         i = Integer.parseInt(str);
         OSGiUtils.log("Setting start level for " + paramBundle + " to " + i);
       } else {
         i = OSGiUtils.getStartLevelService(paramBundleContext).getStartLevel() + 1;
         OSGiUtils.log("No Bundle-Start-Level Manifest attribute found for " + paramBundle + ", so setting to " + i);
       }

       OSGiUtils.getStartLevelService(paramBundleContext).setBundleStartLevel(paramBundle, i);
       return i;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\StartLevelBundleStarter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */