   package temp.com.mathworks.util;


   /**
    * @deprecated

   public class IntBuffer
   {
     public static final int INCREMENT_MODE = 0;

     public static final int MODULO_INCREMENT_MODE = 1;

     public static final int DOUBLE_MODE = 2;

     public static final int DOUBLING_MODE = 3;

     public static final int QUAD_MODE = 4;

     private int[] fBuf;

     private int fLength;

     private int fIncrement;

     private int fGrowthFactor;

     private int fGrowthMode;

     private static final int NULL_VALUE = 0;

     private static final int[] EMPTY_ARRAY = new int[0];
     private final int[] fSingletonArray = new int[1];

     public IntBuffer()
     {
       this(16, 256);
     }

     public IntBuffer(int paramInt1, int paramInt2)
     {
       if (paramInt1 < 0) {
         throw new IllegalArgumentException("InitialCapacity (" + paramInt1 + ") must be > 0");
       }
       if (paramInt2 <= 0) {
         throw new IllegalArgumentException("Increment (" + paramInt2 + ") must be > 0");
       }
       this.fBuf = new int[paramInt1];
       this.fIncrement = paramInt2;
       this.fLength = 0;
     }

     public int length()
     {
       return this.fLength;
     }

     public void setSize(int paramInt)
     {
       setSize(paramInt, true);
     }



     public void setSize(int paramInt, boolean paramBoolean)
     {
       if (paramInt < 0) {
         throw new IllegalArgumentException("newSize (" + paramInt + ") must be >= 0");
       }
       if (paramInt > this.fLength) {
         ensureCapacity(paramInt);
       } else if (paramBoolean)
       {
         for (int i = paramInt; i < this.fBuf.length; i++) {
           this.fBuf[i] = 0;
         }
       }
       this.fLength = paramInt;
     }




     public void ensureCapacity(int paramInt)
     {
       if (paramInt > this.fBuf.length) {
         int i;
         if (this.fGrowthFactor > paramInt) {
           i = this.fGrowthFactor;
         }
         else {
           switch (this.fGrowthMode)
           {
           case 0:
             i = this.fBuf.length + this.fIncrement;
             break;

           case 1:
             i = (paramInt + this.fIncrement - 1) / this.fIncrement * this.fIncrement;
             break;

           case 2:
             i = this.fBuf.length << 1;
             if (i > this.fIncrement) {
               i = (paramInt + this.fIncrement - 1) / this.fIncrement * this.fIncrement;
             }
             break;
           case 3:
             i = this.fBuf.length << 1;
           case 4: default:  while (paramInt > i) {
               i <<= 1; continue;



               i = this.fBuf.length << 2;
               if (i > this.fIncrement) {
                 i = (paramInt + this.fIncrement - 1) / this.fIncrement * this.fIncrement; break;



                 i = 0;
               }
             }
           }

         }



         if (paramInt > i) {
           i = paramInt + this.fIncrement;
         }
         int[] arrayOfInt = this.fBuf;
         this.fBuf = new int[i];
         System.arraycopy(arrayOfInt, 0, this.fBuf, 0, arrayOfInt.length);
       }
     }

     public void setCapacityIncrement(int paramInt)
     {
       if (paramInt > 0) {
         this.fIncrement = paramInt;
       }
     }

     public void setGrowthFactor(int paramInt) {
       if (paramInt >= 0) {
         this.fGrowthFactor = paramInt;
       }
     }

     public void setGrowthMode(int paramInt) {
       if ((paramInt >= 0) && (paramInt <= 4)) {
         this.fGrowthMode = paramInt;
       }
     }

     public void append(int paramInt) {
       insert(this.fLength, paramInt);
     }

     public void append(int[] paramArrayOfInt)
     {
       insert(this.fLength, paramArrayOfInt);
     }

     public void append(int[] paramArrayOfInt, int paramInt)
     {
       replace(this.fLength, this.fLength, paramArrayOfInt, paramInt);
     }

     public synchronized void insert(int paramInt1, int paramInt2)
     {
       this.fSingletonArray[0] = paramInt2;
       replace(paramInt1, paramInt1, this.fSingletonArray);
       this.fSingletonArray[0] = 0;
     }

     public void insert(int paramInt, int[] paramArrayOfInt)
     {
       replace(paramInt, paramInt, paramArrayOfInt);
     }



     public void insertSpace(int paramInt1, int paramInt2)
     {
       ensureCapacity(this.fLength + paramInt2);


       System.arraycopy(this.fBuf, paramInt1, this.fBuf, paramInt1 + paramInt2, this.fLength - paramInt1);


       for (int i = 0; i < paramInt2; i++) {
         this.fBuf[(paramInt1 + i)] = 0;
       }
       this.fLength += paramInt2;
     }










     public void delete(int paramInt1, int paramInt2)
     {
       replace(paramInt1, paramInt2, EMPTY_ARRAY);
     }

     public void replace(int paramInt1, int paramInt2, int paramInt3)
     {
       this.fSingletonArray[0] = paramInt3;
       replace(paramInt1, paramInt2, this.fSingletonArray);
       this.fSingletonArray[0] = 0;
     }

     public void replace(int paramInt1, int paramInt2, int[] paramArrayOfInt)
     {
       replace(paramInt1, paramInt2, paramArrayOfInt, paramArrayOfInt.length);
     }




     private void replace(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
     {
       checkRange(paramInt1, paramInt2);

       if (paramArrayOfInt == null) {
         throw new IllegalArgumentException();
       }
       int i = paramInt3 - (paramInt2 - paramInt1);


       ensureCapacity(this.fLength + i);


       System.arraycopy(this.fBuf, paramInt2, this.fBuf, paramInt2 + i, this.fLength - paramInt2);


       System.arraycopy(paramArrayOfInt, 0, this.fBuf, paramInt1, paramInt3);


       if (i < 0)
       {
         for (int j = this.fLength + i; j < this.fLength; j++) {
           this.fBuf[j] = 0;
         }
       }

       this.fLength += i;
     }

     public int getAt(int paramInt)
     {
       checkIndex(paramInt);
       return this.fBuf[paramInt];
     }

     public void setAt(int paramInt1, int paramInt2)
     {
       checkIndex(paramInt1);
       this.fBuf[paramInt1] = paramInt2;
     }




     public void copyInto(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
     {
       checkRange(paramInt1, paramInt2);
       System.arraycopy(this.fBuf, paramInt1, paramArrayOfInt, paramInt3, paramInt2 - paramInt1);
     }


     public int[] getRawBuf()
     {
       return this.fBuf;
     }





     public int[] toArray()
     {
       int[] arrayOfInt = new int[length()];
       System.arraycopy(this.fBuf, 0, arrayOfInt, 0, arrayOfInt.length);
       return arrayOfInt;
     }



     private void checkRange(int paramInt1, int paramInt2)
     {
       if (Log.LOGGING)
       {
         if (paramInt1 > paramInt2) {
           throw new IllegalArgumentException();
         }
         if (paramInt1 != this.fLength) {
           checkHelper(paramInt1);
         }
         if (paramInt2 != this.fLength) {
           checkHelper(paramInt2);
         }
       }
     }

     private void checkIndex(int paramInt) {
       if (Log.LOGGING) {
         checkHelper(paramInt);
       }
     }

     private void checkHelper(int paramInt) {
       if ((paramInt < 0) || (paramInt >= this.fLength)) {
         throw new IllegalArgumentException("Invalid index (" + paramInt + ") should be in the range [0," + this.fLength + "]");
       }
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\IntBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */