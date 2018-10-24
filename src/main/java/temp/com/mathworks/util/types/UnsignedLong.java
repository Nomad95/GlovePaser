   package temp.com.mathworks.util.types;

   import java.math.BigInteger;



















   public class UnsignedLong
     extends UnsignedNumber
   {
     private final long fValue;
     private static final long MASK = -1L;
     public static final long MIN_VALUE = 0L;
     public static final long MAX_VALUE = -1L;
     public static final Class<Long> TYPE = Long.TYPE;

     public UnsignedLong(byte paramByte) {
       this.fValue = (paramByte & 0xFF);
     }

     public UnsignedLong(short paramShort) { this.fValue = (paramShort & 0xFFFF); }

     public UnsignedLong(int paramInt) {
       this.fValue = (paramInt & 0xFFFFFFFF);
     }

     public UnsignedLong(long paramLong) { this.fValue = paramLong; }






     public double doubleValue()
     {
       if (this.fValue >= 0L) return this.fValue;
       return 9.223372036854776E18D + (this.fValue & 0x7FFFFFFFFFFFFFFF);
     }

     public float floatValue()
     {
       if (this.fValue >= 0L) return (float)this.fValue;
       return 9.223372E18F + (float)(this.fValue & 0x7FFFFFFFFFFFFFFF);
     }

     public int intValue()
     {
       return (int)this.fValue;
     }

     public long longValue()
     {
       return this.fValue;
     }

     public BigInteger bigValue()
     {
       if (this.fValue >= 0L) { return BigInteger.valueOf(this.fValue);
       }

       byte[] arrayOfByte = new byte[8];
       for (int i = 0; i < 8; i++)
         arrayOfByte[i] = ((byte)(int)(this.fValue >> 56 - 8 * i));
       return new BigInteger(1, arrayOfByte);
     }

     public static UnsignedLong valueOf(long paramLong)
     {
       return new UnsignedLong(paramLong);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\types\UnsignedLong.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */