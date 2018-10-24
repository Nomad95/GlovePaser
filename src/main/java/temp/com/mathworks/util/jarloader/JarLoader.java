   package temp.com.mathworks.util.jarloader;

   import java.io.BufferedInputStream;
   import java.io.ByteArrayInputStream;
   import java.io.ByteArrayOutputStream;
   import java.io.FileInputStream;
   import java.io.FileNotFoundException;
   import java.io.IOException;
   import java.io.InputStream;
   import java.io.PrintStream;
   import java.net.URLConnection;
   import java.util.Enumeration;
   import java.util.Hashtable;
   import java.util.Vector;
   import java.util.zip.ZipEntry;
   import java.util.zip.ZipInputStream;







   public class JarLoader
   {
     private static boolean debug = false;


     private InputStream jarStream;


     private String jarName;


     private SimpleClassLoader loader;

     private static boolean warnedAboutNoBeans;


     public JarLoader(String paramString)
       throws FileNotFoundException
     {
       debug("(" + paramString + ")");
       this.jarName = paramString;
       FileInputStream localFileInputStream = new FileInputStream(paramString);
       this.jarStream = new BufferedInputStream(localFileInputStream);
       this.loader = SimpleClassLoader.ourLoader;
     }



     public ClassLoader getLoader()
     {
       return this.loader;
     }



     private String guessContentTypeFromStream(InputStream paramInputStream)
       throws IOException
     {
       String str = URLConnection.guessContentTypeFromStream(paramInputStream);


       if (str == null) {
         paramInputStream.mark(10);
         int i = paramInputStream.read();
         int j = paramInputStream.read();
         int k = paramInputStream.read();
         int m = paramInputStream.read();
         int n = paramInputStream.read();
         int i1 = paramInputStream.read();
         paramInputStream.reset();
         if ((i == 172) && (j == 237)) {
           str = "application/java-serialized-object";
         }
       }
       return str;
     }




     public JarInfo loadJar()
       throws IOException
     {
       ZipInputStream localZipInputStream = null;
       Manifest localManifest = null;
       Vector localVector1 = new Vector();
       Vector localVector2 = new Vector();

       byte[] arrayOfByte1 = new byte['Ѐ'];
       int i = 1;
       try
       {
         localZipInputStream = new ZipInputStream(this.jarStream);
         ZipEntry localZipEntry = null;

         while ((localZipEntry = localZipInputStream.getNextEntry()) != null) {
           i = 0;

           String str1 = localZipEntry.getName();
           String str2 = null;


           ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();





           for (;;)
           {
             int j = localZipInputStream.read(arrayOfByte1);
             if (j < 0) {
               break;
             }
             localByteArrayOutputStream.write(arrayOfByte1, 0, j);
           }

           byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
           int k = arrayOfByte2.length;

           if (Manifest.isManifestName(str1)) {
             str2 = "manifest/manifest";
           }
           Object localObject1;
           if (str2 == null) {
             localObject1 = new ByteArrayInputStream(arrayOfByte2);
             str2 = guessContentTypeFromStream((InputStream)localObject1);
             ((InputStream)localObject1).close();
           }

           if (str2 == null) {
             str2 = "input-stream/input-stream";
           }

           if ((str2.startsWith("application/java-serialized-object")) || (str2.startsWith("application/x-java-serialized-object")))
           {

             this.loader.putLocalResource(str1, arrayOfByte2, str2);


             localObject1 = str1.substring(0, str1.length() - 4);
             localObject1 = ((String)localObject1).replace('/', '.');
             localVector2.addElement(localObject1);
           }
           else if ((str2.startsWith("application/java-vm")) || (str2.startsWith("application/x-java-vm")))
           {










             this.loader.putClassResource(str1, str2);


             localObject1 = str1.substring(0, str1.length() - 6);
             localObject1 = ((String)localObject1).replace('/', '.');
             this.loader.defineClassFromBytes((String)localObject1, arrayOfByte2);
             localVector1.addElement(localObject1);
           }
           else if (str2.equals("manifest/manifest")) {
             localManifest = new Manifest(arrayOfByte2);
           }
           else
           {
             this.loader.putLocalResource(str1, arrayOfByte2, str2);
           }
         }









         if (localZipInputStream != null) {
           try {
             localZipInputStream.close();
           }
           catch (Exception localException1) {}
         }






         if (i == 0) {
           break label495;
         }
       }
       catch (IOException localIOException)
       {
         debug("IOException loading archive: " + localIOException);
         throw localIOException;
       } catch (Throwable localThrowable) {
         debug("Caught " + localThrowable + " in loadit()");
         localThrowable.printStackTrace();
         throw new IOException("loadJar caught: " + localThrowable);
       } finally {
         if (localZipInputStream != null) {
           try {
             localZipInputStream.close();
           }
           catch (Exception localException2) {}
         }
       }






       throw new IOException("JAR file is corrupt or empty");







       label495:






       JarInfo localJarInfo = createJarInfo(localVector1, localVector2, localManifest);
       return localJarInfo;
     }








     private JarInfo createJarInfo(Vector paramVector1, Vector paramVector2, Manifest paramManifest)
     {
       Hashtable localHashtable2 = new Hashtable();
       Hashtable localHashtable1; if (paramManifest == null)
       {


         localHashtable1 = new Hashtable();
       } else {
         localHashtable1 = new Hashtable();
         localObject1 = paramManifest.entries();
         while (((Enumeration)localObject1).hasMoreElements()) {
           localObject2 = (MessageHeader)((Enumeration)localObject1).nextElement();
           localObject3 = ((MessageHeader)localObject2).findValue("Name");
           localObject4 = ((MessageHeader)localObject2).findValue("Java-Bean");
           if ((localObject4 != null) && (((String)localObject4).equalsIgnoreCase("True")))
           {
             boolean bool = true;
             if (((String)localObject3).endsWith(".class")) {
               bool = false;
               localObject5 = ((String)localObject3).substring(0, ((String)localObject3).length() - 6);
             } else if (((String)localObject3).endsWith(".ser")) {
               localObject5 = ((String)localObject3).substring(0, ((String)localObject3).length() - 4);
             } else {
               localObject5 = localObject3;
             }
             Object localObject5 = ((String)localObject5).replace('/', '.');
             localHashtable1.put(localObject5, new Boolean(bool));
             localHashtable2.put(localObject5, localObject2);
           }
         }
       }

       Object localObject1 = new String[localHashtable1.size()];
       Object localObject2 = new boolean[localHashtable1.size()];
       Object localObject3 = new MessageHeader[localHashtable1.size()];


       Object localObject4 = localHashtable1.keys(); for (int i = 0;
           ((Enumeration)localObject4).hasMoreElements();
           i++) {
         String str = (String)((Enumeration)localObject4).nextElement();
         localObject1[i] = str;
         localObject2[i] = ((Boolean)localHashtable1.get(str)).booleanValue();
         localObject3[i] = ((MessageHeader)localHashtable2.get(str));
       }

       return new JarInfo(this.jarName, this.loader, (String[])localObject1, (boolean[])localObject2, (MessageHeader[])localObject3);
     }




     private static void debug(String paramString)
     {
       if (debug) {
         System.err.println("JarLoader:: " + paramString);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\jarloader\JarLoader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */