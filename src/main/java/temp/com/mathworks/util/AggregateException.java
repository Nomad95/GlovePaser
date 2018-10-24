   package temp.com.mathworks.util;

   import java.io.PrintWriter;
   import java.io.StringWriter;









   public class AggregateException
     extends RuntimeException
   {
     private final Throwable[] fCauses;

     public AggregateException()
     {
       this("");
     }

     public AggregateException(String paramString) {
       this(paramString, new Throwable[0]);
     }

     public AggregateException(Throwable paramThrowable) {
       this("", paramThrowable);
     }

     public AggregateException(String paramString, Throwable paramThrowable) {
       this(paramString, new Throwable[] { paramThrowable });
     }

     public AggregateException(String paramString, Throwable[] paramArrayOfThrowable) {
       super(paramString);
       this.fCauses = paramArrayOfThrowable;
     }

     public Throwable[] getCauses() {
       return this.fCauses;
     }

     public String getMessage()
     {
       String str = super.getMessage();
       StringBuffer localStringBuffer = new StringBuffer(str);
       localStringBuffer.append(":\n");
       for (Throwable localThrowable : this.fCauses) {
         localStringBuffer.append("caused by:\n");
         localStringBuffer.append(getThrowableText(localThrowable));
       }
       return localStringBuffer.toString();
     }

     public static String getThrowableText(Throwable paramThrowable) {
       StringWriter localStringWriter = new StringWriter();
       PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
       paramThrowable.printStackTrace(localPrintWriter);
       return localStringWriter.getBuffer().toString();
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\AggregateException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */