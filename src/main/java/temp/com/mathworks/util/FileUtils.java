   package temp.com.mathworks.util;

   import com.mathworks.cfbutils.NativeCfb;
   import java.io.File;
   import java.io.FileFilter;
   import java.io.FileInputStream;
   import java.io.IOException;
   import java.io.InputStream;
   import java.io.OutputStream;
   import java.io.Writer;
   import java.nio.charset.Charset;
   import java.util.Collection;
   import java.util.Locale;
   import javax.swing.text.BadLocationException;
   import javax.swing.text.Document;
   import org.apache.commons.io.FilenameUtils;
   import org.apache.commons.io.IOUtils;
   import org.jetbrains.annotations.NotNull;























   @Deprecated
   public class FileUtils
   {
     @Deprecated
     public static final int DEFAULT_TRUNCATE_LENGTH = 32;
     @Deprecated
     public static final int TRUNCATE_KEEPING_PREFIX = 0;
     @Deprecated
     public static final int TRUNCATE_FRONT = 1;
     @Deprecated
     public static final int TRUNCATE_END = 2;
     @Deprecated
     public static final int TRUNCATE_ALL_EXCEPT_FILENAME = 3;
     @Deprecated
     public static final int TRUNCATE_KEEPING_PREFIX_AND_SUFFIX = 4;

     @Deprecated
     public static File absoluteFile(File paramFile)
     {
       return new File(absolutePathname(paramFile.getAbsolutePath()));
     }

















































     @Deprecated
     public static String absolutePathname(@NotNull String paramString)
     {
       File localFile = new File(paramString);
       String str2; if (PlatformInfo.isWindows()) {
         str1 = FilenameUtils.normalize(paramString);
         if (str1 == null) {
           return paramString;
         }
         str2 = new File(str1).getAbsolutePath();
         String str3;
         try
         {
           str3 = localFile.getCanonicalPath();
         } catch (IOException localIOException) {
           str3 = str2;
         }
         return str2.equalsIgnoreCase(str3) ? str3 : str2;
       }

       String str1 = FilenameUtils.normalize(paramString);
       if ((str1 != null) && (!new File(str1).exists())) {
         return str1;
       }
       if (!localFile.isAbsolute())
       {



         str2 = NativeCfb.toAbsolutePath(paramString);
         if ((str2 != null) && (!str2.isEmpty())) {
           return str2;
         }
       }
       return str1 != null ? str1 : paramString;
     }




     @Deprecated
     public static String normalizePathname(String paramString)
     {
       return com.mathworks.cfbutils.FileUtils.normalizePathname(paramString);
     }













     @Deprecated
     public static String toJavaPath(String paramString)
     {
       return FilenameUtils.separatorsToUnix(paramString);
     }








     @Deprecated
     public static String fromJavaPath(String paramString)
     {
       return FilenameUtils.separatorsToSystem(paramString);
     }



     @Deprecated
     public static String getLocalizedFilename(String paramString)
     {
       return com.mathworks.cfbutils.FileUtils.getLocalizedFilename(paramString);
     }



     @Deprecated
     public static String getLocalizedFilename(String paramString1, String paramString2)
     {
       return com.mathworks.cfbutils.FileUtils.getLocalizedFilename(paramString1, paramString2);
     }



     @Deprecated
     public static String getLocalizedFilename(String paramString, Locale paramLocale)
     {
       return com.mathworks.cfbutils.FileUtils.getLocalizedFilename(paramString, paramLocale);
     }



     @Deprecated
     public static String getLocalizedFilename(String paramString1, String paramString2, Locale paramLocale)
     {
       return com.mathworks.cfbutils.FileUtils.getLocalizedFilename(paramString1, paramString2, paramLocale);
     }








     @Deprecated
     public static String getFileContents(String paramString)
     {
       String str = null;
       File localFile = new File(paramString);
       if ((localFile.exists()) && (!localFile.isDirectory())) {
         FileInputStream localFileInputStream = null;
         try {
           long l = localFile.length();
           byte[] arrayOfByte = new byte[(int)l];
           localFileInputStream = new FileInputStream(localFile);
           localFileInputStream.read(arrayOfByte);
           str = new String(arrayOfByte);
         } catch (Exception localException) {
           Log.logException(localException);
         } finally {
           IOUtils.closeQuietly(localFileInputStream);
         }
       }

       return str;
     }



     @Deprecated
     public static boolean fileExists(String paramString)
     {
       File localFile = new File(absolutePathname(paramString));
       return fileExists(localFile);
     }



     @Deprecated
     public static String getPreferencesDirectory()
     {
       return com.mathworks.cfbutils.FileUtils.getPreferencesDirectory();
     }



     @Deprecated
     public static void setPreferencesDirectory(String paramString)
     {
       com.mathworks.cfbutils.FileUtils.setPreferencesDirectory(paramString);
     }



     @Deprecated
     public static boolean fileExists(File paramFile)
     {
       return (paramFile.exists()) && (!paramFile.isDirectory());
     }








     @Deprecated
     public static void copyStream(InputStream paramInputStream, OutputStream paramOutputStream)
       throws IOException
     {
       IOUtils.copy(paramInputStream, paramOutputStream);
     }








     @Deprecated
     public static void copyFile(File paramFile1, File paramFile2)
       throws IOException
     {
       org.apache.commons.io.FileUtils.copyFile(paramFile1, paramFile2);
     }



     @Deprecated
     public static boolean isHtmlFile(String paramString)
     {
       return com.mathworks.cfbutils.FileUtils.isHtmlFile(paramString);
     }



     @Deprecated
     public static boolean isCFile(String paramString)
     {
       return com.mathworks.cfbutils.FileUtils.isCFile(paramString);
     }



     @Deprecated
     public static boolean isPdfFile(String paramString)
     {
       return com.mathworks.cfbutils.FileUtils.isPdfFile(paramString);
     }





     @Deprecated
     public static String[] listFolderContents(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
     {
       return com.mathworks.cfbutils.FileUtils.listFolderContents(paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
     }



     @Deprecated
     public static String truncatePathname(String paramString)
     {
       return com.mathworks.cfbutils.FileUtils.truncatePathname(paramString);
     }



     @Deprecated
     public static String truncatePathname(String paramString, int paramInt)
     {
       return com.mathworks.cfbutils.FileUtils.truncatePathname(paramString, paramInt);
     }



     @Deprecated
     public static String truncatePathname(String paramString, int paramInt1, int paramInt2)
     {
       return com.mathworks.cfbutils.FileUtils.truncatePathname(paramString, paramInt1, paramInt2);
     }



     @Deprecated
     public static boolean isANativeMacPackage(File paramFile)
     {
       return (PlatformInfo.isMacintosh()) && (paramFile.isDirectory()) && (NativeCfb.isPackage(paramFile.getAbsolutePath()));
     }



     @Deprecated
     public static File getNextUntitledFile(File paramFile, String paramString, FileFilter... paramVarArgs)
     {
       return com.mathworks.cfbutils.FileUtils.getNextUntitledFile(paramFile, paramString, paramVarArgs);
     }







     @Deprecated
     public static File getNextNamedFile(File paramFile, String paramString1, String paramString2, FileFilter... paramVarArgs)
     {
       return com.mathworks.cfbutils.FileUtils.getNextNamedFile(paramFile, paramString1, paramString2, paramVarArgs);
     }









     @Deprecated
     public static File getNextNamedFile(File paramFile, String paramString1, String paramString2, boolean paramBoolean, String paramString3, FileFilter... paramVarArgs)
     {
       return com.mathworks.cfbutils.FileUtils.getNextNamedFile(paramFile, paramString1, paramString2, paramBoolean, paramString3, paramVarArgs);
     }



     @Deprecated
     public static int compareCanonicalPaths(File paramFile1, File paramFile2)
     {
       return com.mathworks.cfbutils.FileUtils.compareCanonicalPaths(paramFile1, paramFile2);
     }



     @Deprecated
     public static boolean matchesDirStylePattern(File paramFile, String paramString)
     {
       return com.mathworks.cfbutils.FileUtils.matchesDirStylePattern(paramFile, paramString);
     }



     @Deprecated
     public static boolean areFilesTheSame(File paramFile1, File paramFile2)
     {
       return com.mathworks.cfbutils.FileUtils.areFilesTheSame(paramFile1, paramFile2);
     }


     @Deprecated
     public static String generateFileHash(File paramFile)
       throws IOException
     {
       return com.mathworks.cfbutils.FileUtils.generateFileHash(paramFile);
     }



     @Deprecated
     public static boolean makeFileWriteable(File paramFile)
     {
       return com.mathworks.cfbutils.FileUtils.makeFileWriteable(paramFile);
     }


     @Deprecated
     public static void makeFileWriteableStrict(File paramFile)
       throws Exception
     {
       com.mathworks.cfbutils.FileUtils.makeFileWriteableStrict(paramFile);
     }



     @Deprecated
     public static File getParent(File paramFile)
     {
       File localFile = paramFile;
       while (localFile.getParentFile() != null) {
         localFile = localFile.getParentFile();
       }
       return localFile;
     }


     @Deprecated
     public static void write(Document paramDocument, File paramFile, Charset paramCharset)
       throws IOException
     {
       com.mathworks.cfbutils.FileUtils.write(paramDocument, paramFile, paramCharset);
     }





     @Deprecated
     public static void writeDocumentContents(Document paramDocument, Writer paramWriter)
       throws IOException, BadLocationException
     {
       com.mathworks.cfbutils.FileUtils.writeDocumentContents(paramDocument, paramWriter);
     }



     @Deprecated
     public static void notifyFileSystemOfFileChange(File paramFile, boolean paramBoolean)
     {
       if (paramBoolean) {
         FileSystemUtils.getFileSystemNotifier().changed(paramFile);
       } else {
         FileSystemUtils.getFileSystemNotifier().created(paramFile);
       }
     }



     @Deprecated
     public static boolean hasSameParent(File paramFile1, File paramFile2)
     {
       return com.mathworks.cfbutils.FileUtils.hasSameParent(paramFile1, paramFile2);
     }



     @Deprecated
     public static boolean isMatchingExtension(String paramString, Collection<String> paramCollection)
     {
       return com.mathworks.cfbutils.FileUtils.isMatchingExtension(paramString, paramCollection);
     }


     @Deprecated
     public static File createUniqueTempDir(boolean paramBoolean)
       throws IOException
     {
       return com.mathworks.cfbutils.FileUtils.createUniqueTempDir(paramBoolean);
     }


     @Deprecated
     public static File createUniqueTempDir(String paramString, boolean paramBoolean)
       throws IOException
     {
       return com.mathworks.cfbutils.FileUtils.createUniqueTempDir(paramString, paramBoolean);
     }



     @Deprecated
     public static String getRelativePathFromDirectory(File paramFile1, File paramFile2)
     {
       return com.mathworks.cfbutils.FileUtils.getRelativePathFromDirectory(paramFile1, paramFile2);
     }



     @Deprecated
     public static String resolveRelativePath(String paramString1, String paramString2)
     {
       return com.mathworks.cfbutils.FileUtils.resolveRelativePath(paramString1, paramString2);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\FileUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */