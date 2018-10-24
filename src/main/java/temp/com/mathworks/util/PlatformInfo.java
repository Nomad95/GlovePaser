   package temp.com.mathworks.util;

   import java.awt.Toolkit;
   import java.beans.PropertyChangeEvent;
   import java.beans.PropertyChangeListener;
   import java.nio.ByteOrder;




































































   public final class PlatformInfo
   {
     /**
      * @deprecated

     public static final int DEFAULT_APPEARANCE = -1;
     public static final int MACINTOSH = 0;
     public static final int WINDOWS = 1;
     public static final int UNIX = 2;
     public static final int UNKNOWN = 0;
     public static final int APPLE = 2;
     public static final int VERSION_118 = 1;
     public static final int VERSION_117 = 2;
     public static final int VERSION_12 = 3;
     public static final int VERSION_13 = 4;
     public static final int VERSION_14 = 5;
     public static final int VERSION_15 = 6;
     public static final int VERSION_16 = 7;
     public static final int VERSION_17 = 8;
     public static final int VERSION_18 = 9;
     public static final int UNKNOWN_VM_VERSION = Integer.MAX_VALUE;
     private static final int UNINITIALIZED = -2;
     public static final int CLASSIC = -1;
     public static final int BLUE = 0;
     public static final int GREEN = 1;
     public static final int SILVER = 2;
     private static final int WIN_98 = 1;
     private static final int WIN_NT = 2;
     private static final int WIN_ME = 3;
     private static final int WIN_2000 = 4;
     private static final int WIN_XP = 5;
     private static final int WIN_VISTA = 6;
     private static final int WIN_7 = 7;
     private static final int LINUX = 8;
     private static final int SOLARIS = 9;
     private static final int LINUX_64 = 10;
     private static final int TIGER = 11;
     private static final int LEOPARD = 12;
     private static final int WIN_8 = 13;
     private static final int WIN_LATEST = 100;
     private static final int POWERPC = 0;
     private static final int INTEL = 1;
     private static int sPlatform;
     private static int sVersion;
     private static int sOSVersion;
     private static int sScheme;
     private static int sMacArch;
     private static boolean sIs64Bit;
     private static boolean sIsBigEndian;
     private static final String XP_THEME_ACTIVE_PROPERTY = "win.xpstyle.themeActive";
     private static final String XP_THEME_COLOR_PROPERTY = "win.xpstyle.colorName";
     public static final int MAC_THEME_BLUE = 1;
     public static final int MAC_THEME_GRAPHITE = 6;

     static
     {
       sScheme = -2;
































       sPlatform = determinePlatform();
       sVersion = determineVersion();

       if (isWindowsXP())
       {

         PropertyChangeListener local1 = new PropertyChangeListener() {
           public void propertyChange(PropertyChangeEvent paramAnonymousPropertyChangeEvent) {
             PlatformInfo.access$002(-2);
           }
         };
         Toolkit localToolkit = Toolkit.getDefaultToolkit();


         localToolkit.getDesktopProperty("win.xpstyle.themeActive");
         localToolkit.getDesktopProperty("win.xpstyle.colorName");
         localToolkit.addPropertyChangeListener("win.xpstyle.themeActive", local1);
         localToolkit.addPropertyChangeListener("win.xpstyle.colorName", local1);
       }
     }


     private static int determinePlatform()
     {
       String str1 = System.getProperty("sun.arch.data.model");
       sIs64Bit = (str1 != null) && (str1.equals("64"));

       sIsBigEndian = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

       String str2 = System.getProperty("os.name");
       String str3;
       int i;
       if (str2.startsWith("Mac OS"))
       {
         str3 = System.getProperty("os.version");

         if (str3.startsWith("10.4")) {
           sOSVersion = 11;
         } else if (str3.startsWith("10.5")) {
           sOSVersion = 12;
         }

         i = 0;
         String str4 = System.getProperty("os.arch");
         if ("ppc".equals(str4)) {
           sMacArch = 0;
         } else if (("i386".equals(str4)) || ("x86_64".equals(str4))) {
           sMacArch = 1;
         }
       } else if (str2.startsWith("Windows"))
       {
         i = 1;

         if (str2.contains("98")) {
           sOSVersion = 1;
         } else if (str2.contains("ME")) {
           sOSVersion = 3;
         } else if (str2.contains("NT")) {
           sOSVersion = 2;


           str3 = System.getProperty("os.version");
           if ((str3 != null) && (str3.length() > 0) && (str3.charAt(0) == '6')) {
             sOSVersion = 6;
           }
         } else if (str2.contains("2000"))
         {

           sOSVersion = 4;
           str3 = System.getProperty("os.version");
           if ((str3 != null) && (str3.length() > 2)) {
             char c1 = str3.charAt(0);
             char c2 = str3.charAt(2);
             if ((Character.isDigit(c1)) && (Character.isDigit(c2))) {
               int j = Character.getNumericValue(c1);
               int k = Character.getNumericValue(c2);
               if (((j == 5) && (k > 0)) || (j > 5)) {
                 sOSVersion = 5;
               }
             }
           }
         } else if (str2.contains("2003")) {
           sOSVersion = 5;
         } else if (str2.contains("XP")) {
           sOSVersion = 5;
         } else if (str2.contains("Vista")) {
           sOSVersion = 6;
         } else if (str2.contains("Windows 7")) {
           sOSVersion = 7;
         } else if (str2.contains("Windows 8")) {
           sOSVersion = 13;
         }
         else {
           sOSVersion = 100;
         }
       }
       else {
         i = 2;
         if (str2.contains("Linux"))
         {
           str3 = System.getProperty("os.arch");
           if ((str3 != null) && (str3.contains("64"))) {
             sOSVersion = 10;
           } else {
             sOSVersion = 8;
           }
         } else if (str2.contains("SunOS")) {
           sOSVersion = 9;
         }
       }
       return i;
     }






     public static int getPlatform()
     {
       return sPlatform;
     }





     public static boolean isMacintosh()
     {
       return sPlatform == 0;
     }





     public static boolean isIntelMac()
     {
       return (isMacintosh()) && (sMacArch == 1);
     }





     public static boolean isIntelMac64()
     {
       return (isMacintosh()) && (sMacArch == 1) && (sIs64Bit);
     }


     /**
      * @deprecated

     public static boolean isMacOSLeopardOrLater()
     {
       return isMacintosh();
     }





     public static boolean isUnix()
     {
       return (sPlatform == 2) || (sPlatform == 0);
     }





     public static boolean isXWindows()
     {
       return sPlatform == 2;
     }





     public static boolean isWindows()
     {
       return sPlatform == 1;
     }





     public static boolean isWindows2000()
     {
       return sOSVersion == 4;
     }





     public static boolean isWindowsXP()
     {
       return sOSVersion == 5;
     }





     public static boolean isWindowsVista()
     {
       return sOSVersion == 6;
     }






     public static boolean isWindowsVistaAndAbove()
     {
       return (isWindows()) && (sOSVersion >= 6);
     }





     public static boolean isWindows64()
     {
       return (isWindows()) && (sIs64Bit);
     }





     public static boolean isWindows7()
     {
       return (isWindows()) && (sOSVersion == 7);
     }





     public static boolean isWindows8()
     {
       return (isWindows()) && (sOSVersion == 13);
     }




     public static boolean isLinux()
     {
       return (sOSVersion == 8) || (sOSVersion == 10);
     }





     public static boolean isLinux64()
     {
       return sOSVersion == 10;
     }





     public static boolean isSolaris()
     {
       return sOSVersion == 9;
     }





     public static boolean isSolaris64()
     {
       return (isSolaris()) && (sIs64Bit);
     }





     public static boolean is64Bit()
     {
       return sIs64Bit;
     }





     public static boolean isBigEndian()
     {
       return sIsBigEndian;
     }



     /**
      * @deprecated

     public static boolean isBlackdownVM()
     {
       return false;
     }

     private static int determineVersion()
     {
       String str = System.getProperty("java.version");

       int i;
       if (str.startsWith("1.1.8")) {
         i = 1;
       } else if (str.startsWith("1.3")) {
         i = 4;
       } else if (str.startsWith("1.4")) {
         i = 5;
       } else if (str.startsWith("1.5")) {
         i = 6;
       } else if (str.startsWith("1.6")) {
         i = 7;
       } else if (str.startsWith("1.7")) {
         i = 8;
       } else if (str.startsWith("1.8")) {
         i = 9;
       } else if (str.startsWith("1.2")) {
         i = 3;
       } else if (str.startsWith("1.1.7")) {
         i = 2;
       } else {
         i = Integer.MAX_VALUE;
       }
       return i;
     }







     public static int getVersion()
     {
       return sVersion;
     }





     public static boolean isVersion118()
     {
       return sVersion == 1;
     }





     public static boolean isVersion14()
     {
       return sVersion == 5;
     }





     public static boolean isVersion15()
     {
       return sVersion == 6;
     }





     public static boolean isVersion16()
     {
       return sVersion == 7;
     }





     public static boolean isVersion17()
     {
       return sVersion == 8;
     }





     public static boolean isVersion18()
     {
       return sVersion == 9;
     }






     public static boolean isVersion2()
     {
       return (sVersion != 2) && (sVersion != 1);
     }







     /**
      * @deprecated

     public static int getAppearance()
     {
       return getPlatform();
     }









     /**
      * @deprecated

     public static boolean useWindowsXPAppearance()
     {
       return isWindowsModernAppearance();
     }








     public static boolean isWindowsXPAppearance()
     {
       return (isWindowsXP()) && (!isWindowsClassicAppearance());
     }









     public static boolean isWindowsVistaAppearance()
     {
       return (isWindowsVistaAndAbove()) && (!isWindowsClassicAppearance());
     }








     public static boolean isWindows8Appearance()
     {
       return (isWindows8AndAbove()) && (!isWindowsClassicAppearance());
     }





     public static boolean isWindows8AndAbove()
     {
       return (isWindows()) && (sOSVersion >= 13);
     }












     public static boolean isWindowsModernAppearance()
     {
       return (isWindows()) && (!isWindowsClassicAppearance());
     }





     public static boolean isWindowsClassicAppearance()
     {
       return (isWindows()) && (getWindowsColorScheme() == -1);
     }



     /**
      * @deprecated

     public static int getWindowsXPScheme()
     {
       return getWindowsColorScheme();
     }













     public static int getWindowsColorScheme()
     {
       if (sScheme == -2)
       {
         Toolkit localToolkit = Toolkit.getDefaultToolkit();
         Boolean localBoolean = (Boolean)localToolkit.getDesktopProperty("win.xpstyle.themeActive");
         if ((localBoolean == null) || (!localBoolean.booleanValue())) {
           sScheme = -1;
         }
         else {
           String str = (String)localToolkit.getDesktopProperty("win.xpstyle.colorName");
           if ((str == null) || (str.equals("NormalColor"))) {
             sScheme = 0;
           } else if (str.equals("HomeStead")) {
             sScheme = 1;
           } else if (str.equals("Metallic")) {
             sScheme = 2;
           } else
             sScheme = 0;
         }
       }
       return sScheme;
     }

















     public static int getVirtualScreenDPI()
     {
       return isMacintosh() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
     }














     public static int getPhysicalScreenDPI()
     {
       return Toolkit.getDefaultToolkit().getScreenResolution();
     }







     public static int getMacAppearanceTheme()
     {
       assert (isMacintosh()) : "Call PlatformInfo.isMacintosh() before calling getMacAppearanceTheme";

       return NativeJava.getMacControlTint();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\PlatformInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */