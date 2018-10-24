   package temp.com.mathworks.util;

   import java.io.IOException;
   import java.nio.file.Path;








   public final class FileAttributeUtils
   {
     public static void setHidden(Path paramPath)
       throws IOException
     {
       com.mathworks.cfbutils.FileAttributeUtils.setHidden(paramPath);
     }





     public static boolean isHidden(Path paramPath)
       throws IOException
     {
       return com.mathworks.cfbutils.FileAttributeUtils.isHidden(paramPath);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\FileAttributeUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */