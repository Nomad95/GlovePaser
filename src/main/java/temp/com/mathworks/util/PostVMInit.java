   package temp.com.mathworks.util;

   import java.io.PrintStream;
   import java.util.ArrayList;
   import java.util.Arrays;
   import java.util.List;




















   public class PostVMInit
   {
     private static final List<StartupClass> STARTUP_CLASSES = new ArrayList(Arrays.asList(new StartupClass[] { new StartupClass("com.mathworks.util.OSGIInitializer", false, true, true, true), new StartupClass("com.mathworks.util.SystemPropertiesInitializer", true, false, true, false), new StartupClass("com.mathworks.services.Prefs", false, false, false, false), new StartupClass("com.mathworks.mlwidgets.html.HTMLPrefs", true, false, false, false), new StartupClass("com.mathworks.services.Log4JConfiguration", false, false, false, false), new StartupClass("com.mathworks.services.AntialiasedFontPrefs", false, false, false, false), new StartupClass("com.mathworks.mwswing.MJStartupForDesktop", true, true, true, false), new StartupClass("com.mathworks.mwswing.MJStartup", true, true, false, false), new StartupClass("com.mathworks.toolstrip.plaf.TSLookAndFeel", true, true, true, true), new StartupClass("com.mathworks.mlwidgets.prefs.InitialWorkingFolder", true, false, true, false), new StartupClass("com.mathworks.matlabserver.connector.api.AutoStart", false, false, false, false), new StartupClass("com.mathworks.util.DeleteOnExitShutdownInitializer", true, false, false, false), new StartupClass("com.mathworks.addons.launchers.TriggerAddOnsStartUpTasks", false, true, true, true) }));


















     private static final class StartupClass
     {
       private final String fClassName;
















       private final boolean fDisplayMessageIfError;
















       private final boolean fRequiresDisplay;
















       private final boolean fRequiresJVMCreatedByMatlab;
















       private final boolean fForbiddenInStandaloneMode;

















       StartupClass(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
       {
         this.fClassName = paramString;
         this.fDisplayMessageIfError = paramBoolean1;
         this.fRequiresDisplay = paramBoolean2;
         this.fRequiresJVMCreatedByMatlab = paramBoolean3;
         this.fForbiddenInStandaloneMode = paramBoolean4;
       }




       void init(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
       {
         if ((this.fRequiresDisplay) && (!paramBoolean1)) {
           return;
         }
         if ((this.fRequiresJVMCreatedByMatlab) && (!paramBoolean2)) {
           return;
         }
         if ((this.fForbiddenInStandaloneMode) && (paramBoolean3)) {
           return;
         }
         try
         {
           Class.forName(this.fClassName);
         }
         catch (Throwable localThrowable)
         {
           if (this.fDisplayMessageIfError)
           {
             System.err.println("PostVMInit failed to initialize " + this.fClassName);



             if (!$assertionsDisabled) throw new AssertionError(localThrowable);
             if (!$assertionsDisabled) { throw new AssertionError(localThrowable.getCause());
             }
           }
         }
       }
     }










     public static void perform(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
     {
       for (StartupClass localStartupClass : STARTUP_CLASSES)
       {
         localStartupClass.init(paramBoolean1, paramBoolean2, paramBoolean3);
       }
     }

     private static boolean sNativeUtilLibraryExists = false;


     static
     {
       if (NativeJavaSwitch.isLoadingEnabled())
       {
         try
         {
           System.loadLibrary("nativeutil");
           sNativeUtilLibraryExists = true;
         }
         catch (Throwable localThrowable) {}
       }
     }





     public static boolean nativeUtilLibraryExists()
     {
       return sNativeUtilLibraryExists;
     }







     public static void perform(boolean paramBoolean1, boolean paramBoolean2)
     {
       boolean bool = true;
       if (sNativeUtilLibraryExists)
       {
         bool = nativeIsStandaloneMode();
       }

       for (StartupClass localStartupClass : STARTUP_CLASSES)
       {
         localStartupClass.init(paramBoolean1, paramBoolean2, bool);
       }
     }





















     public static void addClassToStartupList(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
     {
       STARTUP_CLASSES.add(new StartupClass(paramString, false, paramBoolean1, paramBoolean2, paramBoolean3));
     }







     public static void clearStartupClassList()
     {
       STARTUP_CLASSES.clear();
     }

     private static native boolean nativeIsStandaloneMode();
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\PostVMInit.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */