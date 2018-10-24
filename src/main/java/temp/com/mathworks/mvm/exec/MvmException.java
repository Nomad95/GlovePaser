   package temp.com.mathworks.mvm.exec;

   import java.io.PrintStream;
   import java.io.PrintWriter;
   import java.util.Arrays;
   import org.jetbrains.annotations.Nullable;










   public class MvmException
     extends Exception
   {
     private static final MatlabStackTraceElement[] EMPTY = new MatlabStackTraceElement[0];


     private final MatlabStackTraceElement[] fElements;


     private final String fID;

     private final String fFunctionName;

     private static final long serialVersionUID = 1L;


     MvmException(String paramString1, String paramString2, MatlabStackTraceElement[] paramArrayOfMatlabStackTraceElement, @Nullable String paramString3)
     {
       super(paramString1);

       this.fElements = (paramArrayOfMatlabStackTraceElement == null ? EMPTY : paramArrayOfMatlabStackTraceElement);
       this.fID = paramString2;
       this.fFunctionName = paramString3;
     }






     MvmException(String paramString1, String paramString2, MatlabStackTraceElement[] paramArrayOfMatlabStackTraceElement)
     {
       this(paramString1, paramString2, paramArrayOfMatlabStackTraceElement, null);
     }




     private MvmException(String paramString)
     {
       this(paramString, "dummy:id", new MatlabStackTraceElement[] { new MatlabStackTraceElement("foo", "bar", 5) }, "dummy");
     }













     public StackTraceElement[] getStackTrace()
     {
       StackTraceElement[] arrayOfStackTraceElement = new StackTraceElement[this.fElements.length];
       int i = 0;
       for (MatlabStackTraceElement localMatlabStackTraceElement : this.fElements)
       {

         int m = localMatlabStackTraceElement.fFuncName.indexOf('.');
         String str1 = localMatlabStackTraceElement.fFuncName.substring(0, m < 0 ? 0 : m);

         String str2 = localMatlabStackTraceElement.fFuncName.substring(m + 1);
         arrayOfStackTraceElement[(i++)] = new StackTraceElement(str1, str2, localMatlabStackTraceElement.fFileName, localMatlabStackTraceElement.fLineNum);
       }
       return arrayOfStackTraceElement;
     }

     private MatlabStackTraceElement[] getMatlabStackTrace()
     {
       return this.fElements;
     }




     @Nullable
     public String getFunctionName()
     {
       return this.fFunctionName;
     }




     public void printStackTrace(PrintStream paramPrintStream)
     {
       synchronized (paramPrintStream) {
         paramPrintStream.println(this);
         for (MatlabStackTraceElement localMatlabStackTraceElement : this.fElements) {
           paramPrintStream.println(localMatlabStackTraceElement);
         }
       }
     }




     public void printStackTrace(PrintWriter paramPrintWriter)
     {
       synchronized (paramPrintWriter) {
         paramPrintWriter.println(this);
         for (MatlabStackTraceElement localMatlabStackTraceElement : this.fElements) {
           paramPrintWriter.println(localMatlabStackTraceElement);
         }
       }
     }


     public String getID()
     {
       return this.fID;
     }

     public int hashCode() {
       return (this.fID != null ? this.fID.hashCode() : 0) + (getMessage() != null ? 0 : getMessage().hashCode()) + Arrays.hashCode(this.fElements);
     }






     public boolean equals(Object paramObject)
     {
       if (paramObject == null) return false;
       if (this == paramObject) return true;
       if (!(paramObject instanceof MvmException)) return false;
       MvmException localMvmException = (MvmException)paramObject;
       return (equ(this.fID, localMvmException.getID())) && (equ(getMessage(), localMvmException.getMessage())) && (Arrays.equals(this.fElements, localMvmException.getMatlabStackTrace()));
     }



     private static boolean equ(Object paramObject1, Object paramObject2)
     {
       return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
     }
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MvmException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */