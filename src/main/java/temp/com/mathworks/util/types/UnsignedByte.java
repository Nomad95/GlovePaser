   package temp.com.mathworks.util.types;





   public class UnsignedByte
     extends UnsignedNumber
   {
     private final byte fValue;




     private static final short MASK = 255;




     public static final byte MIN_VALUE = 0;



     public static final short MAX_VALUE = 255;



     public static final Class<Byte> TYPE = Byte.TYPE;

     public UnsignedByte(byte paramByte) {
       this.fValue = paramByte;
     }

     public UnsignedByte(short paramShort) { this.fValue = ((byte)paramShort); }

     public UnsignedByte(int paramInt) {
       this.fValue = ((byte)paramInt);
     }

     public UnsignedByte(long paramLong) { this.fValue = ((byte)(int)paramLong); }


     public double doubleValue()
     {
       return this.fValue & 0xFF;
     }

     public float floatValue()
     {
       return this.fValue & 0xFF;
     }

     public int intValue()
     {
       return this.fValue & 0xFF;
     }

     public long longValue()
     {
       return this.fValue & 0xFF;
     }

     public static UnsignedByte valueOf(byte paramByte) {
       return new UnsignedByte(paramByte);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\types\UnsignedByte.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */