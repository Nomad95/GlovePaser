   package temp.com.mathworks.util.types;





   public class UnsignedShort
     extends UnsignedNumber
   {
     private final short fValue;




     private static final int MASK = 65535;




     public static final short MIN_VALUE = 0;



     public static final int MAX_VALUE = 65535;



     public static final Class<Short> TYPE = Short.TYPE;

     public UnsignedShort(byte paramByte) {
       this.fValue = ((short)(paramByte & 0xFF));
     }

     public UnsignedShort(short paramShort) { this.fValue = paramShort; }

     public UnsignedShort(int paramInt) {
       this.fValue = ((short)paramInt);
     }

     public UnsignedShort(long paramLong) { this.fValue = ((short)(int)paramLong); }


     public double doubleValue()
     {
       return this.fValue & 0xFFFF;
     }

     public float floatValue()
     {
       return this.fValue & 0xFFFF;
     }

     public int intValue()
     {
       return this.fValue & 0xFFFF;
     }

     public long longValue()
     {
       return this.fValue & 0xFFFF;
     }

     public static UnsignedShort valueOf(short paramShort) {
       return new UnsignedShort(paramShort);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\types\UnsignedShort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */