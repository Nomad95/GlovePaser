   package temp.com.mathworks.mvm.exec;

   import com.mathworks.mvm.MvmImpl;
   import com.mathworks.util.ShutdownRuntimeException;
   import java.io.Writer;
   import org.jetbrains.annotations.Nullable;































































   public class MatlabFevalRequest<V>
     extends MatlabRequest<V>
   {
     private final Object[] fArgs;
     private final int fNlhs;
     private final boolean fScalar;

     public MatlabFevalRequest(String paramString, Integer paramInteger, Object... paramVarArgs)
     {
       this(paramString, paramInteger, null, null, (paramVarArgs == null) || (paramVarArgs.length == 0) ? null : paramVarArgs);
     }





     public MatlabFevalRequest(String paramString)
     {
       this(paramString, Integer.valueOf(0), new Object[0]);
     }





















     public MatlabFevalRequest(String paramString, Object... paramVarArgs)
     {
       this(true, paramString, 1, null, null, paramVarArgs);
     }



















     public MatlabFevalRequest(String paramString, Integer paramInteger, @Nullable Writer paramWriter1, @Nullable Writer paramWriter2, Object... paramVarArgs)
     {
       this(false, paramString, paramInteger.intValue(), paramWriter1, paramWriter2, paramVarArgs);
     }





     private MatlabFevalRequest(boolean paramBoolean, String paramString, int paramInt, @Nullable Writer paramWriter1, @Nullable Writer paramWriter2, Object... paramVarArgs)
     {
       super(paramString, paramWriter1, paramWriter2);
       this.fNlhs = paramInt;
       this.fArgs = ((paramVarArgs == null) || (paramVarArgs.length == 0) ? null : paramVarArgs);
       this.fScalar = paramBoolean;
     }








     public Object[] getArgs()
     {
       return this.fArgs;
     }





     protected MatlabIIP createIIP()
       throws ShutdownRuntimeException
     {
       return nativeCreateFevalIIP(this.fCommand, this.fOutWriter, this.fErrWriter, this.fDisableBreakpoints, this.fNlhs, this.fArgs);
     }




     private native MatlabIIP nativeCreateFevalIIP(String paramString, Writer paramWriter1, Writer paramWriter2, boolean paramBoolean, int paramInt, Object[] paramArrayOfObject);




     public boolean isScalar()
     {
       return this.fScalar;
     }


     public int getNlhs()
     {
       return this.fNlhs;
     }

     protected FutureFevalResult<V> createFutureResult(NativeFutureResult<V> paramNativeFutureResult)
     {
       return new FutureFevalResult(paramNativeFutureResult, this.fScalar, this.fNlhs);
     }

     static {}
   }


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\exec\MatlabFevalRequest.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */