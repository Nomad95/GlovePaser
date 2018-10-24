   package temp.com.mathworks.matlab.types;






   public class CellStr
   {
     private Object oStringArray;





     private String sTypeString;






     private static String getTypeString(Class paramClass)
     {
       StringBuffer localStringBuffer = new StringBuffer();
       Class localClass = paramClass;
       if (paramClass.isArray()) {
         localStringBuffer.append('[');
         localClass = paramClass.getComponentType();
         localStringBuffer.append(getTypeString(localClass));
       } else {
         localStringBuffer.append(localClass.getName());
       }
       return localStringBuffer.toString();
     }






     private static boolean compareStringArray(Object paramObject1, Object paramObject2)
     {
       boolean bool = true;
       if (((paramObject1 instanceof Object[])) && ((paramObject2 instanceof Object[]))) {
         if (((Object[])paramObject1).length != ((Object[])paramObject2).length) {
           return false;
         }
         int i = ((Object[])paramObject1).length;
         for (int j = 0; j < i; j++) {
           if (!compareStringArray(((Object[])(Object[])paramObject1)[j], ((Object[])(Object[])paramObject2)[j])) return false;
         }
       }
       else if (((paramObject1 instanceof String)) && ((paramObject2 instanceof String))) {
         if (!paramObject1.equals(paramObject2)) return false;
       } else {
         return false;
       }
       return bool;
     }




     public CellStr(Object paramObject)
     {
       setStringArray(paramObject);
     }

     private void setStringArray(Object paramObject) {
       Class localClass = paramObject.getClass();
       String str = getTypeString(localClass);
       if (str.endsWith("java.lang.String")) {
         this.oStringArray = paramObject;
         this.sTypeString = str;
       } else {
         throw new IllegalArgumentException("CellStr constructor argument must be String or String array.");
       }
     }




     public Object getStringArray()
     {
       return this.oStringArray;
     }




     private String getTypeString()
     {
       return this.sTypeString;
     }





     public boolean equals(Object paramObject)
     {
       boolean bool = true;
       if (!(paramObject instanceof CellStr)) {
         bool = false;
       } else {
         bool = compareStringArray(this.oStringArray, ((CellStr)paramObject).getStringArray());
       }
       return bool;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\matlab\types\CellStr.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */