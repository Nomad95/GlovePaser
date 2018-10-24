   package temp.com.mathworks.mvm.exec;

   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.util.ShutdownRuntimeException;
   import java.io.Writer;
   import org.jetbrains.annotations.Nullable;







   public class MatlabEvalRequest
     extends MatlabRequest<Object>
   {
     protected boolean fExternal = false;
     protected boolean fSuppressDebugControlOutput = false;









     public MatlabEvalRequest(String paramString)
     {
       this(paramString, null, null);
     }






     public MatlabEvalRequest(String paramString, Writer paramWriter)
     {
       this(paramString, paramWriter, null);
     }

     public MatlabEvalRequest(String paramString, @Nullable Writer paramWriter1, @Nullable Writer paramWriter2)
     {
       super(paramString, paramWriter1, paramWriter2);
     }




     public String getCommand()
     {
       return this.fCommand;
     }











     public void setExternal(boolean paramBoolean)
     {
       this.fExternal = paramBoolean;
     }










     public void setSuppressDebugControlOutput(boolean paramBoolean)
     {
       this.fSuppressDebugControlOutput = paramBoolean;
     }





     protected MatlabIIP createIIP()
       throws ShutdownRuntimeException
     {
       return nativeCreateEvalIIP(this.fCommand, this.fOutWriter, this.fErrWriter, this.fDisableBreakpoints, this.fExternal, this.fSuppressDebugControlOutput);
     }





     private native MatlabIIP nativeCreateEvalIIP(String paramString, Writer paramWriter1, Writer paramWriter2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);




     protected FutureEvalResult createFutureResult(NativeFutureResult paramNativeFutureResult)
     {
       return new FutureEvalResult(paramNativeFutureResult);
     }

     static {}
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MatlabEvalRequest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */