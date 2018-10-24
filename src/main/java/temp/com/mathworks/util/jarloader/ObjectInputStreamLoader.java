   package temp.com.mathworks.util.jarloader;

   import java.io.IOException;
   import java.io.InputStream;
   import java.io.ObjectInputStream;
   import java.io.ObjectStreamClass;
   import java.io.StreamCorruptedException;
   import java.lang.reflect.Array;
















   public class ObjectInputStreamLoader
     extends ObjectInputStream
   {
     private ClassLoader loader;

     public ObjectInputStreamLoader(InputStream paramInputStream, ClassLoader paramClassLoader)
       throws IOException, StreamCorruptedException
     {
       super(paramInputStream);
       if ((paramClassLoader == null) || (paramInputStream == null)) {
         throw new IllegalArgumentException("Illegal null argument to ObjectInputStreamLoader");
       }
       this.loader = paramClassLoader;
     }




     private Class primitiveType(char paramChar)
     {
       switch (paramChar) {
       case 'B':  return Byte.TYPE;
       case 'C':  return Character.TYPE;
       case 'D':  return Double.TYPE;
       case 'F':  return Float.TYPE;
       case 'I':  return Integer.TYPE;
       case 'J':  return Long.TYPE;
       case 'S':  return Short.TYPE;
       case 'Z':  return Boolean.TYPE; }
       return null;
     }





     protected Class resolveClass(ObjectStreamClass paramObjectStreamClass)
       throws IOException, ClassNotFoundException
     {
       String str = paramObjectStreamClass.getName();
       if (str.startsWith("["))
       {


         for (int i = 1; str.charAt(i) == '['; i++) {}
         Class localClass; if (str.charAt(i) == 'L') {
           localClass = this.loader.loadClass(str.substring(i + 1, str.length() - 1));
         }
         else {
           if (str.length() != i + 1) {
             throw new ClassNotFoundException(str);
           }
           localClass = primitiveType(str.charAt(i));
         }
         int[] arrayOfInt = new int[i];
         for (int j = 0; j < i; j++) {
           arrayOfInt[j] = 0;
         }
         return Array.newInstance(localClass, arrayOfInt).getClass();
       }
       return this.loader.loadClass(str);
     }


     public ClassLoader getClassLoader()
     {
       return this.loader;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\jarloader\ObjectInputStreamLoader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */