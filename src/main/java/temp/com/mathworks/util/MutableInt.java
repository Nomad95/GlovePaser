   package temp.com.mathworks.util;








   public class MutableInt
   {
     private int fVal;







     public MutableInt()
     {
       this(0);
     }

     public MutableInt(int paramInt) {
       this.fVal = paramInt;
     }

     public MutableInt setValue(int paramInt)
     {
       this.fVal = paramInt;
       return this;
     }

     public int getValue() {
       return this.fVal;
     }

     public boolean equals(Object paramObject) {
       return ((paramObject instanceof MutableInt)) && (((MutableInt)paramObject).getValue() == this.fVal);
     }

     public int hashCode() {
       return this.fVal;
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\MutableInt.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */