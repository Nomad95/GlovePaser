   package temp.com.mathworks.util.jarloader;

   import java.awt.Image;
   import java.awt.Toolkit;
   import java.beans.Beans;
   import java.io.ByteArrayInputStream;
   import java.io.File;
   import java.io.FileInputStream;
   import java.io.IOException;
   import java.io.InputStream;
   import java.io.PrintStream;
   import java.net.URL;
   import java.util.Enumeration;
   import java.util.Hashtable;
   import java.util.Vector;


























   public class SimpleClassLoader
     extends ClassLoader
   {
     private static boolean debug = false;
     private static boolean keepLoading = true;

     private static ClassLoader fDefaultParentLoader = getSystemClassLoader();





     private static Hashtable loaders = new Hashtable();
















     private Hashtable localOverrides = new Hashtable();



     private SimpleClassLoader(String paramString1, String paramString2)
     {
       super(fDefaultParentLoader);
       this.cookie = paramString1;
       this.localResourceDirectory = paramString2;
       loaders.put(paramString1, this);
     }




     private Hashtable resourceHash = new Hashtable();
     private Hashtable mimeHash = new Hashtable();















     private Hashtable rawClasses = new Hashtable();






     public void defineClassFromBytes(String paramString, byte[] paramArrayOfByte)
     {
       this.rawClasses.put(paramString, paramArrayOfByte);
     }





     private Class applyDefinition(String paramString, boolean paramBoolean)
     {
       byte[] arrayOfByte = (byte[])this.rawClasses.get(paramString);
       this.rawClasses.remove(paramString);
       if (arrayOfByte == null) {
         return null;
       }
       Class localClass = null;
       try {
         localClass = super.defineClass(null, arrayOfByte, 0, arrayOfByte.length);
         if ((localClass != null) && (paramBoolean)) {
           resolveClass(localClass);
         }
       } catch (ClassFormatError localClassFormatError) {
         System.err.println("The definition for " + paramString + " in the JAR file");
         System.err.println("has a format error.");
         return null;
       }
       catch (NoClassDefFoundError localNoClassDefFoundError)
       {
         return null;
       }

       if (!localClass.getName().equals(paramString)) {
         System.err.println("\nWARNING: file name versus class name mismatch");
         String str = paramString.replace('.', '/') + ".class";
         System.err.println("    JAR entry \"" + str + "\" was expected " + "to contain class \"" + paramString + "\"");

         System.err.println("    but instead contained class \"" + localClass.getName() + "\"");
         System.err.println("    This may cause future class-loading problems.\n");
       }
       return localClass;
     }




     private static byte[] getByteArray(String paramString)
       throws IOException
     {
       File localFile = new File(paramString);
       int i = (int)localFile.length();
       byte[] arrayOfByte = new byte[i];

       FileInputStream localFileInputStream = new FileInputStream(paramString);
       int j = 0;
       while (j < i) {
         int k = localFileInputStream.read(arrayOfByte, j, i - j);
         if (k < 0) {
           break;
         }
         j += k;
       }
       return arrayOfByte;
     }


     public Class loadClassFromFile(String paramString)
       throws ClassNotFoundException
     {
       try
       {
         byte[] arrayOfByte = getByteArray(paramString);
         Class localClass = super.defineClass(null, arrayOfByte, 0, arrayOfByte.length);
         if (localClass != null) {
           resolveClass(localClass);
         }
         if (localClass == null)
           throw new ClassNotFoundException(paramString);
         return localClass;
       } catch (Exception localException) {
         debug("LoadFromFile/caught " + localException + " when loading from file " + paramString);
         throw new ClassNotFoundException(paramString);
       }
     }




     public Class loadClass(String paramString)
       throws ClassNotFoundException
     {
       return loadClass(paramString, true);
     }








     protected Class loadClass(String paramString, boolean paramBoolean)
       throws ClassNotFoundException
     {
       Class localClass = findLoadedClass(paramString);
       if ((localClass == null) && (paramString.charAt(0) == '[')) {
         try {
           localClass = Class.forName(paramString, true, this);
         }
         catch (ClassNotFoundException localClassNotFoundException1) {}
       }




       if (localClass == null)
       {
         localClass = applyDefinition(paramString, paramBoolean);
       }


       if (localClass == null) {
         try {
           return Class.forName(paramString, true, getClass().getClassLoader());
         }
         catch (ClassNotFoundException localClassNotFoundException2) {}
       }


       if (localClass == null) {
         throw new ClassNotFoundException(paramString);
       }
       if (paramBoolean) {
         resolveClass(localClass);
       }
       return localClass;
     }






     public Object instantiate(String paramString, InputStreamProducer paramInputStreamProducer)
       throws ClassNotFoundException, IOException
     {
       String str = paramString.replace('.', '/');
       Object localObject1 = null;
       try {
         setLocalResourceSource(str + ".ser", paramInputStreamProducer);
         localObject1 = Beans.instantiate(this, paramString);
       } finally {
         this.localOverrides.remove(str + ".ser");
       }
       return localObject1;
     }










     public void setLocalResourceSource(String paramString, InputStreamProducer paramInputStreamProducer)
     {
       this.localOverrides.put(paramString, paramInputStreamProducer);
     }

     void putClassResource(String paramString1, String paramString2) {
       this.resourceHash.put(paramString1, "A CLASS FILE");
       this.mimeHash.put(paramString1, paramString2);
     }

     void putLocalResource(String paramString1, byte[] paramArrayOfByte, String paramString2) {
       this.resourceHash.put(paramString1, paramArrayOfByte);
       this.mimeHash.put(paramString1, paramString2);
     }















     public URL getResource(String paramString)
     {
       return getLocalResource(paramString);
     }

     public InputStream getResourceAsStream(String paramString) {
       InputStream localInputStream = getSystemResourceAsStream(paramString);
       if (localInputStream != null) {
         return localInputStream;
       }
       return getLocalResourceAsStream(paramString);
     }






     public URL getLocalResource(String paramString)
     {
       Object localObject1 = this.localOverrides.get(paramString);
       if (localObject1 == null)
       {
         localObject1 = this.resourceHash.get(paramString); }
       Object localObject2;
       if ((localObject1 == null) && (this.localResourceDirectory != null))
       {
         localObject2 = new File(this.localResourceDirectory, paramString);
         if (((File)localObject2).exists()) {
           localObject1 = new Integer("1");
         }
       }
       if (localObject1 != null) {
         try
         {
           return new URL("simpleresource", "", "/SIMPLE" + this.cookie + "/+/" + paramString);

         }
         catch (Exception localException)
         {

           debug("Exception " + localException + " while building a resource URL");
           return null;
         }
       }
       return null;
     }


     private InputStream getLocalResourceAsStream(String paramString)
     {
       Object localObject1 = this.localOverrides.get(paramString);
       if (localObject1 != null) {
         return ((InputStreamProducer)localObject1).getInputStream();
       }

       localObject1 = this.resourceHash.get(paramString);
       Object localObject2;
       if (localObject1 != null) {
         if ((localObject1 instanceof String))
         {

           throw new SecurityException("No access through getResource() to .class in 1.1");
         }

         localObject2 = (byte[])localObject1;
         return new ByteArrayInputStream((byte[])localObject2);
       }
       if (this.localResourceDirectory != null)
       {
         localObject2 = new File(this.localResourceDirectory, paramString);
         try {
           return new FileInputStream((File)localObject2);
         } catch (Exception localException) {
           return null;
         }
       }
       return null;
     }





     public static SimpleClassLoader createLoader(String paramString1, String paramString2)
     {
       SimpleClassLoader localSimpleClassLoader = getLoader(paramString1);
       if (localSimpleClassLoader != null) {
         if (!localSimpleClassLoader.localResourceDirectory.equals(paramString2)) {
           throw new Error("internal error!");
         }
         return localSimpleClassLoader;
       }
       return new SimpleClassLoader(paramString1, paramString2);
     }

     private static SimpleClassLoader getLoader(String paramString)
     {
       return (SimpleClassLoader)loaders.get(paramString);
     }



     public static Object getLocalResource(String paramString1, String paramString2)
     {
       SimpleClassLoader localSimpleClassLoader = getLoader(paramString1);


       Object localObject1 = localSimpleClassLoader.localOverrides.get(paramString2);
       if (localObject1 != null) {
         return ((InputStreamProducer)localObject1).getInputStream();
       }

       String str = (String)localSimpleClassLoader.mimeHash.get(paramString2);
       Object localObject2; if (str != null)
       {
         localObject1 = localSimpleClassLoader.resourceHash.get(paramString2);

         if ((localObject1 instanceof String))
         {

           throw new SecurityException("No access through getResource() to .class in 1.1");
         }

         localObject2 = (byte[])localObject1;
         if (str.startsWith("image")) {
           return Toolkit.getDefaultToolkit().createImage((byte[])localObject2).getSource();
         }
         return new ByteArrayInputStream((byte[])localObject2);
       }

       if (localSimpleClassLoader.localResourceDirectory != null)
       {
         localObject2 = new File(localSimpleClassLoader.localResourceDirectory, paramString2);
         if (((File)localObject2).exists()) {
           try {
             URL localURL = new URL("file", "", ((File)localObject2).getAbsolutePath());


             return localURL.getContent();
           } catch (Exception localException) {
             throw new Error("no such resource");
           }
         }
       }
       return null;
     }



     public static InputStream getLocalResourceAsStream(String paramString1, String paramString2)
     {
       SimpleClassLoader localSimpleClassLoader = getLoader(paramString1);
       return localSimpleClassLoader.getLocalResourceAsStream(paramString2);
     }












     public synchronized boolean applyDefinitions(Vector paramVector)
     {
       boolean bool = true;
       Enumeration localEnumeration = paramVector.elements();
       while (localEnumeration.hasMoreElements()) {
         String str = (String)localEnumeration.nextElement();
         Class localClass = findLoadedClass(str);
         if (localClass == null)
         {
           localClass = applyDefinition(str, true);
           if (localClass == null) {
             if (bool == true) {
               System.err.println("NOTE: There are classes that cannot be defined in this JAR file");
               System.err.println("    Some of these classes will cause the failure of defining or linking ");
               System.err.println("    other classes that depend on them.");
               if (keepLoading) {
                 System.err.println("NOTE: To simplify debugging JAR files, we will proceed loading classes");
                 System.err.println("    although this may lead eventually to an UnknownError or the like");
                 System.err.println();
               }
             }
             System.err.println("Class " + str + " could not be defined from JAR file");
             bool = false;
           }
         }
       }
       return bool;
     }



     private static void debug(String paramString)
     {
       if (debug) {
         System.err.println("SimpleClassLoader:: " + paramString);
       }
     }




     static
     {
       System.setProperty("java.protocol.handler.pkgs", System.getProperty("java.protocol.handler.pkgs") + "|com.mathworks.util.jarloader");
       try
       {
         fDefaultParentLoader = Class.forName("com.mathworks.jmi.OpaqueJavaInterface").getClassLoader();
       } catch (ClassNotFoundException localClassNotFoundException) {} }

     public static SimpleClassLoader ourLoader = createLoader("JarLoader", null);
     public static final String urlPrefix = "SIMPLE";
     private static final String protocolPathProp = "java.protocol.handler.pkgs";
     private String cookie;
     private String localResourceDirectory;
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\jarloader\SimpleClassLoader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */