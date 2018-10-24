   package temp.com.mathworks.util;












   public class NativeJavaSwitch
   {
     static boolean fIsLoadingEnabled = Boolean.valueOf(System.getProperty("mathworks.nativeJavaLoadingEnabled", "true")).booleanValue();





     public static void setLoadingEnabled(boolean paramBoolean)
     {
       fIsLoadingEnabled = paramBoolean;
     }

     public static boolean isLoadingEnabled()
     {
       return fIsLoadingEnabled;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\NativeJavaSwitch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */