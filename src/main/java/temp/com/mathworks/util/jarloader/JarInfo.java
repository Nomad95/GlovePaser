   package temp.com.mathworks.util.jarloader;

   import java.beans.BeanInfo;
   import java.beans.Beans;
   import java.beans.Introspector;
   import java.io.IOException;
   import java.io.InputStream;
   import java.io.ObjectInputStream;
   import java.io.ObjectStreamException;
   import java.io.PrintStream;
   import java.lang.reflect.Constructor;
   import java.lang.reflect.Modifier;
   import java.util.Hashtable;









   public class JarInfo
   {
     private SimpleClassLoader classLoader;
     private String[] beanNames;
     private BeanInfo[] beanInfos;
     private boolean[] fromPrototype;
     private MessageHeader[] manifestData;
     private String jarName;
     private static Hashtable beanToJar = new Hashtable();















     public JarInfo(String paramString, SimpleClassLoader paramSimpleClassLoader, String[] paramArrayOfString, boolean[] paramArrayOfBoolean, MessageHeader[] paramArrayOfMessageHeader)
     {
       if (paramArrayOfString.length != paramArrayOfBoolean.length) {
         throw new Error("beanNames and fromPrototype need to have the same length");
       }
       this.jarName = paramString;
       this.classLoader = paramSimpleClassLoader;
       this.beanNames = paramArrayOfString;
       this.fromPrototype = paramArrayOfBoolean;
       this.manifestData = paramArrayOfMessageHeader;
       this.beanInfos = new BeanInfo[paramArrayOfString.length];
       for (int i = 0; i < paramArrayOfString.length; i++) {
         beanToJar.put(paramArrayOfString[i], paramString);
         if (paramArrayOfBoolean[i] == 0)
         {
           Class localClass;


           try
           {
             localClass = paramSimpleClassLoader.loadClass(paramArrayOfString[i]);
           }
           catch (Exception localException1)
           {
             continue;
           }

           BeanInfo localBeanInfo;
           try
           {
             localBeanInfo = Introspector.getBeanInfo(localClass);
           } catch (Exception localException2) {
             System.err.println("JarInfo: couldn't find BeanInfo for " + localClass + "; caught " + localException2);

             continue;
           }
           this.beanInfos[i] = localBeanInfo;
           debug("JarInfo:: @ " + i + "; beanName: " + paramArrayOfString[i] + "; fromPrototype: " + paramArrayOfBoolean[i]);
         }
       }
     }



     public static String getJarName(String paramString)
     {
       return (String)beanToJar.get(paramString);
     }







     public String getJarName()
     {
       return this.jarName;
     }



     public int getCount()
     {
       return this.beanNames.length;
     }



     public BeanInfo getBeanInfo(int paramInt)
     {
       if (this.beanInfos[paramInt] != null) {
         return this.beanInfos[paramInt];
       }
       Object localObject = getInstance(this.beanNames[paramInt]);
       if (localObject != null) {
         Class localClass = localObject.getClass();
         BeanInfo localBeanInfo;
         try {
           localBeanInfo = Introspector.getBeanInfo(localClass);
         } catch (Exception localException) {
           System.err.println("JarInfo: couldn't find BeanInfo for " + localClass + "; caught " + localException);
           return null;
         }
         this.beanInfos[paramInt] = localBeanInfo;
         return localBeanInfo;
       }
       return null;
     }




     public String getName(int paramInt)
     {
       return this.beanNames[paramInt];
     }



     public boolean isFromPrototype(String paramString)
     {
       return this.fromPrototype[indexForName(paramString)];
     }



     public MessageHeader getManifestData(String paramString)
     {
       return this.manifestData[indexForName(paramString)];
     }


     public Object getInstance(String paramString)
     {
       try
       {
         return Beans.instantiate(this.classLoader, paramString);
       } catch (Throwable localThrowable) {
         diagnoseInstantiationException(this.classLoader, paramString, localThrowable);
         System.err.println("");
         if (paramString.indexOf('\\') >= 0) {
           System.err.println("    Note that file names in manifests must use forward slashes \"/\" \n    rather than back-slashes \"\\\"");
         }
       }
       return null;
     }





     void diagnoseInstantiationException(SimpleClassLoader paramSimpleClassLoader, String paramString, Throwable paramThrowable)
     {
       System.err.print("\nWARNING: Could not instantiate bean \"" + paramString + "\"");

       if (paramSimpleClassLoader == null)
       {
         System.err.println(" from the system class-loader");
         return;
       }

       System.err.println(" from JAR \"" + this.jarName + "\"");


       String str1 = paramString.replace('.', '/').concat(".ser");
       InputStream localInputStream = paramSimpleClassLoader.getResourceAsStream(str1);
       Object localObject1;
       if (localInputStream != null) {
         System.err.println("    We found a serialized template file \"" + str1 + "\"");
         try
         {
           localObject1 = new ObjectInputStreamLoader(localInputStream, paramSimpleClassLoader);
           Object localObject2 = ((ObjectInputStream)localObject1).readObject();
           System.err.println("    An object could be read from the serialized template OK.");
           System.err.println("    But an exception was generated in Beans.instantiate:");
           System.err.println("        " + paramThrowable);
         } catch (ObjectStreamException localObjectStreamException) {
           System.err.println("    But caught an ObjectStreamException while reading the serialized object:");
           System.err.println("        " + localObjectStreamException);
           System.err.println("    This indicates there is a problem with the contents of the template file.");
         } catch (IOException localIOException) {
           System.err.println("    But caught an IOException while reading the serialized object:");
           System.err.println("        " + localIOException);
         } catch (ClassNotFoundException localClassNotFoundException) {
           System.err.println("    But caught a ClassNotFoundException while reading the serialized object:");
           System.err.println("        " + localClassNotFoundException);
           System.err.println("    This indicates that there was a problem finding a .class file for one");
           System.err.println("    of the serialized objects");
         } catch (Throwable localThrowable1) {
           System.err.println("    But caught an unexpected exception while reading the serialized object:");
           System.err.println("        " + localThrowable1);
         }
         try {
           localInputStream.close();
         }
         catch (Exception localException1) {}
         return;
       }

       try
       {
         localObject1 = paramString.replace('.', '/').concat(".class");
         localInputStream = paramSimpleClassLoader.getResourceAsStream((String)localObject1);

         if (localInputStream == null) {
           System.err.println("    We couldn't open the class file \"" + (String)localObject1 + "\" in the JAR");

           return;
         }


         System.err.println("    We found the class file \"" + (String)localObject1 + "\"");
       }
       catch (SecurityException localSecurityException) {}







       String str2 = "the default package";
       String str3 = paramString;
       if (paramString.lastIndexOf('.') > 0) {
         str2 = "the package \"" + paramString.substring(0, paramString.lastIndexOf('.')) + "\"";
         str3 = paramString.substring(paramString.lastIndexOf('.') + 1);
       }
       Class localClass;
       try
       {
         localClass = paramSimpleClassLoader.loadClass(paramString);
       } catch (Exception localException2) {
         System.err.println("    But were unable to load the class \"" + paramString + "\" because of");
         System.err.println("        " + localException2);
         System.err.println("    Common reasons for this failure include:");
         System.err.println("    (1) The class is not defined in the correct package");
         System.err.println("        it should be in " + str2);
         System.err.println("    (2) The class has not been given the correct name");
         System.err.println("    it should be called \"" + str3 + "\"");
         System.err.println("    (3) The class file contains the wrong class or no class at all");
         return;
       } catch (Throwable localThrowable2) {
         System.err.println("    But were unable to load the class \"" + paramString + "\" because of");
         System.err.println("        " + localThrowable2);
         if (((localThrowable2 instanceof ClassFormatError)) && (localThrowable2.getMessage().equals("Duplicate name"))) {
           System.err.println("    This particular error is often caused by having a mismatch between the name of");
           System.err.println("    the .class file and the name of the contained class.");
           System.err.println("    In this case make sure that class file contains a class");
           System.err.println("    called \"" + str3 + "\" in " + str2 + ".");
         } else {
           localThrowable2.printStackTrace();
         }
         return;
       }


       System.err.println("    We located the class \"" + paramString + "\" OK");
       int i = localClass.getModifiers();
       if (!Modifier.isPublic(i)) {
         System.err.println("    But the class was not declared public, so we could not create a bean");
         return;
       }

       try
       {
         Class[] arrayOfClass = new Class[0];
         Constructor localConstructor = localClass.getConstructor(arrayOfClass);
         if (localConstructor == null) {
           System.err.println("    But the class did not have a zero-arg constructor.");
           System.err.println("    All beans must provide public zero-arg constructors.");
           return;
         }
         i = localConstructor.getModifiers();
         if (!Modifier.isPublic(i)) {
           System.err.println("    But the class's zero-arg constructor was not declared public");
           System.err.println("    All beans must provide public zero-arg constructors.");
           return;
         }
       } catch (NoSuchMethodException localNoSuchMethodException) {
         System.err.println("    But the class did not have a zero-arg constructor.");
         System.err.println("    All beans must provide public zero-arg constructors.");
         return;
       } catch (Throwable localThrowable3) {
         System.err.println("    Unexpected exception in disgnoseInstantiationException");
         System.err.println("    " + localThrowable3);
         localThrowable3.printStackTrace();
         return;
       }

       System.err.println("    The class provides a public zero-arg constructor");
       try {
         Object localObject3 = localClass.newInstance();
       } catch (Throwable localThrowable4) {
         System.err.println("    But were unable to create an instance of the class because we");
         System.err.println("    got an exception while doing Class.newInstance() :");
         System.err.println("       " + localThrowable4);
         System.err.println("    The stack backtrace at the time of this exception is");
         localThrowable4.printStackTrace();
         return;
       }


       System.err.println("    But an exception was generated in Beans.instantiate:");
       System.err.println("        " + paramThrowable);
       paramThrowable.printStackTrace();
     }

     private int indexForName(String paramString)
     {
       for (int i = 0; i < this.beanNames.length; i++) {
         if (this.beanNames[i].equals(paramString)) {
           return i;
         }
       }
       return -1;
     }




     private static boolean debug = false;

     private static void debug(String paramString) { if (debug) {
         System.err.println("JarInfo:: " + paramString);
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\jarloader\JarInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */