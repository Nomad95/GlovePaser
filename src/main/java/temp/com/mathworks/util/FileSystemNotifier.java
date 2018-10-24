   package temp.com.mathworks.util;

   import com.mathworks.cfbutils.FileSystemNotificationUtils;
   import com.mathworks.cfbutils.FileSystemPollingChangeListener;
   import com.mathworks.cfbutils.FileSystemPollingChangeNotification;
   import java.io.File;
   import java.nio.file.Path;
   import java.util.HashMap;
   import java.util.List;
   import java.util.Map;









   @Deprecated
   public class FileSystemNotifier
   {
     private final Map<PollingChangeListener, FileSystemPollingChangeListener> pollingChangeListenerMap = new HashMap();
     private final Map<FileSystemListener, com.mathworks.cfbutils.FileSystemListener> fileSystemListenerMap = new HashMap();





     @Deprecated
     public void created(File paramFile)
     {
       FileSystemNotificationUtils.created(paramFile);
     }





     @Deprecated
     public void changed(File paramFile)
     {
       FileSystemNotificationUtils.changed(paramFile);
     }






















     @Deprecated
     public void moved(File paramFile1, File paramFile2)
     {
       FileSystemNotificationUtils.moved(paramFile1, paramFile2);
     }





     @Deprecated
     public synchronized void deleted(File paramFile)
     {
       FileSystemNotificationUtils.deleted(paramFile);
     }











     @Deprecated
     public synchronized void addFileSystemListener(final FileSystemListener paramFileSystemListener)
     {
       com.mathworks.cfbutils.FileSystemListener local1 = new com.mathworks.cfbutils.FileSystemListener()
       {
         public void fileCreated(File paramAnonymousFile)
         {
           paramFileSystemListener.fileCreated(paramAnonymousFile);
         }

         public void fileMoved(File paramAnonymousFile1, File paramAnonymousFile2)
         {
           paramFileSystemListener.fileMoved(paramAnonymousFile1, paramAnonymousFile2);
         }

         public void fileDeleted(File paramAnonymousFile)
         {
           paramFileSystemListener.fileDeleted(paramAnonymousFile);
         }

         public void fileChanged(File paramAnonymousFile)
         {
           paramFileSystemListener.fileChanged(paramAnonymousFile);
         }
       };
       FileSystemNotificationUtils.addFileSystemListener(local1);
       this.fileSystemListenerMap.put(paramFileSystemListener, local1);
     }











     @Deprecated
     public synchronized void removeFileSystemListener(FileSystemListener paramFileSystemListener)
     {
       if (this.fileSystemListenerMap.containsKey(paramFileSystemListener)) {
         FileSystemNotificationUtils.removeFileSystemListener((com.mathworks.cfbutils.FileSystemListener)this.fileSystemListenerMap.get(paramFileSystemListener));
         this.fileSystemListenerMap.remove(paramFileSystemListener);
       }
     }









     @Deprecated
     public void removePollingChangeListener(PollingChangeListener paramPollingChangeListener)
     {
       if (this.pollingChangeListenerMap.containsKey(paramPollingChangeListener)) {
         FileSystemNotificationUtils.removePollingChangeListener((FileSystemPollingChangeListener)this.pollingChangeListenerMap.get(paramPollingChangeListener));
         this.pollingChangeListenerMap.remove(paramPollingChangeListener);
       }
     }








     @Deprecated
     public void addPollingChangeListener(final PollingChangeListener paramPollingChangeListener)
     {
       FileSystemPollingChangeListener local2 = new FileSystemPollingChangeListener()
       {
         public void pollingDetectedChange(FileSystemPollingChangeNotification paramAnonymousFileSystemPollingChangeNotification) {
           PollingChangeNotification localPollingChangeNotification = new PollingChangeNotification(paramAnonymousFileSystemPollingChangeNotification.getFolderChanged(), paramAnonymousFileSystemPollingChangeNotification.getSourceIdentifier());

           List localList = paramAnonymousFileSystemPollingChangeNotification.getfListOfChangedFiles();
           for (Path localPath : localList) {
             localPollingChangeNotification.add(localPath);
           }
           paramPollingChangeListener.pollingDetectedChange(localPollingChangeNotification);
         }


       };
       FileSystemNotificationUtils.addPollingChangeListener(local2);
       this.pollingChangeListenerMap.put(paramPollingChangeListener, local2);
     }





     @Deprecated
     public void pollingDetectedChange(PollingChangeNotification paramPollingChangeNotification)
     {
       FileSystemPollingChangeNotification localFileSystemPollingChangeNotification = new FileSystemPollingChangeNotification(paramPollingChangeNotification.getFolderChanged(), paramPollingChangeNotification.getSourceIdentifier());

       List localList = paramPollingChangeNotification.getfListOfChangedFiles();
       for (Path localPath : localList) {
         localFileSystemPollingChangeNotification.add(localPath);
       }
       FileSystemNotificationUtils.pollingDetectedChange(localFileSystemPollingChangeNotification);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\FileSystemNotifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */