   package temp.com.mathworks.util;

   import java.io.PrintWriter;
   import org.apache.commons.lang.StringUtils;
   import org.apache.commons.lang.text.StrBuilder;



















   public final class DebugUtils
   {
     private static volatile int DEFAULT_STACK_TRACE_DEPTH;
     private static final PrintWriter out;
     private static final PrintWriter err;
     private static final boolean areAssertionsEnabled;

     static
     {
       DEFAULT_STACK_TRACE_DEPTH = 1;










       out = new PrintWriter(System.out, true);
       err = new PrintWriter(System.err, true);

       boolean bool = false;
       assert ((bool = 1) != 0);
       areAssertionsEnabled = bool;
     }













     public static void setDefaultStackTraceDepth(int paramInt)
     {
       DEFAULT_STACK_TRACE_DEPTH = paramInt;
     }





     public static boolean areAssertionsEnabled()
     {
       return areAssertionsEnabled;
     }











     public static boolean warning(String paramString)
     {
       if (paramString == null) {
         return true;
       }
       outputStackTrace(2, paramString, "Warning", DEFAULT_STACK_TRACE_DEPTH, err);

       return true;
     }











     public static boolean warning(String paramString, int paramInt)
     {
       if (paramString == null) {
         return true;
       }
       outputStackTrace(2, paramString, "Warning", paramInt, err);

       return true;
     }









     public static boolean trace(Object... paramVarArgs)
     {
       if (paramVarArgs == null)
       {
         outputStackTrace(2, "null", "Trace", DEFAULT_STACK_TRACE_DEPTH, out);
         return true;
       }

       if (paramVarArgs.length == 0)
       {
         outputStackTrace(2, "", "Trace", DEFAULT_STACK_TRACE_DEPTH, out);
         return true;
       }

       StrBuilder localStrBuilder = new StrBuilder();
       localStrBuilder.setNullText("null");
       for (int i = 0; i < paramVarArgs.length; i++)
       {
         localStrBuilder.append(paramVarArgs[i]);
         if (i != paramVarArgs.length - 1) {
           localStrBuilder.append(",").appendNewLine();
         }
       }
       outputStackTrace(2, localStrBuilder.toString(), "Trace", DEFAULT_STACK_TRACE_DEPTH, out);

       return true;
     }






     public static String argStr(Object... paramVarArgs)
     {
       if (paramVarArgs == null) {
         return "null";
       }
       StrBuilder localStrBuilder = new StrBuilder();
       localStrBuilder.setNullText("null");
       for (int i = 0; i < paramVarArgs.length; i++)
       {
         localStrBuilder.append(paramVarArgs[i]);
         if (i != paramVarArgs.length - 1) {
           localStrBuilder.append(",");
         }
       }
       return localStrBuilder.toString();
     }













     public static boolean outputException(Throwable paramThrowable)
     {
       if (paramThrowable == null) {
         return true;
       }
       outputStackTrace(2, "", "Handled exception", 1, err);
       paramThrowable.printStackTrace(err);

       return true;
     }






     public static boolean dumpStack()
     {
       outputStackTrace(2, "", "Stack dump", Integer.MAX_VALUE, out);
       return true;
     }







     public static boolean dumpStack(int paramInt)
     {
       outputStackTrace(2, "", "Stack dump", paramInt, out);
       return true;
     }

     private static void outputStackTrace(int paramInt1, String paramString1, String paramString2, int paramInt2, PrintWriter paramPrintWriter)
     {
       assert (paramPrintWriter != null);
       assert (paramInt1 >= 0);





       Throwable localThrowable = new Throwable();
       localThrowable.fillInStackTrace();
       StackTraceElement[] arrayOfStackTraceElement = localThrowable.getStackTrace();

       StrBuilder localStrBuilder = new StrBuilder();
       localStrBuilder.append(paramString2).append(StringUtils.isNotBlank(paramString1) ? ": " + paramString1 + " " : " ");


       if (paramInt2 <= 0)
       {
         paramPrintWriter.println(localStrBuilder.toString());
         return;
       }

       int i = 0;
       if (paramInt2 == Integer.MAX_VALUE) {
         i = arrayOfStackTraceElement.length;
       } else {
         i = Math.min(paramInt2 + paramInt1, arrayOfStackTraceElement.length);
       }

       if (i - paramInt1 > 0) {
         localStrBuilder.appendNewLine();
       }
       for (int j = paramInt1; j < i; j++)
       {

         localStrBuilder.append("\tat ").append(arrayOfStackTraceElement[j].toString());
         if (j != i - 1) {
           localStrBuilder.appendNewLine();
         }
       }
       paramPrintWriter.println(localStrBuilder.toString());
     }


     public static void main(String[] paramArrayOfString)
     {
       assert (warning("some message"));

       int i = 1;
       String str = "string";
       Object localObject = new Object();
       if (!$assertionsDisabled) if (!trace(new Object[] { Integer.valueOf(i), str, localObject, null })) { throw new AssertionError();
         }
       IllegalArgumentException localIllegalArgumentException = new IllegalArgumentException("test exception");
       assert (outputException(localIllegalArgumentException));

       assert (dumpStack());
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\util\DebugUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */