   package temp.com.mathworks.mvm.eventmgr;

   import com.mathworks.mvm.MvmImpl;
   import java.lang.reflect.Method;
   import java.lang.reflect.Modifier;

























   class InsecureReflection
   {
     static Method getMethod(Class<?> paramClass1, int paramInt, String paramString, Class<?> paramClass2, Class<?>... paramVarArgs)
       throws NoSuchMethodException
     {
       MvmImpl.loadLibrary();

       String str1 = "(" + getArgumentJNISignature(paramVarArgs) + ")" + getJniTypeString(paramClass2);



       Method localMethod = nativeReflectedMethod(paramClass1, paramString, str1, Modifier.isStatic(paramInt));

       if (null != localMethod) {
         checkModifiers(localMethod, paramInt);
         return localMethod;
       }
       String str2 = paramClass2.toString() + " " + paramString + "(" + getArgumentSignature(paramVarArgs) + ")" + " of class " + paramClass1.toString();




       throw new NoSuchMethodException(str2);
     }




     private static String getJniTypeString(Class<?> paramClass)
     {
       String str;



       if (paramClass.isArray()) {
         str = paramClass.getName();
       } else if (paramClass == Boolean.TYPE) {
         str = "Z";
       } else if (paramClass == Byte.TYPE) {
         str = "B";
       } else if (paramClass == Character.TYPE) {
         str = "C";
       } else if (paramClass == Double.TYPE) {
         str = "D";
       } else if (paramClass == Float.TYPE) {
         str = "F";
       } else if (paramClass == Integer.TYPE) {
         str = "I";
       } else if (paramClass == Long.TYPE) {
         str = "J";
       } else if (paramClass == Short.TYPE) {
         str = "S";
       } else {
         str = "L" + paramClass.getName() + ";";
       }
       return str.replace('.', '/');
     }






     private static String getArgumentJNISignature(Class<?>... paramVarArgs)
     {
       StringBuilder localStringBuilder = new StringBuilder();
       if (null != paramVarArgs) {
         for (Class<?> localClass : paramVarArgs) {
           localStringBuilder.append(getJniTypeString(localClass));
         }
       }
       return localStringBuilder.toString();
     }






     private static String getArgumentSignature(Class<?>... paramVarArgs)
     {
       StringBuilder localStringBuilder = new StringBuilder();
       if (null != paramVarArgs) {
         String str = "";
         for (Class<?> localClass : paramVarArgs) {
           localStringBuilder.append(str);
           localStringBuilder.append(localClass.toString());
           str = ",";
         }
       }
       return localStringBuilder.toString();
     }








     private static void checkModifiers(Method paramMethod, int paramInt)
       throws NoSuchMethodException
     {
       int i = paramMethod.getModifiers() & paramInt;
       int j = paramInt & (i ^ 0xFFFFFFFF);
       if (0 != j) {
         String str = paramMethod.toString() + " is not " + Modifier.toString(j);
         throw new NoSuchMethodException(str);
       }
     }

     private static native Method nativeReflectedMethod(Class<?> paramClass, String paramString1, String paramString2, boolean paramBoolean);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\eventmgr\InsecureReflection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */