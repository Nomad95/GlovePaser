   package temp.com.mathworks.util;

   import com.mathworks.cfbutils.FileSystemPollingChangeNotification;
   import java.nio.file.Path;
   import java.util.List;







   @Deprecated
   public final class PollingChangeNotification
   {
     private final FileSystemPollingChangeNotification fileSystemPollingChangeNotification;

     @Deprecated
     public PollingChangeNotification(Path paramPath, String paramString)
     {
       this.fileSystemPollingChangeNotification = new FileSystemPollingChangeNotification(paramPath, paramString);
     }



     @Deprecated
     public void add(Path paramPath)
     {
       this.fileSystemPollingChangeNotification.add(paramPath);
     }



     @Deprecated
     public Path getFolderChanged()
     {
       return this.fileSystemPollingChangeNotification.getFolderChanged();
     }



     @Deprecated
     public List<Path> getfListOfChangedFiles()
     {
       return this.fileSystemPollingChangeNotification.getfListOfChangedFiles();
     }



     @Deprecated
     public String getSourceIdentifier()
     {
       return this.fileSystemPollingChangeNotification.getSourceIdentifier();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\PollingChangeNotification.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */