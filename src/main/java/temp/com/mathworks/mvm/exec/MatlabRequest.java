   package temp.com.mathworks.mvm.exec;

   import java.io.Writer;









   public abstract class MatlabRequest<V>
   {
     public static final Writer NULL_WRITER = new Writer()
     {
       public void write(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2) {}


       public void flush() {}


       public void close() {}
     };

     protected final Writer fOutWriter;

     protected final Writer fErrWriter;
     protected final String fCommand;

     public void setDisableBreakpoints(boolean paramBoolean)
     {
       this.fDisableBreakpoints = paramBoolean;
     }




     protected boolean fDisableBreakpoints = false;







     protected MatlabRequest(String paramString, Writer paramWriter1, Writer paramWriter2)
     {
       this.fCommand = paramString;
       this.fOutWriter = paramWriter1;
       this.fErrWriter = paramWriter2;
     }

     protected abstract MatlabIIP createIIP();

     protected abstract FutureResult<V> createFutureResult(NativeFutureResult<V> paramNativeFutureResult);
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MatlabRequest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */