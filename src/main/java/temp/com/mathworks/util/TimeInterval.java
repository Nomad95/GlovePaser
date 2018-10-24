   package temp.com.mathworks.util;

   import java.io.PrintStream;
   import java.util.concurrent.TimeUnit;
   import org.apache.commons.lang.text.StrBuilder;












   public final class TimeInterval
   {
     public static final TimeInterval ZERO_SEC = new TimeInterval(0L, TimeUnit.SECONDS);
     public static final TimeInterval HALF_SEC = new TimeInterval(500L, TimeUnit.MILLISECONDS);
     public static final TimeInterval ONE_SEC = new TimeInterval(1L, TimeUnit.SECONDS);
     public static final TimeInterval TWO_SEC = new TimeInterval(2L, TimeUnit.SECONDS);
     public static final TimeInterval THREE_SEC = new TimeInterval(3L, TimeUnit.SECONDS);



     private final long interval;


     private final TimeUnit unit;



     public TimeInterval(long paramLong, TimeUnit paramTimeUnit)
     {
       if (paramTimeUnit == null) {
         throw new IllegalArgumentException("Argument unit must not be null.");
       }
       this.interval = Math.abs(paramLong);
       this.unit = paramTimeUnit;
     }





     public long getInterval()
     {
       return this.interval;
     }





     public TimeUnit getTimeUnit()
     {
       return this.unit;
     }




     public String toString()
     {
       StrBuilder localStrBuilder = new StrBuilder();
       localStrBuilder.append(this.interval).append(" ").append(this.unit.toString());

       return localStrBuilder.toString();
     }

     public static void main(String[] paramArrayOfString)
     {
       System.out.println(new TimeInterval(5L, TimeUnit.SECONDS));
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\TimeInterval.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */