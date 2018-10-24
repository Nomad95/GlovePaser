   package temp.com.mathworks.util;







   @Deprecated
   public class FileSystemUtils
   {
     private static FileSystemNotifier sFileSystemMonitor = new FileSystemNotifier();









     @Deprecated
     public static FileSystemNotifier getFileSystemNotifier()
     {
       return sFileSystemMonitor;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\FileSystemUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */