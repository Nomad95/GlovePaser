   package temp.com.mathworks.util;

   import java.lang.reflect.Array;
   import org.apache.commons.lang.StringUtils;













   public final class ArrayUtils
   {
     public static final class EmptyObjects
     {
       public static final String[] STRING = new String[0];






       public static final Object[] OBJECT = new Object[0];






       public static final Class[] CLASS = new Class[0];






       public static final Byte[] BYTE = new Byte[0];






       public static final Short[] SHORT = new Short[0];






       public static final Integer[] INTEGER = new Integer[0];






       public static final Long[] LONG = new Long[0];






       public static final Float[] FLOAT = new Float[0];






       public static final Double[] DOUBLE = new Double[0];






       public static final Character[] CHARACTER = new Character[0];






       public static final Boolean[] BOOLEAN = new Boolean[0];
     }








     public static final class EmptyPrimitives
     {
       public static final byte[] BYTE = new byte[0];






       public static final short[] SHORT = new short[0];






       public static final int[] INT = new int[0];






       public static final long[] LONG = new long[0];






       public static final float[] FLOAT = new float[0];






       public static final double[] DOUBLE = new double[0];






       public static final char[] CHAR = new char[0];






       public static final boolean[] BOOLEAN = new boolean[0];
     }













     public static boolean isRectangular(Object paramObject)
     {
       if (paramObject == null)
         throw new IllegalArgumentException("'array' cannot be null");
       if (Array.getLength(paramObject) == 0)
         return true;
       Object localObject1 = Array.get(paramObject, 0);
       if (localObject1 == null)
         return false;
       int i = Array.getLength(localObject1);
       boolean bool = true;
       for (int j = 1; (j < Array.getLength(paramObject)) && (bool); j++) {
         Object localObject2 = Array.get(paramObject, j);
         bool = (localObject2 != null) && (Array.getLength(localObject2) == i);
       }
       return bool;
     }









     /**
      * @deprecated

     public static int[] getLengths(Object[][] paramArrayOfObject)
     {
       if (!isRectangular(paramArrayOfObject))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfObject);
     }









     /**
      * @deprecated

     public static int[] getLengths(boolean[][] paramArrayOfBoolean)
     {
       if (!isRectangular(paramArrayOfBoolean))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfBoolean);
     }









     /**
      * @deprecated

     public static int[] getLengths(byte[][] paramArrayOfByte)
     {
       if (!isRectangular(paramArrayOfByte))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfByte);
     }









     /**
      * @deprecated

     public static int[] getLengths(short[][] paramArrayOfShort)
     {
       if (!isRectangular(paramArrayOfShort))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfShort);
     }









     /**
      * @deprecated

     public static int[] getLengths(char[][] paramArrayOfChar)
     {
       if (!isRectangular(paramArrayOfChar))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfChar);
     }









     /**
      * @deprecated

     public static int[] getLengths(int[][] paramArrayOfInt)
     {
       if (!isRectangular(paramArrayOfInt))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfInt);
     }









     /**
      * @deprecated

     public static int[] getLengths(long[][] paramArrayOfLong)
     {
       if (!isRectangular(paramArrayOfLong))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfLong);
     }









     /**
      * @deprecated

     public static int[] getLengths(float[][] paramArrayOfFloat)
     {
       if (!isRectangular(paramArrayOfFloat))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfFloat);
     }









     /**
      * @deprecated

     public static int[] getLengths(double[][] paramArrayOfDouble)
     {
       if (!isRectangular(paramArrayOfDouble))
         throw new IllegalArgumentException("Input argument must be a rectangular array.");
       return getArrayLengths(paramArrayOfDouble);
     }








     public static int[] getArrayLengths(Object paramObject)
     {
       if (paramObject == null)
         throw new IllegalArgumentException("Input argument must be non-null.");
       int i = getDimensions(paramObject);
       int[] arrayOfInt = new int[i];
       arrayOfInt[0] = Array.getLength(paramObject);
       for (int j = 1; j < i; j++)
         arrayOfInt[j] = getMaxLength(paramObject, j - 1);
       return arrayOfInt;
     }












     public static int getDimensions(Object paramObject)
     {
       if (paramObject == null) {
         throw new IllegalArgumentException("Input argument must be non-null.");
       }
       return StringUtils.countMatches(paramObject.getClass().getName(), "[");
     }








     public static int getMaxLength(Object paramObject, int paramInt)
     {
       assert (paramObject.getClass().isArray());
       int i = 0;
       for (int j = 0; j < Array.getLength(paramObject); j++)
       {
         Object localObject = Array.get(paramObject, j);
         assert (localObject.getClass().isArray());
         if (paramInt == 0) {
           i = Math.max(i, Array.getLength(localObject));
         } else
           i = Math.max(i, getMaxLength(localObject, paramInt - 1));
       }
       return i;
     }







     public static boolean areContentsEqual(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
     {
       if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null)) {
         return (paramArrayOfInt1 == null) && (paramArrayOfInt2 == null);
       }
       if (paramArrayOfInt1.length != paramArrayOfInt2.length) {
         return false;
       }
       for (int i = 0; i < paramArrayOfInt1.length; i++)
       {
         if (paramArrayOfInt1[i] != paramArrayOfInt2[i])
           return false;
       }
       return true;
     }








     public static boolean areContentsEqual(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
     {
       if ((paramArrayOfObject1 == null) || (paramArrayOfObject2 == null)) {
         return (paramArrayOfObject1 == null) && (paramArrayOfObject2 == null);
       }
       if (paramArrayOfObject1.length != paramArrayOfObject2.length) {
         return false;
       }
       for (int i = 0; i < paramArrayOfObject1.length; i++)
       {
         if ((paramArrayOfObject1[i] == null) || (paramArrayOfObject2[i] == null))
         {
           if ((paramArrayOfObject1[i] != null) || (paramArrayOfObject2[i] != null)) {
             return false;
           }
         } else if (!paramArrayOfObject1[i].equals(paramArrayOfObject2[i]))
           return false;
       }
       return true;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\ArrayUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */