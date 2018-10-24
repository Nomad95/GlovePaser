   package temp.com.mathworks.util;






   public class ClassLoaderBridge
   {
     private static ClassLoaderSupplier sSupplier;






     public static Class findClass(String paramString)
       throws ClassNotFoundException
     {
       if (sSupplier == null) {
         return Class.forName(paramString);
       }
       return sSupplier.findClass(paramString);
     }

     public static void setSupplier(ClassLoaderSupplier paramClassLoaderSupplier) { sSupplier = paramClassLoaderSupplier; }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ClassLoaderBridge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */