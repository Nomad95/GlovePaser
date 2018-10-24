   package temp.com.mathworks.util;





   /**
    * @deprecated

   public class CharBuffer
   {
     public static final int INCREMENT_MODE = 0;




     public static final int MODULO_INCREMENT_MODE = 1;




     public static final int DOUBLE_MODE = 2;



     public static final int DOUBLING_MODE = 3;



     public static final int QUAD_MODE = 4;



     private char[] fBuf;



     private int fLength;



     private int fIncrement;



     private int fGrowthFactor;



     private int fGrowthMode;



     private static final char NULL_VALUE = '\000';



     private static final char[] EMPTY_ARRAY = new char[0];
     private final char[] fSingletonArray = new char[1];





     public CharBuffer()
     {
       this(32, 256);
     }









     public CharBuffer(int paramInt1, int paramInt2)
     {
       if (paramInt1 < 0) {
         throw new IllegalArgumentException("InitialCapacity (" + paramInt1 + ") must be > 0");
       }
       if (paramInt2 <= 0) {
         throw new IllegalArgumentException("Increment (" + paramInt2 + ") must be > 0");
       }
       this.fLength = 0;
       this.fIncrement = paramInt2;
       this.fBuf = new char[paramInt1];
     }






     public CharBuffer(String paramString)
     {
       this.fIncrement = 256;
       if (paramString == null) {
         this.fBuf = new char[32];
       }
       else {
         this.fLength = paramString.length();
         this.fBuf = new char[this.fLength];
         paramString.getChars(0, this.fLength, this.fBuf, 0);
       }
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
           this.fBuf[i] = '\000';
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
         char[] arrayOfChar = this.fBuf;
         this.fBuf = new char[i];
         System.arraycopy(arrayOfChar, 0, this.fBuf, 0, arrayOfChar.length);
       }
     }






     public void setCapacityIncrement(int paramInt)
     {
       if (paramInt > 0) {
         this.fIncrement = paramInt;
       }
     }






     public void setGrowthFactor(int paramInt)
     {
       if (paramInt >= 0) {
         this.fGrowthFactor = paramInt;
       }
     }











     public void setGrowthMode(int paramInt)
     {
       if ((paramInt >= 0) && (paramInt <= 4)) {
         this.fGrowthMode = paramInt;
       }
     }






     public void append(char paramChar)
     {
       insert(this.fLength, paramChar);
     }







     public void append(char[] paramArrayOfChar)
     {
       insert(this.fLength, paramArrayOfChar);
     }










     public synchronized void insert(int paramInt, char paramChar)
     {
       this.fSingletonArray[0] = paramChar;
       replace(paramInt, paramInt, this.fSingletonArray);
       this.fSingletonArray[0] = '\000';
     }











     public void insert(int paramInt, char[] paramArrayOfChar)
     {
       replace(paramInt, paramInt, paramArrayOfChar, paramArrayOfChar.length);
     }












     public void insert(int paramInt1, char[] paramArrayOfChar, int paramInt2)
     {
       replace(paramInt1, paramInt1, paramArrayOfChar, paramInt2);
     }










     public void delete(int paramInt1, int paramInt2)
     {
       replace(paramInt1, paramInt2, EMPTY_ARRAY);
     }














     public void replace(int paramInt1, int paramInt2, char paramChar)
     {
       this.fSingletonArray[0] = paramChar;
       replace(paramInt1, paramInt2, this.fSingletonArray);
       this.fSingletonArray[0] = '\000';
     }














     public void replace(int paramInt1, int paramInt2, char[] paramArrayOfChar)
     {
       replace(paramInt1, paramInt2, paramArrayOfChar, paramArrayOfChar.length);
     }















     public void replace(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
     {
       replace(paramInt1, paramInt2, paramArrayOfChar, 0, paramInt3);
     }



















     public void replace(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int paramInt4)
     {
       checkRange(paramInt1, paramInt2);

       if (paramArrayOfChar == null) {
         throw new IllegalArgumentException();
       }
       int i = paramInt4 - (paramInt2 - paramInt1);


       ensureCapacity(this.fLength + i);


       System.arraycopy(this.fBuf, paramInt2, this.fBuf, paramInt2 + i, this.fLength - paramInt2);


       System.arraycopy(paramArrayOfChar, paramInt3, this.fBuf, paramInt1, paramInt4);


       if (i < 0)
       {
         for (int j = this.fLength + i; j < this.fLength; j++) {
           this.fBuf[j] = '\000';
         }
       }

       this.fLength += i;
     }







     public char getAt(int paramInt)
     {
       checkIndex(paramInt);
       return this.fBuf[paramInt];
     }









     public void setAt(int paramInt, char paramChar)
     {
       checkIndex(paramInt);
       this.fBuf[paramInt] = paramChar;
     }










     public char[] getRawBuf()
     {
       return this.fBuf;
     }








     public String toString()
     {
       return toString(0, length());
     }










     public String toString(int paramInt1, int paramInt2)
     {
       checkRange(paramInt1, paramInt2);
       return new String(this.fBuf, paramInt1, paramInt2 - paramInt1);
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


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\CharBuffer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */