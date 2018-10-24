   package temp.com.mathworks.engine;











   public class Utility
   {
     public static String getSignatureOfObjectClass(Object paramObject)
     {
       String str = null;
       if (paramObject != null) {
         Class localClass = paramObject.getClass();
         str = getSignatureOfClass(localClass);
       }
       return str;
     }





     public static String getSignatureOfClass(Class paramClass)
     {
       StringBuffer localStringBuffer = new StringBuffer();
       Class localClass = paramClass;

       if (paramClass.isArray()) {
         localStringBuffer.append('[');
         localClass = paramClass.getComponentType();
         localStringBuffer.append(getSignatureOfClass(localClass));
       } else if (localClass.isPrimitive()) {
         if (localClass.equals(Void.TYPE)) {
           localStringBuffer.append('V');
         } else if (localClass.equals(Boolean.TYPE)) {
           localStringBuffer.append('Z');
         } else if (localClass.equals(Byte.TYPE)) {
           localStringBuffer.append('B');
         } else if (localClass.equals(Character.TYPE)) {
           localStringBuffer.append('C');
         } else if (localClass.equals(Short.TYPE)) {
           localStringBuffer.append('S');
         } else if (localClass.equals(Integer.TYPE)) {
           localStringBuffer.append('I');
         } else if (localClass.equals(Long.TYPE)) {
           localStringBuffer.append('J');
         } else if (localClass.equals(Float.TYPE)) {
           localStringBuffer.append('F');
         } else if (localClass.equals(Double.TYPE)) {
           localStringBuffer.append('D');
         }
       } else {
         localStringBuffer.append('L');
         localStringBuffer.append(localClass.getName().replace('.', '/'));
         localStringBuffer.append(';');
       }

       return localStringBuffer.toString();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\engine\Utility.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */