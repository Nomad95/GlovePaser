   package temp.com.mathworks.mvm.helpers;

   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.util.PlatformInfo;
   import java.io.ByteArrayOutputStream;
   import java.io.PrintStream;
   import java.io.UnsupportedEncodingException;






























   public final class MatlabPrintStreamManager
   {
     private static MatlabPrintStreamManager sInstance = null;


     private final PrintStream fOriginalSystemOut = System.out;


     private final PrintStream fOriginalSystemErr = System.err;





     private static String encoding = "UTF-8";




     public static synchronized MatlabPrintStreamManager getInstance()
     {
       if (null == sInstance) {
         sInstance = new MatlabPrintStreamManager();
       }
       return sInstance;
     }

     public static PrintStream createMatlabOutputStream()
     {
       try {
         return new PrintStream(new MatlabOutStream(encoding), true, encoding);
       }
       catch (UnsupportedEncodingException localUnsupportedEncodingException) {
         if (!$assertionsDisabled) throw new AssertionError();
       }
       return new PrintStream(new MatlabOutStream(), true);
     }

     public static PrintStream createMatlabErrorStream()
     {
       try
       {
         return new PrintStream(new MatlabErrStream(encoding), true, encoding);
       }
       catch (UnsupportedEncodingException localUnsupportedEncodingException) {
         if (!$assertionsDisabled) throw new AssertionError();
       }
       return new PrintStream(new MatlabErrStream(), true);
     }










     private void installPrintStreams()
     {
       System.setOut(createMatlabOutputStream());
       System.setErr(createMatlabErrorStream());
     }



     private void restorePrintStreams()
     {
       System.setOut(getOriginalSystemOut());
       System.setErr(getOriginalSystemErr());
     }




     public PrintStream getOriginalSystemOut()
     {
       return this.fOriginalSystemOut;
     }




     public PrintStream getOriginalSystemErr()
     {
       return this.fOriginalSystemErr;
     }










     private static abstract class MatlabStream
       extends ByteArrayOutputStream
     {
       private String encoding = "";

       MatlabStream(String paramString)
       {
         super();
         this.encoding = paramString;
       }



       MatlabStream()
       {
         super();
       }






       public final void close()
       {
         flush();
       }








       abstract void matlabFlush(String paramString);







       public final void flush()
       {
         synchronized (this)
         {
           if (size() != 0)
           {
             String str;
             try
             {
               if (this.encoding.length() > 0)
               {
                 str = toString(this.encoding);

               }
               else
               {

                 if (!$assertionsDisabled) throw new AssertionError();
                 str = toString();
               }
             }
             catch (UnsupportedEncodingException localUnsupportedEncodingException)
             {
               if (!$assertionsDisabled) throw new AssertionError();
               str = toString();
             }
             reset();
             matlabFlush(str);
           }
         }
       }















       public final synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
       {
         int i = paramInt1;
         int j = paramInt2;
         if (PlatformInfo.isWindows())
         {
           if (paramInt2 == 2)
           {
             if ((paramArrayOfByte[i] == 13) && (paramArrayOfByte[(i + 1)] == 10))
             {

               i++;
               j--;
             }
           }
         }
         super.write(paramArrayOfByte, i, j);
       }
     }

     private static final class MatlabOutStream extends MatlabStream
     {
       MatlabOutStream(String paramString)
       {
         super();
       }





       MatlabOutStream() {}





       void matlabFlush(String paramString)
       {
         MvmImpl.loadLibrary();
         nativeWrite(paramString);
       }

       private native void nativeWrite(String paramString);
     }

     private static final class MatlabErrStream extends MatlabStream
     {
       MatlabErrStream(String paramString)
       {
         super();
       }






       MatlabErrStream() {}






       private static boolean stackTraceContainsAccessibilityMethod()
       {
         if (PlatformInfo.isMacintosh())
         {
           StackTraceElement[] arrayOfStackTraceElement1 = new Throwable().getStackTrace();

           for (StackTraceElement localStackTraceElement : arrayOfStackTraceElement1) {
             String str = localStackTraceElement.toString();

             if (str.contains("apple.awt.CAccessible.getCAccessible")) {
               return true;
             }
           }
         }
         return false;
       }





       void matlabFlush(String paramString)
       {
         boolean bool = stackTraceContainsAccessibilityMethod();

         if (!bool)
         {
           MvmImpl.loadLibrary();
           nativeWrite(paramString);
         }
       }

       private native void nativeWrite(String paramString);
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\helpers\MatlabPrintStreamManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */