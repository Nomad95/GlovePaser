   package temp.com.mathworks.util;

   import org.apache.commons.lang.builder.EqualsBuilder;
   import org.apache.commons.lang.builder.HashCodeBuilder;
   import org.apache.commons.lang.text.StrBuilder;



























   public final class Pair<T1, T2>
   {
     private final T1 first;
     private final T2 second;
     private final int hashCode;

     public Pair(T1 paramT1, T2 paramT2)
     {
       this.first = paramT1;
       this.second = paramT2;
       this.hashCode = new HashCodeBuilder(17, 37).append(paramT1).append(paramT2).toHashCode();
     }





     public T1 getFirst()
     {
       return (T1)this.first;
     }





     public T2 getSecond()
     {
       return (T2)this.second;
     }





     public boolean equals(Object paramObject)
     {
       if (paramObject == this) {
         return true;
       }
       if (!(paramObject instanceof Pair)) {
         return false;
       }
       return EqualsBuilder.reflectionEquals(this, paramObject);
     }





     public int hashCode()
     {
       return this.hashCode;
     }

     public String toString()
     {
       StrBuilder localStrBuilder = new StrBuilder();
       localStrBuilder.setNullText("null");
       localStrBuilder.append("(").append(this.first).append(",").append(this.second).append(")");

       return localStrBuilder.toString();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\Pair.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */