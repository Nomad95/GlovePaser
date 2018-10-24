package temp.com.mathworks.mvm;

import com.mathworks.mvm.eventmgr.EventMgr;
import com.mathworks.mvm.exec.FutureEvalResult;
import com.mathworks.mvm.exec.FutureFevalResult;
import com.mathworks.mvm.exec.MatlabExecutor;
import java.io.Writer;
import org.jetbrains.annotations.Nullable;

public abstract interface MVM
{
  public abstract long getHandle();
  
  public abstract <V> FutureFevalResult<V> feval(String paramString, Object... paramVarArgs);
  
  public abstract FutureEvalResult eval(String paramString, @Nullable Writer paramWriter1,
          @Nullable Writer paramWriter2);
  
  public abstract FutureEvalResult eval(String paramString);
  
  public abstract EventMgr getEventMgr();
  
  public abstract MatlabExecutor getExecutor();
  
  public abstract boolean isTerminated();
  
  public abstract boolean breakInDebugger();
  
  public abstract void terminate()
    throws IllegalStateException;
}


/* Location:              D:\Materialy\dmytruk\AnalizaDanych.jar!\com\mathworks\mvm\MVM.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */