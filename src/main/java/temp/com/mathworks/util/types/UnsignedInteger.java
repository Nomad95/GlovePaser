   package temp.com.mathworks.util.types;





   public class UnsignedInteger
     extends UnsignedNumber
   {
     private final int fValue;




     private static final long MASK = 4294967295L;




     public static final int MIN_VALUE = 0;



     public static final long MAX_VALUE = 4294967295L;



     public static final Class<Integer> TYPE = Integer.TYPE;

     public UnsignedInteger(byte paramByte) {
       this.fValue = (paramByte & 0xFF);
     }

     public UnsignedInteger(short paramShort) { this.fValue = (paramShort & 0xFFFF); }

     public UnsignedInteger(int paramInt) {
       this.fValue = paramInt;
     }

     public UnsignedInteger(long paramLong) { this.fValue = ((int)paramLong); }


     public double doubleValue()
     {
       return this.fValue & 0xFFFFFFFF;
     }

     public float floatValue()
     {
       return (float)(this.fValue & 0xFFFFFFFF);
     }

     public int intValue()
     {
       return this.fValue;
     }

     public long longValue()
     {
       return this.fValue & 0xFFFFFFFF;
     }

     public static UnsignedInteger valueOf(int paramInt) {
       return new UnsignedInteger(paramInt);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\types\UnsignedInteger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */