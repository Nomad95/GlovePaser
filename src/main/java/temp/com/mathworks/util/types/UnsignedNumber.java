   package temp.com.mathworks.util.types;


























































   public abstract class UnsignedNumber
     extends Number
     implements Comparable<UnsignedNumber>
   {
     public int compareTo(UnsignedNumber paramUnsignedNumber)
     {
       long l1 = longValue();
       long l2 = paramUnsignedNumber.longValue();
       if (l1 == l2) { return 0;
       }


       int i = l1 > l2 ? 1 : -1;
       return (l1 > 0L ? 1 : 0) == (l2 > 0L ? 1 : 0) ? i : -i;
     }



     public boolean equals(Object paramObject)
     {
       return ((paramObject instanceof UnsignedNumber)) && (longValue() == ((Number)paramObject).longValue());
     }

     public int hashCode()
     {
       long l = longValue();
       return (int)(l ^ l >>> 32);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\types\UnsignedNumber.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */