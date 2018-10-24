   package temp.com.mathworks.util;

   import java.awt.GraphicsEnvironment;
   import java.lang.reflect.Method;








   public class ResolutionUtils
   {
     public static final int PLATFORM_DPI = PlatformInfo.isMacintosh() ? 72 : 96;



     public static final int DPI = (!isHighDPISupportEnabled()) || (GraphicsEnvironment.isHeadless()) || (PlatformInfo.isLinux()) ? PLATFORM_DPI : PlatformInfo.getVirtualScreenDPI();
     public static final int REFERENCE_DPI = Math.min(DPI, PLATFORM_DPI);
     private static double USER_SCALE_FACTOR = 1.0D;

     static {
       try { Class localClass = Class.forName("com.mathworks.services.DisplayScaleFactorSetting");
         Method localMethod = localClass.getMethod("getValue", new Class[0]);
         USER_SCALE_FACTOR = ((Double)localMethod.invoke(null, new Object[0])).doubleValue();
       }
       catch (Exception localException) {}
     }




     public static boolean scalingEnabled()
     {
       return (USER_SCALE_FACTOR > 1.0D) || (DPI != REFERENCE_DPI);
     }




     public static boolean performingUserSpecifiedScaling()
     {
       return USER_SCALE_FACTOR > 1.0D;
     }

     public static double userSpecifiedScaleFactor() {
       return USER_SCALE_FACTOR;
     }



     public static int scaleSize(int paramInt)
     {
       return (int)(USER_SCALE_FACTOR * DPI * paramInt / REFERENCE_DPI);
     }



     public static int unScaleSize(int paramInt)
     {
       return (int)(REFERENCE_DPI * paramInt / (DPI * USER_SCALE_FACTOR));
     }






     private static boolean isHighDPISupportEnabled()
     {
       return NativeJava.nativeLibraryExists();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ResolutionUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */