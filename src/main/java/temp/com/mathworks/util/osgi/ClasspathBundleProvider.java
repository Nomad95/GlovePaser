   package temp.com.mathworks.util.osgi;

   import com.mathworks.util.FactoryUtils;
   import com.mathworks.util.Log;
   import java.io.File;
   import java.io.FileInputStream;
   import java.io.FileNotFoundException;
   import java.io.IOException;
   import java.util.Collection;
   import java.util.HashSet;
   import java.util.jar.Attributes;
   import java.util.jar.JarInputStream;
   import java.util.jar.Manifest;
   import org.apache.commons.io.FilenameUtils;
   import org.apache.commons.io.IOUtils;
   import org.osgi.framework.Bundle;

















   public final class ClasspathBundleProvider
     implements BundleProvider
   {
     private static final String MATHWORKS_BUNDLE_ATTRIBUTE = "MathWorks-Bundle";

     public Collection<File> getBundleJarFiles(Bundle paramBundle)
     {
       String str1 = FactoryUtils.getMatlabStaticClasspath();

       String[] arrayOfString1 = str1.split(File.pathSeparator);
       HashSet localHashSet = new HashSet();

       for (String str2 : arrayOfString1)
       {
         if (str2.endsWith("\\")) {
           str2 = str2.substring(0, str2.length() - 1);
         }

         if ((FilenameUtils.isExtension(str2, "jar")) && (hasBundleAttribute(str2)))
         {
           OSGiUtils.log("Added " + str2);
           localHashSet.add(new File(str2));
         } else {
           OSGiUtils.log("Skipped " + str2);
         }
       }

       return localHashSet;
     }




     private static boolean hasBundleAttribute(String paramString)
     {
       boolean bool = false;
       JarInputStream localJarInputStream = null;
       try
       {
         localJarInputStream = new JarInputStream(new FileInputStream(paramString));
         Manifest localManifest = localJarInputStream.getManifest();
         if (localManifest != null) {
           Attributes localAttributes = localManifest.getMainAttributes();
           bool = "true".equals(localAttributes.getValue("MathWorks-Bundle"));
         }
       }
       catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException)
       {
         OSGiUtils.log("Error loading " + paramString);
         Log.logException(localIOException);
       } finally {
         IOUtils.closeQuietly(localJarInputStream);
       }
       return bool;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\osgi\ClasspathBundleProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */